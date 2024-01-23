package com.example.finalproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.models.UserModel;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<UserModel> userList;

    public UserAdapter(List<UserModel> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserModel user = userList.get(position);

        holder.textViewUserName.setText(user.getName());
        holder.textViewUserLastname.setText(user.getLastname());


        Glide.with(holder.itemView.getContext()).load(user.getPhotoUrl()).into(holder.imageViewUser);

        holder.textViewLabelName.setText("Tıklanan Etiket: " + user.getSelectedLabel());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUserName;
        TextView textViewUserLastname;
        ImageView imageViewUser;
        TextView textViewLabelName;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUserName = itemView.findViewById(R.id.textViewUserName);
            textViewUserLastname = itemView.findViewById(R.id.textViewUserLastname); // Soyadı için TextView tanımla
            imageViewUser = itemView.findViewById(R.id.imageViewUser);
            textViewLabelName = itemView.findViewById(R.id.textViewLabelName);
        }
    }
}
