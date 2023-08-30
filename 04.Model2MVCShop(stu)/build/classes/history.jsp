<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page import="java.net.URLEncoder" %>

<html>
<head>

<title>열어본 상품 보기</title>
<script type="text/javascript">

function fncDeleteCookieprodNoo) {
	request.setCharacterEncoding("euc-kr");
	response.setCharacterEncoding("euc-kr");
	String history = null;
	Cookie[] cookies = request.getCookies();
	if (cookies!=null && cookies.length > 0) {
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("history")) {
				history = cookie.getValue();
			}
		}
		if (history != null) {
			String[] h = history.split(URLEncoder.encode(","));
			for (int i = h.length-1; i >= 0; i--) {
				if (h[i].equals(prodNo)) {
			        c[i].setMaxAge(0); // 유효시간을 0으로 설정
			        response.addCookie(c[i]); // 응답에 추가하여 만료시키기.
	    		}
			}
		}
	}
}

</script>
</head>
<body>
	당신이 열어본 상품을 알고 있다
<br>
<br>
<%-- 
	Cookie[] c = request.getCookies(); // 모든 쿠키의 정보를 cookies에 저장
	if (c != null) { // 쿠키가 한개라도 있으면 실행
		for (int i = 0; i < c.length; i++) {
	        c[i].setMaxAge(0); // 유효시간을 0으로 설정
	        response.addCookie(c[i]); // 응답에 추가하여 만료시키기.
	    }
	}
--%>
<%
	request.setCharacterEncoding("euc-kr");
	response.setCharacterEncoding("euc-kr");
	String history = null;
	Cookie[] cookies = request.getCookies();
	if (cookies!=null && cookies.length > 0) {
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("history")) {
				history = cookie.getValue();
			}
		}
		if (history != null) {
			String[] h = history.split(URLEncoder.encode(","));
			for (int i = h.length-1; i >= 0; i--) {
				if (!h[i].equals("null")) {
					%>
						<a href="/getProduct.do?prodNo=<%=h[i]%>&menu=search"	target="rightFrame"><%=h[i]%></a>
						<!--  <button onClick="javascript:fncDeleteCookie('<%= h[i] %>'')" style="margin:5px">삭제</button>
						-->
						<br>
					<%
				}
			}
		}
	}
%>

</body>
</html>