package ca.easybooks.data.transferobject;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import ca.easybooks.data.enums.TransactionCategory;

public class LedgerEntryInput {
    private String fileName;
    private byte[] fileData;
    private int transactionAmount;
    private TransactionCategory transactionCategory;
    private String transactionDescription;
    private int transactionYear;
    private int transactionMonth;
    private int transactionDay;

    public String getFileName() {
        return fileName;
    }

    @FormParam("fileName")
    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileData() {
        return fileData;
    }

    @FormParam("selectedFile")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public void setFileData(final byte[] fileData) {
        this.fileData = fileData;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

    @FormParam("transactionAmount")
    public void setTransactionAmount(final int transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public TransactionCategory getTransactionCategory() {
        return transactionCategory;
    }

    @FormParam("transactionCategory")
    public void setTransactionCategory(final TransactionCategory transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    @FormParam("transactionDescription")
    public void setTransactionDescription(final String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public int getTransactionYear() {
        return transactionYear;
    }

    @FormParam("transactionYear")
    public void setTransactionYear(final int transactionYear) {
        this.transactionYear = transactionYear;
    }

    public int getTransactionMonth() {
        return transactionMonth;
    }

    @FormParam("transactionMonth")
    public void setTransactionMonth(final int transactionMonth) {
        this.transactionMonth = transactionMonth;
    }

    public int getTransactionDay() {
        return transactionDay;
    }

    @FormParam("transactionDay")
    public void setTransactionDay(final int transactionDay) {
        this.transactionDay = transactionDay;
    }
}
