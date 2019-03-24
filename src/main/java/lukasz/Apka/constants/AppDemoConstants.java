package lukasz.Apka.constants;

public class AppDemoConstants {
	
	/*
	 * 	 sprawdzamy czy email i password spelniaja wymagania
	 */
	public static final String emailPattern = "^[a-zA-z0-9]+[\\._a-zA-Z0-9]*@[a-zA-Z0-9]+{2,}\\.[a-zA-Z]{2,}[\\.a-zA-Z0-9]*$";
	public static final String passwordPattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\!\\@\\#\\$\\*])(?!.*\\s).{8,12}$";

}
