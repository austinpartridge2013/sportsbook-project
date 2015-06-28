package ca.easybooks.service.interfaces;

import java.io.File;
import java.util.List;

import ca.easybooks.data.entity.LedgerEntry;
import ca.easybooks.data.transferobject.LedgerEntryInput;

public interface LedgerService {
    void uploadFile(LedgerEntryInput registrationAttempt);
    List<LedgerEntry> getTransactions();
    File getTransactionsInExcelFormat(int year);
}
