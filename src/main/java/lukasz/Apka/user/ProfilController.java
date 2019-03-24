package lukasz.Apka.user;

import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import lukasz.Apka.utilities.UserUtilities;
//import lukasz.Apka.validators.EditUserProfileValidator;
import lukasz.Apka.validators.ChangePasswordValidator;
import lukasz.Apka.validators.EditUserProfileValidator;

@Controller
public class ProfilController {
	

	
	/*
	 * USER modyfikuje dane za pomoca email a admin w swoim kontrolerze za pomoca id 
	 * dzieki temu wszystkiemu co ponizej uzytkownik po zalogowaniu ma dostep do roznych funkcji np. zmiana danych itd.
	 * 
	 * */
	
	@Autowired
	private UserService userService; // odczytanie/pobranie na podstawie nazwy uzytkownika jego danych 
	
	@Autowired
	private MessageSource messageSource;
	

	/*
	 * wyswietlenie formularza 
	 * ta metoda - kontroler - pobiera nam dane aktualnie zalogowanego uzytkownika i wczytuje strone jsp z uzyskanymi danymi
	 * 
	 * */
	
	
	@GET
	@RequestMapping(value = "/profil")
								
	public String showUserProfilePage(Model model) 
	{	
		String username = UserUtilities.getLoggedUser(); 		// ze spring security... z klasy UserUtilites... z contextu przechwytujemy nazwe uzytkownika ktora ta metoda nam zwroci     
		User user = userService.findUserByEmail(username); 		// z klasy UserUtilites bedzie wracał nam email ( email jest nasza nazwa uzytkownika ) 
		int nrRoli = user.getRoles().iterator().next().getId(); // pobieramy/odczytujemy numer roli z bazy danych zeby wiedziec czy to uzytkownik czy administrator
		user.setNrRoli(nrRoli); 								// ustawiamy/przekazujemy do obiektu nr pobranej roli ktora ma nam trafic na strone jsp
		model.addAttribute("user", user);						// zeby nr roli trafil na strone jsp musimy przekazac obiekt do naszej strony jsp 
		return "profil";
	}
	
	/* 
	 * wyswietlenie formularza  - dzieki metodzie ponizej edytujemy haslo
	*/
	@GET
	@RequestMapping(value = "/editpassword")
	@Secured(value = { "ROLE_USER" ,"ROLE_ADMIN" }) 		// dostep do tej metody ma amin i user  ... wazne zeby w klasie SecurityConfig byla adnotacja @EnableGlobalMethodSecurity(securedEnabled = true)
	public String editUserPassword(Model model) 
	{
		String username = UserUtilities.getLoggedUser(); 	// chwytamy kto jest zalogowany 
		User user = userService.findUserByEmail(username); 	// ta metoda zwraca nam caly obiekt user - wszystkie dane 
		model.addAttribute("user", user); 					// przekazujemy usera do naszej strony i user trafia do naszego formularza ... a dokladnie do pola email  <sf:hidden path="email"/> w editpassword.jsp  
		return "editpassword";
	}
	


	@POST
	@RequestMapping(value = "/updatepass")
	public String changeUSerPassword(User user, BindingResult result, Model model, Locale locale) 
	{
		String returnPage = null;
		
		new ChangePasswordValidator().validate(user, result); 						 // ten walidator sprawdza czy pole nie jest puste 		
		new ChangePasswordValidator().checkPasswords(user.getNewPassword(), result); // ten validator sprawdza czy haslo ktore podalismy (jezeli pole nie jest puste) 
																					 // jest zgodne ze wzorcem (czy ma np. jedna duza litere itd.)
		
		if (result.hasErrors())  	
		{
			returnPage = "editpassword"; 
		}
		else 
		{
			// springboot pod spodem wywoluje implementacje tej metody (UserSerwiceImpl) kozysta z UserRepository i wywoluje nasze query z klasy UserRepository 
			userService.updateUserPassword(user.getNewPassword(), user.getEmail());                          // z usera chwytamy email i stad spring security wie kogo ta zmiana hasla dotyczy ... usera bierzemy z klasy  UserUtilities
			returnPage = "editpassword"; 																	 // jak wszystko jest ok to ustawiamy strone powrotna na editpassword - czyli na ta sama co zmienialismy haslo i ...
			model.addAttribute("message", messageSource.getMessage("passwordChange.success", null, locale)); // ... i... w editpassword.jsp w "${message}" wyswietlamy komunikat ze chaslo zostalo zmienione pomyslnie 
		}
		return returnPage;
	}
	

	/*
	 * wyswietlenie formularza 
	 */
	@GET
	@RequestMapping(value = "/editprofil")
	public String changeUserData(Model model) {
		String username = UserUtilities.getLoggedUser(); 	 // odczytujemy kto jest zalogowany 
		User user = userService.findUserByEmail(username);   // i siegamy po dane tego uzytkownika 
		model.addAttribute("user", user);					 // przekazujemy te dane do formularza 
		return "editprofil";
	}
	

	
	@POST
	@RequestMapping(value = "/updateprofil")
	public String changeUserDataAction(User user, BindingResult result, Model model, Locale locale) {
		String returnPage = null;
		new EditUserProfileValidator().validate(user, result);  // po odebraniu danych z formularza ... 
																// validator sprawdza czy wszystkie pola zostaly wypelnione 
																// a przede wszystkim czy podany email (ktory zmieniamy ) pasuje do wzorca 
		if (result.hasErrors()) {
			returnPage = "editprofil"; // jak sa bledy formularza to wracamy do strony editprofile.jsp ( czyli tam gdzie zmieniamy dane ) 
		}
		else 
		{   // jezeli nie ma bledow to wywolujemy metode updateUserProfile i odczytujemy dane ( nowe dane !) w sposob podany ponizej 
			//(w ten sposob bo wraca obiekt user - a konkretnie wracaja dane ktore zostaja podpiete 
			//pod odpowiednie pola w klasie User - ktore dostaja nowe wartosci... chodzi o to ze pod spodem zostaly wywolane metody setName, setLastName, setEmail )
			userService.updateUserProfile(user.getName(), user.getLastName(), user.getEmail(), user.getId()); 
			model.addAttribute("message", messageSource.getMessage("profilEdit.success", null, locale)); // wyswietlenie komunikatu
			returnPage = "afteredit"; // jak wszytko ok to przekierowuje nas tutaj 
		}
		return returnPage;
	}

}