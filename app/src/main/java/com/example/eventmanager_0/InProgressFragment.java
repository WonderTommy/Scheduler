package com.example.eventmanager_0;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class InProgressFragment extends Fragment {
    private RecyclerView eventListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.in_progress_layout, container, false);

        RecyclerView eventList = (RecyclerView) view.findViewById(R.id.in_progress_event_list);
        this.eventListView = eventList;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        eventList.setLayoutManager(layoutManager);
        EventAdapter eventAdapter = new EventAdapter(this.getId(), getActivity(), createEvents());
        eventList.setAdapter(eventAdapter);
        return view;
    }

    private List<EventContent> createEvents(){
        List<EventContent> list = new ArrayList<EventContent>();
        EventContent e1 = new EventContent("first event", "Assignment", "MATH 135");
        EventContent e2 = new EventContent("second event", "Test", "MATH137");
        list.add(e1);
        list.add(e2);
        for(int index = 0; index < 0; ++index){
            if(index%2==0){
                list.add(e1);
            } else {
                list.add(e2);
            }
        }
        return list;
    }

    public void addNewEvent(String title, String type, String subject){
        EventContent newEvent = new EventContent(title, type, subject);
        EventAdapter eventAdapter = (EventAdapter) this.eventListView.getAdapter();
        eventAdapter.addNewItem(newEvent);
        eventAdapter.notifyItemInserted(eventAdapter.getItemCount() - 1);

        Toast.makeText(getContext(), "New Event Added", Toast.LENGTH_SHORT).show();
    }

    public RecyclerView getEventListView(){
        return this.eventListView;
    }
}
