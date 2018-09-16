package com.eightbit.fott.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverter;
import android.renderscript.Type;

import com.eightbit.fott.data.entity.Feed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antran on 12/2/17.
 */

@Dao
public interface FeedDao {
    @Query("SELECT * FROM feed")
    LiveData<List<Feed>> getAllFeeds();

    @Query("SELECT COUNT(*) from feed")
    int countFeeds();

    @Insert
    void insertAll(Feed... feeds);

    @Delete
    void delete(Feed feed);

    @Delete
    void deleteAll(Feed... feeds);

    @Query("DELETE FROM feed")
    void nukeFeed();

    @Query("SELECT * FROM feed WHERE id = :id")
    Feed getFeedById(int id);
}