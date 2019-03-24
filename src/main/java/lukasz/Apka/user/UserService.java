package lukasz.Apka.user;

import java.util.List;

public interface UserService {

	public User findUserByEmail(String email); // wyszukuje uzytkownika po emailu

	public void saveUser(User user);          // zapisywanie uzytkownika

	public void updateUserPassword(String newPassword, String email);

	public void updateUserProfile(String newName, String newLastName, String newEmail, int id);

	public List<User> findAll();            // dajemy to do ilmplementacji UserServiceImpl

}
