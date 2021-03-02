package com.example.acedata.recyclerViewAdapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acedata.FormData;
import com.example.acedata.R;

import java.util.ArrayList;
import java.util.List;

public class recyclerviewadapter extends RecyclerView.Adapter<recyclerviewadapter.recyclerviewHolder>{

    private List<FormData> itemData=new ArrayList<>();
    private Context context;

    public recyclerviewadapter(ArrayList<FormData> itemData, Context context) {
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
        holder.number.setText(itemData.get(position).getAdhar());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,itemData.get(position).getName()+"\n"+itemData.get(position).getAdhar(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }

    public class recyclerviewHolder extends RecyclerView.ViewHolder{
        TextView name,number;
        ImageView imageView;
        ConstraintLayout parent;

        public recyclerviewHolder(@NonNull View itemView) {
            super(itemView);

//            name=itemView.findViewById(R.id.Name);
//            number=itemView.findViewById(R.id.Number);
//            imageView=itemView.findViewById(R.id.imageView2);
//            parent=itemView.findViewById(R.id.parent);

        }
    }
}
