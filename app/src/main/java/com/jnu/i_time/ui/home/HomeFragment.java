package com.jnu.i_time.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        final ListView listView = view.findViewById(R.id.button_list);

        homeViewModel.getAdapter().observe(this, new Observer<DayArrayAdapter>() {
            @Override
            public void onChanged(@Nullable DayArrayAdapter adapter) {
                listView.setAdapter(adapter);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.getContext(),"点击",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.getContext(), DayMessageActivity.class);
                //Log.d("size：",""+homeViewModel.getDays_of_home().size());
                //Log.d("id：",""+homeViewModel.getDays_of_home().get(position).getId());
                intent.putExtra("dayId",homeViewModel.getDays_of_home().get(position).getId());
                startActivityForResult(intent, MainActivity.REQUEST_CODE_UPDATE_DAY);
            }
        });
        final Handler handler=new Handler(){
            public void handleMessage(Message msg){
                homeViewModel.getAdapter().observe(HomeFragment.this, new Observer<DayArrayAdapter>() {
                    @Override
                    public void onChanged(@Nullable DayArrayAdapter adapter) {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while(true){
                        handler.sendEmptyMessage(1);
                        Thread.sleep(1000);
                    }
                }catch (InterruptedException e){

                }
            }
        }).start();
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
                        //Log.d("aftdele",""+MainActivity.getDays().size());
                        MainActivity.getDays().remove( MainActivity.getIdFindDay().get(id));
                        //Log.d("aftdele",""+MainActivity.getDays().size());
                        MainActivity.getIdFindDay().remove(id);

                    }
                    homeViewModel.getAdapter().observe(this, new Observer<DayArrayAdapter>() {
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