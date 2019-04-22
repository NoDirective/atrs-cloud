/*
 * Copyright(c) 2017 NTT Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.co.ntt.atrs.domain.service.c2;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.terasoluna.gfw.common.exception.SystemException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3URI;

import jp.co.ntt.atrs.domain.amazonaws.helper.S3Helper;
import jp.co.ntt.atrs.domain.common.exception.AtrsBusinessException;
import jp.co.ntt.atrs.domain.common.logging.LogMessages;
import jp.co.ntt.atrs.domain.common.util.ImageFileBase64Encoder;
import jp.co.ntt.atrs.domain.model.Member;
import jp.co.ntt.atrs.domain.model.MemberLogin;
import jp.co.ntt.atrs.domain.repository.member.MemberRepository;

/**
 * 会員情報変更を行うService実装クラス。
 * @author NTT 電電花子
 */
@Service
@CacheConfig(cacheNames = "members")
public class MemberUpdateServiceImpl implements MemberUpdateService {

    /**
     * S3バケット。
     */
    @Value("${upload.bucketName}")
    String bucketName;

    /**
     * ファイル一時保存ディレクトリ。
     */
    @Value("${upload.temporaryDirectory}")
    String tmpDirectory;

    /**
     * ファイル保存ディレクトリ。
     */
    @Value("${upload.saveDirectory}")
    String saveDirectory;

    /**
     * 会員情報リポジトリ。
     */
    @Inject
    MemberRepository memberRepository;

    /**
     * パスワードをハッシュ化するためのエンコーダ。
     */
    @Inject
    PasswordEncoder passwordEncoder;

    /**
     * 画像変換ユーティリティ。
     */
    @Inject
    ImageFileBase64Encoder imageFileBase64Encoder;

    /**
     * S3クライアント。
     */
    @Inject
    AmazonS3 s3client;

    /**
     * S3Helper。
     */
    @Inject
    S3Helper s3Helper;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(key = "'member/' + #customerNo")
    public Member findMember(String customerNo) throws IOException {

        Member member = memberRepository.findOne(customerNo);

        // 顔写真を取得する。
        Resource photoFileResource = s3Helper.getResource(bucketName,
                saveDirectory, member.getRegisteredPhotoFileName());
        if (photoFileResource.exists()) {
            // 顔写真が存在する場合は、顔写真データのBase64変換を行う。
            try (InputStream photoFile = photoFileResource.getInputStream()) {
                member.setPhotoBase64(imageFileBase64Encoder.encodeBase64(
                        photoFile, "jpg"));
            }
        }

        return member;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @CacheEvict(key = "'member/' + #member.customerNo")
    public void updateMember(Member member) {

        // ユーザ情報変更を実施し、登録情報の更新を行う。
        int updateMemberCount = memberRepository.update(member);
        if (updateMemberCount != 1) {
            throw new SystemException(LogMessages.E_AR_A0_L9002
                    .getCode(), LogMessages.E_AR_A0_L9002.getMessage(
                            updateMemberCount, 1));
        }

        // パスワードの変更がある場合のみMemberLoginを更新する。
        MemberLogin memberLogin = member.getMemberLogin();
        if (StringUtils.hasLength(memberLogin.getPassword())) {

            // パスワードのハッシュ化
            memberLogin.setPassword(passwordEncoder.encode(member
                    .getMemberLogin().getPassword()));

            // MemberLoginの更新
            int updateMemberLoginCount = memberRepository.updateMemberLogin(
                    member);
            if (updateMemberLoginCount != 1) {
                throw new SystemException(LogMessages.E_AR_A0_L9002
                        .getCode(), LogMessages.E_AR_A0_L9002.getMessage(
                                updateMemberLoginCount, 1));
            }
        }

        // 画像ファイルが選択されている場合のみ画像の更新を行う 。（photoFileNameの有無で判断する）
        if (member.getPhotoFileName() != null) {
            // ファイル保存を行う。
            // 削除対象旧ファイルの検索
            Resource[] oldPhotoResources = s3Helper.fileSearch(bucketName,
                    saveDirectory, member.getCustomerNo() + "*");
            // 新規ファイル保存
            String s3PhotoFileName = member.getCustomerNo() + "_" + UUID
                    .randomUUID().toString() + ".jpg";
            s3Helper.fileCopy(bucketName, tmpDirectory, member
                    .getPhotoFileName(), bucketName, saveDirectory,
                    s3PhotoFileName);

            // 旧ファイルおよび一時ファイルの削除
            List<String> deleteKeyList = new ArrayList<String>();
            for (Resource oldPhotoResource : oldPhotoResources) {
                AmazonS3URI deleteURI = s3Helper.getAmazonS3URI(
                        oldPhotoResource);
                deleteKeyList.add(deleteURI.getKey());
            }
            deleteKeyList.add(tmpDirectory + member.getPhotoFileName());
            s3Helper.multiFileDelete(bucketName, deleteKeyList);

            // 顔写真ファイル名の更新を行う。
            member.setRegisteredPhotoFileName(s3PhotoFileName);
            int updateCount = memberRepository.update(member);
            if (updateCount != 1) {
                throw new SystemException(LogMessages.E_AR_A0_L9002
                        .getCode(), LogMessages.E_AR_A0_L9002.getMessage(
                                updateCount, 1));
            }
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkMemberPassword(String password,
            String customerNo) throws IOException {

        if (StringUtils.hasLength(password)) {
            // DBから正しいパスワードを取得
            Member member = findMember(customerNo);
            String currentPassword = member.getMemberLogin().getPassword();

            // 比較結果が違う場合、業務例外をスローする。
            if (!passwordEncoder.matches(password, currentPassword)) {
                throw new AtrsBusinessException(MemberUpdateErrorCode.E_AR_C2_2001);
            }
        }
    }

}
