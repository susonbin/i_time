package com.jnu.i_time.ui.anniversary;

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

public class AnniversaryFragment extends Fragment {

    private AnniversaryViewModel anniversaryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        anniversaryViewModel =
                ViewModelProviders.of(this).get(AnniversaryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_anniversary, container, false);
        final TextView textView = root.findViewById(R.id.text_anniversary);
        anniversaryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}