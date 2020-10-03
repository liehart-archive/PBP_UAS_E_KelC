package com.tugasbesar.alamart;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.FirebaseFirestore;
import com.tugasbesar.alamart.databinding.FragmentHomeBinding;
import com.tugasbesar.alamart.item.Item;
import com.tugasbesar.alamart.item.ItemAdapter;
import com.tugasbesar.alamart.item.ItemList;
import com.tugasbesar.alamart.item.ItemSpaceDecorator;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding fragmentHomeBinding;
    private ArrayList<Item> items;
    private ItemAdapter adapter;

    public HomeFragment() {}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View view = fragmentHomeBinding.getRoot();
        items = new ItemList().items;

//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        for (Item item : items) {
//            db.collection("items").add(item);
//        }


        adapter = new ItemAdapter(getContext(), items);
        fragmentHomeBinding.setAdapter(adapter);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_item);
        recyclerView.addItemDecoration(new ItemSpaceDecorator(30));
        return view;
    }
}