package com.thefightpredictor.sportsbook.core.service.interfaces;

import com.thefightpredictor.sportsbook.core.data.transferobject.LedgerEntryInput;

public interface LedgerService {
    void uploadFile(LedgerEntryInput registrationAttempt);
}
