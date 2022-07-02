package com.report;

public record AuditTrail(int auditID, String userID, String position,String action,String transactUserID, String date) {
    public int getAuditID() {
        return auditID;
    }
    public String getUserID() {
        return userID;
    }
    public String getPosition() {
        return position;
    }
    public String getAction() {
        return action;
    }
    public String getDate() {
        return date;
    }
    public String getTransactUserID() {
        return transactUserID;
    }
}
