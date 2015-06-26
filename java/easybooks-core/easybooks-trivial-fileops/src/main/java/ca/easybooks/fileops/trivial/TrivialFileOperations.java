package ca.easybooks.fileops.trivial;

import java.io.File;
import java.io.IOException;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import ca.easybooks.data.transferobject.LedgerEntryInput;
import ca.easybooks.service.interfaces.FileOperations;

import com.google.common.io.Files;

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
            final File receiptFile = new File(fileName);
            receiptFile.createNewFile();
            Files.write(ledgerInput.getFileData(), receiptFile);
        } catch (final IOException e) {
            log.error("Error storing file", e);
            throw new RuntimeException("Unable to store file", e);
        }
    }

    public File getFile(final String fileName) {
        return new File(DEFAULT_FILE_PATH + fileName);
    }
}
