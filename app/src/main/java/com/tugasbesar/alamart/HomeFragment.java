package com.tugasbesar.alamart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.tugasbesar.alamart.api.ApiClient;
import com.tugasbesar.alamart.api.ApiInterface;
import com.tugasbesar.alamart.api.GetItems;
import com.tugasbesar.alamart.databinding.FragmentHomeBinding;
import com.tugasbesar.alamart.item.Item;
import com.tugasbesar.alamart.item.ItemAdapter;
import com.tugasbesar.alamart.item.ItemSpaceDecorator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private static final String TAG = "MainActivity";
    private FragmentHomeBinding fragmentHomeBinding;
    private ItemAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ShimmerFrameLayout shimmerFrameLayout;
    private ApiInterface apiInterface;
    private boolean executing = false;
    private int page = 1;

    private List<Item> items = new ArrayList<>();

    public HomeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View view = fragmentHomeBinding.getRoot();

        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

        recyclerView = view.findViewById(R.id.recycler_view_item);
        refreshLayout = view.findViewById(R.id.swipe_refresh);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_item_container);

        adapter = new ItemAdapter(getContext(), items);
        fragmentHomeBinding.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemSpaceDecorator(30));
        recyclerView.setVisibility(View.VISIBLE);

        refreshLayout.setOnRefreshListener(() -> {
            items.clear();
            page = 1;
            executing = true;
            getItem(page);
            refreshLayout.setRefreshing(false);
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                if (manager != null) {
                    int[] lastVisibleItems = new int[2];
                    manager.findLastCompletelyVisibleItemPositions(lastVisibleItems);
                    int lastVisibleItem = Math.max(lastVisibleItems[0], lastVisibleItems[1]);
                    if (!executing && lastVisibleItem == items.size() - 1) {
                        System.out.println("Need to load more data");
                        executing = true;
                        page = page + 1;
                        getItem(page);
                    }
                }
            }
        });

        getItem(page);

        return view;
    }

    public void getItem(int page) {
        /*
        Fungsi untuk mengkonsumsi API dengan GET request page pertama item list dari API
         */

        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);

        recyclerView.setVisibility(View.GONE);

        Call<GetItems> itemsCall = apiInterface.getItems(page);
        itemsCall.enqueue(new Callback<GetItems>() {
            @Override
            public void onResponse(Call<GetItems> call, Response<GetItems> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        System.out.println("Menkonsumsi API halaman " + page);
                        List<Item> res = response.body().getItemList();
                        if (res.size() > 0) {
                            items.addAll(res);
                        } else {
                            Toast.makeText(getContext(), "No more data available", Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Log.e(TAG,"Load more error with: "+ response.code());
                }
                if (!response.body().isLast() && executing) {
                    executing = false;
                }
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);

                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<GetItems> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });
    }
}