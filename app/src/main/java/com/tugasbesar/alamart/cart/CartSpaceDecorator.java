package com.tugasbesar.alamart.cart;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartSpaceDecorator extends RecyclerView.ItemDecoration {

    private final int space;

    public CartSpaceDecorator(int space) {
        this.space = space;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);

        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        int spanIndex = lp.getSpanIndex();

        if (position < 1) {
            outRect.top = space;
        }

        outRect.right = space;
        outRect.left = space;
        outRect.bottom = space;
    }
}