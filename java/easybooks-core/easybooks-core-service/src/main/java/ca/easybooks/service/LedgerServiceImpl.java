package ca.easybooks.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import ca.easybooks.data.entity.LedgerEntry;
import ca.easybooks.data.enums.TransactionCategory;
import ca.easybooks.data.transferobject.LedgerEntryInput;
import ca.easybooks.service.interfaces.FileOperations;
import ca.easybooks.service.interfaces.LedgerService;

@Stateless
public class LedgerServiceImpl implements LedgerService
{
    private static final String REVENUES_HEADING = "REVENUES";
    private static final int HEADING_ROW = 0;
    private static final int REVENUE_COLUMN = 0;
    private static final int EXPENSE_COLUMN = 3;

    private static final int DATA_START_ROW = 0;

    private static final String EXPENSES_HEADING = "EXPENSES";

    private static final int EXPENSE_ROW_START = 0;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private FileOperations fileOperations;

    @Override
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

    @Override
    public List<LedgerEntry> getTransactions() {
        return em.createQuery("SELECT l FROM LedgerEntry l ORDER BY l.transactionYear", LedgerEntry.class).getResultList();
    }

    @Override
    public File getTransactionsInExcelFormat(final int year) {
        try (final HSSFWorkbook excelWorkbook = generateExcelFile(year)) {
            return getExcelFile(excelWorkbook);
        } catch (final IOException e) {
            throw new RuntimeException("Error creating excel file", e);
        }
    }

    public HSSFWorkbook generateExcelFile(final int year) throws IOException {
        final HSSFWorkbook workbook = new HSSFWorkbook();
        final HSSFSheet sheet = workbook.createSheet("IncomeStatement");
        addHeadings(sheet);
        addData(sheet, year);
        return workbook;
    }

    private void addData(final HSSFSheet sheet, final int year) {
        final List<LedgerEntry> transactionsInYear = getTransactionsForYear(year);
        final List<LedgerEntry> revenues = getAllEntriesOfTransactionType(transactionsInYear, TransactionCategory.REVENUE);
        final List<LedgerEntry> expenses = getAllEntriesOfTransactionType(transactionsInYear, TransactionCategory.EXPENSE);

        for (int i = 0; i < Math.max(revenues.size(), expenses.size()); i++) {
            final Row currentRow = sheet.createRow(HEADING_ROW + 1 + i);
            if (i < revenues.size()) {
                final Cell revenueDescriptionCell = currentRow.createCell(REVENUE_COLUMN);
                revenueDescriptionCell.setCellValue(revenues.get(i).getTransactionDescription());

                final Cell revenueValueCell = currentRow.createCell(REVENUE_COLUMN + 1);
                revenueValueCell.setCellValue(revenues.get(i).getTransactionAmount());
            }
            if (i < expenses.size()) {
                final Cell expenseDescriptionCell = currentRow.createCell(EXPENSE_COLUMN);
                expenseDescriptionCell.setCellValue(expenses.get(i).getTransactionDescription());

                final Cell expenseValueCell = currentRow.createCell(EXPENSE_COLUMN + 1);
                expenseValueCell.setCellValue(expenses.get(i).getTransactionAmount());
            }
        }
    }

    private List<LedgerEntry> getTransactionsForYear(final int year) {
        return em.createNamedQuery("LedgerEntry.getTransactionsForGivenYear", LedgerEntry.class)
                .setParameter("transactionYear", year)
                .getResultList();
    }

    private void addHeadings(final HSSFSheet sheet) {
        final Row headingRow = sheet.createRow(HEADING_ROW);
        final Cell revenueHeadingCell = headingRow.createCell(REVENUE_COLUMN);
        revenueHeadingCell.setCellValue(REVENUES_HEADING);

        final Cell expenseHeadingCell = headingRow.createCell(EXPENSE_COLUMN);
        expenseHeadingCell.setCellValue(EXPENSES_HEADING);
    }

    private List<LedgerEntry> getAllEntriesOfTransactionType(final List<LedgerEntry> transactions, final TransactionCategory category) {
        return transactions.stream().filter(t -> t.getTransactionCategory() == category).collect(Collectors.toList());
    }

    private File getExcelFile(final HSSFWorkbook workbook) {
        try {
            final FileOutputStream out = new FileOutputStream("/tmp/tempExcelFile.xls");
            workbook.write(out);
            out.close();
            return new File("/tmp/tempExcelFile.xls");
        } catch (final IOException e) {
            throw new RuntimeException("Issue writing to excel file", e);
        }
    }
}
