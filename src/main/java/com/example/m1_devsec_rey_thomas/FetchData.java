package com.example.m1_devsec_rey_thomas;

import android.os.AsyncTask;
import android.text.method.ScrollingMovementMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static com.example.m1_devsec_rey_thomas.XORCrypt.decrypt;
import static com.example.m1_devsec_rey_thomas.XORCrypt.keyval;

public class FetchData extends AsyncTask<Void,Void,Void>{
    String data = "";
    String dataParsed = "";
    String singleParsed = "";
    @Override
    protected Void doInBackground(Void... voids) {
        try{
            // Secure Https request using TLS
            URL url = new URL(decrypt(new int[]{57,77,49,57,65,50,73,143,136,122,134,48,128,76,117,50,48,129,119,120,52,128,137,117,120,133,56,51,112,143,120,143,78,63,69,71,143,135,114,76,135,56,62,118,68,74,49,135,115,78,74,57},keyval));
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = httpsURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null)
            {
                line = bufferedReader.readLine();
                data = data + line;
            }

            JSONArray JA = new JSONArray(data);
            for(int i =0; i <JA.length(); i++){
                JSONObject JO = (JSONObject) JA.get(i);
                singleParsed = "Account name: "+ JO.get("accountName") + "\n"
                        + "Amount: "+ JO.get("amount") + "\n"
                        + "IBAN: "+ JO.get("iban") + "\n"
                        + "Currency: "+ JO.get("currency") + "\n\n";

                dataParsed = dataParsed + singleParsed;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.accounts.setMovementMethod(new ScrollingMovementMethod());
        MainActivity.accounts.setText(this.dataParsed);
    }
}
