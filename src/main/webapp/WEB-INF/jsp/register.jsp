<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- walidatory -->
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!-- importujemy biblioteke tagow ktore pozwalaja nam wstawiac komunikaty.... wszystko co ma prefix "s" ponizej -->
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!-- potrzebne zeby to zadzialalo <include file="/WEB-INF/incl/menu.app" > -->
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!-- zestaw tagow pozwalajacych na obslugiwanie i konstruowanie formularzy -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- sterowanie wyswietlania np. zawartosci tabel albo w zaleznosci od tego jaka przyjdzie wartosc z bazy danych wyswietlanie odpowiedniego komunikatu na ekranie   -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">



<title><s:message code="menu.register" /></title>

<!-- style css -->
<link rel="stylesheet" type="text/css" href="/resources/css/style.css" />

</head>
<body>
	<%@include file="/WEB-INF/incl/menu.app"%>
	<!-- zaimportowanie menu do kazdej strony za pomoca tylko tej jednej liniki -->

	<h2>
		<s:message code="menu.register" />
	</h2>





	<p align="center">
		<c:out value="${message }" />
		<!-- wyswietlamy jakas wartosc ktora zostanie zwrocona przez kontroler pod kodem ${message} -->
	</p>

	<!-- sf:form -  informujemy ze to formularz ... action - jak klikniemy na zarejestruj ... modelAttribute="user  - musimy przekazac do formularza na jakim modelu pracujemy(nasz model to "user" i on bedzie wiedzial ze sklada sie z tego co ponizej jest poprzedzone prefikse sf )   -->
	<sf:form id="usersForm" action="adduser" modelAttribute="user"
		enctype="multipart/form-data" method="POST">

		<table width="500" border="0" cellpadding="4" cellspacing="1"
			align="center">




			<tr>
				<td width="130" align="right"><s:message code="register.name" /></td>
				<!-- pole name musi odpowiadac polu name z klasy User -->
				<td width="270" align="left"><sf:input path="name" size="28"
						id="name" /></td>
			</tr>

			<tr>
				<!-- mamy sf w komurce jakiejs tabelki umieszczone   -->
				<td colspan="2" align="center"><font color="red"><sf:errors
							path="name" /></font></td>
				<!-- spring znajdzie sf i po nazwie trafi do pola name i w tym polu wyswietli komunikat z walidatora ze jest error -->
			</tr>




			<tr>
				<td width="130" align="right"><s:message
						code="register.lastName" /></td>
				<td width="270" align="left"><sf:input path="lastName"
						size="28" /></td>
			</tr>

			<tr>
				<td colspan="2" align="center"><font color="red"><sf:errors
							path="name" /></font></td>
			</tr>




			<tr>
				<td width="130" align="right"><s:message code="register.email" /></td>
				<td width="270" align="left"><sf:input path="email" size="28" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><font color="red"><sf:errors
							path="name" /></font></td>
			</tr>




			<tr>
				<td width="130" align="right"><s:message
						code="register.password" /></td>
				<td width="270" align="left"><sf:password path="password"
						size="28" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><font color="red"><sf:errors
							path="name" /></font></td>
			</tr>






			<tr>
				<td colspan="2" align="center" bgcolor="#fff"><input
					type="submit" value="<s:message code="button.register"/>"
					class="formbutton" /> <input type="button"
					value="<s:message code="button.cancel"/>" class="formbutton"
					onclick="window.location.href='${pageContext.request.contextPath}/'" />
				</td>
			</tr>

		</table>

	</sf:form>


</body>
</html>