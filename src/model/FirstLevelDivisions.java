package model;

import java.time.LocalDateTime;
/**
 * First Level Divisions class
 * Contains one constructors with setters and getters
 *
 * @author Jonathan Congmon
 */
public abstract class FirstLevelDivisions {
    /**
     * Division ID
     */
    private int divisionId;
    /**
     * Division Name
     */
    private String division;
    /**
     * Date created
     */
    private LocalDateTime createDate;
    /**
     * User that created the appointment
     */
    private String createdBy;
    /**
     * Last updated
     */
    private LocalDateTime lastUpdate;
    /**
     * User that last updated the appointment
     */
    private String lastUpdatedBy;
    /**
     * Country ID
     */
    private int countryId;
    /**
     * First Level Divisions Constructor
     */
    public FirstLevelDivisions(int divisionId, String division, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
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
    /**
     * Gets Division Name
     */
    public String getDivision() {
        return division;
    }
    /**
     * Sets Division Name
     */
    public void setDivision(String division) {
        this.division = division;
    }
    /**
     * Gets Create Date
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }
    /**
     * Sets Create Date
     */
    public void setCreateDate(LocalDateTime createDate) {
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
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
    /**
     * Sets Last Update
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
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
     * Gets Country ID
     */
    public int getCountryId() {
        return countryId;
    }
    /**
     * Sets Country ID
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
