package edu.neu.a7sticker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class UserSummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_chat_summary);
    }


    // called upon clicking Send sticker button
    public void onClickSendStickerButton(View view) {
        System.out.println("Send sticker button clicked");
        startActivity(new Intent(UserSummaryActivity.this, DisplayStickers.class));
    }
}