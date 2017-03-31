package fivetwentysix.ware.com.actotracker;

import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


import fivetwentysix.ware.com.actotracker.appData.ActoTrackerContract;

/**
 * Created by tim on 1/30/2017.
 */

@RunWith(AndroidJUnit4.class)
public class SyncTests {

    private HTTPURLConnect syncService;
    private Context mContext;

    @Before
    public void setupForTest() {
        mContext = InstrumentationRegistry.getContext();
        syncService = new HTTPURLConnect();

    }
    @Test
    public void testMoveDataToServer(){
        String path = "http://192.168.1.103:8080/error_table/save";
        syncService.WriteServerData(path);
    }

public class HTTPURLConnect {
    URL url;

    public String WriteServerData(String path) {
        try {
            url = new URL(path);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            connect.setReadTimeout(15000);
            connect.setConnectTimeout(15000);
            connect.setRequestMethod("POST");
            connect.setDoInput(true);
            connect.setDoOutput(true);

            OutputStream os = connect.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(postDataStrings());
            writer.flush();
            writer.close();
            os.close();
            int response = connect.getResponseCode();

            System.out.println("HAHA");

        } catch (Exception e) {
        }
        return "Done";
    }
     /**
     * postDataStrings - post first entry in table to server database 
     * 
     * @return - string of no value
     * @throws UnsupportedEncodingException
     */
    private String postDataStrings() throws UnsupportedEncodingException{
        StringBuilder sbResult = new StringBuilder();
        Cursor errCur = mContext.getContentResolver().query(
                ActoTrackerContract.ErrorEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
        if (errCur!=null && errCur.moveToFirst()){
            sbResult.append(URLEncoder.encode("date", "UTF-8")).append("=");
            String dateStr = Integer.toString(errCur.getInt(errCur.getColumnIndex(ActoTrackerContract.ErrorEntry.COLUMN_DATE)));
            sbResult.append(URLEncoder.encode(dateStr,"UTF-8")).append("&").append(URLEncoder.encode("module", "UTF-8")).append("=");
            sbResult.append(URLEncoder.encode(errCur.getString(errCur.getColumnIndex(ActoTrackerContract.ErrorEntry.COLUMN_MODULE)),"UTF-8"));
            sbResult.append("&").append(URLEncoder.encode("line_number", "UTF-8")).append("=");
            String lineNum = Integer.toString(errCur.getInt(errCur.getColumnIndex(ActoTrackerContract.ErrorEntry.COLUMN_LINE_NUMBER)));
            sbResult.append(URLEncoder.encode(lineNum,"UTF-8")).append("&").append(URLEncoder.encode("error_message", "UTF-8")).append("=");
            sbResult.append(errCur.getString(errCur.getColumnIndex(ActoTrackerContract.ErrorEntry.COLUMN_ERROR_MESSAGE)));
        }

        return sbResult.toString();
    }
}
}
