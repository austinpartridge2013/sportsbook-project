package ca.easybooks.service.interfaces;

import java.util.List;

import ca.easybooks.data.entity.LedgerEntry;
import ca.easybooks.data.transferobject.LedgerEntryInput;

public interface LedgerEntryService {
    LedgerEntry createLedgerEntry(LedgerEntryInput registrationAttempt);

    List<LedgerEntry> getAllTransactions();
    List<LedgerEntry> getTransactionsForYear(int year);
}
