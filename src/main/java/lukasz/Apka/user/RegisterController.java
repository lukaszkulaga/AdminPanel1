package lukasz.Apka.user;

import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import lukasz.Apka.validators.UserRegisterValidator;

@Controller
public class RegisterController {

	/*
	 * USER modyfikuje dane za pomoca email a admin w swoim kontrolerze za pomoca id
	 * zadaniem jest wyswietlenie formularza
	 */

	@Autowired
	private UserService userService;

	@Autowired
	MessageSource messageSource;

	@GET
	@RequestMapping(value = "/register")
	public String registerForm(Model model) {
		User u = new User();
		model.addAttribute("user", u);

		return "register";
	}

	
	
	
	
	/*	
	 * user - do tego przypisane bedzie to co odbierzemy z formularza ,
	 * BindingResult - obsluga bledow , potrzebne do walidACJI bo z walidacji trafia
	 * tutaj bledy, sprawdzenie czy dane sa kompletne (wiazanie pomiedzy atrybutami
  	 * obiektu a polami w formularzu) ,
	 * Model - przy jego pomocy wrzucimy sobie dane po rejestracji , 
	 * Locale -pozwala posluzyc sie messagesource
	 * */
	
	
	@POST 
	@RequestMapping(value = "/adduser")

	public String registerAction(User user, BindingResult result, Model model, Locale locale) {

		String returnPage = null; // ta zmienna przechowuje nazwe strony która zostanie zwrucona po rejestracji
									// niezaleznie od tego czy sa bledy czy nie

		// hibernate sprawdza czy uzytkownik o podanym adresie email w formularzu - czy
		// taki uzytkownik juz istnieje, defakto czy taki email juz istnieje / jest
		// zarejestrowany
		User userExist = userService.findUserByEmail(user.getEmail());

		new UserRegisterValidator().validateEmailExist(userExist, result); // walidator - tutaj zeczywiscie sprawdzamy
																			// czy email jest juz zarejestrowany - wynik
																			// przekazujemy do walidatora czyli do
																			// UserRegisterValidator i wywolujemy metode
																			// validateEmailExist

		// wywolanie walidatora z klasy UserRegisterValidator
		// w tym validatorze sprawdzamy czy formularz zostal wypelniony
		new UserRegisterValidator().validate(user, result); // ten walidator jest wywolywany jezeli nie trafi zaden blad
															// do Validatora powyżej czyli jezeli wszystkie dane sa poprawne
														

		// tutaj sprawdzamy czy BindingResult z walidatora zlapal jakies bledy -
		// jezeli tak to z automatu wracamy na strone "register"
		if (result.hasErrors()) {
			returnPage = "register";
		} else {
			// jezeli nie ma bledow to wywolujemy metode zapisu uzytkownika do bazy
			userService.saveUser(user);
			model.addAttribute("message", messageSource.getMessage("user.register.success", null, locale));
			model.addAttribute("user", new User()); 
												

			returnPage = "register";
		}

		return returnPage;
	}

}
