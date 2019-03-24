<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/resources/css/style.css" />
<title><s:message code="profil.userDane" /></title>
</head>
<body>
	<%@include file="/WEB-INF/incl/menu.app"%>
	<div align="center">
		<h2>
			<s:message code="profil.userDane" />
		</h2>
	</div>

	<table width="500" border="0" cellpadding="4" cellspacing="1"
		align="center">


		<!-- siegamy po atrybut .email -->
		<tr>
			<td width="130" align="right"><s:message code="register.email" />
				<!-- register.email - pochodzi z message.properties --></td>
			<td width="270" align="left">
				<!-- c:out - wyslij na zewnatrz ... ${user.email } - sieganie po atrybut, user to nazwa naszego obiektu .email to pole z klasy User (nazwy musza byc takie same) -->
				<c:out value="${user.email }" /> <!-- siegamy po atrybuty Usera którego przekazemy w klasie ProfilController ... model.addAttribute("user", user); -->
			</td>
		</tr>


		<!-- to co wyzej ale siegamy po atrybut .name -->
		<tr>
			<td width="130" align="right"><s:message code="register.name" />
			</td>
			<td width="270" align="left"><c:out value="${user.name }" /></td>
		</tr>



		<!-- to co wyzej ale siegamy po atrybut .lastname -->
		<tr>
			<td width="130" align="right"><s:message
					code="register.lastName" /></td>
			<td width="270" align="left"><c:out value="${user.lastName }" />
			</td>
		</tr>



		<!--  wyswietlanie czy uzytkownik jest aktywny -->
		<tr>
			<td width="130" align="right"><s:message
					code="profil.czyAktywny" /></td>
			<td width="270" align="left">
				<!-- to jest tak jakby zastapienie if,else --> <c:choose>
					<c:when test="${user.active == 1 }">
						<!-- warunek if -->
						<s:message code="word.tak" />
						<!-- z messages properties -->
					</c:when>
					<c:otherwise>
						<!-- warunek else -->
						<s:message code="word.nie" />
					</c:otherwise>
				</c:choose>
			</td>
		</tr>



		<!-- wyswietlnenie roli ... uzytkownik czy administrator   -->
		<tr>
			<td width="130" align="right"><s:message code="profil.rola" />
			</td>
			<td width="270" align="left"><c:choose>
					<c:when test="${user.nrRoli == 1 }">
						<s:message code="word.admin" />
					</c:when>
					<c:otherwise>
						<s:message code="word.user" />
					</c:otherwise>
				</c:choose></td>
		</tr>

	</table>

	<table width="500" border="0" cellpadding="4" cellspacing="1"
		align="center">

		<tr>
			<td align="center"><input type="button"
				value="<s:message code="button.edycjaProfilu"/>"
				onclick="window.location.href='${pageContext.request.contextPath}/editprofil'" />
			</td>
			<td align="center"><input type="button"
				value="<s:message code="button.zmianaHasla"/>"
				onclick="window.location.href='${pageContext.request.contextPath}/editpassword'" />
			</td>
		</tr>
	</table>


</body>
</html>