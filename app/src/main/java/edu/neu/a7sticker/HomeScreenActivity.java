package edu.neu.a7sticker;

import android.content.Intent;
import android.graphics.Typeface;
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

public class HomeScreenActivity extends AppCompatActivity implements RecyclerViewAdapter.UserSummaryListener{

    private RecyclerView recyclerViewForAllUsers;
    private RecyclerView.LayoutManager recyclerViewLayoutManger;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<UserCard> userCards = new ArrayList<>();
    private String username;
    private DatabaseReference mDatabase;
    private DatabaseReference mUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        username = getIntent().getStringExtra("USERNAME");

        TextView user_info = (TextView) findViewById(R.id.userInfoTextView);
        user_info.setTypeface(null, Typeface.BOLD);
        user_info.append("Hello " + username + ", send a sticker to any of the app users. Click on the user to see history");
        UserCard user = new UserCard(username);
        //reference to firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //get user reference
        mUsers = mDatabase.child("users");
        //add user to db
        mUsers.child(username).setValue(user);

        //recycler view
        recyclerViewForAllUsers = findViewById(R.id.recyclerViewAllChats);
        recyclerViewLayoutManger = new LinearLayoutManager(this);
        recyclerViewForAllUsers.setLayoutManager(recyclerViewLayoutManger);

        recyclerViewAdapter = new RecyclerViewAdapter(userCards, this);
        recyclerViewForAllUsers.setAdapter(recyclerViewAdapter);

        //Display all users
        mUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userCards.clear();
                for(DataSnapshot snapshot1 :snapshot.getChildren()){
                    UserCard user = snapshot1.getValue(UserCard.class);
                    if(!user.getUsername().equals(username)) {
                        userCards.add(user);
                    }
                }
                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onUserClick(int position) {
        System.out.println("User clicked at position " + position);

        Intent intent = new Intent(this, ChatHistoryActivity.class);
        intent.putExtra("friend_username", userCards.get(position).getUsername());
        intent.putExtra("current_user_username", username);

        startActivity(intent);
    }

    @Override
    public void onUserSendStickerButtonClick(int position) {
        System.out.println("Send sticker clicked at position " + position);

        Intent intent = new Intent(this, DisplayStickers.class);
        intent.putExtra("friend_username", userCards.get(position).getUsername());
        intent.putExtra("current_user_username", username);

        startActivity(intent);
    }
}