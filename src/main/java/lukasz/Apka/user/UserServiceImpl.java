package lukasz.Apka.user;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("userService") // service - usluga dostepu do warstwy danych 
						// (jest to powiazane z aplication.properties ... z linikami z #DATA SOURCE I BAZA DANYCH)                      
@Transactional // włacza nam transakcje - chodzi o zapis danych 
			   // (jezeli pierwszy zapis sie nie powiedzie to drugi nie bedzie uruchomiony natomiast 
               // jezeli pierwszy sie uda a drugi nie to adnotacja @Transactional wycofa pierwszy zapis z bazy danych  
public class UserServiceImpl implements UserService {
	
	@Autowired // tworzy nam beana i wstrzykuje tam gdzie trzeba 
	private UserRepository userRepository; 
	@Autowired
	private RoleRepository roleRepository; 
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder; 

	
	// to nam słuzy do walidacji (mamy ja w kontrolerze ) - czyli do sprawdzenia czy uzytkownik wpisal istniejacy email czy nie 
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email); // ta metoda sprawdza czy istnieje uzytkownik o danym email 
												  //...jezeli tak to wypełni nam obiekt User danymi i ten obiekt zwroci nam do kontrolera ( w którym mamy walidacje ) 
		                                          //... jezeli obiekt nie istnieje to zwroci nam null
	}

	
	/*
	 * zapis uzytkownika 
	 * 
	 */
	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); // pobieramy haslo ktore uzytkownik wpisał ... przekazujemy je do metody encode ktora szyfruje to haslo i za pomoca metody setPassword 
																			// ustawiamy nowa wartosc hasla która jest zapisywana do uzytkownika user
		user.setActive(1);                                                  // sprawdzanie czy uzytkownik jest aktywny czyli czy moze sie zalogowac czy jeszcze nie 
																			// (1 - oznacza ze jest aktywny i moze sie uzytkownik zalogowac  a 0 ze jest nie aktywny i nie moze sie zalogowac)
											
		Role role = roleRepository.findByRole("ROLE_USER"); 
		user.setRoles(new HashSet<Role>(Arrays.asList(role))); 
		userRepository.save(user); 
	}


	/*
	 * zmiana hasła uzytkownika
	 * 
	 */
	@Override
	public void updateUserPassword(String newPassword, String email) {
		userRepository.updateUserPassword(bCryptPasswordEncoder.encode(newPassword), email);
		
	}


	/**
	 * pobiera nam dane zalogowanego uzytkownika ... wstawia nam nowe dane 
	 */
	@Override
	// ta metode wywolujemy z controlera czyli w klasie ProfilController  
	public void updateUserProfile(String newName, String newLastName, String newEmail, int id) {
		userRepository.updateUserProfile(newName, newLastName, newEmail, id);
	}


	/**
	 * ta metoda siegnie po obiekty klasy User 
	 */
	@Override
	public List<User> findAll() {
		List<User> userList = userRepository.findAll();
		return userList;
	}
	
}
