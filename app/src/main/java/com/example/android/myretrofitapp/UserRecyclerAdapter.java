package com.example.android.myretrofitapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.myretrofitapp.data.User;

import java.util.List;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.CustomViewHolder> {

    //include the list that will keep the data
    List<User> users;

    private OnUserListener onUserListener;

    public UserRecyclerAdapter(List<User> users, OnUserListener onUserListener) {
        this.users = users;
        this.onUserListener = onUserListener;

    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }
    //dont forget to add... implements View.OnClickListener and implements its method onClick
    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //Get a reference to the Views in our layout
        public final View myView;
        //add more components to show more info apart from textuser
        TextView textUser;

        OnUserListener onUserListener;

        public CustomViewHolder(@NonNull View itemView, OnUserListener onUserListener) {
            super(itemView);
            myView = itemView;

            textUser = myView.findViewById(R.id.user);


            this.onUserListener = onUserListener;
            //to attach that listener to the entire viewHolder
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onUserListener.onUserClick(getAdapterPosition());
        }
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_layout, parent, false);
        return new CustomViewHolder(view, onUserListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.textUser.setText(users.get(position).getUsername());
        //set more data ...
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public interface OnUserListener{
        void onUserClick(int position);
    }


}
