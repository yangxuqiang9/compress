package com.example.a328789.compress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.a328789.compress.service.MainService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.button1)
    Button compressFile;
    @BindView(R.id.button2)
    Button compressFolder;
    private MainService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        service = MainService.getInstance();
        compressFile.setOnClickListener(this);
        compressFolder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1://压缩文件
                try {
                    InputStream open = getAssets().open("hh.txt");
                    String des=service.getPath()+"/hh.zip";
                    service.compressFile(open,"d",des);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button2:
                Toast.makeText(this,"压缩完成",Toast.LENGTH_LONG).show();
                String content="我是被压缩的内容";
                String s = service.getPath() + "content.zip";
                Log.e("$$$$$$$$",s);
                try {
                    GZIPOutputStream gzipOutputStream = new GZIPOutputStream(new FileOutputStream(s));
                    gzipOutputStream.write(s.getBytes());
                    gzipOutputStream.flush();
                    gzipOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
