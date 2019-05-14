package com.example.eventmanager_0;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class EventAdapter extends  RecyclerView.Adapter<EventAdapter.ViewHolder>{
    private List<EventContent> mEvents;
    private Context context;
    private int fragmentId;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        View eventCard;
        TextView eventTitle;
        TextView eventType;
        TextView eventSubject;

        public ViewHolder(View view){
            super(view);
            eventCard = view;
            eventTitle = (TextView) view.findViewById(R.id.event_title);
            eventType = (TextView) view.findViewById(R.id.event_type);
            eventSubject = (TextView) view.findViewById(R.id.event_subject);
        }
    }

    public EventAdapter(int fragmentId, Context context, List<EventContent> myEvents){
        this.fragmentId = fragmentId;
        this.context = context;
        this.mEvents = myEvents;
    }


    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.eventCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = viewHolder.getAdapterPosition();
                EventContent eventContent = mEvents.get(position);
                Toast.makeText(v.getContext(), eventContent.getIndex() + " " + eventContent.getSubject(), Toast.LENGTH_SHORT).show();
                itemOnclick(position, eventContent.getIndex());
            }
        });
        return viewHolder;
    }

    private void itemOnclick(int position, int index){
        Intent intent = new Intent(context, SelectAction.class);
        intent.putExtra("eventIndex", Integer.toString(index));
        intent.putExtra("eventPosition", Integer.toString(position));
        intent.putExtra("fragmentId", Integer.toString(fragmentId));
        //v.getContext().startActivity(intent);
        ((MainActivity) context).startActivityForResult(intent, ((MainActivity) context).toDoSelectActionRequestCode);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        EventContent eventContent = mEvents.get(position);
        holder.eventTitle.setText(eventContent.getTitle());
        holder.eventType.setText(eventContent.getType());
        holder.eventSubject.setText(eventContent.getSubject());
    }

    @Override
    public int getItemCount(){
        return this.mEvents.size();
    }

    public void addNewItem(EventContent event){
        this.mEvents.add(event);
    }

    public void removeItem(int index){
        this.mEvents.remove(index);
    }

    public void updateItem(int index, EventContent event){
        EventContent currentEvent = this.mEvents.get(index);
        currentEvent.setTitle(event.getTitle());
        currentEvent.setType(event.getType());
        currentEvent.setSubject(event.getSubject());
    }

    public EventContent getItem(int position){
        return this.mEvents.get(position);
    }

}
