package it.petshop.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.ServletContext;
import javax.servlet.http.Part;

public class FileUtil {

    public static String saveFileToProdottiPath(ServletContext context, Part sourcePart) throws IOException {
        String prodottiPath = context.getRealPath(context.getInitParameter("imgProdottiPath"));
        Path destinationPath = Paths.get(prodottiPath, Paths.get(sourcePart.getSubmittedFileName()).getFileName().toString());
        Files.copy(sourcePart.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
        return destinationPath.getFileName().toString();
    }

    public static String copyFileFromProdottiToElementiPath(ServletContext context, String sourceFileName, String destinationFileName) throws IOException {
        String prodottiPath = context.getRealPath(context.getInitParameter("imgProdottiPath"));
        String elementiPath = context.getRealPath(context.getInitParameter("imgElementiPath"));
        Path sourcePath = Paths.get(prodottiPath, sourceFileName);
        Path destinationPath = Paths.get(elementiPath, destinationFileName);
        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        return destinationPath.getFileName().toString();
    }
}


