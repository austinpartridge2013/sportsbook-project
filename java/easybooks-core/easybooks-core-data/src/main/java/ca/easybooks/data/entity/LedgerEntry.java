package ca.easybooks.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ca.easybooks.data.enums.TransactionCategory;

@Entity
@Table(name = "ledger_entry")
@NamedQuery(name = "LedgerEntry.getTransactionsForGivenYear",
query = "SELECT l FROM LedgerEntry l WHERE l.transactionYear = :transactionYear")
public class LedgerEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ledger_entry_id_seq")
    @SequenceGenerator(name="ledger_entry_id_seq", sequenceName="ledger_entry_id_seq", allocationSize=1)
    @Column(name = "ledger_entry_id")
    private int ledgerEntryId;

    @Column(name = "transaction_amount")
    private int transactionAmount;

    @Column(name = "transaction_category")
    @Enumerated(EnumType.STRING)
    private TransactionCategory transactionCategory;

    @Column(name = "transaction_description")
    private String transactionDescription;

    @Column(name = "receipt_file")
    private String receiptFile;

    @Column(name = "transaction_year")
    private int transactionYear;

    @Column(name = "transaction_month")
    private int transactionMonth;

    @Column(name = "transaction_day")
    private int transactionDay;

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

    public TransactionCategory getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(final TransactionCategory transactionCategory) {
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

    public long getTransactionYear() {
        return transactionYear;
    }

    public void setTransactionYear(final int transactionYear) {
        this.transactionYear = transactionYear;
    }

    public long getTransactionMonth() {
        return transactionMonth;
    }

    public void setTransactionMonth(final int transactionMonth) {
        this.transactionMonth = transactionMonth;
    }

    public long getTransactionDay() {
        return transactionDay;
    }

    public void setTransactionDay(final int transactionDay) {
        this.transactionDay = transactionDay;
    }

    public String getReceiptFilePath() {
        return this.transactionYear + "/" +
                this.transactionMonth + "/" +
                this.transactionDay + "/";
    }
}
