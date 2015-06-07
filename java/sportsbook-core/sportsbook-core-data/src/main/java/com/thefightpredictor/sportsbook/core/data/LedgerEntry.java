package com.thefightpredictor.sportsbook.core.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ledger_entry")
public class LedgerEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ledger_entry_id_seq")
    @SequenceGenerator(name="ledger_entry_id_seq", sequenceName="ledger_entry_id_seq", allocationSize=1)
    @Column(name = "ledger_entry_id")
    private int ledgerEntryId;

    @Column(name = "transaction_amount")
    private int transactionAmount;

    @Column(name = "transaction_category")
    private String transactionCategory;

    @Column(name = "transaction_description")
    private String transactionDescription;

    @Column(name = "receipt_file")
    private String receiptFile;

    @Column(name = "transaction_date")
    private Date transactionDate;

    public int getLedgerEntryId() {
        return ledgerEntryId;
    }

    public void setLedgerEntryId(final int ledgerEntryId) {
        this.ledgerEntryId = ledgerEntryId;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(final int transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(final String transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(final String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public String getReceiptFile() {
        return receiptFile;
    }

    public void setReceiptFile(final String receiptFile) {
        this.receiptFile = receiptFile;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(final Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
