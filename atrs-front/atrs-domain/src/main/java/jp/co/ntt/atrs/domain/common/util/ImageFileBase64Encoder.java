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
package jp.co.ntt.atrs.domain.common.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

/**
 * 画像変換に関するユーティリティクラス。
 * @author NTT 電電太郎
 */
@Component
public class ImageFileBase64Encoder {

    /**
     * 画像データのBase64変換を行う。
     * @param inputStream 画像データ
     * @param fileExtension ファイル拡張子
     * @return 変換後の文字列
     * @throws IOException
     */
    public String encodeBase64(InputStream inputStream, String fileExtension) throws IOException {
        BufferedImage image = ImageIO.read(inputStream);
        image.flush();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Base64.Encoder encoder = Base64.getEncoder();
        try (OutputStream encodedOs = encoder.wrap(baos)) {
            ImageIO.write(image, fileExtension, encodedOs);
        }
        return new String(baos.toByteArray());
    }
}
