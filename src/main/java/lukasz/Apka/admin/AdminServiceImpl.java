package lukasz.Apka.admin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lukasz.Apka.user.Role;
import lukasz.Apka.user.RoleRepository;
import lukasz.Apka.user.User;

/*
 *tutaj wywolujemy transakcje 
 *
 * */

@Service("adminService")
@Transactional // ta adnotacja zapewnia nam transakcyjnosc w przypadku wykonywania wiecej niz
				// jednego updte,insert itd. ... chodzi o to ze wszystkie musza sie poprawnie
				// wykonac ... jezeli nie to stan bazy danych wraca do pierwotnej postaci
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private JpaContext jpaContext;

	private static final Logger LOG = LoggerFactory.getLogger(AdminServiceImpl.class);

	/*
	 * tutaj generujemy zapytanie ktore przygotuje nam tabelki pod stronnicowanie
	 */
	@Override
	public Page<User> findAll(Pageable pageable) {
		Page<User> userList = adminRepository.findAll(pageable);
		return userList;
	}

	/*
	 * ta metoda jest potrzebna do otwarcia formularza useredit.jsp i wypelnienia go
	 * danymi
	 * 
	 */
	@Override
	public User findUserById(int id) {
		User user = adminRepository.findUserById(id); // transakcja
		return user;
	}

	// wywolujemy dwie transakcje
	@Override
	public void updateUser(int id, int nrRoli, int activity) {
		adminRepository.updateActivationUser(activity, id); // jezeli ta transakcja sie powiedzie a ta nizej nie to ta
															// pierwsza zostanie wycofana ... jezeli ta sie nie wykona
															// to nastepna tez nie ... to zapewnia nam adnotacja
															// @Transactional
		adminRepository.updateRoleUser(nrRoli, id);
	}

	@Override
	public Page<User> findAllSearch(String param, Pageable pageable) {
		Page<User> userList = adminRepository.findAllSearch(param, pageable);
		return userList;
	}

	/*
	 * importowanie listy użytkowników
	 */

	@Override
	public void insertInBatch(List<User> userList) {
		// EntityManager - tutaj zarzadza porcjami danych , jpaContext - wskazuje na
		// context naszej aplikacji czyli na polaczenie/adres strony jsp
		EntityManager em = jpaContext.getEntityManagerByManagedType(User.class); // wskazujemy naszemu EntityManager
																					// jaka klasa jest nasza encja
																					// (czyli na bazie ktorej klasy
																					// bedzie odbywał sie INSERT)

		for (int i = 0; i < userList.size(); i++) {
			User u = userList.get(i);
			Role role = roleRepository.findByRole("ROLE_USER"); // ustawiamy odpowiednia role dla urzytkownika
			u.setRoles(new HashSet<Role>(Arrays.asList(role)));
			u.setPassword(bCryptPasswordEncoder.encode(u.getPassword())); // w przychodzacej liscie haslo jest jawne
																			// wiec je szyfrujemy....

			em.persist(u); // ... i wrzucamy do bazy dane

			if (i % 10 == 0 && i > 0) {
				em.flush(); // pchamy do bazy danych te 10 rekordow
				em.clear(); // czyscimy EntityManagera zeby mozna bylo pchnac nastepna porcje danych ...
				System.out.println("**** Załadowano " + i + " rekordów z " + userList.size());
			}
		}
	}

	@Override
	public void deleteUserById(int id) {
		LOG.debug("[WYWOŁANIE >>> AdminServiceImpl.deleteUserById > PARAMETR: " + id);
		LOG.debug("[WYWOŁANIE >>> AdminRepository.deleteUserFromUserRole > PARAMETR: " + id);
		adminRepository.deleteUserFromUserRole(id);
		LOG.debug("[WYWOŁANIE >>> AdminRepository.deleteUserFromUser > PARAMETR: " + id);
		adminRepository.deleteUserFromUser(id);
	}

}
