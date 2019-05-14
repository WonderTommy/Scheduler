package com.example.eventmanager_0;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class CreateEvent extends AppCompatActivity implements View.OnClickListener{
    private EditText titleInput;
    private EditText typeInput;
    private EditText subjectInput;

    private Button saveButton;
    private Button cancelButton;

    private Intent intent;
    private String action;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createevent);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = (dm.widthPixels / 5) * 4;
        int height = (dm.heightPixels / 5) * 3;

        getWindow().setLayout(width, height);

        this.setView();
        this.setViewOnClickListener();
        this.setIntent();



    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.save_button:
                setReturnResult();
                break;
            case R.id.cancel_button:
                finish();
                break;
            default:
        }
    }

    private void setView(){
        this.titleInput = (EditText) findViewById(R.id.title_input);
        this.typeInput = (EditText) findViewById(R.id.type_input);
        this.subjectInput = (EditText) findViewById(R.id.subject_input);
        this.saveButton = (Button) findViewById(R.id.save_button);
        this.cancelButton = (Button) findViewById(R.id.cancel_button);
    }

    private void setViewOnClickListener(){
        this.saveButton.setOnClickListener(this);
        this.cancelButton.setOnClickListener(this);
    }

    private void setIntent(){
        this.intent = getIntent();
        this.action = intent.getStringExtra("action");
        System.out.println("!!!The program runs here " + this.intent.getStringExtra("position"));
        if(action.equals("update")){
            this.titleInput.setText(intent.getStringExtra("title"));
            this.typeInput.setText(intent.getStringExtra("type"));
            this.subjectInput.setText(intent.getStringExtra("subject"));
        }
    }


    private void setReturnResult(){
        Map<String, String> newEventInfo = this.getNewEventInfo();
        Intent intent = new Intent();
        intent.putExtra("fragmentId", this.intent.getStringExtra("fragmentId"));
        intent.putExtra("id", this.intent.getStringExtra("eventId"));
        System.out.println("!!!!the program runs here " + intent.getStringExtra("id"));
        intent.putExtra("event_title", newEventInfo.get("title"));
        intent.putExtra("event_type", newEventInfo.get("type"));
        intent.putExtra("event_subject", newEventInfo.get("subject"));
        intent.putExtra("position", this.intent.getStringExtra("position"));
        System.out.println("The program runs here " + this.intent.getStringExtra("position"));
        intent.putExtra("action", this.action);
        setResult(RESULT_OK, intent);
        finish();
    }

    private Map<String, String> getNewEventInfo(){
        Map<String, String> newEventInfo = new HashMap<String, String>();
        newEventInfo.put("title", this.titleInput.getText().toString());
        newEventInfo.put("type", this.typeInput.getText().toString());
        newEventInfo.put("subject", this.subjectInput.getText().toString());
        return newEventInfo;
    }
}
