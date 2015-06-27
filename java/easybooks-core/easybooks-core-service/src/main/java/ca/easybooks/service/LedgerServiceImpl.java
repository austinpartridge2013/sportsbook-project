package ca.easybooks.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

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

    public InputStream getTransactionsInExcelFormat() {
        final HSSFWorkbook workbook = new HSSFWorkbook();
        try {
            final HSSFSheet sheet = workbook.createSheet("Sample sheet");
            //Create a new row in current sheet
            final Row row = sheet.createRow(0);
            //Create a new cell in current row
            final Cell cell = row.createCell(0);
            //Set value to new value
            cell.setCellValue("Blahblah");
            return getInputStream(workbook);
        } finally {
            try {
                workbook.close();
            } catch (final IOException e) {
                //Nothing we can do here
            }
        }
    }

    private InputStream getInputStream(final HSSFWorkbook workbook) {
        return new ByteArrayInputStream(workbook.getBytes());
    }
}
