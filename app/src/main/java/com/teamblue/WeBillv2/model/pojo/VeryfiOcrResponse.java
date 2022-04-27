package com.teamblue.WeBillv2.model.pojo;

import java.util.List;

public class VeryfiOcrResponse {

    private String category;
    private String currency_code;
    private String date;

    private String document_reference_number;

    private String img_file_name;

    private String img_thumbnail_url;

    private String img_url;

    private String invoice_number;

    private double total;

    private String ocr_text;

    private List<LineItems> line_items;

    private Vendor vendor;

    public VeryfiOcrResponse() {
    }

    public VeryfiOcrResponse(String category, String currency_code, String date, String document_reference_number, String img_file_name, String img_thumbnail_url, String img_url, String invoice_number, double total, String ocr_text, List<LineItems> line_items, Vendor vendor) {
        this.category = category;
        this.currency_code = currency_code;
        this.date = date;
        this.document_reference_number = document_reference_number;
        this.img_file_name = img_file_name;
        this.img_thumbnail_url = img_thumbnail_url;
        this.img_url = img_url;
        this.invoice_number = invoice_number;
        this.total = total;
        this.ocr_text = ocr_text;
        this.line_items = line_items;
        this.vendor = vendor;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDocument_reference_number() {
        return document_reference_number;
    }

    public void setDocument_reference_number(String document_reference_number) {
        this.document_reference_number = document_reference_number;
    }

    public String getImg_file_name() {
        return img_file_name;
    }

    public void setImg_file_name(String img_file_name) {
        this.img_file_name = img_file_name;
    }

    public String getImg_thumbnail_url() {
        return img_thumbnail_url;
    }

    public void setImg_thumbnail_url(String img_thumbnail_url) {
        this.img_thumbnail_url = img_thumbnail_url;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getOcr_text() {
        return ocr_text;
    }

    public void setOcr_text(String ocr_text) {
        this.ocr_text = ocr_text;
    }

    public List<LineItems> getLine_items() {
        return line_items;
    }

    public void setLine_items(List<LineItems> line_items) {
        this.line_items = line_items;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
}
