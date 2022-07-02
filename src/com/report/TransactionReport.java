package com.report;

public record TransactionReport(String transactionID, String userID, String custName,
                                int grandTotal, String transactionDate) {

    public String getTransactionID() {
        return transactionID;
    }

    public String getUserID() {
        return userID;
    }

    public String getCustName() {
        return custName;
    }

    public int getGrandTotal() {
        return grandTotal;
    }

    public String getTransactionDate() {
        return transactionDate;
    }



}
