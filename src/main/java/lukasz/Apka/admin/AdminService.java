package lukasz.Apka.admin;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lukasz.Apka.user.User;
import lukasz.Apka.utilities.UserUtilities;

public interface AdminService {

	Page<User> findAll(Pageable pageable); // dzielenie na strony to co sobie pobierzemy z bazy danych
	User findUserById(int id);
	void updateUser(int id, int nrRoli, int activity); // ta metoda obsluguje dwa zapytania z AdmiRepository
														// updateActivationUser i updateRoleUser
	Page<User> findAllSearch(String param, Pageable pageable);
	void insertInBatch(List<User> userList); // przekazujemy liste uzytkownikow ktora odbieramy w AdminPageController w
												// metodzie importUsersFromXML w linice List<User> userList =
												// UserUtilities.usersDataLoader(file);
	void deleteUserById(int id);

}
