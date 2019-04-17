package download.file.app.filedownloader;

import android.app.DownloadManager;
import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class DownloadIntentService extends IntentService {
    final static int id = 12456789;

    public DownloadIntentService() {
        super("DownloadIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
       // Thread thread = new Thread(new DownloadRunnable(intent));
        String[] urls = intent.getStringArrayExtra("URLs");
        DownloadHelper helper = new DownloadHelper(getBaseContext());
        for (int index = 0; index < urls.length; index++) {
            long downloadId = helper.downloadFile(urls[index], "pdf_" + index + ".pdf");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException exception) {

            }
        }

    }

    /*public class DownloadRunnable implements Runnable {
        Intent intent;
        public DownloadRunnable(Intent intent) {
            this.intent = intent;
        }

        @Override
        public void run() {
            String[] urls = intent.getStringArrayExtra("URLs");
            DownloadHelper helper = new DownloadHelper(getBaseContext());
            for (int index = 0; index < urls.length; index++) {
                long downloadId = helper.downloadFile(urls[index], "pdf_" + index + ".pdf");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException exception) {

                }
            }
        }
    }*/
}
