package com.justinpfeifler.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance();
    private static final NumberFormat PERCENT_FORMAT = NumberFormat.getPercentInstance();

    private double nBillAmount = 0.0;
    private double nPercent = 0.15;
    private TextView nAmountTextView;
    private TextView nTipTextView;
    private TextView nTotalTextView;
    private TextView nPercentTextView;

    private final SeekBar.OnSeekBarChangeListener nSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean front) {
            nPercent = (double) progress / 100.0;
            calculate();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private final TextWatcher nAmountEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                nBillAmount = Double.parseDouble(s.toString()) / 100.0;
                nAmountTextView.setText(CURRENCY_FORMAT.format(nBillAmount));
            } catch (NumberFormatException nFEx) {
                nAmountTextView.setText("");
                nBillAmount = 0.0;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            calculate();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.nAmountTextView = (TextView) findViewById(R.id.amount_text_view);
        this.nTipTextView = (TextView) findViewById(R.id.tip_text_view);
        this.nTotalTextView = (TextView) findViewById(R.id.total_text_view);
        this.nPercentTextView = (TextView) findViewById(R.id.percent_text_view);

        EditText amountEditText = (EditText) findViewById(R.id.amount_edit_text);
        amountEditText.addTextChangedListener(this.nAmountEditTextWatcher);
        SeekBar percentSeekBar = findViewById(R.id.percent_seek_bar);
        percentSeekBar.setOnSeekBarChangeListener(this.nSeekBarChangeListener);
        calculate();
    }

    private void calculate() {

        // update the seekbar with the percent
        this.nPercentTextView.setText(PERCENT_FORMAT.format(this.nPercent));

        // calculate the tip and total
        double tip = this.nBillAmount * this.nPercent;
        double total = this.nBillAmount + tip;

        // update the tip and total textviews with the new amounts
        this.nTipTextView.setText(CURRENCY_FORMAT.format(tip));
        this.nTotalTextView.setText(CURRENCY_FORMAT.format(total));

    }
}
