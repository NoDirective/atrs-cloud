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
package jp.co.ntt.atrs.app.b2;

import jp.co.ntt.atrs.app.b0.SelectFlightDto;
import jp.co.ntt.atrs.app.b0.TicketHelper;
import jp.co.ntt.atrs.app.common.exception.BadRequestException;
import jp.co.ntt.atrs.domain.model.Flight;
import jp.co.ntt.atrs.domain.service.a1.AtrsUserDetails;
import jp.co.ntt.atrs.domain.service.b2.TicketReserveErrorCode;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessage;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;

import java.util.List;
import javax.inject.Inject;

/**
 * チケット予約コントローラ。
 * @author NTT 電電三郎
 */
@Controller
@RequestMapping("Ticket/reserve")
@TransactionTokenCheck("Ticket/reserve")
public class TicketReserveController {

    /**
     * チケット予約共通Helper。
     */
    @Inject
    TicketHelper ticketHelper;

    /**
     * チケット予約Helper。
     */
    @Inject
    TicketReserveHelper ticketReserveHelper;

    /**
     * チケット予約用選択フライト情報フォームのバリデータ。
     */
    @Inject
    TicketReserveSelectFlightValidator ticketReserveSelectFlightValidator;

    /**
     * チケット予約フォームのバリデータ。
     */
    @Inject
    TicketReserveValidator ticketReserveValidator;

    /**
     * チケット予約用選択フライト情報フォームのバリデータをバインダに追加する。
     * @param binder バインダ
     */
    @InitBinder("ticketReserveSelectFlightForm")
    public void initBinderForTicketReserveSelectFlight(WebDataBinder binder) {
        binder.addValidators(ticketReserveSelectFlightValidator);
    }

    /**
     * チケット予約フォームのバリデータをバインダに追加する。
     * @param binder バインダ
     */
    @InitBinder("ticketReserveForm")
    public void initBinderForTicketReserve(WebDataBinder binder) {
        binder.addValidators(ticketReserveValidator);
    }

    /**
     * 選択フライト確認画面を表示する。
     * <p>
     * 選択したフライト情報リストを作成して、選択フライト確認画面を表示する。
     * </p>
     * @param ticketReserveSelectFlightForm チケット予約用選択フライト情報フォーム
     * @param model 出力情報を保持するオブジェクト
     * @return View論理名
     */
    @RequestMapping(method = RequestMethod.GET, params = "select")
    public String reserveSelect(
            TicketReserveSelectFlightForm ticketReserveSelectFlightForm,
            Model model) {

        // 選択フライト情報リストを作成する。
        List<Flight> flightList = ticketHelper.createFlightList(
                ticketReserveSelectFlightForm.getSelectFlightFormList());
        List<SelectFlightDto> selectFlightDtoList = ticketReserveHelper
                .createSelectFlightDtoList(flightList);

        // 出力DTOを作成する。
        SelectFlightConfirmOutputDto selectFlightConfirmOutPutDto = new SelectFlightConfirmOutputDto();
        selectFlightConfirmOutPutDto.setSelectFlightDtoList(
                selectFlightDtoList);
        selectFlightConfirmOutPutDto.setHasHomeward(ticketReserveHelper
                .hasHomeward(selectFlightDtoList));

        model.addAttribute(selectFlightConfirmOutPutDto);

        return "B2/selectFlightConfirm";
    }

    /**
     * 予約入力画面を表示する。
     * @param ticketReserveSelectFlightForm チケット予約用選択フライト情報フォーム
     * @param ticketReserveForm チケット予約フォーム
     * @param userDetails ログイン情報を保持するオブジェクト
     * @param model 出力情報を保持するオブジェクト
     * @return View論理名
     */
    @RequestMapping(method = RequestMethod.GET, params = "form")
    public String reserveForm(
            TicketReserveSelectFlightForm ticketReserveSelectFlightForm,
            TicketReserveForm ticketReserveForm,
            @AuthenticationPrincipal AtrsUserDetails userDetails, Model model) {

        List<Flight> flightList = ticketHelper.createFlightList(
                ticketReserveSelectFlightForm.getSelectFlightFormList());

        ticketReserveForm.setOutwardLineSearchCriteriaForm(
                ticketReserveSelectFlightForm
                        .getOutwardLineSearchCriteriaForm());

        ticketReserveForm.setSelectFlightFormList(ticketReserveSelectFlightForm
                .getSelectFlightFormList());

        ticketReserveHelper.prepareReservationForm(ticketReserveForm
                .getReservationForm(), userDetails);

        model.addAttribute("selectFlightDtoList", ticketReserveHelper
                .createSelectFlightDtoList(flightList));

        return "B2/reserveForm";
    }

    /**
     * 予約入力画面を再表示する。
     * @param ticketReserveForm チケット予約フォーム
     * @param userDetails ログイン情報を保持するオブジェクト
     * @param model 出力情報を保持するオブジェクト
     * @return View論理名
     */
    @RequestMapping(method = RequestMethod.POST, params = "redo")
    public String reserveRedo(TicketReserveForm ticketReserveForm,
            @AuthenticationPrincipal AtrsUserDetails userDetails, Model model) {

        List<Flight> flightList = ticketHelper.createFlightList(
                ticketReserveForm.getSelectFlightFormList());

        ticketReserveHelper.prepareReservationForm(ticketReserveForm
                .getReservationForm(), userDetails);

        model.addAttribute("selectFlightDtoList", ticketReserveHelper
                .createSelectFlightDtoList(flightList));

        return "B2/reserveForm";
    }

