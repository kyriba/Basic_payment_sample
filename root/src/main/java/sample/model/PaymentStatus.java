package sample.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentStatus {

    private String reference;
    private String transactionType;
    private String debitingAccount;
    private Long transactionAmount;
    private String transactionCurrency;
    private String transactionStatus;
    private String remittanceID;
    private String lastACK;
    private String remittanceBankStatus;
    private Date lastACKDate;
    private Date lastACKTime;
    private String rejectionInformation;

    public PaymentStatus() {
    }

    public PaymentStatus(String reference, String transactionType, String debitingAccount, Long transactionAmount,
                         String transactionCurrency, String transactionStatus, String remittanceID, String lastACK,
                         String remittanceBankStatus, Date lastACKDate, Date lastACKTime, String rejectionInformation) {
        this.reference = reference;
        this.transactionType = transactionType;
        this.debitingAccount = debitingAccount;
        this.transactionAmount = transactionAmount;
        this.transactionCurrency = transactionCurrency;
        this.transactionStatus = transactionStatus;
        this.remittanceID = remittanceID;
        this.lastACK = lastACK;
        this.remittanceBankStatus = remittanceBankStatus;
        this.lastACKDate = lastACKDate;
        this.lastACKTime = lastACKTime;
        this.rejectionInformation = rejectionInformation;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getDebitingAccount() {
        return debitingAccount;
    }

    public void setDebitingAccount(String debitingAccount) {
        this.debitingAccount = debitingAccount;
    }

    public Long getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Long transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getRemittanceID() {
        return remittanceID;
    }

    public void setRemittanceID(String remittanceID) {
        this.remittanceID = remittanceID;
    }

    public String getLastACK() {
        return lastACK;
    }

    public void setLastACK(String lastACK) {
        this.lastACK = lastACK;
    }

    public String getRemittanceBankStatus() {
        return remittanceBankStatus;
    }

    public void setRemittanceBankStatus(String remittanceBankStatus) {
        this.remittanceBankStatus = remittanceBankStatus;
    }

    public Date getLastACKDate() {
        return lastACKDate;
    }

    public void setLastACKDate(Date lastACKDate) {
        this.lastACKDate = lastACKDate;
    }

    public Date getLastACKTime() {
        return lastACKTime;
    }

    public void setLastACKTime(Date lastACKTime) {
        this.lastACKTime = lastACKTime;
    }

    public String getRejectionInformation() {
        return rejectionInformation;
    }

    public void setRejectionInformation(String rejectionInformation) {
        this.rejectionInformation = rejectionInformation;
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String lastACKDateString = null;
        if (lastACKDate != null) {
            lastACKDateString = dateFormat.format(lastACKDate);
        }
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String lastACKTimeString = null;
        if (lastACKTime != null) {
            lastACKTimeString = timeFormat.format(lastACKTime);
        }
        return "{\n" +
                "   reference: \"" + reference + '\"' +
                ",\n   transactionType: \"" + transactionType + '\"' +
                ",\n   debitingAccount: \"" + debitingAccount + '\"' +
                ",\n   transactionAmount: " + transactionAmount +
                ",\n   transactionCurrency: \"" + transactionCurrency + '\"' +
                ",\n   transactionStatus: \"" + transactionStatus + '\"' +
                ",\n   remittanceID: \"" + remittanceID + '\"' +
                ",\n   lastACK: \"" + lastACK + '\"' +
                ",\n   remittanceBankStatus: \"" + remittanceBankStatus + '\"' +
                ",\n   lastACKDate: " + lastACKDateString +
                ",\n   lastACKTime: " + lastACKTimeString +
                ",\n   rejectionInformation: \"" + rejectionInformation + '\"' +
                "\n}";
    }
}
