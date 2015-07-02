package ca.easybooks.service.interfaces;

import java.io.File;

public interface DataPresentationService {
    File getAllTransactionsInExcelFormat();
    File getTransactionsInExcelFormat(int year);
    File getAllTransactionsIncludingDocumentationInZipFormat();
    File getTransactionsIncludingDocumentationInZipFormat(int year);
}
