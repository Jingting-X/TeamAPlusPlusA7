package edu.neu.a7sticker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class RecyclerViewAdapterChatHistory extends RecyclerView.Adapter<RecyclerViewAdapterChatHistory.RecyclerViewHolder>{

    private ArrayList<ChatCard> chat_card_list;
    private String current_user_username, friend_username;
    private HashMap<String, Integer> sticker_id_mapping;

    public RecyclerViewAdapterChatHistory(ArrayList<ChatCard> chat_card_list,
                                          String current_user_username, String friend_username) {
        this.chat_card_list = chat_card_list;
        this.current_user_username = current_user_username;
        this.friend_username = friend_username;
        this.sticker_id_mapping = new HashMap<>();
        map_sticker_ids();
    }

    private void map_sticker_ids() {
        sticker_id_mapping.put("sticker1", R.drawable.sticker1);
        sticker_id_mapping.put("sticker2", R.drawable.sticker2);
        sticker_id_mapping.put("sticker3", R.drawable.sticker3);
        sticker_id_mapping.put("sticker4", R.drawable.sticker4);
        sticker_id_mapping.put("sticker5", R.drawable.sticker5);
        sticker_id_mapping.put("sticker6", R.drawable.sticker6);
        sticker_id_mapping.put("sticker7", R.drawable.sticker7);
        sticker_id_mapping.put("sticker8", R.drawable.sticker8);
        sticker_id_mapping.put("sticker9", R.drawable.sticker9);
        sticker_id_mapping.put("sticker10", R.drawable.sticker10);
        sticker_id_mapping.put("sticker12", R.drawable.sticker12);
        sticker_id_mapping.put("sticker13", R.drawable.sticker13);
        sticker_id_mapping.put("sticker14", R.drawable.sticker14);
        sticker_id_mapping.put("sticker15", R.drawable.sticker15);
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.chat_row, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        ChatCard chatCard = chat_card_list.get(position);

        String sticker_tag = chatCard.getSticker();
        String time = chatCard.getTime();
        Long value = Long.parseLong(time);
        Date date = new Date(value);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        String formatted = format.format(date);

        String sender = chatCard.getSender();
        String receiver = chatCard.getReceiver();

        if (sender.equals(current_user_username) && receiver.equals(friend_username)) {
            holder.senderSticker.setImageResource(sticker_id_mapping.get(sticker_tag));
            holder.receiverSticker.setImageResource(0);
            holder.senderStickerTime.setText(formatted);
            holder.receiverStickerTime.setText("");
        } else if(sender.equals(friend_username) && receiver.equals(current_user_username)) {
            holder.receiverSticker.setImageResource(sticker_id_mapping.get(sticker_tag));
            holder.senderSticker.setImageResource(0);
            holder.receiverStickerTime.setText(formatted);
            holder.senderStickerTime.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return chat_card_list.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private ImageView senderSticker;
        private ImageView receiverSticker;
        private TextView senderStickerTime;
        private TextView receiverStickerTime;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            senderSticker = itemView.findViewById(R.id.imageViewSender);
            receiverSticker = itemView.findViewById(R.id.imageViewReceiver);
            senderStickerTime = itemView.findViewById(R.id.textViewSenderTime);
            receiverStickerTime = itemView.findViewById(R.id.textViewReceiverTime);
        }

    }
}
