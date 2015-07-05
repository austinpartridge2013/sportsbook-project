package ca.easybooks.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Formatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.commons.collections4.ListUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ca.easybooks.data.entity.LedgerEntry;
import ca.easybooks.data.enums.TransactionCategory;
import ca.easybooks.service.interfaces.DataPresentationService;
import ca.easybooks.service.interfaces.FileOperations;
import ca.easybooks.service.interfaces.LedgerEntryService;

@RequestScoped
public class DataPresentationServiceImpl implements DataPresentationService {
    private static final String EXCEL_FILE_LINK_TEMPLATE =
            "HYPERLINK(CONCATENATE(LEFT(CELL(\"filename\"),FIND(\"[\", CELL(\"filename\"))-1),\"%1$s\"), \"%2$s\")";
    private static final String REVENUES_HEADING = "REVENUES";
    private static final String EXPENSES_HEADING = "EXPENSES";

    private static final int HEADING_ROW = 0;
    private static final int REVENUE_COLUMN = 0;
    private static final int EXPENSE_COLUMN = 4;

    @Inject
    private LedgerEntryService ledgerEntryService;

    @Inject
    private FileOperations fileOperations;

    private List<LedgerEntry> revenues;
    private List<LedgerEntry> expenses;

    public void populateLedgerEntries() {
        final List<LedgerEntry> allLedgerEntries = ledgerEntryService.getAllTransactions();
        populateRevenuesAndExpenses(allLedgerEntries);
    }

    public void populateLedgerEntries(final Integer year) {
        final List<LedgerEntry> ledgerEntriesForYear = ledgerEntryService.getTransactionsForYear(year);
        populateRevenuesAndExpenses(ledgerEntriesForYear);
    }

    private void populateRevenuesAndExpenses(final List<LedgerEntry> ledgerEntries) {
        revenues = getAllEntriesOfTransactionType(ledgerEntries, TransactionCategory.REVENUE);
        expenses = getAllEntriesOfTransactionType(ledgerEntries, TransactionCategory.EXPENSE);
    }

    @Override
    public File getAllTransactionsInExcelFormat() {
        populateLedgerEntries();

        return getExcelFileForCollectedDate();
    }

    @Override
    public File getTransactionsInExcelFormat(final int year) {
        populateLedgerEntries(year);

        return getExcelFileForCollectedDate();
    }

    @Override
    public File getAllTransactionsIncludingDocumentationInZipFormat() {
        final File excelFile = getAllTransactionsInExcelFormat();
        return getZipFileForCollectedData(excelFile);
    }

    @Override
    public File getTransactionsIncludingDocumentationInZipFormat(final int year) {
        final File excelFile = getTransactionsInExcelFormat(year);
        return getZipFileForCollectedData(excelFile);
    }

    private Workbook generateExcelFile() throws IOException {
        final Workbook workbook = new XSSFWorkbook();
        final Sheet sheet = workbook.createSheet("IncomeStatement");
        addHeadings(sheet);
        addData(sheet);
        return workbook;
    }

    private void addData(final Sheet sheet) {
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
        fileLinkCell.setCellValue(ledgerEntry.getReceiptFile());
    }

    private String getExcelFormulaToLinkFile(final LedgerEntry ledgerEntry) {
        final StringBuilder stringBuilder = new StringBuilder();
        try (final Formatter formatter = new Formatter(stringBuilder)) {
            formatter.format(EXCEL_FILE_LINK_TEMPLATE, ledgerEntry.getReceiptFilePath() + ledgerEntry.getReceiptFile(), ledgerEntry.getReceiptFile());
            return stringBuilder.toString();
        }
    }

    private void addHeadings(final Sheet sheet) {
        final Row headingRow = sheet.createRow(HEADING_ROW);
        final Cell revenueHeadingCell = headingRow.createCell(REVENUE_COLUMN);
        revenueHeadingCell.setCellValue(REVENUES_HEADING);

        final Cell expenseHeadingCell = headingRow.createCell(EXPENSE_COLUMN);
        expenseHeadingCell.setCellValue(EXPENSES_HEADING);
    }

    private File getExcelFile(final Workbook workbook) {
        try {
            final FileOutputStream out = new FileOutputStream("/tmp/tempExcelFile.xlsx");
            workbook.write(out);
            out.close();
            return new File("/tmp/tempExcelFile.xlsx");
        } catch (final IOException e) {
            throw new RuntimeException("Issue writing to excel file", e);
        }
    }

    private List<LedgerEntry> getAllEntriesOfTransactionType(final List<LedgerEntry> transactions, final TransactionCategory category) {
        return transactions.stream().filter(t -> t.getTransactionCategory() == category).collect(Collectors.toList());
    }

    private File getExcelFileForCollectedDate() {
        try (final Workbook excelWorkbook = generateExcelFile()) {
            return getExcelFile(excelWorkbook);
        } catch (final IOException e) {
            throw new RuntimeException("Error creating excel file", e);
        }
    }

    private File getZipFileForCollectedData(final File excelFile) {
        try (final ZipFileService zipFileService = new ZipFileService("tmp.zip")) {
            zipFileService.addFile(excelFile, "excel.xlsx");
            addReceiptsToZipFile(zipFileService);
            return zipFileService.getZipFile();
        }
    }

    private void addReceiptsToZipFile(final ZipFileService zipFileService) {
        final List<LedgerEntry> allTransactions = ListUtils.union(revenues, expenses);
        for (final LedgerEntry transaction : allTransactions) {
            final String receiptFilePath = transaction.getReceiptFilePath();
            zipFileService.addFile(fileOperations.getFile(receiptFilePath), receiptFilePath);
        }
    }

}
