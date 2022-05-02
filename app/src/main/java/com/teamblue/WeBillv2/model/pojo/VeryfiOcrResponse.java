package com.teamblue.WeBillv2.model.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class VeryfiOcrResponse {

    private String category;
    private String currency_code;
    private String date;

    private String document_reference_number;

    private String img_file_name;

    private String img_thumbnail_url;

    private String img_url;

    private double subtotal;

    private double total;

    private String ocr_text;

    //private List<LineItems> line_items;

    private Vendor vendor;

    private List<LineItems> line_items;

    public VeryfiOcrResponse() {
    }

    public VeryfiOcrResponse(String category, String currency_code, String date, String document_reference_number, String img_file_name, String img_thumbnail_url, String img_url, double subtotal, double total, String ocr_text, Vendor vendor, List<LineItems> lineItems) {
        this.category = category;
        this.currency_code = currency_code;
        this.date = date;
        this.document_reference_number = document_reference_number;
        this.img_file_name = img_file_name;
        this.img_thumbnail_url = img_thumbnail_url;
        this.img_url = img_url;
        this.subtotal = subtotal;
        this.total = total;
        this.ocr_text = ocr_text;
        this.vendor = vendor;
        this.line_items = lineItems;
    }

    public List<LineItems> getLineItems() {
        return line_items;
    }

    public void setLineItems(List<LineItems> lineItems) {
        this.line_items = lineItems;
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

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
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

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    @Override
    public String toString() {
        return "VeryfiOcrResponse{" +
                "category='" + category + '\'' +
                ", currency_code='" + currency_code + '\'' +
                ", date='" + date + '\'' +
                ", document_reference_number='" + document_reference_number + '\'' +
                ", img_file_name='" + img_file_name + '\'' +
                ", img_thumbnail_url='" + img_thumbnail_url + '\'' +
                ", img_url='" + img_url + '\'' +
                ", subtotal=" + subtotal +
                ", total=" + total +
                ", ocr_text='" + ocr_text + '\'' +
                ", vendor=" + vendor +
                ", lineItems=" + line_items +
                '}';
    }
}
