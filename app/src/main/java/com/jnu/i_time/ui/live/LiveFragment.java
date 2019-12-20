package com.jnu.i_time.ui.live;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jnu.i_time.DayMessageActivity;
import com.jnu.i_time.MainActivity;
import com.jnu.i_time.R;
import com.jnu.i_time.data.DayArrayAdapter;

import static android.app.Activity.RESULT_OK;

public class LiveFragment extends Fragment {

    private LiveViewModel liveViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        liveViewModel = ViewModelProviders.of(this).get(LiveViewModel.class);
        View view = inflater.inflate(R.layout.fragment_live, container, false);

        final TextView textView = view.findViewById(R.id.text_live);
        final ListView listView = view.findViewById(R.id.button_list);

        liveViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        liveViewModel.getAdapter().observe(this, new Observer<DayArrayAdapter>() {
            @Override
            public void onChanged(@Nullable DayArrayAdapter adapter) {
                listView.setAdapter(adapter);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.getContext(),"点击",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.getContext(), DayMessageActivity.class);
                Log.d("id：",""+position);
                intent.putExtra("dayId",position);
                startActivityForResult(intent, MainActivity.REQUEST_CODE_UPDATE_DAY);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case MainActivity.REQUEST_CODE_UPDATE_DAY :{
                if(resultCode==RESULT_OK){
                    //String tag="asdfghjkl";
                    //Log.v(tag,"inreturn");
                    int id=data.getIntExtra("id",0);
                    boolean delete=data.getBooleanExtra("delete",false);
                    if(delete){
                        liveViewModel.getDays_of_live().remove(id);
                    }
                    liveViewModel.getAdapter().observe(this, new Observer<DayArrayAdapter>() {
                        @Override
                        public void onChanged(@Nullable DayArrayAdapter adapter) {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
                break;
            }

        }


    }

}