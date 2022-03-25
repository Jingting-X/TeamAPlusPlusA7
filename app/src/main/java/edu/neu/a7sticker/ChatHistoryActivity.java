package edu.neu.a7sticker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewChatHistory;
    private RecyclerView.LayoutManager recyclerViewLayoutManger;
    private RecyclerViewAdapterChatHistory recyclerViewAdapterChatHistory;
    private ArrayList<ChatCard> chatCards = new ArrayList<>();

    private DatabaseReference mDatabase;
    private DatabaseReference mChat;
    private TextView sticker_count_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_history);

        Intent intent = getIntent();
        String friend_username = intent.getStringExtra("friend_username");
        String current_user_username = intent.getStringExtra("current_user_username");

        TextView chat_info = (TextView) findViewById(R.id.chatHistoryInfoText);
        chat_info.append("This is chat history with " + friend_username);

        recyclerViewChatHistory = findViewById(R.id.recyclerChatHistory);
        recyclerViewLayoutManger = new LinearLayoutManager(this);
        recyclerViewChatHistory.setLayoutManager(recyclerViewLayoutManger);

        recyclerViewAdapterChatHistory = new RecyclerViewAdapterChatHistory(chatCards,
                current_user_username, friend_username);
        recyclerViewChatHistory.setAdapter(recyclerViewAdapterChatHistory);

        //reference to firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //get user reference
        mChat = mDatabase.child("chats");

        mChat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int sent = 0, received = 0;
                chatCards.clear();
                for(DataSnapshot snapshot1 :snapshot.getChildren()){
                    ChatCard chat = snapshot1.getValue(ChatCard.class);
                    if(chat.getSender().equals(current_user_username) && chat.getReceiver().equals(friend_username)){
                        chatCards.add(chat);
                        sent ++;
                    }
                    else if(chat.getSender().equals(friend_username) && chat.getReceiver().equals(current_user_username)){
                        chatCards.add(chat);
                        received ++;
                    }
                }
                //sort based on epoch time
                chatCards.sort((c1,c2)->c1.getTime().compareTo(c2.getTime()));

                recyclerViewAdapterChatHistory.notifyDataSetChanged();
                sticker_count_info = (TextView) findViewById(R.id.userStickerCountTextView);
                sticker_count_info.setText("Stickers sent " + sent + " Received " + received);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}