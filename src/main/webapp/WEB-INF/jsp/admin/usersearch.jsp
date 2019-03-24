
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- formularz  -->


<html>



<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="/resources/css/style.css" />

<title><s:message code="menu.users" /></title>



<!-- pierwsza funkcja - jak najediemy kursorem na tekst to sie nam on podswietli -->
<!-- druga funkcja - jak zdejmiemy kursor z textu to on znowu bedzie bialy  -->

<script type="text/javascript">
	function changeTrBg(row) {
		row.style.backgroundColor = "#e6e6e6";
	}

	function defaultTrBg(row) {
		row.style.backgroundColor = "white";

	}

	function startSerach(pParam) {
		// pobierz wartosc pola o takim id 
		var searchWord = document.getElementById('searchString').value; // odbieram wartosc pola id="searchString"
		var page = parseInt(document.getElementById('cp').value)
				+ parseInt(pParam);

		if (searchWord.length < 3) // jezeli podalismy mniej niz 3 znaki to...
		{ //... wewnatrz elementu "errorSearch" wsadzamy takie message
			document.getElementById("errorSearch").innerHTML = "<s:message code="error.searchString.toShort"/>";
			return false; // zwracamy false zeby juz zadna akcja sie nie wykonala 
		} else {
			document.getElementById("errorSearch").innerHTML = ""; // jezeli warunek spelniony to czyscimy pole span
			// tworzymy link ... zmienna search link przechowuje link z odczytanym slowkiem... searchWord
			var searchLink = '${pageContext.request.contextPath}/admin/users/search/'
					+ searchWord + '/' + page; // nasze szukane slowo... serchWord.... chwytamy w kontrolerze jako parametr
			window.location.href = searchLink; // wywolujemy link
		}
	}
</script>

</head>




