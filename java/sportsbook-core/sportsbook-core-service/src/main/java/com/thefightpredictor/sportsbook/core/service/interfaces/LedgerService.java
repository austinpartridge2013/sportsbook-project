package com.thefightpredictor.sportsbook.core.service.interfaces;

import java.util.List;

import com.thefightpredictor.sportsbook.core.data.LedgerEntry;
import com.thefightpredictor.sportsbook.core.data.transferobject.LedgerEntryInput;

public interface LedgerService {
    void uploadFile(LedgerEntryInput registrationAttempt);
    List<LedgerEntry> getTransactions();
}
