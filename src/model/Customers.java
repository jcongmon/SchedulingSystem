package model;

import java.sql.Timestamp;

/**
 * Customers class
 * Contains one constructor with setters and getters
 *
 * @author Jonathan Congmon
 */
public class Customers {
    /**
     * Customer ID
     */
    private int customerId;
    /**
     * Customer Name
     */
    private String customerName;
    /**
     * Address
     */
    private String address;
    /**
     * Postal Code
     */
    private String postalCode;
    /**
     * Phone Number
     */
    private String phoneNumber;
    /**
     * Create Date
     */
    private Timestamp createDate;
    /**
     * Created By
     */
    private String createdBy;
    /**
     * Last Update
     */
    private Timestamp lastUpdate;
    /**
     * Last Updated By
     */
    private String lastUpdatedBy;
    /**
     * Division ID
     */
    private int divisionId;

    /**
     * Customers constructor
     */
    public Customers(int customerId, String customerName, String address, String postalCode, String phoneNumber, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
    }

    /**
     * Gets Customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets Customer ID
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets Customer Name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets Customer Name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets Address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets Address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets Postal Code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets Postal Code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets Phone Number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets Phone Number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets Created Date
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * Sets Created Date
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets Created By
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets Created By
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets Last Update
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets Last Update
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets Last Updated By
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets Last Updated By
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets Division ID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets Division ID
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}
