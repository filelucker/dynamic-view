package com.example.dynamicview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dynamicview.model.DataSource;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ScrollView scrollView;
    LinearLayout linearLayout;
    DataSource data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollView = new ScrollView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(layoutParams);

        linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(linearParams);
        linearLayout.setPadding(10, 10, 10, 10);


        scrollView.addView(linearLayout);

        LinearLayout linearLayout1 = findViewById(R.id.rootContainer);
        if (linearLayout1 != null) {
            linearLayout1.addView(scrollView);
        }


        readJSON();


    }

    private void readJSON() {
        InputStream is = getResources().openRawResource(R.raw.scratch);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonString = writer.toString();
        Log.e("JSON ", jsonString);
        Gson gson = new Gson();
        data = gson.fromJson(jsonString, DataSource.class);
        Log.e("Data ", data.getMessage());

        // create view from json
        for (int i = 0; i < data.getData().size(); i++) {
            if (data.getData().get(i).getType().equals("View")) {
                View v = new View(this);
                v.setMinimumHeight(data.getData().get(i).getMinHeight());
                v.setId(i);
                linearLayout.addView(v);
            }
            if (data.getData().get(i).getType().equals("TextView")) {
                TextView textView = new TextView(this);
                textView.setText(data.getData().get(i).getLabel());
                textView.setId(i);
                linearLayout.addView(textView);
            }
            if (data.getData().get(i).getType().equals("EditText")) {
                EditText editText = new EditText(this);
                editText.setHint(data.getData().get(i).getHint());
                editText.setId(i);
//                editText.setText("xxxxxxx");
                linearLayout.addView(editText);
            }
            if (data.getData().get(i).getType().equals("Button")) {
                Button btnTag = new Button(this);
                btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                btnTag.setText(data.getData().get(i).getLabel());
                btnTag.setId(i);
                btnTag.setOnClickListener(this);
                linearLayout.addView(btnTag);
            }

        }
//
//        for (int i = 0; i < linearLayout.getChildCount(); i++) {
//            View v = linearLayout.getChildAt(i);
//            if (v instanceof EditText) {
//                //Do your stuff here
//                Log.e("EditText ", ((EditText) v).getText().toString());
//                Log.e("EditText ", String.valueOf(v.getId()));
//
//            }
////            if (v instanceof TextView) {
////                //Do your stuff here
////                Log.e("TextView ", v.toString());
////            }
//        }

    }

    @Override
    public void onClick(View view) {
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View v = linearLayout.getChildAt(i);
            if (v instanceof Button) {
                if (view.getId() == v.getId()) {
                    String field = data.getData().get(v.getId()).getGetField();

                    if (field.equals("ALL")) {

                        // show all edit text data
                        String showString = "";

                        for (int j = 0; j < linearLayout.getChildCount(); j++) {
                            View vi = linearLayout.getChildAt(j);
                            if (vi instanceof EditText) {
                                String s = ((EditText) vi).getText().toString();
                                showString = showString + s + ", ";
                            }

                        }
                        Toast.makeText(this, showString, Toast.LENGTH_SHORT).show();

                    } else {
                        // show specific edit text data
                        View vi = linearLayout.getChildAt(Integer.parseInt(field));
                        if (vi instanceof EditText) {
                            Log.e("EditText ", ((EditText) vi).getText().toString());
                            Log.e("EditText ", String.valueOf(vi.getId()));

                            String s = ((EditText) vi).getText().toString();
                            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();


                        }
                    }


                    // show data for specific button
//                    for (int j = 0; j < linearLayout.getChildCount(); j++) {
//                        View vi = linearLayout.getChildAt(j);
//                        if (vi instanceof EditText) {
//                            Log.e("EditText ", ((EditText) vi).getText().toString());
//                            Log.e("EditText ", String.valueOf(vi.getId()));
//
//                            String s = ((EditText) vi).getText().toString();
//                            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
//
//
//                        }
//
//                    }
                }

            }
        }

    }
}