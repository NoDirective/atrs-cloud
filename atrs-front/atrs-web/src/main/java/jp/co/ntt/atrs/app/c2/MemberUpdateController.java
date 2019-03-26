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
package jp.co.ntt.atrs.app.c2;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.UUID;

import javax.inject.Inject;
import javax.validation.groups.Default;

import org.apache.commons.io.FilenameUtils;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;

import jp.co.ntt.atrs.app.c0.MemberForm.UploadFileNotRequired;
import jp.co.ntt.atrs.app.c0.MemberForm.UploadFileUncheck;
import jp.co.ntt.atrs.app.c0.MemberHelper;
import jp.co.ntt.atrs.app.common.exception.BadRequestException;
import jp.co.ntt.atrs.domain.amazonaws.helper.S3Helper;
import jp.co.ntt.atrs.domain.common.util.ImageFileBase64Encoder;
import jp.co.ntt.atrs.domain.model.Member;
import jp.co.ntt.atrs.domain.model.MemberLogin;
import jp.co.ntt.atrs.domain.service.c2.MemberUpdateService;

/**
 * 会員情報変更コントローラ。
 * @author NTT 電電花子
 */
@Controller
@RequestMapping("Member/update")
public class MemberUpdateController {

    /**
     * S3バケット。
     */
    @Value("${upload.bucketName}")
    private String bucketName;

    /**
     * ファイル一時保存ディレクトリ。
     */
    @Value("${upload.temporaryDirectory}")
    private String tmpDirectory;

    /**
     * ファイル保存ディレクトリ。
     */
    @Value("${upload.saveDirectory}")
    private String saveDirectory;

    /**
     * 会員情報変更サービス。
     */
    @Inject
    MemberUpdateService memberUpdateService;

    /**
     * 会員情報変更フォームのバリデータ。
     */
    @Inject
    MemberUpdateValidator memberUpdateValidator;

    /**
     * Beanマッパー。
     */
    @Inject
    Mapper beanMapper;

    /**
     * 会員情報Helper。
     */
    @Inject
    MemberHelper memberHelper;

    /**
     * 画像変換ユーティリティ。
     */
    @Inject
    ImageFileBase64Encoder imageFileBase64Encoder;

    /**
     * S3Helper。
     */
    @Inject
    S3Helper s3Helper;

