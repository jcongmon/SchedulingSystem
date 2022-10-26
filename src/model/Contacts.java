package model;

/**
 * Contacts class
 * Contains one constructor with setters and getters
 *
 * @author Jonathan Congmon
 */
public class Contacts {
    /**
     * Contact ID
     */
    private int contactId;
    /**
     * Contact Name
     */
    private String contactName;
    /**
     * Contact Email
     */
    private String email;
    /**
     * Contacts constructor
     */
    public Contacts(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
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
    /**
     * Gets Contact Name
     */
    public String getContactName() {
        return contactName;
    }
    /**
     * Gets Contact Name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    /**
     * Gets Contact Email
     */
    public String getEmail() {
        return email;
    }
    /**
     * Sets Contact Email
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
