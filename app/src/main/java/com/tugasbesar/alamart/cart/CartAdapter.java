package com.tugasbesar.alamart.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tugasbesar.alamart.BR;
import com.tugasbesar.alamart.R;
import com.tugasbesar.alamart.databinding.AdapterCartBinding;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{

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
    }

    @Override
    public int getItemCount() {
        return result.size();
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
