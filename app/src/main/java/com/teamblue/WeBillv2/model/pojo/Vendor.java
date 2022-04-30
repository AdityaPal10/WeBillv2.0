package com.teamblue.WeBillv2.model.pojo;

public class Vendor {
    private String address;
    private String category;
    private String name;
    private String phone_number;

    public Vendor() {
    }

    public Vendor(String address, String category, String name, String phone_number) {
        this.address = address;
        this.category = category;
        this.name = name;
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "address='" + address + '\'' +
                ", category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                '}';
    }
}
