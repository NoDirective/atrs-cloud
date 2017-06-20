/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.service.c1;

import java.util.UUID;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.terasoluna.gfw.common.exception.SystemException;

import jp.co.ntt.atrs.domain.amazonaws.helper.S3Helper;
import jp.co.ntt.atrs.domain.common.logging.LogMessages;
import jp.co.ntt.atrs.domain.common.shard.ShardKeyResolver;
import jp.co.ntt.atrs.domain.common.shard.model.ShardingAccount;
import jp.co.ntt.atrs.domain.common.shard.repository.AccountShardKeyRepository;
import jp.co.ntt.atrs.domain.model.Member;
import jp.co.ntt.atrs.domain.model.MemberLogin;
import jp.co.ntt.atrs.domain.repository.member.MemberRepository;

/**
 * 会員情報登録を行うService実装クラス。
 * @author NTT 電電花子
 */
@Service
@Transactional
public class MemberRegisterServiceImpl implements MemberRegisterService {

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
     * シャードアカウントのリポジトリー。
     */
    @Inject
    AccountShardKeyRepository accountShardKeyRepository;

    /**
     * S3Helper。
     */
    @Inject
    S3Helper s3Helper;

    /**
     * シャードキーのリゾルバ。
     */
    @Inject
    ShardKeyResolver shardKeyResolver;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Member register(Member member) {
        Assert.notNull(member);

        MemberLogin memberLogin = member.getMemberLogin();
        Assert.notNull(memberLogin);

        // パスワードをエンコードする。
        String hashedPassword = passwordEncoder.encode(member.getMemberLogin()
                .getPassword());

        memberLogin.setPassword(hashedPassword);
        memberLogin.setLastPassword(hashedPassword);
        memberLogin.setLoginFlg(false);

        // 入力されたユーザ情報を基に、データベースへの登録を行う。
        // MyBatis3の機能(SelectKey)によりmemberにはcustomerNoが格納される。
        int insertMemberCount = memberRepository.insert(member);
        if (insertMemberCount != 1) {
            throw new SystemException(LogMessages.E_AR_A0_L9002.getCode(), LogMessages.E_AR_A0_L9002
                    .getMessage(insertMemberCount, 1));
        }

        int insertMemberLoginCount = memberRepository.insertMemberLogin(member);
        if (insertMemberLoginCount != 1) {
            throw new SystemException(LogMessages.E_AR_A0_L9002.getCode(), LogMessages.E_AR_A0_L9002
                    .getMessage(insertMemberLoginCount, 1));
        }

        // シャードアカウント情報の保存を行う。
        ShardingAccount shardingAccount = new ShardingAccount();
        shardingAccount.setUserId(member.getCustomerNo());
        shardingAccount.setDataSourceKey(shardKeyResolver
                .resolveShardKey(member.getCustomerNo()));
        accountShardKeyRepository.save(shardingAccount);

        // ファイル保存を行う。
        s3Helper.fileCopy(bucketName, tmpDirectory, member.getPhotoFileName(),
                bucketName, saveDirectory, member.getCustomerNo() + "_"
                        + UUID.randomUUID().toString() + ".jpg");

        s3Helper.fileDelete(bucketName, tmpDirectory, member.getPhotoFileName());

        return member;
    }
}
