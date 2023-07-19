package it.petshop.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileUtil {

	// Path.of(getServletContext().getRealPath(getServletContext().getInitParameter("imgProdottiPath"))
	// Path destinazione = Path.of(getServletContext().getRealPath(getServletContext().getInitParameter("imgElementiPath")), elemento);
	
	public void salvaImmagine(String ori, String dest) {
		Path origine = Path.of(ori);
		Path destinazione = Path.of(dest);
		try {
			Files.copy(origine, destinazione, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new PetShopException("Errore I/O: impossibile salvere immagine", 500, e);
		}
	}
}
