package com.example.a328789.compress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a328789.compress.service.MainService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.button1)
    Button compressFile;
    @BindView(R.id.button2)
    Button compressFolder;
    @BindView(R.id.button3)
    Button jCompress;
    @BindView(R.id.image)
    ImageView image;
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
        jCompress.setOnClickListener(this);

        loadImage();
    }

    private void loadImage() {

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
                InputStream hh=null;
                ZipOutputStream zipoutputStream=null;
                try {
                    File file1 = new File(service.getPath() + "/L.txt");
                    hh = new FileInputStream(file1);
                    File file = new File(service.getPath() + "/L.zip");
                    if(!file.exists()){
                        file.createNewFile();
                    }

                    zipoutputStream = new ZipOutputStream(new FileOutputStream(service.getPath() + "/L.zip"));
                    Log.e("########",service.getPath() + "/L.zip");
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zipEntry.setSize(file1.length());
                    zipoutputStream.putNextEntry(zipEntry);
                    zipoutputStream.setComment("comment");
                    int len=0;
                    byte[] bytes = new byte[1024];
                    while ((len=hh.read(bytes))!=-1){
                        zipoutputStream.write(bytes,0,len);

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("EEEEEEEEEE",e.getMessage());
                }
                break;
            case R.id.button3:
                service.jCompress(service.getPath()+"/L.zip",service.getPath()+"/g.txt");
                break;
        }
    }
}
