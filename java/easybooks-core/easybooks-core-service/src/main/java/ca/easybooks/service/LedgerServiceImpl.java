package ca.easybooks.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ca.easybooks.data.entity.LedgerEntry;
import ca.easybooks.data.transferobject.LedgerEntryInput;
import ca.easybooks.service.interfaces.FileOperations;
import ca.easybooks.service.interfaces.LedgerService;

@Stateless
public class LedgerServiceImpl implements LedgerService
{
    @PersistenceContext
    private EntityManager em;

    @Inject
    private FileOperations fileOperations;

    public void uploadFile(final LedgerEntryInput ledgerInput)
    {
        fileOperations.saveFile(ledgerInput.getRelativeFilePath(), ledgerInput.getFileData());

        final LedgerEntry ledgerEntry = new LedgerEntry();
        ledgerEntry.setReceiptFile(ledgerInput.getFileName());
        ledgerEntry.setTransactionAmount(ledgerInput.getTransactionAmount());
        ledgerEntry.setTransactionCategory(ledgerInput.getTransactionCategory());
        ledgerEntry.setTransactionDescription(ledgerInput.getTransactionDescription());
        ledgerEntry.setTransactionYear(ledgerInput.getTransactionYear());
        ledgerEntry.setTransactionMonth(ledgerInput.getTransactionMonth());
        ledgerEntry.setTransactionDay(ledgerInput.getTransactionDay());
        em.persist(ledgerEntry);
    }

    public List<LedgerEntry> getTransactions() {
        return em.createQuery("SELECT l FROM LedgerEntry l ORDER BY l.transactionDate", LedgerEntry.class).getResultList();
    }
}
