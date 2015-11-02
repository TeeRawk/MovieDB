package chernenko.alexey.com.moviedb;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by saion on 21.10.2015.
 */
public class FetchMoviewTask extends AsyncTask <String,Object,String> {

    @Override
    protected String doInBackground(String... params) {
        String movieJsonString;
        HttpURLConnection urlConnection=null;
        BufferedReader reader=null;
        StringBuffer stringBuffer=null;

        final String API_KEY="Movie Db api key goes here";
        final String MOVIE_BASE_URL="http://api.themoviedb.org/3/discover/movie?";
        final String SORT_VALUE="popularity.desc";
        final String PAGE_VALUE=params[0];


        try{
            final String SORT_PARAM="sort_by";
            final String API_PARAM="api_key";
            final String PAGE_PARAM="page";

            Uri buildUri=Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendQueryParameter(SORT_PARAM,SORT_VALUE)
                    .appendQueryParameter(API_PARAM,API_KEY)
                    .appendQueryParameter(PAGE_PARAM,PAGE_VALUE).build();

            URL url=new URL(buildUri.toString());

            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream=urlConnection.getInputStream();
             stringBuffer=new StringBuffer();

            if (inputStream==null)
            {}

            reader=new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line=reader.readLine())!=null)
            {
                stringBuffer.append(line+"\n");
            }

            if (stringBuffer.length()==0)
            {
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (urlConnection!=null)
            {
                urlConnection.disconnect();
            }
            if (reader!=null)
            {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(stringBuffer!=null){
        movieJsonString=stringBuffer.toString();
        return movieJsonString;}
        return null;
    }
}
