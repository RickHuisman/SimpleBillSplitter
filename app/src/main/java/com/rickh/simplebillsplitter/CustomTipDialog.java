package com.rickh.simplebillsplitter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class CustomTipDialog extends DialogFragment {

    private SetTipDialog mListener;
    private int mTipPercentage = -1;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (SetTipDialog) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_custom_tip, null);

        builder.setView(view);

        final TextInputEditText textInput = view.findViewById(R.id.text_input);
        MaterialButton cancelButton = view.findViewById(R.id.cancel_button);
        final MaterialButton setTipButton = view.findViewById(R.id.set_tip_button);

        if (mTipPercentage != -1) {
            textInput.setText(String.valueOf(mTipPercentage));
        }

        textInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (count > 0) {
                    setTipButton.setEnabled(true);
                } else {
                    setTipButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().hide();
                mListener.onHide();
            }
        });

        setTipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tipPercentage = Integer.valueOf(textInput.getText().toString());
                mListener.onSetTipClick(tipPercentage);

                mTipPercentage = tipPercentage;

                getDialog().hide();
            }
        });

        return builder.create();
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        mListener.onHide();
    }
}
