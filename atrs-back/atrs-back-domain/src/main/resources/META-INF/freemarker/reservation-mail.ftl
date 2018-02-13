<#assign outward=reserveFlightList[0].flight>
${repFamilyName} ${repGivenName} 様の予約処理が完了しました。
お支払期限までにご購入いただけない場合、すべてのフライトが自動的にキャンセルされます。

[チケット]
　■予約番号${"\t"}:　${reserveNo}
<#setting number_format=",##0">
　■合計金額${"\t"}:　${totalFare}円
　■支払期限${"\t"}:　${reserveFlightList[0].flight.departureDate?string("M月d日(E)")}

[予約代表者情報]
　■お客様番号${"\t"}:　<#if repMember.customerNo??>${repMember.customerNo}</#if>
　■代表者氏名${"\t"}:　${repFamilyName} ${repGivenName} 様
　■年齢${"\t"}:　${repAge}歳
　■性別${"\t"}:　<#if repGender.code = "F">女性<#else>男性</#if>
　■電話番号${"\t"}:　${repTel}
　■メール${"\t"}:　${repMail}

[予約フライト情報]
▽往路
　■搭乗日${"\t"}:　${outward.departureDate?string("M月d日(E)")}
　■便名${"\t"}:　${outward.flightMaster.flightName}
　■出発空港${"\t"}:　${outward.flightMaster.route.departureAirport.name}
　■出発時刻${"\t"}:　${outward.flightMaster.departureTime}
　■到着空港${"\t"}:　${outward.flightMaster.route.arrivalAirport.name}
　■到着時刻${"\t"}:　${outward.flightMaster.arrivalTime}
　■搭乗クラス${"\t"}:　${outward.boardingClass.boardingClassName}
　■運賃種別${"\t"}:　${outward.fareType.fareTypeName}
　■運賃${"\t"}:　${outward.flightMaster.route.basicFare}円

<#if reserveFlightList[1]?has_content>
<#assign homeward=reserveFlightList[1].flight>
▽復路
　■搭乗日${"\t"}:　${homeward.departureDate?string("M月d日(E)")}
　■便名${"\t"}:　${homeward.flightMaster.flightName}
　■出発空港${"\t"}:　${homeward.flightMaster.route.departureAirport.name}
　■出発時刻${"\t"}:　${homeward.flightMaster.departureTime}
　■到着空港${"\t"}:　${homeward.flightMaster.route.arrivalAirport.name}
　■到着時刻${"\t"}:　${homeward.flightMaster.arrivalTime}
　■搭乗クラス${"\t"}:　${homeward.boardingClass.boardingClassName}
　■運賃種別${"\t"}:　${homeward.fareType.fareTypeName}
　■運賃${"\t"}:　${homeward.flightMaster.route.basicFare}円

</#if>
[搭乗者情報]
<#assign passengers=reserveFlightList[0].passengerList>
<#list passengers as passenger>
▽搭乗者${passenger_index?int+1}
　■氏名${"\t"}:　${passenger.familyName} ${passenger.givenName} 様
　■年齢${"\t"}:　${passenger.age}歳
　■性別${"\t"}:　<#if passenger.gender.code = "F">女性<#else>男性</#if>
</#list>
