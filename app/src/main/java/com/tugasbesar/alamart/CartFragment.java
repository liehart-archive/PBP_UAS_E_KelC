package com.tugasbesar.alamart;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.mapbox.mapboxsdk.plugins.annotation.Line;
import com.tugasbesar.alamart.cart.Cart;
import com.tugasbesar.alamart.cart.CartAdapter;
import com.tugasbesar.alamart.cart.CartAppDatabase;
import com.tugasbesar.alamart.cart.CartDao;
import com.tugasbesar.alamart.cart.CartDatabaseClient;
import com.tugasbesar.alamart.cart.CartSpaceDecorator;
import com.tugasbesar.alamart.databinding.FragmentCartBinding;
import com.tugasbesar.alamart.profile.Profile;
import com.tugasbesar.alamart.profile.ProfileDatabaseClient;

import org.w3c.dom.ls.LSException;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private FragmentCartBinding adapterCartBinding;
    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private MaterialButton btnDelete;
    private List<Cart> carts;
    private LinearLayout noCart;

    public CartFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        adapterCartBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        View view = adapterCartBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnDelete = view.findViewById(R.id.delete_cart);
        recyclerView = view.findViewById(R.id.recycler_view_cart);
        recyclerView.addItemDecoration(new CartSpaceDecorator(30));

        noCart = view.findViewById(R.id.noCart);

        CartAppDatabase client = CartDatabaseClient.getInstance(getActivity().getApplicationContext()).getDatabase();

        client.cartDao().getLiveDAO().observe(getViewLifecycleOwner(), new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                setAdapter(carts);
            }
        });
        getCart();
    }

    private void getCart() {
        class GetCart extends AsyncTask<Void, Void, List<Cart>> {

            @Override
            protected List<Cart> doInBackground(Void... voids) {
                List<Cart> carts = CartDatabaseClient
                        .getInstance(getContext())
                        .getDatabase()
                        .cartDao()
                        .getAll();

                return carts;
            }

            @Override
            protected void onPostExecute(List<Cart> carts) {
                super.onPostExecute(carts);
                if (!carts.isEmpty()) {
                    setAdapter(carts);
                }
            }
        }

        GetCart getCart = new GetCart();
        getCart.execute();
    }

    private void setAdapter(List<Cart> carts) {

        noCart.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        if (!carts.isEmpty()) {
            adapter = new CartAdapter(getContext(), carts);
            recyclerView.setAdapter(adapter);
        } else {
            recyclerView.setVisibility(View.GONE);
            noCart.setVisibility(View.VISIBLE);
        }
    }

}