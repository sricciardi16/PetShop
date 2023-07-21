package it.petshop.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.ServletContext;
import javax.servlet.http.Part;

public class FileUtil {

	public static final String PRODOTTI_PATH = "imgProdottiPath";
	public static final String ELEMENTI_PATH = "imgElementiPath";
	
    public static String saveFileToProdottiPath(ServletContext context, Part sourcePart) {
        try {
            String prodottiPath = context.getRealPath(context.getInitParameter(PRODOTTI_PATH));
            Path destinationPath = Paths.get(prodottiPath, Paths.get(sourcePart.getSubmittedFileName()).getFileName().toString());
            Files.copy(sourcePart.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            return destinationPath.getFileName().toString();
        } catch (IOException e) {
            throw new PetShopException("Errore durante il salvataggio del file", 500, e);
        }
    }

    public static String copyFileFromProdottiToElementiPath(ServletContext context, String sourceFileName, String destinationFileName) {
        try {
            String prodottiPath = context.getRealPath(context.getInitParameter(PRODOTTI_PATH));
            String elementiPath = context.getRealPath(context.getInitParameter(ELEMENTI_PATH));
            Path sourcePath = Paths.get(prodottiPath, sourceFileName);
            Path destinationPath = Paths.get(elementiPath, destinationFileName);
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            return destinationPath.getFileName().toString();
        } catch (IOException e) {
            throw new PetShopException("Errore durante la copia del file " + e.getMessage(), 500, e);
        }
    }
}
