package chernenko.alexey.com.moviedb;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by saion on 21.10.2015.
 */
public class MovieListFragment extends Fragment {
    String Json=null;
    private RecyclerView mRecyclerView;
    private ArrayList<Movie> mMovies=new ArrayList<Movie>();
    FetchMoviewTask fetchMoviewTask;
    private LinearLayoutManager mLayoutManager;
    private HashMap<Integer,String> genres;
    protected Handler handler;
    private final static int VIEW_TYPE_ITEM = 1;
    private final static int VIEW_TYPE_PROGRESSBAR = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       fetchMoviewTask=new FetchMoviewTask();

        fetchMoviewTask.execute("1");

        FetchGenresTask fetchGenresTask=new FetchGenresTask();
        fetchGenresTask.execute();




        String JsonGenres=null;
        try {
         Json = fetchMoviewTask.get();
         JsonGenres=fetchGenresTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        getGenresFromJson(JsonGenres);
        getMoviesFormJson(Json);
    }

    private void getGenresFromJson(String json)
    {

        try {
            JSONObject jsonObject=new JSONObject(json);
            JSONArray jsonArray=jsonObject.getJSONArray("genres");
            genres=new HashMap<Integer,String>();

            for(int i=0;i<=jsonArray.length()-1;i++)
            {   JSONObject genre=jsonArray.getJSONObject(i);
                Integer key=new Integer(genre.getInt("id"));
                String value=genre.getString("name");
                genres.put(key,value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void getMoviesFormJson(String json)
    {   StringBuilder stringBuilder=null;


        try {
            JSONObject jsonObject=new JSONObject(json);
            JSONArray jsonArray=jsonObject.getJSONArray("results");

            for (int i=0;i<=jsonArray.length()-1;i++)
            {
                JSONObject movie=jsonArray.getJSONObject(i);
                String title=movie.getString("title");
                String posterUrl=movie.getString("poster_path");
                String date=movie.getString("release_date");
                String description=movie.getString("overview");
                double vote=movie.getDouble("vote_average");

                JSONArray genres_array =movie.getJSONArray("genre_ids");
                 stringBuilder= new StringBuilder();

                 for (int j=0;j<=genres_array.length()-1;j++) {
                     int genre_id= genres_array.getInt(j);
                     if(j<3) {
                         stringBuilder.append(genres.get(Integer.valueOf(genre_id)));
                         stringBuilder.append(",");
                     }
                 }
                String genres_string=stringBuilder.toString();

                mMovies.add(new Movie(title, posterUrl, date, description, vote,genres_string));

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            if(stringBuilder!=null)
            {stringBuilder=null;}
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View v=inflater.inflate(R.layout.fragment_movie_list,container,false);
        mRecyclerView=(RecyclerView)v.findViewById(R.id.Recycler);
        ViewDecoration   mDecoration=new ViewDecoration(10);
        mRecyclerView.addItemDecoration(mDecoration);

        mRecyclerView.setHasFixedSize(true);


        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        final MovieRecyclerAdapter adapter=new MovieRecyclerAdapter(mMovies,getActivity(),mRecyclerView);
        mRecyclerView.setAdapter(adapter);


        mLayoutManager.setSmoothScrollbarEnabled(true);




      adapter.setOnLoadMoreListener(new OnLoadMoreListener() {

          @Override
          public void onLoadMore(int current_page) {

              FetchMoviewTask taskUpdate = new FetchMoviewTask();
              taskUpdate.execute(Integer.toString(current_page));

              try {
                  Json = taskUpdate.get();
                  getMoviesFormJson(Json);


              } catch (InterruptedException e) {
                  e.printStackTrace();
              } catch (ExecutionException e) {
                  e.printStackTrace();
              }





          }
        });


        return v;
    }
}