<body>

	<%@include file="/WEB-INF/incl/menu.app"%>
	<h2>
		<s:message code="menu.users" />
	</h2>


	<c:set var="count" value="${recordStartCounter }" />
	<!-- value="${recordStartCounter}" to pochodzi z contrlera adminPageControler -->


	<div align="center">


		<!-- wyszukiwarka ... obsluga javascript  -->
		<div align="right" style="width: 1000px; padding: 2px;">

			<input type="hidden" name="cp" id="cp" value="${currentPage}" /> <input
				type="text" id="searchString" />&nbsp;&nbsp;<input type="button"
				value="<s:message code="button.search"/>" onclick="startSerach(0);" /><br />
			<span id="errorSearch" style="color: red;"></span>
		</div>



		<!-- w tej tabeli wyswietlamy uzytkownikow  -->

		<table width="1000" border="0" cellpadding="6" cellspacing="2">
			<tr bgcolor="#ffddcc">
				<!-- tr - to wiersze -->

				<!-- td to komorki znajdujace sie w wierszu -->
				<td width="40" align="center"></td>
				<!-- ta kolumna przechowuje numer kolejny naszego wiersza ... ten numer przechowywany jest w zmiennej <c:set var="count" value="0" scope="page"/>    -->

				<td width="40" align="center"><b><s:message
							code="admin.user.id" /></b></td>
				<td width="200" align="center"><b><s:message
							code="register.name" /></b></td>
				<td width="200" align="center"><b><s:message
							code="register.lastName" /></b></td>
				<td width="220" align="center"><b><s:message
							code="register.email" /></b></td>
				<td width="100" align="center"><b><s:message
							code="profil.czyAktywny" /></b></td>
				<td width="200" align="center"><b><s:message
							code="profil.rola" /></b></td>
			</tr>

			<!-- w kontrolerze bedziemy oddawac liste uzytkownikow ukryta pod zmienna userList... zeby to wyswietlic na stronie , nie stosujac wyrafinowanych metod wykozystamy petle for each wbudowana w jezyk jstl -->
			<!--   var="u" - zmienna ktora bedziemy sie poslugiwac do odczytywania czyli do jakiej zmiennej ma byc przypisywana aktualny wiersz z listy ktory odczytujemy    -->
			<!-- items="${userList }" - items czyli lista obiektow ktora przychodzi .. iterujemy po userList ... pobieramy wiersz (kazdy wiersz to obiekt typu User) i przypisujemy go do naszej zmiennej u ... i bedziemy siegac po konkretne informacje z tego wiersza   -->

			<c:forEach var="u" items="${userList }">

				<!-- tr - dla kazdej iteracji musimy stworzyc nowy wiersz w naszej tabelce  -->
				<!--  ...dodalem ... onmouseover="changeTrBg(this)" onmouseout="defaultTrBg(this)"   oraz   <c:set var="count" value="${count+1}" scope="page"/> -->

				<c:set var="licznik" value="${licznik+1}" />
				<!-- zwiekszamy count podane wyzej o 1 dla strony "page" -->

				<!-- wywolanie funkcji javascriptu -->
				<tr onmouseover="changeTrBg(this)" onmouseout="defaultTrBg(this)">



					<!-- algin - wyrownanie -->

					<td align="right"><c:out value="${licznik }" /></td>
					<!-- wypisanie wartosci count -->
					<td align="right"><a href="edit/${u.id }"><c:out
								value="${u.id }" /></a></td>
					<!-- warosci value musza nazywac sie tak jak pola w kalasie User poniewaz nie siegamy po cos co jest w bazie danych tylko po wartosc obiektu  -->
					<td align="left"><a href="edit/${u.id }"><c:out
								value="${u.name }" /></a></td>
					<td align="left"><a href="edit/${u.id }"><c:out
								value="${u.lastName }" /></a></td>
					<td align="center"><a href="edit/${u.id }"><c:out
								value="${u.email }" /></a></td>
					<td align="center">
						<!-- chcemy wyswietlic konkretne wartosci (ktore moga sie zmieniac np. ktos jest aktywnt albo nie ...ktos ma role 1 a za chwile moze miec role 2 ... w tym celu stosujemy blok choose) -->
						<!-- blok choose zawiera warunki  --> <c:choose>
							<c:when test="${u.active == 1 }">
								<font color="green"><s:message code="word.tak" /></font>
							</c:when>
							<c:otherwise>
								<!-- cos jak else  -->
								<font color="red"><s:message code="word.nie" /></font>
							</c:otherwise>
						</c:choose>
					</td>
					<td align="center"><c:choose>
							<c:when test="${u.nrRoli == 1 }">
								<font color="green"><s:message code="word.admin" /></font>
							</c:when>
							<c:otherwise>
								<s:message code="word.user" />
							</c:otherwise>
						</c:choose></td>
				</tr>
			</c:forEach>
		</table>



		<!-- w tej tabeli obslugujemy stronnicowanie -->

		<table width="1000" border="0" cellpadding="6" cellspacing="0"
			bgcolor="#ffddcc">
			<tr>

				<td width="300" align="left"><s:message code="info.page" />
					${currentPage} <s:message code="info.from" /> ${totalPages} <!-- to ...${currentPage} i to ... ${totalPages} ... nazwa odpowiada temu co mamy w AdminPageCotroller w cudzyslowiach-->
				</td>


				<td align="right"><c:if test="${currentPage > 1}">
						<!-- jezeli currentPage jest wieksze od 1 to....  ${currentPages} ... nazwa odpowiada temu co mamy w AdminPageCotroller w cudzyslowiach  -->
						<!-- ... to wyswietlamy nowy button/przycisk  ... ktory wykona akcje/bedzie linkiem ... onclick(czyli pobieramy aktualny context i wykonujemy currentPage - 1 ) czyli z warunku wynika ze jezeli nasza strona jest wieksza od 1 to pozwalamy sie cofnac do poprzedniej ... stad currentPage - 1   -->
						<!-- nastepnie za pomoca ... ${pageContext.request.contextPath} ... pobieramy contextPath czyli localhost:8080 i doklejamy do tego  /admin/users/  -->
						<!--  contextPath to to samo co localhost:8080: -->
						<input type="button" onclick="startSerach(-1);"
							value="<s:message code="link.poprzedni"/>" />&nbsp;&nbsp;

			</c:if> <!--  z warunku wynika ze jezeli nasza strona jest mniejsza od liczby wszystkich stron to pozwalamy przejsc do nastepnej strony ... stad currentPage +1 -->
					<c:if test="${currentPage < totalPages}">
						<input type="button" onclick="startSerach(1);"
							value="<s:message code="link.nastepny"/>" />

					</c:if></td>
			</tr>
		</table>



	</div>
</body>
</html>



