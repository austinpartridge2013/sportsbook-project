package ca.easybooks.fileops.trivial;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import ca.easybooks.service.interfaces.FileOperations;

@Stateless
public class TrivialFileOperations implements FileOperations {
    private static final Logger log = Logger.getLogger(TrivialFileOperations.class);

    private static final String DEFAULT_FILE_PATH = "/tmp";

    public void saveFile(final String relativePath, final byte[] fileContents) {
        try {
            Files.write(Paths.get(DEFAULT_FILE_PATH, relativePath), fileContents);
        } catch (final IOException e) {
            log.error("Error storing file", e);
            throw new RuntimeException("Unable to store file", e);
        }
    }

    public File getFile(final String fileName) {
        return new File(DEFAULT_FILE_PATH + fileName);
    }
}
