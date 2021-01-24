package com.group.idm2;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AsyncTask<String, Void, String> {
    private Context context;
    private String username, password;

    public LoginActivity(Context context, String username, String password) {
        this.context = context;
        this.username = username;
        this.password = password;
    }

    protected String doInBackground(String... arg0) {

        try{
            String username = this.username;
            String password = this.password;

            String link="http://10.0.2.2/identitymanagement/login.php";
            String data  = URLEncoder.encode("username", "UTF-8") + "=" +
                    URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                    URLEncoder.encode(password, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write( data );
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }

            return sb.toString();
        } catch(Exception e){
            return "0";
        }
    }

    protected void onPostExecute(String result) {
        try {
            if (Integer.parseInt(result) > 0) {
                //Go to logged in
                System.out.println("Successful login");
            } else {
                Toast.makeText(this.context,"Error logging in",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this.context,"Error logging in",Toast.LENGTH_SHORT).show();
        }
    }
}
