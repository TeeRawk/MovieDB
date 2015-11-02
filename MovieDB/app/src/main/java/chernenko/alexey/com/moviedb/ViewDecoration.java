package chernenko.alexey.com.moviedb;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by saion on 27.10.2015.
 */
public class ViewDecoration extends RecyclerView.ItemDecoration {

    private final int mVerticalSpaceHeight;

    public ViewDecoration(int mVerticalSpaceHeight) {
        this.mVerticalSpaceHeight = mVerticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.bottom = mVerticalSpaceHeight;
        outRect.top=mVerticalSpaceHeight;
        outRect.left=mVerticalSpaceHeight;
        outRect.right=mVerticalSpaceHeight;
    }
}
