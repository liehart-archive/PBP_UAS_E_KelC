package com.tugasbesar.alamart.item;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tugasbesar.alamart.BR;
import com.tugasbesar.alamart.R;
import com.tugasbesar.alamart.databinding.AdapterItemBinding;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private Context context;
    private List<Item> result;

    public ItemAdapter(Context context, List<Item> result) {
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(
                parent.getContext()), R.layout.adapter_item, parent, false
        );
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder holder, int position) {
        Item item = result.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        public AdapterItemBinding binding;

        public ItemViewHolder(@NonNull AdapterItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Item item) {
            binding.setVariable(BR.item, item);
            binding.executePendingBindings();
        }
    }
}
