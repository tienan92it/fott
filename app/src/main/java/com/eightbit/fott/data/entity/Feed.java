package com.eightbit.fott.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antran on 12/2/17.
 */
@Entity(tableName = "feed")
public class Feed {
    @PrimaryKey
    private int id;

    private String title;

    @ColumnInfo(name = "date_time")
    private String dateTime;

    private List<String> tags = new ArrayList<>();

    private String ingress;

    private String image;

    private int created;

    private int changed;

    @Embedded
    private FeedContent firstContent = new FeedContent();

    @Ignore
    public Feed() {
    }

    public Feed(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getIngress() {
        return ingress;
    }

    public void setIngress(String ingress) {
        this.ingress = ingress;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public int getChanged() {
        return changed;
    }

    public void setChanged(int changed) {
        this.changed = changed;
    }

    public FeedContent getFirstContent() {
        return firstContent;
    }

    public void setFirstContent(FeedContent firstContent) {
        this.firstContent = firstContent;
    }
}
