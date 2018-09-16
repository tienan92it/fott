package com.eightbit.fott.ui.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eightbit.fott.R;
import com.eightbit.fott.data.database.AppDatabase;
import com.eightbit.fott.data.entity.Feed;
import com.eightbit.fott.ui.adapter.FeedAdapter;
import com.eightbit.fott.ui.widget.MyDividerItemDecoration;
import com.eightbit.fott.util.DatabaseInitializer;
import com.eightbit.fott.util.Global;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by antran on 12/2/17.
 */

public class FOTTFeedFragment extends Fragment {

    private static final int LAYOUT_ID = R.layout.fragment_feed;
    public static final String TAG = "FOTTFeed";

    private List<Feed> feedList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FeedAdapter mAdapter;

    //Http request
    private RequestQueue queue;
    private JsonObjectRequest feedRequest;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(getContext());

        // Request a string response from the provided URL.
        feedRequest = new JsonObjectRequest(Request.Method.GET, Global.GET_FEED_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                swipeRefreshLayout.setRefreshing(false);
                try {
                    if (response.getString("status").compareTo("success") == 0)
                        DatabaseInitializer.populateAsyncFeeds(AppDatabase.getAppDatabase(getContext()), response.getJSONArray("content"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Something went wrong!",
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "Something went wrong!",
                        Toast.LENGTH_LONG).show();
            }
        });
        feedRequest.setTag(TAG);

        queue.add(feedRequest);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT_ID, container, false);

        //Init swipe to refresh
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queue.cancelAll(TAG);
                queue.add(feedRequest);
            }
        });

        //Init recycler view
        recyclerView = view.findViewById(R.id.recyclerview);
        mAdapter = new FeedAdapter(getContext(), feedList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 0));
        recyclerView.setAdapter(mAdapter);

        //Live data
        final LiveData<List<Feed>> feedLiveData = AppDatabase.getAppDatabase(getContext()).feedDao().getAllFeeds();
        feedLiveData.observe(this, new Observer<List<Feed>>() {
            @Override
            public void onChanged(@Nullable List<Feed> feeds) {
                feedList.clear();
                for (int i = feeds.size() - 1; i >= 0; i--) {
                    feedList.add(feeds.get(i));
                }
                mAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }
}
