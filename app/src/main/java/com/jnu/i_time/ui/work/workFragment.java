package com.jnu.i_time.ui.work;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jnu.i_time.R;

public class workFragment extends Fragment {

    private workViewModel workViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        workViewModel =
                ViewModelProviders.of(this).get(workViewModel.class);
        View root = inflater.inflate(R.layout.fragment_work, container, false);
        final TextView textView = root.findViewById(R.id.text_work);
        workViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}