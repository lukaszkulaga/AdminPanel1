package lukasz.Apka.admin;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lukasz.Apka.user.User;
import lukasz.Apka.user.UserService;
import lukasz.Apka.utilities.UserUtilities;

@Controller
public class AdminPageController {

//	@Autowired
//	private UserService userService;

	private static final Logger LOG = LoggerFactory.getLogger(AdminPageController.class);

	@Autowired
	private AdminService adminService;

	private static int ELEMENTS = 10;

	@Autowired
	private MessageSource messageSource;

	@GET
	@RequestMapping(value = "/admin")
	@Secured(value = { "ROLE_ADMIN" })
	public String openAdminMainPage() {
		return "admin/admin";
	}

	@GET
	@RequestMapping(value = "/admin/users/{page}")
	@Secured(value = { "ROLE_ADMIN" })
	public String openAdminAllUsersPage(@PathVariable("page") int page, Model model) {

		LOG.info("**** WYWOLANO > openAdminAllUsersPage(" + page + "," + model + ")");

		Page<User> pages = getAllUsersPageable(page - 1, false, null);
		int totalPages = pages.getTotalPages();
		int currentPage = pages.getNumber();
		List<User> userList = pages.getContent();
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", currentPage + 1);
		model.addAttribute("userList", userList);
		model.addAttribute("recordStartCounter", currentPage * ELEMENTS);
		return "admin/users";
	}

	/*
	 * 
	 * metoda pobierająca liste użytkowników
	 * 
	 */

	private Page<User> getAllUsersPageable(int page, boolean search, String param) {
		Page<User> pages;
		if (!search) {
			pages = adminService.findAll(PageRequest.of(page, ELEMENTS));
		} else {
			pages = adminService.findAllSearch(param, PageRequest.of(page, ELEMENTS));
		}
		for (User users : pages) {
			int numerRoli = users.getRoles().iterator().next().getId();
			users.setNrRoli(numerRoli);
		}
		return pages;
	}

	/*
	 * za pomoca tej metody modyfikujemy role i aktywnosc uzytkownika
	 * 
	 */

	@GET
	@RequestMapping(value = "/admin/users/edit/{id}")
	@Secured(value = { "ROLE_ADMIN" })
	public String getUserToEdit(@PathVariable("id") int id, Model model) {

		User user = new User();
		user = adminService.findUserById(id);
		Map<Integer, String> roleMap = new HashMap<Integer, String>();
		roleMap = prepareRoleMap();
		Map<Integer, String> activityMap = new HashMap<Integer, String>();
		activityMap = prepareActivityMap();
		int rola = user.getRoles().iterator().next().getId();
		user.setNrRoli(rola);
		model.addAttribute("roleMap", roleMap);
		model.addAttribute("activityMap", activityMap);
		model.addAttribute("user", user);
		return "admin/useredit";
	}

	/*
	 * przygotowanie mapy ról
	 * 
	 */

	public Map<Integer, String> prepareRoleMap() {
		Locale locale = Locale.getDefault();
		Map<Integer, String> roleMap = new HashMap<Integer, String>();
		roleMap.put(1, messageSource.getMessage("word.admin", null, locale)); // dla 1 wkładamy admina
		roleMap.put(2, messageSource.getMessage("word.user", null, locale)); // dla 2 wkladamy uzytkownika
		return roleMap;
	}

	/*
	 * przygotowanie mapy aktywny/nieaktywny
	 *
	 */

	public Map<Integer, String> prepareActivityMap() {
		Locale locale = Locale.getDefault();
		Map<Integer, String> activityMap = new HashMap<Integer, String>();
		activityMap.put(0, messageSource.getMessage("word.nie", null, locale));
		activityMap.put(1, messageSource.getMessage("word.tak", null, locale));
		return activityMap;
	}

