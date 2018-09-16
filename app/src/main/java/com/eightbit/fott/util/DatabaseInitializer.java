package com.eightbit.fott.util;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.eightbit.fott.data.database.AppDatabase;
import com.eightbit.fott.data.entity.Feed;
import com.eightbit.fott.data.entity.FeedContent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antran on 12/2/17.
 */

public class DatabaseInitializer {
    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsyncFeeds(@NonNull final AppDatabase db, JSONArray feedsJSON) {
        List<Feed> feedList = parseFeedFrom(feedsJSON);
        PopulateDbAsync task = new PopulateDbAsync(db, feedList);
        task.execute();
    }

    private static List<Feed> parseFeedFrom(JSONArray feedsJSON) {
        List<Feed> feedList = new ArrayList<>();

        for(int i = 0; i < feedsJSON.length(); i++) {
            try {
                JSONObject feedJson = feedsJSON.getJSONObject(i);
                Feed feed = new Gson().fromJson(feedJson.toString(), new TypeToken<Feed>() {}.getType());
                if (feedJson.getJSONArray("content").length() > 0) {
                    FeedContent feedContent = new FeedContent();
                    JSONObject content = feedJson.getJSONArray("content").getJSONObject(0);
                    feedContent.setType(content.getString("type"));
                    feedContent.setSubject(content.getString("subject"));
                    feedContent.setDescription(content.getString("description"));
                    feed.setFirstContent(feedContent);
                }
                feedList.add(feed);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return feedList;
    }

    private static Feed addFeed(final AppDatabase db, Feed feed) {
        db.feedDao().insertAll(feed);
        return feed;
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;
        private final List<Feed> feedList;

        PopulateDbAsync(AppDatabase db, List<Feed> feeds) {
            mDb = db;
            feedList = feeds;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDb.feedDao().nukeFeed();
            for (int i = 0; i < feedList.size(); i++) {
                addFeed(mDb, feedList.get(i));
            }
            return null;
        }

    }
}
