package com.example.digitaltasbee;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.digitaltasbee.SharedPref.IS_SELECT;

public class MainActivity extends AppCompatActivity {

    ImageView iv_changeTheme, iv_volume, iv_setLimit;
    EditText et_setlimit;
    Integer changeTheme, number, ans, aNs, numb;
    String colorFlag, soundFlag, get_limit,prev_counter;
    LinearLayout linearLayout;
    Button btn_count, iv_reset;
    TextView tv_counter;
    MediaPlayer mmediaPlayer;
    Boolean flag, clearflag;

    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        changeTheme = 1;
        colorFlag = "true";
        soundFlag = "true";
        get_limit = "";
        clearflag = false;
      //  number = 1;
        mmediaPlayer = null;
        ans = 0;
        aNs = 0;
        prev_counter="";
        numb = 0;
        flag = false;
        SharedPref.init(MainActivity.this);

        //call ids
        iv_changeTheme = findViewById(R.id.change_theme);
        linearLayout = findViewById(R.id.main_screen);
        iv_volume = findViewById(R.id.volume);
        iv_setLimit = findViewById(R.id.setlimit_icon);
        et_setlimit = findViewById(R.id.set_limit);
        iv_reset = findViewById(R.id.reset);
        btn_count = findViewById(R.id.count_button);
        tv_counter = findViewById(R.id.counter);





            get_limit = SharedPref.read(SharedPref.limit, "");
       // Toast.makeText(this, get_limit, Toast.LENGTH_SHORT).show();
        String check_flag = SharedPref.read(SharedPref.flag, "false");
       // Toast.makeText(this, check_flag, Toast.LENGTH_SHORT).show();
        if (check_flag.equals("true")){
            et_setlimit.setText(get_limit);
            iv_setLimit.setBackgroundResource(R.drawable.clear);
            flag = true;
            clearflag = true;
        }




        prev_counter = SharedPref.read(SharedPref.count, "000000");
        if (prev_counter.equals("000000")){
            number=1;
        }else {
            number=Integer.parseInt(prev_counter);
            number++;
        }






  //      Toast.makeText(this, prev_counter, Toast.LENGTH_SHORT).show();

        tv_counter.setText(prev_counter);








//change theme
        iv_changeTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTheme++;
                if (changeTheme == 1) {
                    linearLayout.setBackgroundResource(R.drawable.one);
                }
                if (changeTheme == 2) {
                    linearLayout.setBackgroundResource(R.drawable.two);
                }
                if (changeTheme == 3) {
                    linearLayout.setBackgroundResource(R.drawable.three);
                }
                if (changeTheme == 4) {
                    linearLayout.setBackgroundResource(R.drawable.four);
                }
                if (changeTheme == 5) {
                    linearLayout.setBackgroundResource(R.drawable.five);
                }
                if (changeTheme == 6) {
                    linearLayout.setBackgroundResource(R.drawable.six);
                }
                if (changeTheme == 7) {
                    linearLayout.setBackgroundResource(R.drawable.seven);
                    changeTheme = 0;
                }
            }
        });

        //for volume listner

        iv_volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (colorFlag == "true") {
                    iv_volume.setBackgroundResource(R.drawable.volume_off);
                    colorFlag = "false";
                } else if (colorFlag == "false") {
                    iv_volume.setBackgroundResource(R.drawable.volume_up);
                    colorFlag = "true";
                }

            }
        });


        //set limit icon
        iv_setLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String limit = et_setlimit.getText().toString();

                if (clearflag) {
                    et_setlimit.setText("");
                    flag = false;
                    clearflag = false;
                    et_setlimit.setTextColor(Color.parseColor("#686769"));
                    iv_setLimit.setBackgroundResource(R.drawable.uncheck);
                } else if (limit.isEmpty()) {

                    iv_setLimit.setBackgroundResource(R.drawable.uncheck);
                } else {
                    iv_setLimit.setBackgroundResource(R.drawable.clear);
                    flag = true;
                    clearflag = true;
                }

            }
        });

        //reset onclick listner
        iv_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder = new AlertDialog.Builder(MainActivity.this);

                builder.setIcon(R.drawable.refresh);
                builder.setTitle("Counter will be Reset");
                builder.setMessage("Are you sure you want to Reset the counter?");


                // builder.setCancelable(true);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        et_setlimit.setText("");
                        tv_counter.setText("000000");
                        number = 1;
                        iv_setLimit.setBackgroundResource(R.drawable.uncheck);
                        et_setlimit.setTextColor(Color.parseColor("#686769"));
                        flag = false;
                        clearflag = false;
                        SharedPref.write(SharedPref.limit, "");
                        SharedPref.write(SharedPref.IS_SELECT, "false");


                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        });


        btn_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (colorFlag == "true") {
                    volume();
                }



                @SuppressLint("DefaultLocale") String num = String.format("%06d", +number++);


                    get_limit = et_setlimit.getText().toString();


                ans = get_limit.isEmpty() ? 999999 : Integer.parseInt(get_limit);


                @SuppressLint("DefaultLocale") String get_Limit = String.format("%06d", ans);

                if ((num.equals(get_Limit) && (flag))) {
                    et_setlimit.setTextColor(Color.parseColor("#FF0000"));
                    vibratePhone(getApplicationContext(), (short) 600);
                    tv_counter.setText(num);
                    number++;
                    Toast.makeText(MainActivity.this, "Limit Exceed", Toast.LENGTH_SHORT).show();
                }

                aNs = get_Limit.isEmpty() ? 999999 : Integer.parseInt(get_Limit);
                @SuppressLint("DefaultLocale") String numBer = String.format("%06d", number);
                numb = numBer.isEmpty() ? 0 : Integer.parseInt(numBer);
                if ((numb > aNs) && (flag)) {

                    vibratePhone(getApplicationContext(), (short) 600);
                    number--;
                    Toast.makeText(MainActivity.this, "Limit Exceed", Toast.LENGTH_SHORT).show();
                } else {
                    tv_counter.setText(num);
                }
            }
        });


    }

    public void volume() {
        mmediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.counttune);
        mmediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
                mmediaPlayer = null;
            }
        });
        mmediaPlayer.start();

    }

    public static final void vibratePhone(Context context, short vibrateMilliSeconds) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(vibrateMilliSeconds);
    }

    @Override
    public void onBackPressed() {
        String count=tv_counter.getText().toString();
        SharedPref.write(SharedPref.count, count);

        //Toast.makeText(this, count, Toast.LENGTH_SHORT).show();
        if (flag){
    String get_limit = et_setlimit.getText().toString();

    SharedPref.write(SharedPref.limit, get_limit);

    SharedPref.write(SharedPref.flag, "true");


}else {
    SharedPref.write(SharedPref.limit, "");
    SharedPref.write(SharedPref.flag, "false");
}

        super.onBackPressed();
    }
}
