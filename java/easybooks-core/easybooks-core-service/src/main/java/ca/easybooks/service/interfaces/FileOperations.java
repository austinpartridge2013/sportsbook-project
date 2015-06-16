package ca.easybooks.service.interfaces;

import java.io.File;

import ca.easybooks.data.transferobject.LedgerEntryInput;

public interface FileOperations {

    void saveFile(LedgerEntryInput ledgerInput);
    File getFile(String fileName);
}
