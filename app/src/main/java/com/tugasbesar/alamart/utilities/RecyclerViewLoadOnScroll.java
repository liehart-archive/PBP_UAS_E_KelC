package com.tugasbesar.alamart.utilities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public abstract class RecyclerViewLoadOnScroll extends RecyclerView.OnScrollListener {

    RecyclerView.LayoutManager layoutManager;
    private int visibleThreshold = 10;
    private int currentItem = 0;
    private int previousTotalItem = 0;
    private boolean loading = true;
    private final int startingItem = 0;

    public RecyclerViewLoadOnScroll(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public RecyclerViewLoadOnScroll(GridLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    public RecyclerViewLoadOnScroll(StaggeredGridLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int max = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                max = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > max) {
                max = lastVisibleItemPositions[i];
            }
        }
        return max;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int lastVisibleItemPosition = 0;
        int totalItem = layoutManager.getItemCount();

        if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        } else if (layoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }

        if (loading && (totalItem > previousTotalItem)) {
            loading = false;
            previousTotalItem = totalItem;
        }

        if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItem) {
            currentItem++;
            onLoadMore(currentItem, totalItem, recyclerView);
            loading = true;
        }
    }

    public void resetState() {
        this.currentItem = this.startingItem;
        this.previousTotalItem = 0;
        this.loading = false;
    }

    public abstract void onLoadMore(int page, int totalItem, RecyclerView recyclerView);
}
