package com.rickh.simplebillsplitter;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import java.math.BigDecimal;

import androidx.appcompat.app.AppCompatActivity;

public class SplitBillActivity extends AppCompatActivity implements SetTipDialog {

    private Chip[] mChips = new Chip[5];
    private Button mSplitBill;
    private TextView mTotalTextView;
    private TextView mBillDetailsBillTextView;
    private TextView mBillDetailsTipPercentageTextView;
    private TextView mBillDetailsTipTextView;
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
        mBillDetailsBillTextView = findViewById(R.id.bill_details_bill_text_view);
        mBillDetailsTipPercentageTextView = findViewById(R.id.bill_details_tip_percentage_text_view);
        mBillDetailsTipTextView = findViewById(R.id.bill_details_tip_text_view);

        final TextView amountOfFriendsTextView = findViewById(R.id.friends_text_view);
        final TextView friendsTextView = findViewById(R.id.bill_details_friends_text_view);
        amountOfFriendsTextView.setText("2");

        SeekBar amountOfFriends = findViewById(R.id.friends_seekbar);
        amountOfFriends.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mBill.setFriends(progress);
                amountOfFriendsTextView.setText(String.valueOf(mBill.getFriends()));
                friendsTextView.setText(String.valueOf(mBill.getFriends()));
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

        mSplitBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBill.getTotal().compareTo(new BigDecimal(0.00)) == 0) {
                    Snackbar.make(view, "Make sure to fill in a bill", Snackbar.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("bill", mBill);
                    startActivity(intent);
                }
            }
        });
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

            int percentage = -1;
            switch (view.getId()) {
                case R.id.zero_percent_chip:
                    percentage = 0;
                    break;
                case R.id.ten_percent_chip:
                    percentage = 10;
                    break;
                case R.id.fifteen_percent_chip:
                    percentage = 15;
                    break;
                case R.id.twenty_percent_chip:
                    percentage = 20;
                    break;
                case R.id.custom_percent_chip:
                    customTipDialog();
                    break;
            }
            if (percentage != -1) {
                mBill.setTipPercentage(percentage);

                updateViews();
            }
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
                    mBill.addDecimal();
                    break;
                case R.id.backspace_image_view:
                    mBill.backspace();
                    break;
            }
            if (number != -1) {
                mBill.add(number);
            }
            updateViews();
        }
    };

    private void updateViews() {
        mBillDetailsTipPercentageTextView.setText("Tip (" + String.valueOf(mBill.getTipPercentage()) + "%)");
        mBillDetailsTipTextView.setText("$" + mBill.getTip().toString());

        String totalText = "$" + mBill.getTotal().toString();
        mTotalTextView.setText(totalText);
        mBillDetailsBillTextView.setText("$" + mBill.getBillAmount().toString());
    }

    private void updateCustomTipChip() {
        Chip chip = findViewById(R.id.custom_percent_chip);
        chip.setText(mBill.getTipPercentage() + "%");
    }

    private void customTipDialog() {
        CustomTipDialog dialog = new CustomTipDialog();
        dialog.show(getSupportFragmentManager(), "CustomTipDialog");
    }

    @Override
    public void onSetTipClick(int tipPercentage) {
        mBill.setTipPercentage(tipPercentage);
        switch (tipPercentage) {
            case 0:
                setSelectedChip(R.id.zero_percent_chip);
                break;
            case 10:
                setSelectedChip(R.id.ten_percent_chip);
                break;
            case 15:
                setSelectedChip(R.id.fifteen_percent_chip);
                break;
            case 20:
                setSelectedChip(R.id.twenty_percent_chip);
                break;
            default:
                updateCustomTipChip();
                break;
        }
        updateViews();
    }
}
