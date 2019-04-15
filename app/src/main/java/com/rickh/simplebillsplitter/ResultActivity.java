package com.rickh.simplebillsplitter;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ResultActivity extends AppCompatActivity {

    private Bill mBill;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        mBill = (Bill) getIntent().getExtras().getSerializable("bill");

        TextView totalTextView = findViewById(R.id.total_text_view);
        totalTextView.setText("$" + mBill.getTotal().toString());

        TextView billDetailsBillTextView = findViewById(R.id.bill_details_bill_text_view);
        billDetailsBillTextView.setText("$" + mBill.getBillAmount().toString());

        TextView friendsTextView = findViewById(R.id.bill_details_friends_text_view);
        friendsTextView.setText(String.valueOf(mBill.getFriends()));

        TextView tipPercentageTextView = findViewById(R.id.bill_details_tip_percentage_text_view);
        tipPercentageTextView.setText("Tip (" + String.valueOf(mBill.getTipPercentage()) + "%)");

        TextView tipTextView = findViewById(R.id.bill_details_tip_text_view);
        tipTextView.setText("$" + mBill.getTip().toString());

        RecyclerView mFriendsList = findViewById(R.id.friends_recycler_view);

        FriendListAdapter adapter = new FriendListAdapter();

        mFriendsList.setHasFixedSize(true);
        mFriendsList.setLayoutManager(new LinearLayoutManager(this));
        mFriendsList.setAdapter(adapter);

        ArrayList<Friend> friendList = new ArrayList<>();
        for (int i = 0; i < mBill.getFriends(); i++) {
            friendList.add(new Friend());
        }

        adapter.setFriendList(friendList);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();

        return false;
    }
}
