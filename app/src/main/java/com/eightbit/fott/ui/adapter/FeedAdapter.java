package com.eightbit.fott.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.eightbit.fott.R;
import com.eightbit.fott.data.entity.Feed;
import com.eightbit.fott.ui.FeedDetailActivity;
import com.eightbit.fott.util.DateTimeHelper;

import java.util.List;

/**
 * Created by antran on 12/2/17.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {

    public static final String TAG = "FOTTFeed";

    private List<Feed> feedList;
    private Context mContext;

    //Http request
    private RequestQueue queue;
    private ImageLoader imageLoader;

    public FeedAdapter(Context context, List<Feed> feedList) {
        this.mContext = context;
        this.feedList = feedList;
        queue = Volley.newRequestQueue(mContext);
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

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Feed feed = feedList.get(position);
        holder.image.setImageUrl(feed.getImage(), imageLoader);
        holder.setTitle(feed.getTitle());
        holder.setDate(DateTimeHelper.getFormattedDateFrom(feed.getDateTime()));
        holder.setIngress(feed.getIngress());
        holder.setOnDetailEntryClick(feed.getId());
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private NetworkImageView image;
        private TextView title;
        private TextView date;
        private TextView ingress;
        private ImageView detailEntry;

        public MyViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.feed_image);
            title = view.findViewById(R.id.feed_title);
            date = view.findViewById(R.id.feed_date);
            ingress = view.findViewById(R.id.feed_ingress);
            detailEntry = view.findViewById(R.id.detail_entry);
        }

        public void setImage(Bitmap image) {
            this.image.setImageBitmap(image);
        }

        public void setTitle(String title) {
            this.title.setText(title);
        }

        public void setDate(String date) {
            this.date.setText(date);
        }

        public void setIngress(String ingress) {
            this.ingress.setText(ingress);
        }

        public void setOnDetailEntryClick(final int id) {
            detailEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: Show cai detail cua cai feed id
                    Log.d(getClass().getName(), id + "");
                    Intent intent = new Intent(mContext, FeedDetailActivity.class);
                    intent.putExtra("ID", id);
                    mContext.startActivity(intent);
                }
            });
        }
    }

}
