package chernenko.alexey.com.moviedb;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by saion on 30.10.2015.
 */
public class DetailFragment extends Fragment {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.content_detail,container,false);
       Movie movie= (Movie)getActivity().getIntent().getSerializableExtra(MovieRecyclerAdapter.EXTRA_MOVIE);


        ImageView poster=(ImageView)v.findViewById(R.id.detail_poster);
        Picasso.with(getActivity().getApplicationContext()).load("http://image.tmdb.org/t/p/w342/" + movie.getPosterUrl())
                .resize(220, 278).into(poster);

        TextView title=(TextView) v.findViewById(R.id.detail_title);
        title.setText(movie.getTitle());

        TextView year=(TextView)v.findViewById(R.id.detail_date);
        year.setText(movie.getDate());

        TextView desc=(TextView) v.findViewById(R.id.detail_desc);
        desc.setText(movie.getDescription());



        return v;
    }



}
