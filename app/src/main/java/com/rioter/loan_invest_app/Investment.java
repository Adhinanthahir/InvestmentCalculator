package com.rioter.loan_invest_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.shawnlin.numberpicker.NumberPicker;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class Investment extends AppCompatActivity {
            EditText editText1, editText2,rate_in;

            int  totalMonth=24, Year=2,Month=0;
            double simp_rate = 7.2;// Default selected value is 18%
            String data1, data2, MODE_Select="One Time";
            NumberPicker numberPickerYear, numberPickerMonth;
            private Context context=Investment.this;
            TextView gsttextview,interest_show,one_1,recuring,
                    intial_amt, duration_show;
            private Switch switchButton;
            boolean isSwitchButtonTouched;
            private Vibrator vibrator;



                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_investment);

                    vibrator=(Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

                gsttextview= findViewById(R.id.gst12);
                intial_amt= findViewById(R.id.intial_value);
                interest_show= findViewById(R.id.interest_value);
                    one_1=findViewById(R.id.one_time);
                    recuring=findViewById(R.id.recuri);
                    switchButton = findViewById(R.id.switchButton);//one time or recurring
                    switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            isSwitchButtonTouched = true;
                            if (isChecked) {

                                MODE_Select = switchButton.getTextOn().toString();


                                one_1.setTextColor(Color.parseColor("#FFFFFF"));//default grey color

                                recuring.setText(MODE_Select);
                                recuring.setTextColor(Color.parseColor("#7B260B"));
                               } else {

                                MODE_Select = switchButton.getTextOff().toString();

                                recuring.setTextColor(Color.parseColor("#FFFFFF"));//default grey color

                                one_1.setText(MODE_Select);
                                one_1.setTextColor(Color.parseColor("#7B260B"));
                         }

                            if ((!editText2.getText().toString().isEmpty()) || (!editText1.getText().toString().isEmpty())) {
                                subtractInvest();
                                addInvest();
                                interest_show.setVisibility(View.VISIBLE);
                                intial_amt.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(Investment.this, "Please enter a value!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                duration_show = findViewById(R.id.durations);
                duration_show.setText(" Duration :\n\n "+Year+" years & "+Month+" Months ");

                editText1 = findViewById(R.id.editTextNumber1);
                editText2 = findViewById(R.id.editTextNumber2);

                duration_show.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        RightSheetDialog
                        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                                Investment.this, R.style.BottomSheetDialogTheme
                        );
                        View View = LayoutInflater.from(Investment.this)
                                .inflate(
                                        R.layout.layout_bottom_sheet,(LinearLayout)findViewById(R.id.bottonsheetContainer)
                                );
//                        bottomSheetView.findViewById(R.id.things like numpick);
//                        bottomSheetDialog.dismiss();
                        Button cancel = View.findViewById(R.id.Cancel), ok =View.findViewById(R.id.OK);
                        numberPickerYear = View.findViewById(R.id.numberPickerYear);
                        numberPickerMonth = View.findViewById(R.id.numberPickerMonth);
                        numberPickerYear.setMinValue(0);
                        numberPickerYear.setMaxValue(100);
                        numberPickerMonth.setMinValue(0);
                        numberPickerMonth.setMaxValue(11);
                        numberPickerYear.setValue(Year);
                        numberPickerMonth.setValue(Month);
                        numberPickerYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                                submitButton_of_Labo.setImageResource(R.drawable.baseline_replay_circle_filled_24);
                                setVibrationFeedback(numberPickerYear);
                            }
                        });

                        numberPickerMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                                holder.submitButton_of_Labo.setImageResource(R.drawable.baseline_replay_circle_filled_24);
                                setVibrationFeedback(numberPickerMonth);
                            }
                        });

                        //submitbton inside
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(android.view.View v) {

                                 Year =numberPickerYear.getValue();//4
                                 Month = numberPickerMonth.getValue();//3
                                totalMonth = Month + Year*12;//48+3=51
                                duration_show.setText(" Duration :\n\n "+Year+" years & "+Month+" Months ");
                                bottomSheetDialog.dismiss();
                                if ((!editText2.getText().toString().isEmpty()) || (!editText1.getText().toString().isEmpty())) {
                                    subtractInvest();
                                    addInvest();
                                    interest_show.setVisibility(View.VISIBLE);
                                    intial_amt.setVisibility(View.VISIBLE);
                                } else {
                                    Toast.makeText(Investment.this, "Please enter a value!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(android.view.View v) {
                                bottomSheetDialog.dismiss();
                            }
                        });


                        bottomSheetDialog.setContentView(View);
                        bottomSheetDialog.show();
                    }
                });

                rate_in = findViewById(R.id.invest_rate);
                    rate_in.setText(Double.toString(simp_rate));//7.2

                        rate_in.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                if ((!rate_in.getText().toString().isEmpty()) && (!rate_in.getText().toString().equals("."))){
                                    double rate_checker = Double.parseDouble(rate_in.getText().toString().trim());
                                    if (rate_checker >= 100) {
                                        Toast.makeText(context, "Rate shall not be > 99.99!!", Toast.LENGTH_SHORT).show();
                                        rate_in.setText("99.99");
                                        simp_rate = 99.99;
                                    } else if (rate_checker < 0.01) {
                                        Toast.makeText(context, "Rate Shall not be < 0", Toast.LENGTH_SHORT).show();
                                        rate_in.setText("0.01");
                                        simp_rate = 0.01;
                                    } else {
//                                        Toast.makeText(context, "All OK", Toast.LENGTH_SHORT).show();
                                        simp_rate = Double.parseDouble(rate_in.getText().toString().trim());
                                        if ((!editText2.getText().toString().isEmpty()) || (!editText1.getText().toString().isEmpty())) {
                                            subtractInvest();
                                            addInvest();
                                            interest_show.setVisibility(View.VISIBLE);
                                            intial_amt.setVisibility(View.VISIBLE);
                                        } else {
                                            Toast.makeText(Investment.this, "Please enter a value!!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            } else {
                                    Toast.makeText(Investment.this, "Please enter a Rate!!", Toast.LENGTH_SHORT).show();
                                    rate_in.setHint("0.00");
                                    simp_rate = 0.00;
                                }




                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                            }
                        });





                editText1.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        data1 = editText1.getText().toString().trim();
                        addInvest();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

                editText2.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        data2 = editText2.getText().toString().trim();
                        subtractInvest();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

                ImageView leftIcon = findViewById(R.id.left_icon);
                ImageView rightIcon = findViewById(R.id.right_icon);
                rightIcon.setVisibility(View.INVISIBLE);

                leftIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finishAffinity();
                        Toast.makeText(Investment.this, "Bye!!", Toast.LENGTH_SHORT).show();
                    }
                });
                TextView title = findViewById(R.id.title);
                title.setText("Investment Calculator");
                title.setTextSize(20);

                ImageView promo = findViewById(R.id.promoid);
                promo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Investment.this, "Mr. ADHINAN Says Hi!!!!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
            private void addInvest() {
                if (!data1.isEmpty()) {
                    editText2.setText("");
                    double input = Double.parseDouble(data1);
                    double calculatedValue, calcula_intreset;
                    if(MODE_Select.equals("One Time")){
                         calculatedValue = input * (1 + ((simp_rate *totalMonth) / 1200.0));
                        calcula_intreset = input *( (simp_rate *totalMonth) / 1200.0);
                    }else{
                        double powerYears = totalMonth / 12;
                        double powerMonths = (double)(totalMonth % 12) / 12;
                        double power = powerYears + powerMonths;
                        calculatedValue = input * Math.pow( (1 + (simp_rate /100.0)),power);
                        calcula_intreset = calculatedValue-input;
                    }
                    DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
                    decimalFormat.setCurrency(Currency.getInstance("INR"));

//                String formattedCashValue = currencyFormat.format(Double.parseDouble(cashValue));
                    String hint = decimalFormat.format(calculatedValue);
//                String hint = String.format("%.2f", calculatedValue);
//                String gstval = String.format("%.2f", calcula_intreset);
                    String val =decimalFormat.format(calcula_intreset);
//                    gsttextview.setText("Interest : "+gstval);
                    if(MODE_Select.equals("One Time")) {
                        interest_show.setText("Interest : \n" + val);
                    }
                    else {
                        interest_show.setText("Compound Interest :\n" + val);
                    }
                    intial_amt.setText("Initial :\n"+decimalFormat.format(input));
                    editText2.setHint(hint);
                    interest_show.setVisibility(View.VISIBLE);
                    intial_amt.setVisibility(View.VISIBLE);
                } else {
                    interest_show.setVisibility(View.INVISIBLE);
                    intial_amt.setVisibility(View.INVISIBLE);
                    editText2.setHint("");
                }
            }
            private void subtractInvest() {
                if (!data2.isEmpty()) {
                    editText1.setText("");
//                gsttextview.setText("");
                    double input = Double.parseDouble(data2);
                    double calculatedValue , calculainterest;
                    if(MODE_Select.equals("One Time")) {
                        calculatedValue = input / (1 + ((simp_rate * totalMonth) / 1200.0));
                        calculainterest = input - calculatedValue;
                    }else{
                        double powerYears = totalMonth / 12;
                        double powerMonths = (double)(totalMonth % 12) / 12;
                        double power = powerYears + powerMonths;
                        calculatedValue = input / Math.pow( (1 + (simp_rate /100.0)),power);
                        calculainterest = input - calculatedValue;
                    }
                    DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
                    decimalFormat.setCurrency(Currency.getInstance("INR"));
                    String hint =  decimalFormat.format(calculatedValue);

                    String val =decimalFormat.format(calculainterest);


                    if(MODE_Select.equals("One Time")) {
                        interest_show.setText("Interest : \n" + val);
                    }
                    else {
                        interest_show.setText("Compound Interest :\n" + val);
                    }
                    intial_amt.setText("Initial :\n"+hint);

                    editText1.setHint(hint);
                    interest_show.setVisibility(View.VISIBLE);
                    intial_amt.setVisibility(View.VISIBLE);
                } else {
                    interest_show.setVisibility(View.INVISIBLE);
                    intial_amt.setVisibility(View.INVISIBLE);
                    editText1.setHint("");
                }
            }
            //to directly close app
            @Override
            public void onBackPressed() {
                finishAffinity();
//        System.exit(0);
            }

    private void setVibrationFeedback(NumberPicker numberPicker) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                picker.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
            });
        } else {
            numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    vibrator.vibrate(50);
                    picker.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                }
            });
        }
    }
        }


