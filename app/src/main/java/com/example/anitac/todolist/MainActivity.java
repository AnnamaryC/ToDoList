package com.example.anitac.todolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdaptor;
    ListView lvitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ReadItems();
        itemsAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvitems = (ListView) findViewById(R.id.ToDoList);
        lvitems.setAdapter(itemsAdaptor);

        //mock data
        //items.add(" item 1 \n item 1.5 \n item 1.6");
        //items.add("item 2");

        SetUpViewListListener();
    }

    public void onAddItem (View v){  //Used to add text to list
        EditText newtext= (EditText) findViewById(R.id.TextAdd);
        String itemText = newtext.getText().toString();
        itemsAdaptor.add(itemText);
        newtext.setText("");
        WriteItems();
        Toast.makeText(getApplicationContext(), "Item Added to List", Toast.LENGTH_SHORT).show();
    }

    private void SetUpViewListListener(){ //used press and hold button to delete
        Log.i("MainActivity", "Setting up listener");
        lvitems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("MainActivity" , "Item removed from list: " + i );
                items.remove(i);
                itemsAdaptor.notifyDataSetChanged();
                WriteItems();
                return true;
            }
        });
    }

    private File GetDataFile(){
        return new File(getFilesDir(), "ToDo.txt");
    }

    private void ReadItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(GetDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("Main Activity","Error reading file", e);
            items =  new ArrayList<>();
        }
    }

    private void WriteItems(){
        try {
            FileUtils.writeLines(GetDataFile(), items);
        } catch (IOException e) {
            Log.e("Main Activity","Error write file", e);
            items =  new ArrayList<>();
        }

    }
}
