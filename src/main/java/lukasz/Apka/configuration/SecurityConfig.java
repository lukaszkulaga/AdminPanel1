package lukasz.Apka.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/*
 * 
 * KONFIGURACJA SPRING SECURITY
 * 
 * w tej klasie okreslilismy na jakich zasadach beda udostepniane zasoby / w jaki sposob beda obsługiwane 
 * poszczegolne wywolania naszej aplikacji webowej tzn. czy dany link
 * ma byc dostepny dla wszystkich i czy ma sie wywolac wszystko co jest w linku
 * czy ma to byc dostep autoryzowany czyli dostep tylko dla uzytkownika zalogowanego i ktory ma odpowiednie uprawnienia     
 * 
 * */

@Configuration
@EnableWebSecurity // bedziemy sterowac bezpieczenstwem takze z poziomu aplikacji webowej ( jsp )
					// przy pomocy adnotacji securit ktore bedziemy umieszczac w kontrolerach

@EnableGlobalMethodSecurity(securedEnabled = true) // obsluga wszystkich adnotacji @Secured z klasy AdminPageController
													// i wszystkich pozostałych klas

public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder bcp; // koduje i rozkodowuje hasla

	@Autowired
	private DataSource ds;

	@Value("${spring.queries.users-query}")
	private String usersQuery; // zostanie pobrany uzytkownik, hasło i czy uzytkownik jest aktywny

	@Value("${spring.queries.roles-query}")
	private String rolesQuery; // pobiera nam role danego uzytkownika - i na podstawie ról bedziemy udostepniac
								// pewne rzeczy np. to co bedzie w katalogu admin bedzie dostepne tylko dla
								// kogos kto ma role administratora natomiast cała reszta bedzie dostepna dla
								// uzytkownikow a niektore rzeczy dla tych ktorzy nie sa nawet zalogowani

	/*
	 * konfiguracja
	 * 
	 * buduje nam mechanizm uwierzytelniania tych którzy sie loguja - sprawdza kto
	 * jaka ma role i czy jest zalogowany
	 * 
	 */

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().usersByUsernameQuery(usersQuery).authoritiesByUsernameQuery(rolesQuery).dataSource(ds)
				.passwordEncoder(bcp);
	}

	/*
	 * tutaj dostarczamy naszemu spring security pewne zasady ktore musza byc
	 * spelnione w przypadku wywolywania pewnych rzeczy HttpSecurity -
	 * odpowiedzialne za bezpieczenstwo wykonywania wywołan - to nam pilnuje jakie
	 * wywolania sa dostepne dla wszystkich a jakie dla zalogowanych z
	 * uwzglednieniem ich roli
	 * 
	 */

	protected void configure(HttpSecurity httpSec) throws Exception {
		httpSec.authorizeRequests() // zadanie autoryzacji
				// ponizej adresy URL
				.antMatchers("/").permitAll() // dostepne dla wszystkich
				.antMatchers("/index").permitAll() // dostepne dla wszystkich
				.antMatchers("/login").permitAll() // dostepne dla wszystkich
				.antMatchers("/register").permitAll() // dostepne dla wszystkich
				.antMatchers("/adduser").permitAll() // dostepne dla wszystkich
				//.antMatchers("/admin").hasAuthority("ROLE_ADMIN") // zawartosc tego katalogu moze wyswietlic ten kto ma role administratora - juz nam to nie potrzebne bo  sterujemy dostepem do metod administratora z poziomu klasy AdminPageController
				.anyRequest().authenticated() // wszystko co potrzebuje autentykacji - chodzi o kogos kto jest
												// zalogowany i kto ma okreslona role
				.and().csrf().disable().formLogin() // formularz logowania
				.loginPage("/login") // wywolanie tej strony bedzie wykonywane zawsze kiedy bedziemy probowali dobrac
										// sie do np. .antMatchers("/admin").hasAuthority("ROLE_ADMIN") albo do innego
										// url ktory wymaga autoryzacji - przekierowuje nas do strony logowania ..jak
										// bedzie zły login to wywola sie ta metoda ponizej
				.failureUrl("/login?error=true") // jak bedzie zły login to wywola sie ta strona/url
				.defaultSuccessUrl("/").usernameParameter("email") // jezeli logowanie zakonczy sie sukcesem a wiec gdy
																	// email i password bedzie pasował to zostaniemy
																	// przekierowni na strone glowna
				.passwordParameter("password") // jezeli logowanie zakonczy sie sukcesem a wiec gdy password i email
												// bedzie pasował to zostaniemy przekierowani na strone glowna
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // niszczenie sesji czyli
																							// wylogowanie uzytkownika
				.logoutSuccessUrl("/") // jezeli wylogowanie sie powiedzie to przekieruje nas na strone glowna
				.and().exceptionHandling().accessDeniedPage("/denied"); // jezeli jestesmy zalogowani z rola user a
																		// probujemy dobrac sie do admina to wywola nam
																		// sie strona /denied - dostep zabroniony
	}

	/*
	 * okresla nam co nasz spring security ma zignorowac jesli chodzi o
	 * bezpieczenstwo - spring security nie bedzie wymagal autentykacji do
	 * stron/katalogow -
	 * /resources/**", "/statics/**", "/css/**", "/js/**", "/images/**", "/incl/**
	 * 
	 */
	public void configure(WebSecurity webSec) throws Exception {
		webSec.ignoring() // css-arkusze stylow, js -javascript , incl - pozostale pliki dolaczane do jsp
				.antMatchers("/resources/**", "/statics/**", "/css/**", "/js/**", "/images/**", "/incl/**");
	}

}
