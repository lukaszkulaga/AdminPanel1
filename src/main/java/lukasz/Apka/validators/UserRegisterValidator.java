package lukasz.Apka.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import lukasz.Apka.constants.AppDemoConstants;
import lukasz.Apka.user.User;
import lukasz.Apka.utilities.AppdemoUtils;

/**
 * WALIDATOR
 *
 * ta implementacja powoduje ze ta klasa jest walidatorem przy prubie
 * zapisywania danych z formularza
 *
 */

public class UserRegisterValidator implements Validator {

	/*
	 * ta metod jako argument przyjmuje klase - klase bedziemy przekazywac z kontrolera
	 * 
	 */

	@Override
	public boolean supports(Class<?> cls) {

		return User.class.equals(cls);
	}

	/*
	 * to jest nasz walidator ktory pracuje na obiekcie ktory tutaj tworzymy i
	 * kastujemy to co trafia do tej metody na obiekt User czyli rzutujemy z
	 * ogolnego obiektu na konkretny obiekt User
	 */

	@Override
	public void validate(Object obj, Errors errors) {
		User u = (User) obj;

		// rejectIfEmpty - wyrzuc cos na zewnatrz jezeli obiekt ktory podajemy jest pusty
		// errors szuka w formularzu (register.jsp) np. name i wyswietli nam komunikat
		// jezeli nie podamy w formularzu name-imienia
		ValidationUtils.rejectIfEmpty(errors, "name", "error.userName.empty");
		ValidationUtils.rejectIfEmpty(errors, "lastName", "error.userLastName.empty");
		ValidationUtils.rejectIfEmpty(errors, "email", "error.userEmail.empty");
		ValidationUtils.rejectIfEmpty(errors, "password", "error.userPassword.empty");

		// jezeli email nie jest pusty to wywolujemy wzorzec
		if (!u.getEmail().equals(null)) { 
			boolean isMatch = AppdemoUtils.checkEmailOrPassword(AppDemoConstants.emailPattern, u.getEmail());
			if (!isMatch) // jezeli niezgodne ze wzorcem
			{
				errors.rejectValue("email", "error.userEmailIsNotMatch");
			}
		}

		if (!u.getPassword().equals(null)) 
		{ 
			// wzorzec - czyli wymagania dla hasla np. jedna duza litera itd.
			boolean isMatch = AppdemoUtils.checkEmailOrPassword(AppDemoConstants.passwordPattern, u.getPassword());
			if (!isMatch) {
				errors.rejectValue("password", "error.userPasswordIsNotMatch");
			}
		}

	}

	// sprawdzamy czy user jest pusty
	public void validateEmailExist(User user, Errors errors) {
		if (user != null) {
			errors.rejectValue("email", "error.userEmailExist");
		}
	}

}
