package lukasz.Apka.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import lukasz.Apka.constants.AppDemoConstants;
import lukasz.Apka.user.User;
import lukasz.Apka.utilities.AppdemoUtils;

public class EditUserProfileValidator implements Validator {

	@Override
	public boolean supports(Class<?> cls) {
		return User.class.equals(cls);
	}

	/**
	 * ten walidator validuje tylko imie nazwisko i email
	 */

	@Override
	public void validate(Object obj, Errors errors) {
		User u = (User) obj;

		ValidationUtils.rejectIfEmpty(errors, "name", "error.userName.empty");
		ValidationUtils.rejectIfEmpty(errors, "lastName", "error.userLastName.empty");
		ValidationUtils.rejectIfEmpty(errors, "email", "error.userEmail.empty");

		// jezeli email nie jest pusty to sprawdza czy odpowiada on wzorcowi
		if (!u.getEmail().equals(null)) {
			boolean isMatch = AppdemoUtils.checkEmailOrPassword(AppDemoConstants.emailPattern, u.getEmail());
			if (!isMatch) {
				errors.rejectValue("email", "error.userEmailIsNotMatch");
			}
		}
	}
}
