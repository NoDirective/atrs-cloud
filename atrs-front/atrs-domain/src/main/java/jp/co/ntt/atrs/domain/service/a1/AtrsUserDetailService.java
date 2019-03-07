/*
 * Copyright 2014-2018 NTT Corporation.
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
package jp.co.ntt.atrs.domain.service.a1;

import java.util.Locale;

import javax.inject.Inject;

import jp.co.ntt.atrs.domain.common.logging.LogMessages;
import jp.co.ntt.atrs.domain.model.Member;
import jp.co.ntt.atrs.domain.repository.member.MemberRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ログインユーザ情報サービス。
 * @author NTT 電電太郎
 */
@Service
public class AtrsUserDetailService implements UserDetailsService {

    /**
     * ロガー。
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(
            AtrsUserDetailService.class);

    /**
     * メッセージプロパティ設定。
     */
    @Inject
    MessageSource messageSource;

    /**
     * カード会員情報リポジトリ。
     */
    @Inject
    MemberRepository memberRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(
            String username) throws UsernameNotFoundException {
        Member member = memberRepository.findOneForLogin(username);
        if (member == null) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info(LogMessages.I_AR_A1_L2001.getMessage(username));
            }
            String errorMessage = messageSource.getMessage(
                    AuthLoginErrorCode.E_AR_A1_2001.code(), null, Locale
                            .getDefault());
            throw new UsernameNotFoundException(errorMessage);
        }
        return new AtrsUserDetails(member);
    }
}
