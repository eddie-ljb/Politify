package de.ebader.Politify.util;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Util {

    public String getLocaleJsonPath(String dateiEndPfad) {
        URL resourceUrl = Thread.currentThread().getContextClassLoader().getResource(dateiEndPfad);

        if (resourceUrl != null) {
            try {
                Path tempFile = Files.createTempFile("temp-", null);
                Files.copy(resourceUrl.openStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
                return tempFile.toAbsolutePath().toString();
            }
            catch (IOException e) {
                e.printStackTrace(); // Behandle die Ausnahme entsprechend
            }
        }

        return null;
    }

    public URL getURL(String dateiname) {
        return Thread.currentThread().getContextClassLoader().getResource(dateiname);
    }

}