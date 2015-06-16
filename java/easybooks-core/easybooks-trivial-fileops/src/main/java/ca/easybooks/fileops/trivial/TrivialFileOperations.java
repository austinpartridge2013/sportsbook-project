package ca.easybooks.fileops.trivial;

import java.io.File;
import java.io.IOException;

import javax.ejb.Stateless;

import org.apache.commons.io.FileUtils;

import ca.easybooks.data.transferobject.LedgerEntryInput;
import ca.easybooks.service.interfaces.FileOperations;

@Stateless
public class TrivialFileOperations implements FileOperations {
    private static final String DEFAULT_FILE_PATH = "/tmp";

    public void saveFile(final LedgerEntryInput ledgerInput) {
        final String fileName =
                DEFAULT_FILE_PATH +
                ledgerInput.getFileName() == null ? "Unknown" : ledgerInput.getFileName();
        try {
            FileUtils.writeByteArrayToFile(new File(fileName), ledgerInput.getFileData());
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public File getFile(final String fileName) {
        return FileUtils.getFile(DEFAULT_FILE_PATH + fileName);
    }
}
