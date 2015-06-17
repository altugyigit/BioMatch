package com.altygtsoft.biomatch;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DialogActivity extends ActionBarActivity {

    public EditText trainEdtOnline;
    public Button btnTrainOnline;
    public String fileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        trainEdtOnline = (EditText)findViewById(R.id.trainEdtOnline);
        btnTrainOnline = (Button)findViewById(R.id.trainBtnOnline);

        startDialog();
    }

    private void startDialog() {
        btnTrainOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = trainEdtOnline.getText().toString();
                if (text.equals("")){
                    Toast.makeText(getApplicationContext(), "Lütfen tür adı giriniz", Toast.LENGTH_LONG).show();

                }
                fileName = text;
                finish();

            }
        });
    }

    @Override
    protected void onDestroy() {

        Intent intent = new Intent(getApplicationContext(), TakePictureTrainOnline.class);
        intent.putExtra("FILENAME", fileName);
        startActivity(intent);
        super.onDestroy();

    }
}
