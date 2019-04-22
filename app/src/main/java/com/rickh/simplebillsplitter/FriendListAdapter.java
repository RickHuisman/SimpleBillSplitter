package com.rickh.simplebillsplitter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendHolder> {

    private BigDecimal mTotal;
    private ArrayList<Friend> mFriendList = new ArrayList<>();
    private ArrayList<SeekBar> mSeekbarList = new ArrayList<>();
    private ArrayList<TextView> mMoneyList = new ArrayList<>();

    public FriendListAdapter(BigDecimal total) {
        this.mTotal = total;
    }

    @NonNull
    @Override
    public FriendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
        return new FriendHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final FriendHolder holder, int position) {
        final Friend friend = mFriendList.get(position);
        holder.mFriendNameTextView.setText(friend.getName());

        startUp(holder, friend);

        mSeekbarList.add(holder.mPercentageSeekbar);
        mMoneyList.add(holder.mMoneyTextView);
        holder.mPercentageSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                holder.mPercentageTextView.setText(progress + "%");
                friend.setPercentage(new BigDecimal(progress));

                if (fromUser) {
                    holder.mCheckBox.setChecked(false);
                    friend.setLocked(true);

                    holder.mMoneyTextView.setText("$" + mTotal.multiply(new BigDecimal(progress)).divide(new BigDecimal(100)));

                    // count locked friends
                    int lockedFriendCount = 0;
                    int totalPerOfLocked = 0;
                    for (Friend f: mFriendList) {
                        if (f.isLocked()) {
                            lockedFriendCount++;
                            totalPerOfLocked += Integer.valueOf(f.getPercentage().toString());
                        }
                    }

                    BigDecimal result = new BigDecimal(100 - totalPerOfLocked);
                    if (result.compareTo(new BigDecimal(progress)) == -1 ) {
                        if (result.compareTo(new BigDecimal(0)) == -1) {
                            int maxProgress = progress - Integer.valueOf(result.abs().toString());

                            holder.mPercentageSeekbar.setProgress(maxProgress);
                            friend.setPercentage(new BigDecimal(maxProgress));

                            holder.mMoneyTextView.setText("$" + mTotal.multiply(new BigDecimal(maxProgress)).divide(new BigDecimal(100)));
                        }
                    }

                    // calc new percentage for locked items
                    int totalPercentageForUnLockedFriends = 100 - totalPerOfLocked;

                    // divide new percentage by lockedFriendCount
                    BigDecimal newPercentagePerUnLockedFriend;
                    if (totalPercentageForUnLockedFriends <= 0) {
                        newPercentagePerUnLockedFriend = new BigDecimal(0);
                    } else {
                        if ((getItemCount() - lockedFriendCount) == 0) {
                            newPercentagePerUnLockedFriend = new BigDecimal(totalPercentageForUnLockedFriends);
                        } else {
                            newPercentagePerUnLockedFriend = new BigDecimal(totalPercentageForUnLockedFriends / (getItemCount() - lockedFriendCount));
                        }
                    }

                    // set new percentage to every unlocked friend
                    for (int i = 0; i < mFriendList.size(); i++) {
                        if (!mFriendList.get(i).isLocked()) {

                            mSeekbarList.get(i).setProgress(Integer.valueOf(newPercentagePerUnLockedFriend.toString()));

                            int test = Integer.valueOf(newPercentagePerUnLockedFriend.toString());
                            if (test <= 0) {
                                mMoneyList.get(i).setText("$" + 0);
                            } else {
                                mMoneyList.get(i).setText("$" + mTotal.multiply(new BigDecimal(test)).divide(new BigDecimal(100)));
                            }
                        }
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (friend.isLocked()) {
                    friend.setLocked(false);

                    int lockedFriendCount = 0;
                    int totalPerOfLocked = 0;
                    for (Friend f : mFriendList) {
                        if (f.isLocked()) {
                            lockedFriendCount++;
                            totalPerOfLocked += Integer.valueOf(f.getPercentage().toString());
                        }
                    }

                    int result = 100 - totalPerOfLocked;

                    int perPerPerson = result / (getItemCount() - lockedFriendCount);

                    for (int i = 0; i < mFriendList.size(); i++) {
                        Friend f = mFriendList.get(i);
                        if (!f.isLocked()) {
                            f.setPercentage(new BigDecimal(perPerPerson));
                            mSeekbarList.get(i).setProgress(perPerPerson);
                            mMoneyList.get(i).setText("$" + mTotal.multiply(new BigDecimal(perPerPerson)).divide(new BigDecimal(100)));
                        }
                    }
                } else {
                    friend.setLocked(true);
                }
            }
        });
    }

    private void startUp(FriendHolder holder, Friend friend) {
        BigDecimal money = mTotal.divide(new BigDecimal(getItemCount()), 0, RoundingMode.HALF_UP);
        holder.mMoneyTextView.setText("$" + money.toString());

        int progressPerFriend = 100 / getItemCount();
        holder.mPercentageSeekbar.setProgress(progressPerFriend);
        friend.setPercentage(new BigDecimal(progressPerFriend));
        holder.mPercentageTextView.setText(progressPerFriend + "%");
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
        private TextView mMoneyTextView;
        private SeekBar mPercentageSeekbar;
        private TextView mPercentageTextView;
        private CheckBox mCheckBox;

        public FriendHolder(@NonNull View itemView) {
            super(itemView);
            mFriendNameTextView = itemView.findViewById(R.id.friend_name_text_view);
            mMoneyTextView = itemView.findViewById(R.id.money_text_view);
            mPercentageSeekbar = itemView.findViewById(R.id.percentage_seekbar);
            mPercentageTextView = itemView.findViewById(R.id.percentage_text_view);
            mCheckBox = itemView.findViewById(R.id.lock_checkbox);
        }
    }
}
