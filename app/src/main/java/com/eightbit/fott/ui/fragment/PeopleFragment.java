package com.eightbit.fott.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eightbit.fott.R;

/**
 * Created by antran on 12/2/17.
 */

public class PeopleFragment extends Fragment{
    private static final int LAYOUT_ID = R.layout.fragment_people;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT_ID, container, false);
        return view;
    }
}
