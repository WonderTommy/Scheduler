package com.example.eventmanager_0;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ToDoFragment extends Fragment implements View.OnClickListener{
    private View fragmentView;
    private FloatingActionButton fab;
    private RecyclerView eventListView;

    private int eventIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.to_do_layout, container, false);
        this.setView(view);

        RecyclerView eventList = (RecyclerView) view.findViewById(R.id.event_list);
        this.eventListView = eventList;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        eventList.setLayoutManager(layoutManager);
        EventAdapter eventAdapter = new EventAdapter(this.getId(), getActivity(), initiateEventList());
        eventList.setAdapter(eventAdapter);

        this.setFabView();
        this.setButtonOnClick();

        return view;
    }

    private List<EventContent> createEvents(){
        List<EventContent> list = new ArrayList<EventContent>();
        EventContent e1 = new EventContent("first event", "Assignment", "MATH 135");
        EventContent e2 = new EventContent("second event", "Test", "MATH137");
        list.add(e1);
        list.add(e2);
        for(int index = 0; index < 2; ++index){
            if(index%2==0){
                list.add(e1);
            } else {
                list.add(e2);
            }
        }
        return list;
    }

    public List<EventContent> initiateEventList(){
        MainActivity mainActivity = (MainActivity) getActivity();
        List<EventContent> toDoEvents = mainActivity.databaseHelper.queryAllEvent(MainActivity.toDoFragmentCode);
        if(toDoEvents.size() != 0){
            this.eventIndex = toDoEvents.get(toDoEvents.size() - 1).getIndex() + 1;
        } else {
            this.eventIndex = 0;
        }
        return toDoEvents;
    }

    private void setButtonOnClick(){
        this.fab.setOnClickListener(this);
    }

    private void setView(View v){
        this.fragmentView = v;
    }

    private void setFabView(){
        this.fab = (FloatingActionButton) this.fragmentView.findViewById(R.id.create_new_event);;
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.create_new_event:
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.fabOnClick();

                break;

            default:
        }
    }

    public void addNewEvent(String title, String type, String subject){
        EventContent newEvent = new EventContent(title, type, subject);
        newEvent.setId(this.eventIndex);
        newEvent.setIndex(this.eventIndex);
        EventAdapter eventAdapter = (EventAdapter) this.eventListView.getAdapter();
        eventAdapter.addNewItem(newEvent);
        eventAdapter.notifyItemInserted(eventAdapter.getItemCount() - 1);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.databaseHelper.insertEvent(MainActivity.toDoFragmentCode, this.eventIndex, this.eventIndex, title, type, subject);
        Toast.makeText(getContext(), "the new index is " + Integer.toString(newEvent.getIndex()), Toast.LENGTH_SHORT).show();
        ++this.eventIndex;
        //Toast.makeText(getContext(), "New Event Added", Toast.LENGTH_SHORT).show();
    }

    public void removeEvent(int index, int num){
        EventAdapter eventAdapter = (EventAdapter) this.eventListView.getAdapter();
        eventAdapter.removeItem(index);
        eventAdapter.notifyItemRemoved(index);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.databaseHelper.deleteEvent(MainActivity.toDoFragmentCode, num);
    }

    public void updateEvent(int id, int index, String title, String type, String subject){
        EventAdapter eventAdapter = (EventAdapter) this.eventListView.getAdapter();
        EventContent eventContent = new EventContent(title, type, subject);
        eventAdapter.updateItem(index, eventContent);
        eventAdapter.notifyItemChanged(index);

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.databaseHelper.updateEvent(MainActivity.toDoFragmentCode, id, id, title, type, subject);
    }

    public RecyclerView getEventListView(){
        return this.eventListView;
    }
}
