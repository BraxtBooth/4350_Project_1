package com.example.a4350_project_1;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyRVAdapter extends RecyclerView.Adapter<MyRVAdapter.ViewHolder> {
    private List<String> mListItems;
    private Context mContext;
    private DataPasser mDataPasser;

    public MyRVAdapter(List<String> inputList) {
        mListItems = inputList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        protected View moduleLayout;
        protected TextView moduleDesc;
        protected ImageView moduleIcon;

        public ViewHolder(View view){
            super(view);
            moduleLayout = view;
            moduleDesc = (TextView) view.findViewById(R.id.module_data);
            moduleIcon = (ImageView) view.findViewById(R.id.module_icon);
        }
    }

    @NonNull
    @Override
    public MyRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        try{
            mDataPasser = (DataPasser) mContext;
        }catch(ClassCastException e){
            throw new ClassCastException(mContext.toString()+ " must implement DataPasser");
        }

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View myView = layoutInflater.inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        int icon = 0;
        if(holder.getAdapterPosition() == 0) icon = R.drawable.profile;
        else if(holder.getAdapterPosition() == 1) icon = R.drawable.bmi;
        else if(holder.getAdapterPosition() == 2) icon = R.drawable.weather;
        else if(holder.getAdapterPosition() == 3) icon = R.drawable.hikes;
        else if(holder.getAdapterPosition() == 4) icon = R.drawable.gym;
        else if(holder.getAdapterPosition() == 5) icon = R.drawable.step;

        holder.moduleIcon.setImageResource(icon);
        holder.moduleDesc.setText(mListItems.get(holder.getAdapterPosition()));
        holder.moduleLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mDataPasser.passData(holder.getAdapterPosition());
            }
        });
    }

    public void remove(int position){
        mListItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    public static interface DataPasser{
        public void passData(int position);
    }
}
