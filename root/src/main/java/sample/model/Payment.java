package sample.model;

import java.time.LocalDate;

public class Payment {

    private String paymentID;
    private LocalDate paymentDueDate;
    private Double paymentAmount;
    private Long paymentBAN;
    private Long disbursementBankAccountNumber;
    private String paymentDescription;
    private String payeeName;
    private String paymentMethod;
    private String paymentType;
    private String currencyCode;

    public Payment() {
    }

    public Payment(String paymentID, LocalDate paymentDueDate, Double paymentAmount, Long paymentBAN,
                   Long disbursementBankAccountNumber, String paymentDescription, String payeeName,
                   String paymentMethod, String paymentType, String currencyCode) {
        this.paymentID = paymentID;
        this.paymentDueDate = paymentDueDate;
        this.paymentAmount = paymentAmount;
        this.paymentBAN = paymentBAN;
        this.disbursementBankAccountNumber = disbursementBankAccountNumber;
        this.paymentDescription = paymentDescription;
        this.payeeName = payeeName;
        this.paymentMethod = paymentMethod;
        this.paymentType = paymentType;
        this.currencyCode = currencyCode;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public LocalDate getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(LocalDate paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Long getPaymentBAN() {
        return paymentBAN;
    }

    public void setPaymentBAN(Long paymentBAN) {
        this.paymentBAN = paymentBAN;
    }

    public Long getDisbursementBankAccountNumber() {
        return disbursementBankAccountNumber;
    }

    public void setDisbursementBankAccountNumber(Long disbursementBankAccountNumber) {
        this.disbursementBankAccountNumber = disbursementBankAccountNumber;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public String toString() {
        return "{\n" +
                "   paymentID: \"" + paymentID + '\"' +
                ",\n   paymentDueDate: " + paymentDueDate +
                ",\n   paymentAmount: " + paymentAmount +
                ",\n   paymentBAN: " + paymentBAN +
                ",\n   disbursementBankAccountNumber: " + disbursementBankAccountNumber +
                ",\n   paymentDescription: \"" + paymentDescription + '\"' +
                ",\n   payeeName: \"" + payeeName + '\"' +
                ",\n   paymentMethod: \"" + paymentMethod + '\"' +
                ",\n   paymentType: \"" + paymentType + '\"' +
                ",\n   currencyCode: \"" + currencyCode + '\"' +
                "\n}";
    }
}
