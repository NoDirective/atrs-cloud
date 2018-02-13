/*
 * Copyright 2014-2017 NTT Corporation.
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
 *
 */
package jp.co.ntt.atrs.domain.amazonaws.helper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.terasoluna.gfw.common.exception.SystemException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3URI;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;

import jp.co.ntt.atrs.domain.common.constants.AWSConstants;
import jp.co.ntt.atrs.domain.common.logging.LogMessages;

/**
 * AWS S3ヘルパークラス。
 * @author NTT 電電花子
 */
@Component
public class S3Helper {

    /**
     * リソースローダ。
     */
    @Inject
    ResourceLoader resourceLoader;

    /**
     * リソースパタンリゾルバ。
     */
    @Inject
    ResourcePatternResolver resourcePatternResolver;

    /**
     * S3クライアント。
     */
    @Inject
    AmazonS3 s3client;

    /**
     * 単一ファイルのアップロードを行う。
     * @param uploadFile アップロードファイル
     * @param bucketName バケット名
     * @param putDirectory アップロードディレクトリ
     * @param fileName ファイル名
     */
    public void fileUpload(InputStream uploadFile, String bucketName,
            String putDirectory, String fileName) {
        WritableResource resource = getResource(bucketName, putDirectory,
                fileName);
        try (OutputStream out = resource.getOutputStream()) {
            copy(uploadFile, out);
        } catch (IOException e) {
            throw new SystemException(LogMessages.E_AR_A0_L9003.getCode(), LogMessages.E_AR_A0_L9003
                    .getMessage(bucketName), e);
        }
    }

    /**
     * 単一ファイルのダウンロードを行う。
     * @param downloadFile ダウンロードファイル
     * @param bucketName バケット名
     * @param putDirectory ダウンロードディレクトリ
     * @param fileName ファイル名
     * @throws Exception
     */
    public void fileDownload(OutputStream downloadFile, String bucketName,
            String putDirectory, String fileName) {
        Resource resource = getResource(bucketName, putDirectory, fileName);
        try (InputStream in = resource.getInputStream()) {
            copy(in, downloadFile);
        } catch (IOException e) {
            throw new SystemException(LogMessages.E_AR_A0_L9003.getCode(), LogMessages.E_AR_A0_L9003
                    .getMessage(bucketName), e);
        }
    }

    /**
     * 単一ファイルの削除を行う。
     * @param bucketName バケット名
     * @param targetDirectory 削除先ディレクトリ
     * @param fileName ファイル名
     */
    public void fileDelete(String bucketName, String targetDirectory,
            String fileName) {
        s3client.deleteObject(bucketName, targetDirectory + fileName);
    }

    /**
     * 複数ファイルの削除を行う。
     * @param bucketName バケット名
     * @param deleteKeyList 削除対象キーリスト
     */
    public void multiFileDelete(String bucketName, List<String> deleteKeyList) {
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName);
        List<KeyVersion> targetKeys = new ArrayList<KeyVersion>();
        for (String deleteKey : deleteKeyList) {
            targetKeys.add(new KeyVersion(deleteKey));
        }
        deleteObjectsRequest.setKeys(targetKeys);
        s3client.deleteObjects(deleteObjectsRequest);
    }

    /**
     * 単一ファイルのコピーを行う。
     * @param sourceBucketName コピー元バケット名
     * @param sourceDirectory コピー元ディレクトリ
     * @param sourceFileName コピー元ファイル名
     * @param targetBucketName コピー先バケット名
     * @param targetDirectory コピー先ディレクトリ
     * @param targetFileName コピー先ファイル名
     * @throws IOException
     */
    public void fileCopy(String sourceBucketName, String sourceDirectory,
            String sourceFileName, String targetBucketName,
            String targetDirectory, String targetFileName) {
        Resource source = getResource(sourceBucketName, sourceDirectory,
                sourceFileName);
        WritableResource target = getResource(targetBucketName,
                targetDirectory, targetFileName);
        try (InputStream in = source.getInputStream();
                OutputStream out = target.getOutputStream()) {
            copy(in, out);
        } catch (IOException e) {
            throw new SystemException(LogMessages.E_AR_A0_L9003.getCode(), LogMessages.E_AR_A0_L9003
                    .getMessage(sourceBucketName + "," + targetBucketName), e);
        }
    }

    /**
     * AmazonS3URIの取得を行う。
     * @param resource S3リソース
     * @return
     */
    public AmazonS3URI getAmazonS3URI(Resource resource) {
        try {
            return new AmazonS3URI(resource.getURI());
        } catch (IOException e) {
            throw new SystemException(LogMessages.E_AR_A0_L9003.getCode(), LogMessages.E_AR_A0_L9003
                    .getMessage(), e);
        }
    }

    /**
     * ファイルの検索を行う。
     * @param bucketName 検索対象バケット名
     * @param directory 検索対象ディレクトリ
     * @param pattern ファイル名パターン
     * @return
     * @throws IOException
     */
    public Resource[] fileSearch(String bucketName, String directory,
            String pattern) {
        try {
            return resourcePatternResolver
                    .getResources(AWSConstants.S3_PROTOCOL_PREFIX
                            .getConstants()
                            + bucketName + "/" + directory + pattern);
        } catch (IOException e) {
            throw new SystemException(LogMessages.E_AR_A0_L9003.getCode(), LogMessages.E_AR_A0_L9003
                    .getMessage(bucketName), e);
        }
    }

    /**
     * WritableResourceを取得する。
     * @param bucketName バケット名
     * @param putDirectory アップロードディレクトリ
     * @param fileName ファイル名
     * @return WritableResource
     */
    public WritableResource getResource(String bucketName,
            String putDirectory, String fileName) {
        AmazonS3URI tempUri = new AmazonS3URI(AWSConstants.S3_PROTOCOL_PREFIX
                .getConstants()
                + bucketName + "/" + putDirectory + fileName);
        return (WritableResource) resourceLoader.getResource(tempUri.getURI()
                .toString());
    }

    /**
     * Streamのコピーを行う。
     * @param in 入力データ
     * @param out 出力データ
     * @throws IOException
     */
    private void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buff = new byte[1024];
        for (int len = in.read(buff); len > 0; len = in.read(buff)) {
            out.write(buff, 0, len);
        }
    }
}
