package com.jnu.i_time.ui.work;

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
import com.jnu.i_time.ui.home.HomeFragment;

import static android.app.Activity.RESULT_OK;

public class workFragment extends Fragment {

    private workViewModel workViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        workViewModel = ViewModelProviders.of(this).get(workViewModel.class);
        View view = inflater.inflate(R.layout.fragment_work, container, false);

        final ListView listView = view.findViewById(R.id.button_list);

        workViewModel.getAdapter().observe(this, new Observer<DayArrayAdapter>() {
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
                //Log.d("id：",""+position);
                intent.putExtra("dayId",workViewModel.getDays_of_work().get(position).getId());
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
                        //Log.d("aftdele",""+MainActivity.getDays().size());
                        MainActivity.getDays().remove( MainActivity.getIdFindDay().get(id));
                        //Log.d("aftdele",""+MainActivity.getDays().size());
                        MainActivity.getIdFindDay().remove(id);
                        MainActivity.getNavController().navigate(R.id.nav_work);
                    }
                }
                else{
                    MainActivity.getNavController().navigate(R.id.nav_work);
                    //Log.d("back4",resultCode+"");
                }
                break;
            }

        }


    }


}