	/*
	 * 
	 * za pomoca transakcji w klasie AdminServiceImpl wstawiamy nowe dane
	 * zmodyfikowane w metodzie getUserToEdit do bazy danych nastepnie odczytujemy
	 * te dane po kliknieciu zapisz ... zadziala metoda updateUser z klasy
	 * AdminServiceImpl
	 * 
	 */

	@POST
	@RequestMapping(value = "/admin/updateuser/{id}")
	@Secured(value = "ROLE_ADMIN")
	public String updateUser(@PathVariable("id") int id, User user) {
		int nrRoli = user.getNrRoli();
		int czyActive = user.getActive();
		adminService.updateUser(id, nrRoli, czyActive);
		return "redirect:/admin/users/1";
	}

	@GET
	@RequestMapping(value = "/admin/users/search/{searchWord}/{page}")
	@Secured(value = "ROLE_ADMIN")
	public String openSearchUsersPage(@PathVariable("searchWord") String searchWord, @PathVariable("page") int page,
			Model model) {

		LOG.info("**** WYWOLANO > openAdminAllUsersPage(" + searchWord + "," + page + "," + model + ")");
		Page<User> pages = getAllUsersPageable(page - 1, true, searchWord);
		int totalPages = pages.getTotalPages();
		int currentPage = pages.getNumber();
		List<User> userList = pages.getContent();
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", currentPage + 1);
		model.addAttribute("userList", userList);
		model.addAttribute("recordStartCounter", currentPage * ELEMENTS);
		model.addAttribute("searchWord", searchWord);
		model.addAttribute("userList", userList);
		return "admin/usersearch";
	}

	@GET
	@RequestMapping(value = "/admin/users/importusers")
	@Secured(value = "ROLE_ADMIN")
	public String showUploadPageFromXML(Model model) {
		return "admin/importusers";
	}

	/*
	 * po wczytaniu pliku w formularzu i po parsowaniu go w metodzie w klasie
	 * UserUtilities... za pomoca tej metody przejmujemy ten plik i przesylamy go do
	 * serwera (siegnie na nasz dysk i przesle plik na serwer a nastepnie my ten
	 * plik odczytujemy i przekazemy do parsowania w klasie UserUtilities )
	 * 
	 */

	@POST
	@RequestMapping(value = "/admin/users/upload")
	@Secured(value = "ROLE_ADMIN")
	public String importUsersFromXML(@RequestParam("filename") MultipartFile mFile) {

		String uploadDir = System.getProperty("user.dir") + "/uploads";
		System.out.println(uploadDir);
		// sprawdzamy czy katalog /upload istnieje ... jezeli nie to go tworzymy
		File file;
		try {
			file = new File(uploadDir);
			if (!file.exists()) {
				file.mkdir();
			}

			// odbieramy plik z formularza ...odczytujemy jego nazwe i sciezke... pokazujemy
			// gdzie ma byc plik zapisany , pobieramy oryginalna nazwe tego pliku
			// jak juz uporalismy sie z katalogiem to budujemy sciezke i pobieramy
			// oryginalna nazwe pliku
			Path fileAndPath = Paths.get(uploadDir, mFile.getOriginalFilename());
			Files.write(fileAndPath, mFile.getBytes());
			// odczytujemy plik i pokazujemy jego zawartosc
			file = new File(fileAndPath.toString());
			List<User> userList = UserUtilities.usersDataLoader(file);
			adminService.insertInBatch(userList);
			file.delete();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/users/1";
	}

	/*
	 * 
	 * metoda do usuwania użytkownika
	 * 
	 */

	@DELETE
	@RequestMapping(value = "/admin/users/delete/{id}")
	@Secured(value = "ROLE_ADMIN")
	public String deleteUser(@PathVariable("id") int id) {
		LOG.debug("[WYWOŁANIE >>> AdminPageController.deleteUser > PARAMETR: " + id);
		adminService.deleteUserById(id);
		return "redirect:/admin/users/1";
	}

}
