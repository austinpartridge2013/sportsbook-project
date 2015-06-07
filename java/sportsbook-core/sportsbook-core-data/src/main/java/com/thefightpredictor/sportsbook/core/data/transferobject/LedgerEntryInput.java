package com.thefightpredictor.sportsbook.core.data.transferobject;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class LedgerEntryInput {
    private String fileName;
    private byte[] fileData;
    private int transactionAmount;
    private String transactionCategory;
    private String transactionDescription;
    private long transactionDate;

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

    public String getTransactionCategory() {
        return transactionCategory;
    }

    @FormParam("transactionCategory")
    public void setTransactionCategory(final String transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    @FormParam("transactionDescription")
    public void setTransactionDescription(final String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public long getTransactionDate() {
        return transactionDate;
    }

    @FormParam("transactionDate")
    public void setTransactionDescription(final long transactionDate) {
        this.transactionDate = transactionDate;
    }
}
