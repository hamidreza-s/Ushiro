package io.github.ushiro.data;

public class DataModel {
    private String keyUrl;
    private String longUrl;
    private long createdAt;
    private int viewCount;

    public DataModel(String keyUrl, String longUrl, long createdAt, int viewCount) {
        this.keyUrl = keyUrl;
        this.longUrl = longUrl;
        this.createdAt = createdAt;
        this.viewCount = viewCount;
    }

    public String getKeyUrl() {
        return keyUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void incrementViewCount() {
        this.viewCount += 1;
    }
}
