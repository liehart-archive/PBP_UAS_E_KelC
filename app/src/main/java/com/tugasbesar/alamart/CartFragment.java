package com.tugasbesar.alamart;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.tugasbesar.alamart.cart.Cart;
import com.tugasbesar.alamart.cart.CartAdapter;
import com.tugasbesar.alamart.cart.CartDao;
import com.tugasbesar.alamart.cart.CartDatabaseClient;
import com.tugasbesar.alamart.cart.CartSpaceDecorator;
import com.tugasbesar.alamart.databinding.FragmentCartBinding;
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

        getCarts();

        recyclerView.addItemDecoration(new CartSpaceDecorator(30));

        return view;
    }

    private void getCarts() {

        class GetCart extends AsyncTask<Void, Void, List<Cart>> {

            @Override
            protected List<Cart> doInBackground(Void... voids) {
                CartDao client = CartDatabaseClient.getInstance(getActivity().getApplicationContext()).getDatabase().cartDao();

                List<Cart> carts = client.getAll();

                return carts;
            }

            @Override
            protected void onPostExecute(List<Cart> carts) {
                super.onPostExecute(carts);
                adapter = new CartAdapter(getContext(), carts);
                recyclerView.setAdapter(adapter);
            }
        }

        GetCart getCart = new GetCart();
        getCart.execute();

    }
}