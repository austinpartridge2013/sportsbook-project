package ca.easybooks.service.interfaces;

import java.util.List;

import ca.easybooks.data.LedgerEntry;
import ca.easybooks.data.transferobject.LedgerEntryInput;

public interface LedgerService {
    void uploadFile(LedgerEntryInput registrationAttempt);
    List<LedgerEntry> getTransactions();
}
