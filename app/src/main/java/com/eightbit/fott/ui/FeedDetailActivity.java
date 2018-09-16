package com.eightbit.fott.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.eightbit.fott.R;
import com.eightbit.fott.data.database.AppDatabase;
import com.eightbit.fott.data.entity.Feed;
import com.eightbit.fott.util.DateTimeHelper;

/**
 * Created by antran on 12/3/17.
 */

public class FeedDetailActivity extends Activity {
    private static final int LAYOUT_ID = R.layout.activity_feed_detail;

    private int id = -1;
    private Feed feed;


    //Http request
    private RequestQueue queue;
    private ImageLoader imageLoader;

    //Init detail views
    private ImageView backBtn;
    private NetworkImageView feedImage;
    private TextView title;
    private TextView date;
    private TextView ingress;
    private TextView subject;
    private TextView description;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT_ID);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        Bundle b = getIntent().getExtras();
        if (b != null)
            id = b.getInt("ID");

        feed = AppDatabase.getAppDatabase(this).feedDao().getFeedById(id);

        //Init volley reuqest
        queue = Volley.newRequestQueue(this);
        imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap>
                    cache = new LruCache<String, Bitmap>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });

        //Init views
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        feedImage = findViewById(R.id.feed_image);
        title = findViewById(R.id.feed_title);
        date = findViewById(R.id.feed_date);
        ingress = findViewById(R.id.feed_ingress);
        subject = findViewById(R.id.feed_subject);
        description = findViewById(R.id.feed_description);

        bindData(feed);
    }

    private void bindData(Feed feed) {
        feedImage.setImageUrl(feed.getImage(), imageLoader);
        title.setText(feed.getTitle());
        date.setText(DateTimeHelper.getFormattedDateFrom(feed.getDateTime()));
        ingress.setText(feed.getIngress());
        if (feed.getFirstContent() != null) {
            subject.setText(feed.getFirstContent().getSubject());
            description.setText(feed.getFirstContent().getDescription());
        }
    }
}
