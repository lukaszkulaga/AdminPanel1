package lukasz.Apka.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * repository - podpowiada hibernetowi w jaki sposob ma twozyc automatycznie zapytania
 */

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer> {

	public Role findByRole(String role); // ta metoda bedzie wykorzystywana do sprawdzenia czy juz jakas rola jest w
											// bazie zarejestrowana

}
