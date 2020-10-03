package com.tugasbesar.alamart.item;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class ItemSpaceDecorator extends RecyclerView.ItemDecoration {

    private int space;

    public ItemSpaceDecorator(int space) {
        this.space = space;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);

        StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        int spanIndex = lp.getSpanIndex();

        if (position <= 1) {
            outRect.top = space;
        }

        if (spanIndex == 1) {
            outRect.right = space;
            outRect.left = space / 2;
        } else {
            outRect.left = space;
            outRect.right = space / 2;
        }

        outRect.bottom = space;
    }
}