package org.tehlug.androidApp;

/*
 * Created by behdad on 1/30/15.
 */

import android.net.Uri;

public class RssItem {
    private String title;
    private String description;
    private Uri source;
    private int id;
    private long date;

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
        return String.valueOf(date);
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

    public void setDate(long date) {
        this.date = date;
    }
}
