package model;

import java.time.LocalDateTime;

/**
 * Users class
 * Contains one constructor with setters and getters
 *
 * @author Jonathan Congmon
 */
public class Users {
    /**
     * User ID
     */
    private int userId;
    /**
     * User Name
     */
    private String userName;
    /**
     * Password
     */
    private String password;
    /**
     * Create Date
     */
    private LocalDateTime createDate;
    /**
     * Created By
     */
    private String createdBy;
    /**
     * Last Update
     */
    private LocalDateTime lastUpdate;
    /**
     * Last Updated By
     */
    private String lastUpdatedBy;

    /**
     * Users constructor
     */
    public Users(int userId, String userName, String password, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
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
     * Gets User Name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets User Name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets User Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets User Password
     */
    public void setPassword(String password) {
        this.password = password;
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
