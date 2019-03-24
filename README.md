# AdminPanel1
- aplikacja służy do administrowania bazą danych. Obecnie jest na etapie rozbudowy. 


I. Technologie:

Do napisania aplikacji użyłem: Java8, MySQL, Spring Boot, Spring Data, Spring Security, JSP 


II. Funkcje aplikacji:

1. Możemy się zarejestrować jako zwykły użytkownik lub jako administrator (logujemy się na konto administratora: username: jan88@wp.pl , password: Janek88!)

2. Jako administrator możemy:

a) importować z pliku znajdującego się na dysku listę nowych użytkowników 
b) przeglądać listę dodanych użytkowników (paginacja x 10)
c) znaleźć użytkownika po jego danych personalnych
d) usuwać użytkowników
e) zmieniać rolę i aktywność użytkownika
f) edytować swoje dane personalne oraz zmieniać hasło

3. Jako użytkownik możemy:

a) zmieniać swoje dane personalne oraz hasło

4. Aplikacja:

a) sprawdza poprawność wprowadzanych danych
b) szyfruje hasło


III. Uruchomienie:

1.Do projektu dodałem 3 commity "Baza do projektu AdminPanel1"

2.Należy utworzyć baze "mojaBaza" a w niej tabele takie jak zaprezentowałem w commitach 
"Baza do projektu AdminPanel1" wraz z zawartością

3. Pobieramy aplikacje

4. w pliku "Aplication.properties" ustawiamy połączenie z bazą danych

5.Uruchamiamy aplikację

6. w przegladarce wpisujemy http://localhost: + (u mnie 8080)

7. logujemy się na konto administratora: username: jan88@wp.pl , password: Janek88!
