package com.tugasbesar.alamart.cart;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.RecyclerView;

import com.tugasbesar.alamart.BR;
import com.tugasbesar.alamart.CartFragment;
import com.tugasbesar.alamart.R;
import com.tugasbesar.alamart.databinding.AdapterCartBinding;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> implements CartClickListener{

    private Context context;
    private List<Cart> result;

    public CartAdapter(Context context, List<Cart> result) {
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterCartBinding binding = DataBindingUtil.inflate(LayoutInflater.from(
                parent.getContext()), R.layout.adapter_cart, parent, false
        );
        return new CartViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
        Cart cart = result.get(position);
        holder.bind(cart);
        holder.binding.setCartClick(this);
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    @Override
    public void addQty(Cart cart) {
        int jumlah = cart.jumlahBarang;
        jumlah = jumlah + 1;
        cart.setJumlahBarang(jumlah);
        change(cart);
    }


    @Override
    public void minQty(Cart cart) {
        if (cart.jumlahBarang == 1) {
            delete(cart);
        } else {
            int jumlah = cart.jumlahBarang;
            jumlah = jumlah - 1;
            cart.setJumlahBarang(jumlah);
            change(cart);
        }
    }

    private void change(final Cart cart) {

        class ChangeCart extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                CartDatabaseClient.getInstance(context.getApplicationContext()).getDatabase()
                        .cartDao()
                        .update(cart);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }

        ChangeCart change = new ChangeCart();
        change.execute();

    }


    @Override
    public void delCart(Cart cart) {
        delete(cart);
    }

    private void delete(final Cart cart) {

        class DeleteCart extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                CartDatabaseClient.getInstance(context.getApplicationContext()).getDatabase()
                        .cartDao()
                        .delete(cart);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(context.getApplicationContext(), "Item on cart deleted", Toast.LENGTH_SHORT).show();
            }
        }

        DeleteCart delete = new DeleteCart();
        delete.execute();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{
        public AdapterCartBinding binding;

        public CartViewHolder(@NonNull AdapterCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Cart cart) {
            binding.setVariable(BR.cart, cart);
            binding.executePendingBindings();
        }
    }
}
