package com.teamblue.WeBillv2.model.pojo;

public class LineItems {

    private String date;

    private String description;

    private double discount;

    private double discount_rate;

    private int id;

    private int order;

    private int quantity;

    private int price;

    private String section;

    private String text;

    private String type;

    private double total;

    private String unit_of_measure;

    public LineItems() {
    }

    public LineItems(String date, String description, double discount, double discount_rate, int id, int order, int quantity, int price, String section, String text, String type, double total, String unit_of_measure) {
        this.date = date;
        this.description = description;
        this.discount = discount;
        this.discount_rate = discount_rate;
        this.id = id;
        this.order = order;
        this.quantity = quantity;
        this.price = price;
        this.section = section;
        this.text = text;
        this.type = type;
        this.total = total;
        this.unit_of_measure = unit_of_measure;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscount_rate() {
        return discount_rate;
    }

    public void setDiscount_rate(double discount_rate) {
        this.discount_rate = discount_rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getUnit_of_measure() {
        return unit_of_measure;
    }

    public void setUnit_of_measure(String unit_of_measure) {
        this.unit_of_measure = unit_of_measure;
    }
}
