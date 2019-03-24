package lukasz.Apka.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppdemoUtils {

	/**
	 * uniwersalna metoda ktora sprawdza czy email i password uzytkownika jest
	 * poprawny tzn. czy pasuje do wzorca z klasy AppDemoConstants
	 */
	public static boolean checkEmailOrPassword(String pattern, String pStr) {

		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(pStr);
		return m.matches(); // zwraca tru albo false - czy pasuje czy nie
	}

}
