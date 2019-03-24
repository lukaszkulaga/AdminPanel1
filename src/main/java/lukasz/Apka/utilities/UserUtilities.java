package lukasz.Apka.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import lukasz.Apka.user.User;

public class UserUtilities {

	// spring security

	/**
	 * sprawdzamy kto jest zalogowany i siegamy po jego dane ... na stronie klikamy
	 * profil i wyswietla sie dane uzytkownika ktory jest zalogowany do Stringa
	 * zwracamy nazwe uzytkownika ktory jest aktualnie zalogowany metoda jest
	 * statyczna bo zawsze bedzie robila to samo
	 */

	public static String getLoggedUser() {

		String username = null;
		// auth - kontekst // SecurityContextHolder - przechowywanie/tworzenie contextu
		// w ktorym jest zalogowany uzytkownik
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // przechwytywanie contextu -
																						// danych przy pomocy ktorych
																						// uzytkownik sie zautentykowal

		// jezeli w contexcie nie jest przechowywane cos co ma anoymous - a wiec nie
		// jest autentykowane - uzytkownik jest nie znany to....
		if (!(auth instanceof AnonymousAuthenticationToken))    // instanceof AnonymousAuthenticationToken - sprawdzamy czy
																// dane nie zawieraja anoymouse a wiec czy nie sa
																// instancja tej klasy... jezeli nie to ...
		{
			username = auth.getName(); // pobieramy z kontekstu (auth) nazwe zalogowanego uzytkownika ( jezeli
										// uzytkownik jest znany to zwrocony zostanie null )
		}
		return username;
	}

	/**
	 * ta metoda obrabia nam plik przesylany w formularzu importUsers.jsp jako plik
	 * xmp ... odczytywanie tego pliku
	 */

	public static List<User> usersDataLoader(File file) {
		List<User> userList = new ArrayList<User>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBilder = dbFactory.newDocumentBuilder();
			Document doc = dBilder.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("user");
			for (int i = 0; i < nList.getLength(); i++) {
				Node n = nList.item(i);
				if (n.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) n;
					User u = new User();
					u.setEmail(e.getElementsByTagName("email").item(0).getTextContent());
					u.setPassword(e.getElementsByTagName("password").item(0).getTextContent());
					u.setName(e.getElementsByTagName("name").item(0).getTextContent());
					u.setLastName(e.getElementsByTagName("lastname").item(0).getTextContent());
					u.setActive(Integer.valueOf(e.getElementsByTagName("active").item(0).getTextContent()));
					u.setNrRoli(Integer.valueOf(e.getElementsByTagName("nrroli").item(0).getTextContent()));
					userList.add(u);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}
}
