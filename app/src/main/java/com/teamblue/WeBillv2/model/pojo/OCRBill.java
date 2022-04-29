package com.teamblue.WeBillv2.model.pojo;

public class OCRBill {
    private String base64encodedString;
    private String billName;

    public OCRBill() {
    }

    public OCRBill(String base64encodedString, String billName) {
        this.base64encodedString = base64encodedString;
        this.billName = billName;
    }

    public String getBase64encodedString() {
        return base64encodedString;
    }

    public void setBase64encodedString(String base64encodedString) {
        this.base64encodedString = base64encodedString;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }
}
