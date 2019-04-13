package com.rickh.simplebillsplitter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.chip.Chip;

import androidx.appcompat.app.AppCompatActivity;

public class SplitBillActivity extends AppCompatActivity {

    private Chip[] mChips = new Chip[5];
    private Button mSplitBill;
    private TextView mTotalTextView;
    private Bill mBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_bill);

        mBill = new Bill();

        mChips[0] = findViewById(R.id.zero_percent_chip);
        mChips[1] = findViewById(R.id.ten_percent_chip);
        mChips[2] = findViewById(R.id.fifteen_percent_chip);
        mChips[3] = findViewById(R.id.twenty_percent_chip);
        mChips[4] = findViewById(R.id.custom_percent_chip);

        for (Chip chip: mChips) {
            chip.setOnClickListener(chipClickListener);
        }

        setSelectedChip(R.id.zero_percent_chip);

        mSplitBill = findViewById(R.id.split_bill_button);
        mTotalTextView = findViewById(R.id.total_text_view);

        final TextView amountOfFriendsTextView = findViewById(R.id.friends_text_view);
        amountOfFriendsTextView.setText("2");

        SeekBar amountOfFriends = findViewById(R.id.friends_seekbar);
        amountOfFriends.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                amountOfFriendsTextView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button zeroButton = findViewById(R.id.zero_button);
        Button oneButton = findViewById(R.id.one_button);
        Button twoButton = findViewById(R.id.two_button);
        Button threeButton = findViewById(R.id.three_button);
        Button fourButton = findViewById(R.id.four_button);
        Button fiveButton = findViewById(R.id.five_button);
        Button sixButton = findViewById(R.id.six_button);
        Button sevenButton = findViewById(R.id.seven_button);
        Button eightButton = findViewById(R.id.eight_button);
        Button nineButton = findViewById(R.id.nine_button);
        Button decimalButton = findViewById(R.id.decimal_button);
        ImageView backspaceImage = findViewById(R.id.backspace_image_view);

        zeroButton.setOnClickListener(calculatorClickListener);
        oneButton.setOnClickListener(calculatorClickListener);
        twoButton.setOnClickListener(calculatorClickListener);
        threeButton.setOnClickListener(calculatorClickListener);
        fourButton.setOnClickListener(calculatorClickListener);
        fiveButton.setOnClickListener(calculatorClickListener);
        sixButton.setOnClickListener(calculatorClickListener);
        sevenButton.setOnClickListener(calculatorClickListener);
        eightButton.setOnClickListener(calculatorClickListener);
        nineButton.setOnClickListener(calculatorClickListener);
        decimalButton.setOnClickListener(calculatorClickListener);
        backspaceImage.setOnClickListener(calculatorClickListener);
    }

    private void setSelectedChip(int chipId) {
        resetChipColors();

        Chip chip = findViewById(chipId);
        chip.setChipBackgroundColor(ColorStateList.valueOf(getColor(R.color.colorAccent)));
        chip.setChipStrokeColor(ColorStateList.valueOf(getColor(R.color.colorAccent)));
        chip.setTextColor(Color.BLACK);
    }

    private void resetChipColors() {
        for (Chip chip: mChips) {
            chip.setChipBackgroundColor(ColorStateList.valueOf(getColor(R.color.colorPrimary)));
            chip.setChipStrokeColor(ColorStateList.valueOf(getColor(R.color.colorAccent)));
            chip.setTextColor(getColor(R.color.colorAccent));
        }
    }

    View.OnClickListener chipClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setSelectedChip(view.getId());
        }
    };

    View.OnClickListener calculatorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int number = -1;

            switch (view.getId()) {
                case R.id.zero_button:
                    number = 0;
                    break;
                case R.id.one_button:
                    number = 1;
                    break;
                case R.id.two_button:
                    number = 2;
                    break;
                case R.id.three_button:
                    number = 3;
                    break;
                case R.id.four_button:
                    number = 4;
                    break;
                case R.id.five_button:
                    number = 5;
                    break;
                case R.id.six_button:
                    number = 6;
                    break;
                case R.id.seven_button:
                    number = 7;
                    break;
                case R.id.eight_button:
                    number = 8;
                    break;
                case R.id.nine_button:
                    number = 9;
                    break;
                case R.id.decimal_button:
                    break;
                case R.id.backspace_image_view:
                    mBill.backspace();
                    break;
            }
            if (number != -1) {
                mBill.calculate(number);
            }
            mTotalTextView.setText("$" + mBill.getTotal().toString());
        }
    };
}
