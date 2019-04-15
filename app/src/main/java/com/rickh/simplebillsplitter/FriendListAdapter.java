package com.rickh.simplebillsplitter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendHolder> {

    private ArrayList<Friend> mFriendList = new ArrayList<>();

    @NonNull
    @Override
    public FriendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
        return new FriendHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final FriendHolder holder, int position) {
        Friend friend = mFriendList.get(position);
        holder.mFriendNameTextView.setText(friend.getName());

        holder.mPercentageSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                holder.mPercentageTextView.setText(String.valueOf(progress) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mFriendList.size();
    }

    public void setFriendList(ArrayList<Friend> friendList) {
        this.mFriendList = friendList;
        notifyDataSetChanged();
    }

    public class FriendHolder extends RecyclerView.ViewHolder {

        private TextView mFriendNameTextView;
        private SeekBar mPercentageSeekbar;
        private TextView mPercentageTextView;

        public FriendHolder(@NonNull View itemView) {
            super(itemView);
            mFriendNameTextView = itemView.findViewById(R.id.friend_name_text_view);
            mPercentageSeekbar = itemView.findViewById(R.id.percentage_seekbar);
            mPercentageTextView = itemView.findViewById(R.id.percentage_text_view);
        }
    }
}