    /**
     * 会員ログイン成功、お客様情報入力画面にリダイレクトする。
     * @param ticketReserveSelectFlightForm チケット予約用選択フライトフォーム
     * @param redirectAttributes フラッシュスコープ格納用オブジェクト
     * @return View論理名
     */
    @RequestMapping(value = "member", method = RequestMethod.GET)
    public String reserveMember(
            TicketReserveSelectFlightForm ticketReserveSelectFlightForm,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(ticketReserveSelectFlightForm);

        return "redirect:/Ticket/reserve?form";
    }

    /**
     * 予約確認画面を表示する。
     * <p>
     * 入力チェックにエラーがある場合、予約入力画面を表示する。 入力チェックにエラーがない場合、予約確認画面を表示する。
     * </p>
     * @param ticketReserveForm チケット予約フォーム
     * @param result チェック結果
     * @param userDetails ログイン情報を保持するオブジェクト
     * @param model 出力情報を保持するオブジェクト
     * @return View論理名
     * @throws BadRequestException リクエスト不正例外
     */
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    @RequestMapping(method = RequestMethod.POST, params = "confirm")
    public String reserveConfirm(@Validated TicketReserveForm ticketReserveForm,
            BindingResult result,
            @AuthenticationPrincipal AtrsUserDetails userDetails,
            Model model) throws BadRequestException {

        // 入力チェック
        if (result.hasErrors()) {
            return reserveRedo(ticketReserveForm, userDetails, model);
        }

        List<Flight> flightList = ticketReserveHelper.createFlightList(
                ticketReserveForm.getSelectFlightFormList());

        try {
            // チケットを予約確認をする。
            ReserveConfirmOutputDto reserveConfirmOutputDto = ticketReserveHelper
                    .reserveConfirm(ticketReserveForm.getReservationForm(),
                            flightList, userDetails);
            model.addAttribute(reserveConfirmOutputDto);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return reserveRedo(ticketReserveForm, userDetails, model);
        }

        return "B2/reserveConfirm";
    }

    /**
     * チケットを予約する。
     * @param ticketReserveForm チケット予約フォーム
     * @param result チェック結果
     * @param userDetails ログイン情報を保持するオブジェクト
     * @param model 出力情報を保持するオブジェクト
     * @param redirectAttributes フラッシュスコープ格納用オブジェクト
     * @return View論理名
     * @throws BadRequestException リクエスト不正例外
     */
    @TransactionTokenCheck
    @RequestMapping(method = RequestMethod.POST)
    public String reserve(@Validated TicketReserveForm ticketReserveForm,
            BindingResult result,
            @AuthenticationPrincipal AtrsUserDetails userDetails, Model model,
            RedirectAttributes redirectAttributes) throws BadRequestException {

        // 入力チェック(改竄チェック)
        if (result.hasErrors()) {
            throw new BadRequestException(result);
        }

        // 選択フライト情報フォームのリストからフライト情報リストを生成(同時に改竄チェックも行う)
        List<Flight> flightList = ticketReserveHelper.createFlightList(
                ticketReserveForm.getSelectFlightFormList());

        try {
            // チケットを予約する。
            ReserveCompleteOutputDto reserveCompleteOutputDto = ticketReserveHelper
                    .reserve(ticketReserveForm.getReservationForm(), flightList,
                            userDetails);
            if (reserveCompleteOutputDto == null) {
                ResultMessages messages = ResultMessages.error().add(
                        ResultMessage.fromCode(
                                TicketReserveErrorCode.E_AR_B2_2011.code(),
                                new Object[] { null }));
                return createReserveFailMessage(messages, flightList, model);
            }
            redirectAttributes.addFlashAttribute(reserveCompleteOutputDto);
        } catch (BusinessException e) {
            return createReserveFailMessage(e.getResultMessages(), flightList,
                    model);
        }

        return "redirect:/Ticket/reserve?complete";
    }

    /**
     * チケット予約失敗メッセージ作成。
     * @param messages {@link ResultMessages}
     * @param flightList フライトリスト
     * @param model {@link Model}
     * @return メッセージ
     */
    private String createReserveFailMessage(ResultMessages messages,
            List<Flight> flightList, Model model) {
        model.addAttribute(messages);
        model.addAttribute("selectFlightDtoList", ticketReserveHelper
                .createSelectFlightDtoList(flightList));
        return "B2/reserveFail";
    }

    /**
     * 予約完了画面を表示する。
     * @return View論理名
     */
    @RequestMapping(method = RequestMethod.GET, params = "complete")
    public String reserveComplete() {
        return "B2/reserveComplete";
    }

    /**
     * 予約中止確認画面を表示する。
     * @param ticketReserveForm チケット予約フォーム
     * @return View論理名
     */
    @RequestMapping(method = RequestMethod.POST, params = "cancel")
    public String reserveCancel(TicketReserveForm ticketReserveForm) {
        return "B2/reserveCancelConfirm";
    }

}
