<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:message code="profilEdit.pageName" /></title>
</head>
<body>
	<%@include file="/WEB-INF/incl/menu.app"%>
	<h2>
		<s:message code="profilEdit.pageName" />
	</h2>

	<p align="center">
		<c:out value="${message }" />
	</p>

	<!--  zeby akcja save czyli wszystko co ponizej zadzialalo musimy miec odpowiednie zapytanie do bazy danych w klasie  AdminRepository  -->

	<!-- dzieki id wskazujemy ktorego uzytkownika chcemy aktualizowac -->
	<!-- pierwszy sposob lapania id ... to id po zapisie danych jest w kontrolerze dostepne -->
	<sf:form id="usersForm"
		action="${pageContext.request.contextPath}/admin/updateuser/${user.id}"
		modelAttribute="user" enctype="multipart/form-data" method="POST">


		<!-- drugi sposob lapania id .... hidden - ukryte pole , path="id" - sciezka do pola w klasie User ktore to pole przechowuje id , value - tutaj wrzucamy to id -->
		<sf:hidden path="id" value="${user.id}" />

		<table width="500" border="0" cellpadding="4" cellspacing="1"
			align="center">

			<!-- to co mamy w tr ... mozemy odczytac za pomoca kontrolera (przejac te dane) -->

			<tr>
				<td width="130" align="right"><s:message code="register.name" /></td>
				<td width="270" align="left"><sf:input path="name" size="28"
						id="name" readonly="true" /></td>
				<!-- readonly - oznacza ze jest to pole tylko do odczytu a nie do edycji .... musimy wstawiac to tam gdzie nie zmieniamy/edytujemy danych -->
			</tr>
			<tr>
				<td colspan="2" align="center"><font color="red"><sf:errors
							path="name" /></font></td>
			</tr>

			<tr>
				<td width="130" align="right"><s:message
						code="register.lastName" /></td>
				<td width="270" align="left"><sf:input path="lastName"
						size="28" readonly="true" /></td>
				<!-- readonly - oznacza ze jest to pole tylko do odczytu a nie do edycji .... musimy wstawiac to tam gdzie nie zmieniamy/edytujemy danych -->
			</tr>

			<tr>
				<td colspan="2" align="center"><font color="red"><sf:errors
							path="lastName" /></font></td>
			</tr>

			<tr>
				<td width="130" align="right"><s:message code="register.email" /></td>
				<td width="270" align="left"><sf:input path="email" size="28"
						readonly="true" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><font color="red"><sf:errors
							path="email" /></font></td>
			</tr>

			<tr>
				<td width="130" align="right"><s:message code="profil.rola" /></td>
				<td width="270" align="left"><sf:select path="nrRoli"
						items="${roleMap}" /></td>
			</tr>

			<tr>
				<td width="130" align="right"><s:message
						code="profil.czyAktywny" /></td>
				<td width="270" align="left"><sf:select path="active"
						items="${activityMap}" /></td>
			</tr>

			<tr>
				<td colspan="2" align="center" bgcolor="#fff"><input
					type="submit" value="<s:message code="button.save"/>" /> <!--  zeby akcja save czyli wszystko co wyzej zadzialalo musimy miec odpowiednie zapytanie do bazy danych w klasie  AdminRepository  -->
					<input type="button" value="<s:message code="button.cancel"/>"
					onclick="window.location.href='${pageContext.request.contextPath}/admin/users/1'" />
					<!--  jezeli chcemy zrezygnowac z edycji danych uzytkownika .... i kieruje nas na strone /admin/users/1  czyli na pierwsza liste uzytkownikow -->
				</td>
			</tr>

		</table>

	</sf:form>
</body>
</html>