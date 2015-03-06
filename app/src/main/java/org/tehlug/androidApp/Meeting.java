package org.tehlug.androidApp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Meeting extends RealmObject {

    @PrimaryKey
    private int id;
    private String title;
    private String description;
    private String source;
    private String date;
    private String topic;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getSource() {
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

    public void setSource(String source) {
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
