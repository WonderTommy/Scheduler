package com.example.eventmanager_0;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SelectAction extends AppCompatActivity implements View.OnClickListener{
    private Button deleteButton;
    private Button editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectaction);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = (dm.widthPixels / 5) * 2;
        int height = (dm.heightPixels / 10) * 3;

        getWindow().setLayout(width, height);

        this.setButton();
        this.setOnClickListeners();
    }

    private void setButton(){
        this.deleteButton = (Button) findViewById(R.id.delete_button);
        this.editButton = (Button) findViewById(R.id.edit_button);
    }

    private void setOnClickListeners(){
        this.deleteButton.setOnClickListener(this);
        this.editButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Intent intent = getIntent();
        String index = intent.getStringExtra("eventIndex");
        String position = intent.getStringExtra("eventPosition");
        String fragmentId = intent.getStringExtra("fragmentId");
        switch(v.getId()){
            case R.id.delete_button:
                //Toast.makeText(SelectAction.this, "The position is " + position, Toast.LENGTH_SHORT).show();
                this.setDeleteReturnResult(fragmentId, index, position);
                finish();
                break;
            case R.id.edit_button:
                this.setEditReturnResult(fragmentId, index, position);
                finish();
                break;
            default:
        }
    }

    private void setDeleteReturnResult(String fragmentId, String index, String position){
        Intent intent = new Intent();
        intent.putExtra("action", "delete");
        intent.putExtra("index", index);
        intent.putExtra("position", position);
        intent.putExtra("fragmentId", fragmentId);
        setResult(RESULT_OK, intent);
    }

    private void setEditReturnResult(String fragmentId, String index, String position){
        Intent intent = new Intent();
        intent.putExtra("action", "edit");
        intent.putExtra("index", index);
        intent.putExtra("position", position);
        intent.putExtra("fragmentId", fragmentId);
        setResult(RESULT_OK, intent);
    }
}
