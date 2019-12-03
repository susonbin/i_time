package com.jnu.i_time.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jnu.i_time.R;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = view.findViewById(R.id.text_home);
        final ListView listView = view.findViewById(R.id.button_list);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        homeViewModel.getAdapter().observe(this, new Observer<DayArrayAdapter>() {
            @Override
            public void onChanged(@Nullable DayArrayAdapter adapter) {
                listView.setAdapter(adapter);
            }
        });
        return view;
    }
}