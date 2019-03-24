package lukasz.Apka.mainControllerr;

import javax.ws.rs.GET;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainPageController {

	private static final Logger LOG = LoggerFactory.getLogger(MainPageController.class);

	@GET
	@RequestMapping(value = { "/", "/index" })
	public String showMainPage() { // zamiast info mozemy dac debug
		LOG.info("**** WYWOŁANO > showMainPage()");
		return "index";
	}

}
