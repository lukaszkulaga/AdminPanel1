package lukasz.Apka.admin;

import org.springframework.web.multipart.MultipartFile;

/*
 * ta klasa reprezentuje plik na formularzu 
 * utworzylismy strone jsp importUsers w ktorej jest pole do ktorego przekazujemy nasz plik  
 */


public class FileUpload {
	
	private MultipartFile filename;

	public MultipartFile getFilename() {
		return filename;
	}

	public void setFilename(MultipartFile filename) {
		this.filename = filename;
	}
}
