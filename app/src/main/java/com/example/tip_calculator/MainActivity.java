package com.example.tip_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private static final NumberFormat formatCash = NumberFormat.getCurrencyInstance();
    private static final NumberFormat formatPercent = NumberFormat.getPercentInstance();

    private double totalValue = 0.0f;
    private double percent = 0.15f;
    private TextView valueView;
    private TextView percentSeekBarView;
    private TextView tipView;
    private TextView totalCalculatedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        percentSeekBarView = (TextView) findViewById(R.id.percentSeekBarView);
        valueView = (TextView) findViewById(R.id.totalTextView);
        tipView = (TextView) findViewById(R.id.tipTextView);
        totalCalculatedView = (TextView) findViewById(R.id.totalCalculatedView);


        tipView.setText(formatCash.format(0));
        valueView.setText(formatCash.format(0));

        EditText valueForm = (EditText) findViewById(R.id.value);
        valueForm.addTextChangedListener(valueTextWatcher);

        SeekBar percentSeekBar = (SeekBar) findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    };

    private void calculate() {
        percentSeekBarView.setText(formatPercent.format(percent));

        double percentTip = totalValue * percent;
        double totalWithPercentTip = totalValue + percentTip;

        tipView.setText(formatCash.format(percentTip));
        totalCalculatedView.setText(formatCash.format(totalWithPercentTip));
    };

    private final SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            percent = i/100.0f;
            calculate();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    };

    private final TextWatcher valueTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            try {
                totalValue = Double.parseDouble(charSequence.toString());
                valueView.setText(formatCash.format(totalValue));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                valueView.setText("");
                totalValue = 0.0;
            }
            calculate();
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };
}