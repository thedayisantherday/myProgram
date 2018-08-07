package com.example.zxg.myprogram.utils.imagecache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by zxg on 2016/9/23.
 * QQ:1092885570
 */
public class ImageGetFromHttp {
    private static final String LOG_TAG = ImageGetFromHttp.class.getSimpleName();
    private static final int CONN_TIMEOUT = 1000*10;

    /**
     * 从网络上下载Bitmap图片
     * (这是自己写的方法，还没经过测试)
     * @param url
     * @return
     */
    public static Bitmap downloadBitmap(String url) {
        try {
            URL postUrl = new URL(url);
            final HttpsURLConnection connection = (HttpsURLConnection) postUrl.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.connect();

            if(HttpsURLConnection.HTTP_OK == connection.getResponseCode()){
                FilterInputStream fis = new FlushedInputStream(connection.getInputStream());
                return BitmapFactory.decodeStream(fis);
            }
        }catch (IOException e){
            Log.w(LOG_TAG, "I/O error while retrieving bitmap from " + url, e);
        }catch (IllegalStateException e){
            Log.w(LOG_TAG, "Incorrect URL: " + url, e);
        }catch (Exception e){
            Log.w(LOG_TAG, "Error while retrieving bitmap from " + url, e);
        }

        return null;
    }

    /**
     * 从网络上下载Bitmap图片
     */
//    public static Bitmap downloadBitmap(String url) {
//        final HttpClient client = new DefaultHttpClient();
//        final HttpGet getRequest = new HttpGet(url);
//
//        try {
//            HttpResponse response = client.execute(getRequest);
//            final int statusCode = response.getStatusLine().getStatusCode();
//            if (statusCode != HttpStatus.SC_OK) {
//                Log.w(LOG_TAG, "Error " + statusCode + " while retrieving bitmap from " + url);
//                return null;
//            }
//
//            final HttpEntity entity = response.getEntity();
//            if (entity != null) {
//                InputStream inputStream = null;
//                try {
//                    inputStream = entity.getContent();
//                    FilterInputStream fit = new FlushedInputStream(inputStream);
//                    return BitmapFactory.decodeStream(fit);
//                } finally {
//                    if (inputStream != null) {
//                        inputStream.close();
//                        inputStream = null;
//                    }
//                    entity.consumeContent();
//                }
//            }
//        } catch (IOException e) {
//            getRequest.abort();
//            Log.w(LOG_TAG, "I/O error while retrieving bitmap from " + url, e);
//        } catch (IllegalStateException e) {
//            getRequest.abort();
//            Log.w(LOG_TAG, "Incorrect URL: " + url);
//        } catch (Exception e) {
//            getRequest.abort();
//            Log.w(LOG_TAG, "Error while retrieving bitmap from " + url, e);
//        } finally {
//            client.getConnectionManager().shutdown();
//        }
//        return null;
//    }

    /**
     * An InputStream that skips the exact number of bytes provided, unless it reaches EOF.
     * Android对于InputStream流在网速慢的情况下可能产生中断。
     * 重写FilterInputStream的skip解决此bug，通过skip方法强制flush流的数据
     * 主要原理就是检查是否到文件末端，告诉http类是否继续。
     */
    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int b = read();
                    if (b < 0) {
                        break;  // we reached EOF
                    } else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }
}
