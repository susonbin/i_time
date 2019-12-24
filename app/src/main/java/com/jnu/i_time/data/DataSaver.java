package com.jnu.i_time.data;

import android.content.Context;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataSaver {
    Context context;
    ArrayList<Day> books=new ArrayList<Day>();

    public DataSaver(Context context) {
        this.context = context;
    }

    public ArrayList<Day> getBooks() {
        return books;
    }


    public void save_days(){
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput("Serializable_days.txt",Context.MODE_PRIVATE)
            );
            outputStream.writeObject(books);
            outputStream.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Day> load_days() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(
                    context.openFileInput("Serializable_days.txt")
            );
            books = (ArrayList<Day>) inputStream.readObject();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
}
