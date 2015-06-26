package ca.easybooks.fileops.trivial;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import ca.easybooks.data.transferobject.LedgerEntryInput;
import ca.easybooks.service.interfaces.FileOperations;

@Stateless
public class TrivialFileOperations implements FileOperations {
    private static final Logger log = Logger.getLogger(TrivialFileOperations.class);

    private static final String DEFAULT_FILE_PATH = "/home/austin/";

    public void saveFile(final LedgerEntryInput ledgerInput) {
        final String fileName =
                DEFAULT_FILE_PATH +
                ledgerInput.getFileName() == null ? "Unknown" : ledgerInput.getFileName();

        log.debug("Writing local file to " + fileName);

        try {
            Files.write(Paths.get(fileName), ledgerInput.getFileData());
        } catch (final IOException e) {
            log.error("Error storing file", e);
            throw new RuntimeException("Unable to store file", e);
        }
    }

    public File getFile(final String fileName) {
        return new File(DEFAULT_FILE_PATH + fileName);
    }
}
