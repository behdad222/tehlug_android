package org.tehlug.androidApp;

import android.net.Uri;

public class RssItem {
    private String title;
    private String description;
    private Uri source;
    private int id;
    private String date;
    private String topic;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Uri getSource() {
        return source;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTopic() {
        return topic;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSource(Uri source) {
        this.source = source;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
