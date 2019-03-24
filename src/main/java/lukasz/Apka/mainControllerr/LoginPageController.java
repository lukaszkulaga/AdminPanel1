package lukasz.Apka.mainControllerr;

import javax.ws.rs.GET;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * 
 * ten kontroler wyswietla tylko strone logowania
 * proces logowania jest kontrolowany przez spring security bo to on lapie nam akcje z jsp/login
 * */

@Controller
public class LoginPageController {

	@GET
	@RequestMapping(value = "/login")
	public String showLoginPage() {
		return "login";
	}

}
