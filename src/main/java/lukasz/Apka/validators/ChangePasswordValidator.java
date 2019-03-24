package lukasz.Apka.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import lukasz.Apka.constants.AppDemoConstants;
import lukasz.Apka.user.User;
import lukasz.Apka.utilities.AppdemoUtils;

public class ChangePasswordValidator implements Validator {

	@Override
	public boolean supports(Class<?> cls) {
		return User.class.equals(cls);
	}

	@Override
	public void validate(Object obj, Errors errors) {

		@SuppressWarnings("unused")
		User u = (User) obj;

		ValidationUtils.rejectIfEmpty(errors, "newPassword", "error.userPassword.empty");

	}

	// newPass - nowe haslo w formularzu
	public void checkPasswords(String newPass, Errors errors) {

		if (!newPass.equals(null)) {
			// wzorzec hasla // newPass - nowe haslo ktore bedzie porownywane ze wzorcem
			boolean isMatch = AppdemoUtils.checkEmailOrPassword(AppDemoConstants.passwordPattern, newPass);
			if (!isMatch) {
				errors.rejectValue("newPassword", "error.userPasswordIsNotMatch");

			}
		}
	}
}