    /**
     * 会員情報変更フォームのバリデータをバインダに追加する。
     * @param binder バインダ
     */
    @InitBinder("memberUpdateForm")
    //
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(memberUpdateValidator);
    }

    /**
     * 会員情報変更フォームを初期化する。
     * @return 会員情報変更フォーム
     */
    @ModelAttribute("memberUpdateForm")
    public MemberUpdateForm setUpForm() {
        MemberUpdateForm memberUpdateForm = new MemberUpdateForm();
        return memberUpdateForm;
    }

    /**
     * ユーザ情報管理画面に遷移し、現在登録されている会員情報を表示する。
     * @param model 出力情報を保持するクラス
     * @param principal ログイン情報を持つオブジェクト
     * @return View論理名
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET, params = "showDetail")
    public String updateShowDetail(Model model,
            Principal principal) throws IOException {

        // ログインユーザ情報からCustomerNoを取得
        String customerNo = principal.getName();

        // DBからデータを取得
        Member member = memberUpdateService.findMember(customerNo);

        model.addAttribute(member);

        return "C2/memberDetail";
    }

    /**
     * ユーザ情報変更画面へ遷移する。
     * @param model 出力情報を保持するクラス
     * @param principal ログイン情報を持つオブジェクト
     * @return View論理名
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET, params = "form")
    public String updateForm(Model model,
            Principal principal) throws IOException {

        // ログインユーザ情報からCustomerNoを取得
        String customerNo = principal.getName();

        // DBからMemberデータを取得
        Member member = memberUpdateService.findMember(customerNo);

        // MemberデータをMemberUpdateformオブジェクトに格納
        MemberUpdateForm memberUpdateForm = new MemberUpdateForm();
        memberUpdateForm.setMemberForm(memberHelper.memberToForm(member));

        model.addAttribute(memberUpdateForm);

        return "C2/memberUpdateForm";
    }

    /**
     * 変更情報確認画面へ遷移する。
     * @param memberUpdateForm 会員情報変更フォーム
     * @param result チェック結果を保持するクラス
     * @param principal ログイン情報を持つオブジェクト
     * @param model 出力情報を保持するクラス
     * @return View論理名
     * @throws IOException
     * @throws IllegalStateException
     */
    @RequestMapping(method = RequestMethod.POST, params = "confirm")
    public String updateConfirm(@Validated({ UploadFileNotRequired.class,
            Default.class }) MemberUpdateForm memberUpdateForm,
            BindingResult result, Principal principal,
            Model model) throws IllegalStateException, IOException {

        if (result.hasErrors()) {
            return updateRedo(memberUpdateForm, model);
        }

        String originalFilename = memberUpdateForm.getMemberForm().getPhoto()
                .getOriginalFilename();
        String fileExtension = FilenameUtils.getExtension(originalFilename);

        // ログインユーザ情報からCustomerNoを取得
        String customerNo = principal.getName();

        // 確認パスワードが登録されているものと同じかチェックを行う。
        try {
            memberUpdateService.checkMemberPassword(memberUpdateForm
                    .getNowPassword(), customerNo);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return updateRedo(memberUpdateForm, model);
        }

        if (!originalFilename.equals("")) {
            // ファイル一時保存
            MultipartFile uploadFile = memberUpdateForm.getMemberForm()
                    .getPhoto();
            String photoFileName = UUID.randomUUID().toString() + FilenameUtils
                    .getName(originalFilename);
            try (InputStream inputStream = uploadFile.getInputStream()) {
                s3Helper.fileUpload(inputStream, bucketName, tmpDirectory,
                        photoFileName);
            }

            // ファイル名をformに設定
            memberUpdateForm.getMemberForm().setPhotoFileName(photoFileName);

            // 画像をエンコードしてFormに設定
            try (InputStream photoFile = uploadFile.getInputStream()) {
                memberUpdateForm.getMemberForm().setPhotoBase64(
                        imageFileBase64Encoder.encodeBase64(photoFile,
                                fileExtension));
            }
        }

        return "C2/memberUpdateConfirm";
    }

    /**
     * ユーザ情報管理画面を再表示する。
     * @return View論理名
     */
    @RequestMapping(method = RequestMethod.GET, params = "backToDetail")
    public String updateBackToDetail() {
        return "redirect:/Member/update?showDetail";

    }

    /**
     * 会員情報の変更を行う。
     * @param memberUpdateForm 会員情報変更フォーム
     * @param model 出力情報を保持するクラス
     * @param redirectAttributes フラッシュスコープ格納用オブジェクト
     * @param result チェック結果を保持するクラス
     * @param principal ログイン情報を持つオブジェクト
     * @return View論理名
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.POST)
    public String update(@Validated({ UploadFileUncheck.class,
            Default.class }) MemberUpdateForm memberUpdateForm,
            BindingResult result, Model model,
            RedirectAttributes redirectAttributes,
            Principal principal) throws IOException {

        if (result.hasErrors()) {
            throw new BadRequestException(result);
        }

        // ログインユーザ情報からCustomerNoを取得
        String customerNo = principal.getName();

        // 確認パスワードが入力されている場合に登録されているものと同じかチェックを行う。
        try {
            memberUpdateService.checkMemberPassword(memberUpdateForm
                    .getNowPassword(), customerNo);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return updateRedo(memberUpdateForm, model);
        }

        // 入力情報をMemberオブジェクトに格納
        Member member = memberHelper.formToMember(memberUpdateForm
                .getMemberForm());
        member.setCustomerNo(customerNo);
        MemberLogin memberLogin = new MemberLogin();
        memberLogin.setPassword(memberUpdateForm.getPassword());
        member.setMemberLogin(memberLogin);

        // DB更新
        memberUpdateService.updateMember(member);

        // 入力情報をupdateCompleteへリダイレクトする準備
        redirectAttributes.addFlashAttribute(member);

        return "redirect:/Member/update?complete";
    }

    /**
     * 会員情報変更終了後にユーザ情報管理画面へ遷移する。
     * @return View論理名
     */
    @RequestMapping(params = "complete", method = RequestMethod.GET)
    public String updateComplete() {
        return "redirect:/Member/update?showDetail";
    }

    /**
     * ユーザ情報変更画面を再表示する。
     * @param memberUpdateForm 会員情報変更フォーム
     * @param model 出力情報を保持するクラス
     * @return View論理名
     */
    @RequestMapping(params = "redo", method = RequestMethod.POST)
    public String updateRedo(MemberUpdateForm memberUpdateForm, Model model) {
        return "C2/memberUpdateForm";
    }

}
