package ca.easybooks.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ca.easybooks.data.entity.LedgerEntry;
import ca.easybooks.data.transferobject.LedgerEntryInput;
import ca.easybooks.service.interfaces.FileOperations;
import ca.easybooks.service.interfaces.LedgerEntryService;

@Stateless
public class LedgerServiceImpl implements LedgerEntryService
{

    @PersistenceContext
    private EntityManager em;

    @Inject
    private FileOperations fileOperations;

    @Override
    public LedgerEntry createLedgerEntry(final LedgerEntryInput ledgerEntryInput)
    {
        fileOperations.saveFile(ledgerEntryInput.getRelativeFilePath(), ledgerEntryInput.getFileData());

        final LedgerEntry ledgerEntry = new LedgerEntry();
        ledgerEntry.setReceiptFile(ledgerEntryInput.getFileName());
        ledgerEntry.setTransactionAmount(ledgerEntryInput.getTransactionAmount());
        ledgerEntry.setTransactionCategory(ledgerEntryInput.getTransactionCategory());
        ledgerEntry.setTransactionDescription(ledgerEntryInput.getTransactionDescription());
        ledgerEntry.setTransactionYear(ledgerEntryInput.getTransactionYear());
        ledgerEntry.setTransactionMonth(ledgerEntryInput.getTransactionMonth());
        ledgerEntry.setTransactionDay(ledgerEntryInput.getTransactionDay());
        em.persist(ledgerEntry);

        return ledgerEntry;
    }

    @Override
    public List<LedgerEntry> getAllTransactions() {
        return em.createQuery("SELECT l FROM LedgerEntry l ORDER BY l.transactionYear", LedgerEntry.class).getResultList();
    }

    @Override
    public List<LedgerEntry> getTransactionsForYear(final int year) {
        return em.createNamedQuery("LedgerEntry.getTransactionsForGivenYear", LedgerEntry.class)
                .setParameter("transactionYear", year)
                .getResultList();
    }
}
