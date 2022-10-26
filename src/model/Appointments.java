package model;

import java.sql.Timestamp;

/**
 * Appointments class
 * Contains two constructors with setters and getters
 *
 * @author Jonathan Congmon
 */
public class Appointments {
    /**
     * Appointment ID
     */
    private int appointmentId;
    /**
     * Title
     */
    private String title;
    /**
     * Description
     */
    private String description;
    /**
     * Type of appointment
     */
    private String type;
    /**
     * Location
     */
    private String location;
    /**
     * Start time
     */
    private Timestamp start;
    /**
     * End time
     */
    private Timestamp end;
    /**
     * Date created
     */
    private Timestamp createDate;
    /**
     * User that created the appointment
     */
    private String createdBy;
    /**
     * Last updated
     */
    private Timestamp lastUpdate;
    /**
     * User that last updated the appointment
     */
    private String lastUpdatedBy;
    /**
     * Customer ID
     */
    private int customerId;
    /**
     * User ID
     */
    private int userId;
    /**
     * Contact ID
     */
    private int contactId;
    /**
     * Division ID
     * */
    private int divisionId;

    /**
     * Appointment constructor
     */
    public Appointments(String title, String description, String location, String type, Timestamp start, Timestamp end, String createdBy, int customerId, int userId, int contactId) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.start = start;
        this.end = end;
        this.location = location;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * Appointment constructor
     */
    public Appointments(int appointmentId, String title, String description, String type, String location, Timestamp start, Timestamp end, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.type = type;
        this.location = location;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * Gets Appointment ID
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets Appointment ID
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Gets Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets Title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets Description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets Type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets Type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets Location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets Location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets Start Time
     */
    public Timestamp getStart() {
        return start;
    }

    /**
     * Sets Start Time
     */
    public void setStart(Timestamp start) {
        this.start = start;
    }

    /**
     * Gets End Time
     */
    public Timestamp getEnd() {
        return end;
    }

    /**
     * Sets End Time
     */
    public void setEnd(Timestamp end) {
        this.end = end;
    }

    /**
     * Gets Create Date
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * Sets Create Date
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
     * Gets User ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets User ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets Contact ID
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets Contact ID
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
