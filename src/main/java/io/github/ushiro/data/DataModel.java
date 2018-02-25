package io.github.ushiro.data;

/**
 * A data model object which contains all information
 * about a URL such as its key, creation time and view-count.
 * It is beeing used for data manipulation such as storing,
 * retrieving, and checking the existence in both database
 * and cache storage.
 */
public class DataModel {

    private String keyUrl;
    private String longUrl;
    private long createdAt;
    private int viewCount;

    /**
     * Object constructor for the data model
     *
     * @param keyUrl The key of short URL which is used as the primary key in database
     * @param longUrl The long URL which is given by the user
     * @param createdAt The creation time of the record
     * @param viewCount The number of retrieving the record
     */
    public DataModel(String keyUrl, String longUrl, long createdAt, int viewCount) {
        this.keyUrl = keyUrl;
        this.longUrl = longUrl;
        this.createdAt = createdAt;
        this.viewCount = viewCount;
    }

    /**
     * Get the key of URL
     */
    public String getKeyUrl() {
        return keyUrl;
    }

    /**
     * Get the long URL
     */
    public String getLongUrl() {
        return longUrl;
    }

    /**
     * Get the creation time of data model
     */
    public long getCreatedAt() {
        return createdAt;
    }

    /**
     * Get the number of retrieving the record
     */
    public int getViewCount() {
        return viewCount;
    }

    /**
     * Increment the number of retrieving the record
     */
    public void incrementViewCount() {
        this.viewCount += 1;
    }
}
