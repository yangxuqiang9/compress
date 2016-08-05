package com.example.a328789.compress.service;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by 328789 on 2016/7/28.
 */
public class MainService {

    private static MainService mainService;
    public static final String TAG = MainService.class.getSimpleName();
    private BufferedInputStream bufferedInputStream;
    private GZIPOutputStream gzipOutputStream;

    private MainService() {
    }

    public static MainService getInstance() {
        if (mainService == null) {
            synchronized (MainService.class) {
                if (mainService == null) {
                    mainService = new MainService();
                }
            }
        }
        return mainService;
    }

    /**
     * 压缩文件
     */
    public void compressFile(InputStream i,String src, String des) {
        File srcFile = new File(src);

        try {
            if (!srcFile.exists()) {
                srcFile.createNewFile();
            }
            bufferedInputStream = new BufferedInputStream(new FileInputStream(srcFile));
            gzipOutputStream = new GZIPOutputStream(new FileOutputStream(des));
//            read(bufferedInputStream, gzipOutputStream);
            read(i,gzipOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "文件创建失败");
        }finally {
            if(bufferedInputStream!=null){
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                if(gzipOutputStream!=null)
                gzipOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 解压
     */
    public void jCompress(String src,String des){
        File file = new File(src);
        if(!file.exists()){
            throw new IllegalStateException("压缩文件不存在");
        }
        InputStream inputStream=null;
        BufferedOutputStream bufferedOutputStream2=null;
        try {
            ZipFile zipFile = new ZipFile(src);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            ZipEntry zipEntry = entries.nextElement();
             inputStream = zipFile.getInputStream(zipEntry);
            bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(des));
            read(inputStream,bufferedOutputStream2);
        } catch (IOException e) {
            Log.e("EEEEEEEEEE",e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if(inputStream!=null)
                    inputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                if(bufferedOutputStream2!=null)
                bufferedOutputStream2.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    /**
      *@method:328789
      *@author 328789
      *@desc:写文件
      *@Title:
      *@company:德邦物流
      *@return:
      *@形参:
      *@time:10:31
      */
    private void read(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, 1);
        }
        outputStream.flush();
    }
    public String getPath(){
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }
}
