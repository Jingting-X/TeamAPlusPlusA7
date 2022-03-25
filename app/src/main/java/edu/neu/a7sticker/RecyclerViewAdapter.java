package edu.neu.a7sticker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>{

    private ArrayList<UserCard> user_card_list;
    private UserSummaryListener userSummaryListener;

    public RecyclerViewAdapter(ArrayList<UserCard> user_card_list, UserSummaryListener userSummaryListener) {
        this.user_card_list = user_card_list;
        this.userSummaryListener = userSummaryListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_chat_summary, parent, false);
        return new RecyclerViewHolder(view, userSummaryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        String username = user_card_list.get(position).getUsername();
        holder.username.setText(username);
        holder.sendSticker.setTag(username);
    }

    @Override
    public int getItemCount() {
        return user_card_list.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView username;
        private Button sendSticker;
        private UserSummaryListener userSummaryListener;

        public RecyclerViewHolder(@NonNull View itemView, UserSummaryListener userSummaryListener) {
            super(itemView);
            username = itemView.findViewById(R.id.usernameTextView);
            sendSticker = itemView.findViewById(R.id.sendStickerButton);
            this.userSummaryListener = userSummaryListener;

            itemView.setOnClickListener(this);
            sendSticker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userSummaryListener.onUserSendStickerButtonClick(getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View v) {
            userSummaryListener.onUserClick(getAdapterPosition());
        }
    }

    public interface UserSummaryListener{
        void onUserClick(int position);
        void onUserSendStickerButtonClick(int position);
    }
}
