package lukasz.Apka.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;





/*
 *  repository - podpowiada hibernetowi w jaki sposob ma twozyc automatycznie zapytania
 *  podajemy w nawiasie nazwe repozytorium zeby pokazac hibernetowi pozniej 
 *  w implementacji czego on ma szukac (hibernate uzyje sobie tej nazwy do generowania zapytan)                  
 */
@Repository("userRepository")					    
public interface UserRepository extends JpaRepository<User, Integer>
{

	public User findByEmail(String email); // ta metoda bedzie wykozytywana do sprawdzenia czy juz jakis email jest w bazie zarejestrowany 

	// dzieki temu hibernate zmieni tylko haslo dla okreslonego uzytkownika 
	@Modifying 
	@Query("UPDATE User u SET u.password = :newPassword WHERE u.email= :email") 
	
					// @Param - chwytanie powyzszych parametrow nastepnie podajemy co ma byc w to miejsce wstawione czyli podajemy nazwy zmiennych np. String password , String email 
	public void updateUserPassword(@Param("newPassword") String password, @Param("email") String email); // to zostanie wywolane jak klikniemy na stronie przycisk zmien haslo 
	                                                                                                     //... String password, String email - te parametry beda wstawiane/wywolywane 
	                                                                                                     // w controlerze czyli w klasie ProfilController     
	

	// update/pobranie danych uzytkownika  - tutaj laduja dane pobrane z formularza logowania sie 
	
	@Modifying                        // ustawiamy nowe dane ktore odbierzemy z formularza      // pobieramy id z formularza ktorym poslugujemy sie aby wskazac który rekord (o jakim id) chcemy zaktualizowac
	@Query("UPDATE User u SET u.name = :newName, u.lastName = :newLastName, u.email = :newEmail WHERE u.id= :id")
	public void updateUserProfile(@Param("newName") String newName, @Param("newLastName") String newLastName,
			@Param("newEmail") String newEmail, @Param("id") Integer id);

}
