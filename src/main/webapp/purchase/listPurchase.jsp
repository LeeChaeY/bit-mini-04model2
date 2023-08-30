<%@ page contentType="text/html; charset=EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="type" value="Purchase"/>

<html>
<head>
<title>구매 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript" src="../javascript/calendar.js">
</script>

<script type="text/javascript">
	function fncGet${ type }List(currentPage) {
		if (document.detailForm.beginDate.value == "" && document.detailForm.endDate.value != "") {
			alert("기간별 조회는 시작 날짜를 기입해야합니다.");
			return false;
		}
		document.getElementById("currentPage").value = currentPage;
		document.detailForm.submit();
	}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/listPurchase.do" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">구매 목록조회</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right" width="450">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td><strong>기간별 조회 </strong></td>
					<td width="150" class="ct_write01">
						<input 	type="text" readonly="readonly" name="beginDate" class="ct_input_g" 
										style="width: 100px; height: 19px" maxLength="20" value="${ !empty beginDate ? beginDate : ''}"/>
						<img 	src="../images/ct_icon_date.gif" width="15" height="15"	
									onclick="show_calendar('document.detailForm.beginDate', document.detailForm.beginDate.value)"/>
					</td>
					<td width="20"><strong> ~ </strong></td>
					<td width="150" class="ct_write01">
						<input 	type="text" readonly="readonly" name="endDate" class="ct_input_g" 
										style="width: 100px; height: 19px;" maxLength="20" value="${ !empty endDate ? endDate : ''}"/>
						<img 	src="../images/ct_icon_date.gif" width="15" height="15"	
									onclick="show_calendar('document.detailForm.endDate', document.detailForm.endDate.value)"/>
					</td>
					<tr>
			</table>
		</td>
		
		<td align="right" width="10">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGet${ type }List('1');">조회</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">
			전체  ${ resultPage.totalCount } 건수,	현재 ${ resultPage.currentPage } 페이지
		</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="300">구매일자</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">배송주소</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">배송현황</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">정보수정</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	<c:set var="i" value="0"/>
	<c:forEach var="purchase" items="${ list }">
	<c:set var="i" value="${i+1}"/>
	<tr class="ct_list_pop">
		<td align="center">
			${ i }
		</td>
		<td></td>
		<td align="left">
			<a href="/getProduct.do?menu=search&prodNo=${ purchase.purchaseProd.prodNo }">${ purchase.purchaseProd.prodName }</a>
		</td>
		<td></td>
		<td align="left">
			${ purchase.orderDate }
			<a href="/getPurchase.do?tranNo=${ purchase.tranNo }" style="margin:30px">구매상세조회</a>
		</td>
		<td></td>
		<td align="left">${ purchase.divyAddr }</td>
		<td></td>
		<td align="left">
			현재
			<c:choose>
				<c:when test="${ !empty purchase.tranCode && purchase.tranCode.equals('2') }">
					구매완료
				</c:when>
				<c:when test="${ !empty purchase.tranCode && purchase.tranCode.equals('3') }">
					배송중
				</c:when>
				<c:when test="${ !empty purchase.tranCode && purchase.tranCode.equals('4') }">
					배송완료
				</c:when>
			</c:choose>
			상태 입니다.		
		</td>
		<td></td>
		<td align="left">
			<%--배송중 상태면 보임, 사용자가 배송완료되면 누르게끔
			물건 도착하면 정보수정에 아무것도 없음, 관리자 사용자 모두 배송완료로 바뀜 --%>
			<c:if test="${ !empty purchase.tranCode && purchase.tranCode.equals('3') }">
				<a href="/updateTranCode.do?tranNo=${ purchase.tranNo }&tranCode=${ purchase.tranCode }">물건도착</a>
			</c:if>
		</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	</c:forEach>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
	<tr>
		<td align="center">
			<input type="hidden" id="currentPage" name="currentPage" value=""/>
			<jsp:include page="../common/pageNavigator.jsp">
				<jsp:param value="${ type }" name="type"/>
			</jsp:include>	
		</td>
	</tr>
</table>

<!--  페이지 Navigator 끝 -->
</form>

</div>

</body>
</html>