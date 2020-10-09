package com.tugasbesar.alamart;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    private FragmentHomeBinding fragmentHomeBinding;
    private ItemAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ShimmerFrameLayout shimmerFrameLayout;
    private ApiInterface apiInterface;

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
            getItemRetrofitFirst();
            refreshLayout.setRefreshing(false);
        });

        getItemRetrofitFirst();

        return view;
    }

    public void getItemRetrofitFirst() {
        /*
        Fungsi untuk mengkonsumsi API dengan GET request page pertama item list dari API
         */

        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);

        recyclerView.setVisibility(View.GONE);

        Call<GetItems> itemsCall = apiInterface.getItems(1);
        itemsCall.enqueue(new Callback<GetItems>() {
            @Override
            public void onResponse(Call<GetItems> call, Response<GetItems> response) {
                items.clear();
                for (Item item : response.body().getItemList() ) {
                    items.add(item);
                }
                adapter.notifyDataSetChanged();

                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);

                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<GetItems> call, Throwable t) {
                return;
            }
        });
    }
}