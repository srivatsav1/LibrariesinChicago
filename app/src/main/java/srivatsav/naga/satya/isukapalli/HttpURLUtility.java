package srivatsav.naga.satya.isukapalli;

import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class HttpURLUtility {



    private static String convertStreamToString(InputStream is) throws IOException {
        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }


    public static JSONArray getRequestArray(String url)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpClient hc = new DefaultHttpClient();
        HttpGet p = new HttpGet(url);
        JSONArray respObj=null;
        try {

            p.setHeader("Content-Type", "application/json");
            p.setHeader("Accept","application/json");

            HttpResponse resp = hc.execute(p);
            String response = "";
            if (resp != null) {
                if (resp.getStatusLine().getStatusCode() == 204 || resp.getStatusLine().getStatusCode()==200)
                {
                    InputStream is = resp.getEntity().getContent();
                    response = convertStreamToString(is);


                }
            }
            if(response.trim().length() != 0 || !response.trim().equals("")){
                respObj = new JSONArray(response);
            }else{
                respObj = new JSONArray();
            }

            Log.d("Status line", "" + resp.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();

        }

        return  respObj;
    }

}
