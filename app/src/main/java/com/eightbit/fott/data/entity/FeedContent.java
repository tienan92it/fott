package com.eightbit.fott.data.entity;

import android.arch.persistence.room.Ignore;

/**
 * Created by antran on 12/3/17.
 */

public class FeedContent {
    private String type;
    private String subject;
    private String description;

    public FeedContent() {
    }

    @Ignore
    public FeedContent(String type, String subject, String description) {
        this.type = type;
        this.subject = subject;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