//package com.rioter.gstcalculaor;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Switch;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class gst extends AppCompatActivity {
//    EditText editText1, editText2;
//    Button btn_3,btn_5,btn_12,btn_18,btn_28;
//    public int values;
//    String data1 , data2;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_gst);
//
//
//
//
//        btn_3 = findViewById(R.id.btn_3);
//        btn_5 = findViewById(R.id.btn_5);
//        btn_12 = findViewById(R.id.btn_12);
//        btn_18 = findViewById(R.id.btn_18);
//        btn_28 = findViewById(R.id.btn_28);
//        editText1 = findViewById(R.id.editTextNumber1);
//        editText2 = findViewById(R.id.editTextNumber2);
//
//        btn_18.setBackgroundColor(Color.BLUE);
//                values= 18;
////editText.setHint("yo");
//        View.OnClickListener buttonClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Reset background colors for all buttons
//                btn_3.setBackgroundColor(Color.WHITE);
//                btn_5.setBackgroundColor(Color.WHITE);
//                btn_12.setBackgroundColor(Color.WHITE);
//                btn_18.setBackgroundColor(Color.WHITE);
//                btn_28.setBackgroundColor(Color.WHITE);
//
//                // Set background color for the clicked button
//                Button clickedButton = (Button) v;
//                clickedButton.setBackgroundColor(Color.BLUE);
//                String buttonText = clickedButton.getText().toString();
//                switch (buttonText){
//                    case "3%":
//                        values= 3;
//                        break;
//                    case "5%":
//                        values= 5;
//                        break;
//                    case "12%":
//                        values= 12;
//                        break;
//                   case "18%":
//                        values= 18;
//                        break;
//                   case "28%":
//                        values= 28;
//                        break;
//                }
//
//                // Perform additional actions as needed
//                // ...
//            }
//        };
//
//        btn_3.setOnClickListener(buttonClickListener);
//        btn_5.setOnClickListener(buttonClickListener);
//        btn_12.setOnClickListener(buttonClickListener);
//        btn_18.setOnClickListener(buttonClickListener);
//        btn_28.setOnClickListener(buttonClickListener);
//
//        editText1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editText1.setHint("Actual value");
//                editText2.setHint("Final value");
//                editText2.setText("");
//            }
//        });editText2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editText1.setText("");
//                editText1.setHint("Actual value");
//                editText2.setHint("Final value");
//            }
//        });
////        });
//        TextWatcher textWatcher = new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count)
//            {
//                data1 = editText1.getText().toString().trim();
//                data2 = editText2.getText().toString().trim();
//                if (!data1.isEmpty()) {
//                    double i1 = Double.parseDouble(data1)*(100 + values)  ;
//                    double decimalValue1 =  i1/100;  // Convert to decimal value
//                    String hint1 = String.format("%.2f", decimalValue1);  // Format the decimal value to 2 decimal places
//                    editText2.setHint(hint1);
//                } else if (!data2.isEmpty()) {
////                    int i2 = Integer.parseInt(data2) * (100 / (values + 100));
//                    double i2 = Double.parseDouble(data2)/(100 + values)  ;
//                    double decimalValue2 =100* i2;  // Convert to decimal value
//                    String hint2 = String.format("%.2f", decimalValue2);  // Format the decimal value to 2 decimal places
//                    editText1.setHint(hint2);
//                }
////                if (!data1.isEmpty()) {
////                    int i1 = Integer.parseInt(data1)*((100 + values) / 100);
////                    editText2.setHint(i1);
////                } else if (!data2.isEmpty()) {
////                    int i2 = Integer.parseInt(data2) * (100 / (values + 100));
////                    editText1.setHint(String.valueOf(i2));
////                }
//            }
//
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        };
//        editText1.addTextChangedListener(textWatcher);
//        editText2.addTextChangedListener(textWatcher);
//
//
////        leftIcon.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Toast.makeText(gst.this, "You've clicked on left icon", Toast.LENGTH_SHORT).show();
////            }
////        });
////        rightIcon.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Toast.makeText(gst.this, "You've clicked on right icon", Toast.LENGTH_SHORT).show();
////            }
////        });
////        title.setText("GST");
//    }
//}