package com.teamblue.WeBillv2.model.pojo;

public class Vendor {
    private String address;
    private String category;
    private String email;
    private String fax_number;
    private String name;
    private String phone_number;
    private String raw_name;
    private String vendor_logo;
    private String vendor_reg_number;
    private String vendor_type;
    private String web;

    public Vendor() {
    }

    public Vendor(String address, String category, String email, String fax_number, String name, String phone_number, String raw_name, String vendor_logo, String vendor_reg_number, String vendor_type, String web) {
        this.address = address;
        this.category = category;
        this.email = email;
        this.fax_number = fax_number;
        this.name = name;
        this.phone_number = phone_number;
        this.raw_name = raw_name;
        this.vendor_logo = vendor_logo;
        this.vendor_reg_number = vendor_reg_number;
        this.vendor_type = vendor_type;
        this.web = web;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax_number() {
        return fax_number;
    }

    public void setFax_number(String fax_number) {
        this.fax_number = fax_number;
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

    public String getRaw_name() {
        return raw_name;
    }

    public void setRaw_name(String raw_name) {
        this.raw_name = raw_name;
    }

    public String getVendor_logo() {
        return vendor_logo;
    }

    public void setVendor_logo(String vendor_logo) {
        this.vendor_logo = vendor_logo;
    }

    public String getVendor_reg_number() {
        return vendor_reg_number;
    }

    public void setVendor_reg_number(String vendor_reg_number) {
        this.vendor_reg_number = vendor_reg_number;
    }

    public String getVendor_type() {
        return vendor_type;
    }

    public void setVendor_type(String vendor_type) {
        this.vendor_type = vendor_type;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }
}
