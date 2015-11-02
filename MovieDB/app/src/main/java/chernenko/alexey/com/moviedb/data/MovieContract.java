package chernenko.alexey.com.moviedb.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by saion on 02.11.2015.
 */
public class MovieContract {
    public static final String CONTENT_AUTHORITY = "chernenko.alexey.com.moviedb";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = "movie";


    public final static class MovieEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "location";

        public static final String COLUMN_TITLE="title";

        public static final String COLUMN_DATE="date";

        public static final String COLUMN_DESC="description";

        public static final String COLUMN_POSTER_URL="poster_url";

        public static final String COLUMN_GENRES="genres";

        public static final String COLUMN_VOTE="vote";

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String getLocationSettingFromUri(Uri uri) {
            return uri.getPathSegments().get(0);
        }



    }

}
