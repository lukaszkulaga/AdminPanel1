<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!-- wczytywanie biblioteki tagow ... wszedzie gdzie damy prefix "s" to nasza aplikacja bedzie wiedziala ze ma siegnac po tagi stad ... "http://www.springframework.org/tags" -->
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="/resources/css/style.css" />


<title><s:message code="menu.mainPage" /></title>
<!-- zeby to zadzialalo to dajemy powyzej < taglib prefix="s"  uri="http://www.springframework.org/tags" ... zauwaz ze dalismy prefix "s" czyli wskazanie na ta biblioteke powyzej - ten adres>       -->
</head>
<body>
	<%@include file="/WEB-INF/incl/menu.app"%>
	<!-- wskazanie na sciezke pliku menu.app - wyswietlenie menu na stronie głównej -->
</body>
</html>