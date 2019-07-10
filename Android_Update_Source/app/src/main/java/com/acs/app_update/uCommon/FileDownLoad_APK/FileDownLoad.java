package com.acs.app_update.uCommon.FileDownLoad_APK;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.acs.app_update.uConfig.Grobal;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class FileDownLoad extends Activity {
    InputStream inputStream = null;
    OutputStream outputStream = null;
    Context _context;
    public File _DownLoadFile=null;

    public FileDownLoad(Context context) {
        this._context = context;
    }

    public File writeResponseBodyToDisk(byte[] body) {
        try {
            // todo 기존에 패키지 명으로 되어있었지만 ... (api24 이하버전은)기본 다운로드 경로로 변경.
            File futureStudioIconFile = null;
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
                futureStudioIconFile = new File(Environment.getExternalStorageDirectory() + "/download/", Grobal.DownLoadAppName+".apk");
            }
            else {
                File path = _context.getFilesDir();
                futureStudioIconFile = new File(path, Grobal.DownLoadAppName + ".apk");
            }

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.length;
//                long fileSize = Long.parseLong(body.toString());
//                  long fileSize = body.length();

                long fileSizeDownloaded = 0;

                inputStream = new ByteArrayInputStream(body);
//                inputStream = new ByteArrayInputStream(body.getBytes());
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Log.d("File Download: ", fileSizeDownloaded + " of " + fileSize);
                    Log.d("File Download: ", fileSizeDownloaded + " of " + fileSize);
//                    Pair<Long, Long> pairs = new Pair<>(fileSizeDownloaded, fileSize);
//                    downloadZipFileTask.doProgress(pairs);
                }

                outputStream.flush();

                _DownLoadFile=futureStudioIconFile;
               // installApk(futureStudioIconFile);

//                deleteApk();


                return _DownLoadFile;
            } catch (IOException e) {
                return _DownLoadFile;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return _DownLoadFile;
        }
    }


}
