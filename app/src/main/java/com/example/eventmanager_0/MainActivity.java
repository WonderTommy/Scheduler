package com.example.eventmanager_0;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public DatabaseHelper databaseHelper;

    private ToDoFragment toDoFragment;
    private InProgressFragment inProgressFragment;

    public static final int toDoFragmentCode = 0;
    public static final int inProgressFragmentCode = 1;

    private final int fabRequestCode = 1;
    public final int toDoSelectActionRequestCode = 2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setDatabaseHelper();
        setContentView(R.layout.activity_main);

        this.setFragment();
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.create_new_event);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Toast.makeText(v.getContext(), "FAB clicked", Toast.LENGTH_SHORT ).show();
                //Intent createNewEvent = new Intent(MainActivity.this, CreateEvent.class);
                //startActivity(createNewEvent);
                InProgressFragment inProgressFragment = (InProgressFragment) getSupportFragmentManager().findFragmentById(R.id.in_progress_list);
                inProgressFragment.addNewEvent();
                Toast.makeText(v.getContext(), "New Event Added", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case fabRequestCode:
                if(resultCode == RESULT_OK){
                    String eventTitle = data.getStringExtra("event_title");
                    String eventType = data.getStringExtra("event_type");
                    String eventSubject = data.getStringExtra("event_subject");
                    switch(data.getStringExtra("action")){
                        case "createNew":
                            this.toDoFragment.addNewEvent(eventTitle, eventType, eventSubject);
                            break;
                        case "update":
                            String position = data.getStringExtra("position");
                            System.out.println("!The program runs here " + data.getStringExtra("id"));
                            //int fragmentId = Integer.parseInt(data.getStringExtra("fragmentId"));
                            int id = Integer.parseInt(data.getStringExtra("id"));
                            this.toDoFragment.updateEvent(id, Integer.parseInt(position), eventTitle, eventType, eventSubject);
                            break;
                    }
                }
                break;
            case toDoSelectActionRequestCode:
                if(resultCode == RESULT_OK){
                    String takeAction = data.getStringExtra("action");
                    int index = Integer.parseInt(data.getStringExtra("index"));
                    int position = Integer.parseInt(data.getStringExtra("position"));
                    int fragmentId = Integer.parseInt(data.getStringExtra("fragmentId"));
                    Toast.makeText(MainActivity.this, takeAction + " the item at position " + position, Toast.LENGTH_SHORT).show();
                    /*if(fragmentId == R.id.to_do_list){
                        //this.toDoFragment.removeEvent(position, index);
                        Toast.makeText(MainActivity.this, "to do", Toast.LENGTH_SHORT).show();
                    } else if(fragmentId == R.id.in_progress_list){
                        Toast.makeText(MainActivity.this, "in progress", Toast.LENGTH_SHORT).show();
                    }*/
                    switch(takeAction){
                        case "delete":
                            if(fragmentId == R.id.to_do_list){
                                this.toDoFragment.removeEvent(position, index);
                            } else if(fragmentId == R.id.in_progress_list){
                                
                            }
                            break;
                        case "edit":
                            if(fragmentId == R.id.to_do_list){
                                //this.toDoFragment.removeEvent(position, index);
                                Intent intent = new Intent(MainActivity.this, CreateEvent.class);
                                intent.putExtra("position", Integer.toString(position));
                                //System.out.println("The program runs here " + position);

                                EventContent eventContent = ((EventAdapter) this.toDoFragment.getEventListView().getAdapter()).getItem(position);
                                intent.putExtra("eventId", Integer.toString(eventContent.getId()));
                                intent.putExtra("title", eventContent.getTitle());
                                intent.putExtra("type", eventContent.getType());
                                intent.putExtra("subject", eventContent.getSubject());
                                intent.putExtra("fragmentId", Integer.toString(fragmentId));
                                intent.putExtra("action", "update");
                                startActivityForResult(intent, this.fabRequestCode);
                            } else if(fragmentId == R.id.in_progress_list){

                            }
                            break;
                        default:
                    }
                }
                break;
            default:
        }
    }

    public void fabOnClick(){
        Intent intent = new Intent(MainActivity.this, CreateEvent.class);
        intent.putExtra("action", "createNew");
        intent.putExtra("position", "-1");
        startActivityForResult(intent, this.fabRequestCode);
    }

    private void setFragment(){
        this.toDoFragment = (ToDoFragment) getSupportFragmentManager().findFragmentById(R.id.to_do_list);
        this.inProgressFragment = (InProgressFragment) getSupportFragmentManager().findFragmentById(R.id.in_progress_list);
    }

    private void setDatabaseHelper(){
        this.databaseHelper = new DatabaseHelper(MainActivity.this);
    }




    /*@Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.create_new_event:
                InProgressFragment inProgressFragment = (InProgressFragment) getSupportFragmentManager().findFragmentById(R.id.in_progress_list);
                inProgressFragment.addNewEvent();
                Toast.makeText(v.getContext(), "New Event Added", Toast.LENGTH_SHORT).show();

                break;

            default:
        }
    }*/

    /*
    <?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical" android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        android:text="hello"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:gravity="center"/>

    <android.support.v7.widget.RecyclerView
        android:id="@+id/event_list"
        android:layout_width="match_parent"
        android:layout_weight="10"
        android:layout_height="0dp"/>

    <android.support.design.widget.FloatingActionButton
        android:id="@+id/create_new_event"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="16dp"
        android:layout_gravity="start|bottom"
        android:background="#0000FF"/>


</LinearLayout>
     */
}
