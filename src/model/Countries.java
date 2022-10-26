package model;

import java.time.LocalDateTime;

/**
 * Countries class
 * Contains one constructor with setters and getters
 *
 * @author Jonathan Congmon
 */
public abstract class Countries {
    /**
     * Country ID
     */
    private int countryId;
    /**
     * Country Name
     */
    private String country;
    /**
     * Create Date
     */
    private LocalDateTime createDate;
    /**
     * Created By
     */
    private String createdBy;
    /**
     * Last Updated
     */
    private LocalDateTime lastUpdate;
    /**
     * Last Updated By
     */
    private String lastUpdatedBy;

    /**
     * Countries constructor
     */
    public Countries(int countryId, String country, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.countryId = countryId;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
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

    /**
     * Gets Country Name
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets Country Name
     */
    public void setCountry(String country) {
        this.country = country;
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
}
