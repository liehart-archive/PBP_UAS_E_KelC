package com.tugasbesar.alamart;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.tugasbesar.alamart.cart.Cart;
import com.tugasbesar.alamart.cart.CartAdapter;
import com.tugasbesar.alamart.cart.CartAppDatabase;
import com.tugasbesar.alamart.cart.CartDao;
import com.tugasbesar.alamart.cart.CartDatabaseClient;
import com.tugasbesar.alamart.cart.CartSpaceDecorator;
import com.tugasbesar.alamart.databinding.FragmentCartBinding;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private FragmentCartBinding adapterCartBinding;
    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private MaterialButton btnDelete;

    public CartFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        adapterCartBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        View view = adapterCartBinding.getRoot();

        btnDelete = view.findViewById(R.id.delete_cart);
        recyclerView = view.findViewById(R.id.recycler_view_cart);
        recyclerView.addItemDecoration(new CartSpaceDecorator(30));

        CartAppDatabase client = CartDatabaseClient.getInstance(getActivity().getApplicationContext()).getDatabase();

        client.cartDao().getAll().observe(getViewLifecycleOwner(), new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                returnCarts(carts);
            }
        });

        return view;
    }

    private void returnCarts(List<Cart> carts) {
        if (!carts.isEmpty()) {
            adapter = new CartAdapter(getContext(), carts);
            recyclerView.setAdapter(adapter);

        } else {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_layout, new EmptyCartFragment())
                    .commit();
        }
    }
}