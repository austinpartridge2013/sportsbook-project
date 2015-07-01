package ca.easybooks.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Formatter;
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
    private static final String EXCEL_FILE_LINK_TEMPLATE =
            "HYPERLINK(CONCATENATE(LEFT(CELL(\"filename\"),FIND(\"[\", CELL(\"filename\"))-1),\"%1$s\"), \"%2$s\")";
    private static final String REVENUES_HEADING = "REVENUES";
    private static final String EXPENSES_HEADING = "EXPENSES";

    private static final int HEADING_ROW = 0;
    private static final int REVENUE_COLUMN = 0;
    private static final int EXPENSE_COLUMN = 4;

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
    public File getTransactionsIncludingDocumentation(final int year) {
        final File excelFile = getTransactionsInExcelFormat(year);

        try (final ZipFileService zipFileService = new ZipFileService("tmp.zip")) {
            zipFileService.addFile(excelFile, "excel.xls");
            return zipFileService.getZipFile();
        }
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
                addTransactionDetails(currentRow, REVENUE_COLUMN, revenues.get(i));
            }
            if (i < expenses.size()) {
                addTransactionDetails(currentRow, EXPENSE_COLUMN, expenses.get(i));
            }
        }
    }

    private void addTransactionDetails(final Row row, final int column, final LedgerEntry ledgerEntry) {
        final Cell revenueDescriptionCell = row.createCell(column);
        revenueDescriptionCell.setCellValue(ledgerEntry.getTransactionDescription());

        final Cell revenueValueCell = row.createCell(column + 1);
        revenueValueCell.setCellValue(ledgerEntry.getTransactionAmount());

        final Cell fileLinkCell = row.createCell(column + 2);
        fileLinkCell.setCellFormula(getExcelFormulaToLinkFile(ledgerEntry));
    }

    private String getExcelFormulaToLinkFile(final LedgerEntry ledgerEntry) {
        final StringBuilder stringBuilder = new StringBuilder();
        try (final Formatter formatter = new Formatter(stringBuilder)) {
            formatter.format(EXCEL_FILE_LINK_TEMPLATE, ledgerEntry.getReceiptFilePath() + ledgerEntry.getReceiptFile(), ledgerEntry.getReceiptFile());
            return stringBuilder.toString();
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
