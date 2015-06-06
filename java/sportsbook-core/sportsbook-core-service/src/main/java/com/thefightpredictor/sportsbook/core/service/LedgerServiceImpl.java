package com.thefightpredictor.sportsbook.core.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.thefightpredictor.sportsbook.core.data.LedgerEntry;
import com.thefightpredictor.sportsbook.core.data.transferobject.LedgerEntryInput;
import com.thefightpredictor.sportsbook.core.service.interfaces.LedgerService;

@Stateless
public class LedgerServiceImpl implements LedgerService
{
    @PersistenceContext
    private EntityManager em;

    public void uploadFile(final LedgerEntryInput ledgerInput)
    {
        final String fileName = ledgerInput.getFileName() == null ? "Unknown" : ledgerInput.getFileName() ;

        final String completeFilePath = "/tmp/" + fileName;

        try
        {
            //Save the file
            final File file = new File(completeFilePath);

            if (!file.exists())
            {
                file.createNewFile();
            }

            final FileOutputStream fos = new FileOutputStream(file);

            fos.write(ledgerInput.getFileData());
            fos.flush();
            fos.close();
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }

        final LedgerEntry ledgerEntry = new LedgerEntry();
        ledgerEntry.setReceiptFile(ledgerInput.getFileName());
        ledgerEntry.setTransactionAmount(ledgerInput.getTransactionAmount());
        ledgerEntry.setTransactionCategory(ledgerInput.getTransactionCategory());
        ledgerEntry.setTransactionDescription(ledgerInput.getTransactionDescription());
        em.persist(ledgerEntry);
    }

    public List<LedgerEntry> getTransactions() {
        return em.createQuery("SELECT l FROM LedgerEntry l", LedgerEntry.class).getResultList();
    }
}
