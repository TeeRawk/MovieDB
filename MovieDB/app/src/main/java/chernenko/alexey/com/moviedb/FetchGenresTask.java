package chernenko.alexey.com.moviedb;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by saion on 30.10.2015.
 */
public class FetchGenresTask extends AsyncTask<Void,Void,String> {
    @Override
    protected String doInBackground(Void... voids) {
        String genreJsonString;
        HttpURLConnection urlConnection=null;
        BufferedReader reader=null;
        StringBuffer stringBuffer=null;

        final String API_KEY="Movie DB api key goe here";
        final String MOVIE_BASE_URL="http://api.themoviedb.org/3/genre/movie/list";


        final String API_PARAM="api_key";

        Uri buildUri=Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendQueryParameter(API_PARAM, API_KEY)
                .build();
        Log.v("FetchGenreTask", buildUri.toString());


        URL url= null;
        try {
            url = new URL(buildUri.toString());



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
            genreJsonString=stringBuffer.toString();
            return genreJsonString;}

        return null;
    }
}
