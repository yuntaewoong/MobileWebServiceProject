package com.example.mobilewebserviceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    String urlStr = "http://127.0.0.1:8000";
    TextView text1;
    TextView text2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = (TextView)findViewById(R.id.text1);
        text2 = (TextView)findViewById(R.id.text2);
    }
    public void onClickForLoad(View v) {
        Log.d("Debug","Click");
        new Thread(){
            @Override
            public void run() {
                try{
                    URL url = new URL("http://10.0.2.2:8000/get_SleepStart");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    if(conn != null){
                        conn.setConnectTimeout(10000);
                        conn.setRequestMethod("GET");
                        int resCode = conn.getResponseCode();

                        if(resCode == HttpURLConnection.HTTP_OK){
                            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            String line = null;

                            while(true){
                                line = reader.readLine();
                                if(line == null){
                                    break;
                                }
                                JSONObject jsonObject = new JSONObject(line);
                                String receiveString = jsonObject.getString("data");
                                String resultString =
                                        "수면 시작 : "
                                        + receiveString.substring(5,7)
                                        + "월 "
                                        + receiveString.substring(8,10)
                                        + "일 "
                                        + receiveString.substring(11,13)
                                        + "시 "
                                        + receiveString.substring(14,16)
                                        + "분";

                                text1.setText(resultString);
                            }
                            reader.close();
                        }
                        conn.disconnect();
                    }
                }catch (Exception e){

                }
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                try{
                    URL url = new URL("http://10.0.2.2:8000/get_SleepEnd");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    if(conn != null){
                        conn.setConnectTimeout(10000);
                        conn.setRequestMethod("GET");
                        int resCode = conn.getResponseCode();

                        if(resCode == HttpURLConnection.HTTP_OK){
                            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            String line = null;

                            while(true){
                                line = reader.readLine();
                                if(line == null){
                                    break;
                                }
                                JSONObject jsonObject = new JSONObject(line);
                                String receiveString = jsonObject.getString("data");
                                String resultString =
                                        "수면 종료 : "
                                                + receiveString.substring(5,7)
                                                + "월 "
                                                + receiveString.substring(8,10)
                                                + "일 "
                                                + receiveString.substring(11,13)
                                                + "시 "
                                                + receiveString.substring(14,16)
                                                + "분";

                                text2.setText(resultString);
                            }
                            reader.close();
                        }
                        conn.disconnect();
                    }
                }catch (Exception e){

                }
            }
        }.start();
    }
}


