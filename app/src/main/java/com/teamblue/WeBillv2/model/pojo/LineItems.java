package com.teamblue.WeBillv2.model.pojo;

public class LineItems {

    private String description;

    private double quantity;

    private String type;

    private double total;


    public LineItems() {
    }

    public LineItems(String description, double quantity, String type, double total) {
        this.description = description;
        this.quantity = quantity;
        this.type = type;
        this.total = total;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
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

    @Override
    public String toString() {
        return "LineItems{" +
                "description='" + description + '\'' +
                ", quantity=" + quantity +
                ", type='" + type + '\'' +
                ", total=" + total +
                '}';
    }
}
