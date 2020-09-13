package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownLoadActivity extends AppCompatActivity {

    public static final int INIT_PROGRESS_DATA = 0;
    public static final String DOWNLOAD_URL = "http://idea.medeming.com/jets/images/jihuoma.zip";
    public static final String FILE_NAME = "idea.txt";
    private Button buttonDownload;
    private TextView textViewDownload;
    private ProgressBar progressBarDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        getPermission();
        initView();
        setListener();
        setInitData();
    }

    private void getPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_CONTACTS
                },1);
    }

    private void setInitData() {
        buttonDownload.setText(R.string.button_download);
        textViewDownload.setText(R.string.textview_download);
        progressBarDownload.setProgress(INIT_PROGRESS_DATA);
    }

    private void setListener() {
        buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadAsyncTask downloadAsyncTask = new DownloadAsyncTask();
                downloadAsyncTask.execute(DOWNLOAD_URL);
            }
        });
    }

    private void initView() {
        buttonDownload = findViewById(R.id.button_download);
        textViewDownload = findViewById(R.id.textview_download);
        progressBarDownload = findViewById(R.id.progressbar_download);

    }

    public class DownloadAsyncTask extends AsyncTask<String, Integer, Boolean> {

        private String filePath;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            buttonDownload.setText(R.string.downloading);
            textViewDownload.setText(R.string.downloading);
            progressBarDownload.setProgress(INIT_PROGRESS_DATA);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            if (strings == null || strings.length == 0) {
                return false;
            }
            try {
                URL url = new URL(strings[0]);
                URLConnection urlConnection = url.openConnection();
                int contentLength = urlConnection.getContentLength();
                filePath = Environment.getExternalStorageDirectory() + File.separator + FILE_NAME;


                int downloadLength = 0;
                File file = new File(filePath);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                InputStream inputStream = urlConnection.getInputStream();
                byte[] bytes = new byte[1024];

                int length = 0;
                while ((length = inputStream.read(bytes)) != -1) {
                    fileOutputStream.write(bytes, 0, length);
                    downloadLength += length;
                    publishProgress(downloadLength * 100 / contentLength);
                }
                fileOutputStream.close();
                inputStream.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            textViewDownload.setText(aBoolean ? R.string.download_finish + filePath : getString(R.string.download_fail));
            buttonDownload.setText(aBoolean ? R.string.download_finish + filePath : getString(R.string.download_fail));
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(values != null && values.length > 0){
                progressBarDownload.setProgress(values[0]);
            }
        }
    }
}