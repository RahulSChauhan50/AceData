package com.example.acedata.recyclerViewAdapter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acedata.FormData;
import com.example.acedata.R;
import com.example.acedata.ui.formScreens.Form1Fragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class recyclerviewadapter extends RecyclerView.Adapter<recyclerviewadapter.recyclerviewHolder>{


    private List<FormData> itemData;
    private Context context;

    public recyclerviewadapter(List<FormData> itemData, Context context) {
        this.itemData= itemData;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public recyclerviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.datalistitem,parent,false);
        recyclerviewHolder holder=new recyclerviewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerviewHolder holder, final int position) {
        holder.name.setText(itemData.get(position).getName());
        holder.adhar.setText(itemData.get(position).getAdhar());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,itemData.get(position).getName()+"\n"+itemData.get(position).getAdhar(),Toast.LENGTH_SHORT).show();

                //converting object to string for passing
                Gson gson = new Gson();
                String object_pass = gson.toJson(itemData.get(position));

                Form1Fragment form1Fragment=new Form1Fragment();
                Bundle arguments = new Bundle();
                arguments.putString( "itemstring" , object_pass);
                form1Fragment.setArguments(arguments);

                FragmentManager fragmentManager=((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction tr=fragmentManager.beginTransaction();
                tr.setReorderingAllowed(true);
                tr.replace(R.id.fragment_container_appactivity, form1Fragment,null);
                tr.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }

    public class recyclerviewHolder extends RecyclerView.ViewHolder{
        TextView name,adhar;
        ConstraintLayout parent;

        public recyclerviewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.list_textviewnamevalue);
            adhar=itemView.findViewById(R.id.list_textviewadharvalue);
            parent=itemView.findViewById(R.id.itemparent);

        }
    }
}
