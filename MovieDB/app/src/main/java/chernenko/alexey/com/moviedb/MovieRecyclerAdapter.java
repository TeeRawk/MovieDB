package chernenko.alexey.com.moviedb;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.RippleDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by saion on 26.10.2015.
 */
public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MainViewHolder> {

    private final static int VIEW_TYPE_ITEM = 1;
    private final static int VIEW_TYPE_PROGRESSBAR = 0;



    private ArrayList<Movie> mMovies;
    private Context mContext;
    public static final String EXTRA_MOVIE="chernenko.alexey.com.moviedb.MovieRecyclerAdapter.mMovies";

    private boolean loading = false; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 6; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount,current_page=2;

    private OnLoadMoreListener onLoadMoreListener;


    public MovieRecyclerAdapter(ArrayList<Movie> movies,Context context,RecyclerView recyclerView)
    {
        mMovies=movies;
        mContext=context;

        final LinearLayoutManager mLinearLayoutManager  = (LinearLayoutManager) recyclerView
                    .getLayoutManager();
            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            visibleItemCount = recyclerView.getChildCount();
                            totalItemCount = mLinearLayoutManager.getItemCount();
                            firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

                            if(loading)
                            {
                                loading=false;
                            }
                            if (!loading && (totalItemCount - visibleItemCount)
                                    <= (firstVisibleItem + visibleThreshold)) {

                                onLoadMoreListener.onLoadMore(current_page);
                                current_page++;
                                loading = true;
                                notifyDataSetChanged();
                            }
                        }
                    });
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       RecyclerView.ViewHolder vh ;
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.grid_item, parent, false);
        return new MainViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, final int position) {

        Movie movie = mMovies.get(position);
        holder.title.setText(movie.getTitle());
        holder.date.setText(movie.getYear());
        holder.genres.setText(movie.getGenres());
        holder.vote.setText(String.valueOf(movie.getVote()));
            final String url = movie.getPosterUrl();
            Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/" + url).into((holder.poster));
            holder.ln.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mContext, DetailActivity.class);
                    i.putExtra(EXTRA_MOVIE, mMovies.get(position));
                    mContext.startActivity(i);

                }
            });

            RippleDrawable rippledImage = null;
            //making ther Ripple effect for image
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                int[] attrs = new int[]{R.attr.colorControlHighlight};
                TypedArray typedArray = mContext.obtainStyledAttributes(attrs);
                int backgroundResource = typedArray.getResourceId(0, 0);
                rippledImage = new RippleDrawable(ColorStateList.valueOf(mContext.getResources()
                        .getColor(backgroundResource)), ((MainViewHolder)holder).poster.getDrawable(), null);
                holder.poster.setImageDrawable(rippledImage);
            }

        }




    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    class MainViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView date;
        private TextView vote;
        private TextView genres;
        private ImageView poster;
        private LinearLayout ln;



        public MainViewHolder(View itemView) {
            super(itemView);
            title=(TextView) itemView.findViewById(R.id.grid_item_title);
            poster=(ImageView) itemView.findViewById(R.id.grid_item_poster);
            date=(TextView) itemView.findViewById(R.id.grid_item_date);
            vote=(TextView) itemView.findViewById(R.id.grid_item_vote);
            genres=(TextView) itemView.findViewById(R.id.grid_item_genre);
            ln=(LinearLayout)itemView.findViewById(R.id.creator);

        }
    }
}
