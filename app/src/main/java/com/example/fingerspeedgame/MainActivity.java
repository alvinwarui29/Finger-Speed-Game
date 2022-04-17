package com.example.fingerspeedgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView timertxtView;
    private TextView triestxtView;
    private Button tapbtn;
    private CountDownTimer countDownTimer;

    private int remainingTime = 60;
    private int Duration = 20000;
    private int interval = 1000;

    private int tries = 10;

    private final String TRIES_KEY = "tries key";
    private final String REMAINING_TIME_KEY = "remaining time key";




    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(TRIES_KEY,tries);
        outState.putInt(REMAINING_TIME_KEY,remainingTime);
        countDownTimer.cancel();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timertxtView = findViewById(R.id.timer);
        triestxtView = findViewById(R.id.Tries);
        tapbtn = findViewById(R.id.tapbtn);

        triestxtView.setText(tries + "");


        if(savedInstanceState != null){

            tries = savedInstanceState.getInt(TRIES_KEY);
            remainingTime= savedInstanceState.getInt(REMAINING_TIME_KEY);

            RestoreGame();
        }



        if (savedInstanceState == null){

            countDownTimer = new CountDownTimer(Duration,interval) {
                @Override
                public void onTick(long RTimeInMillis) {

                    remainingTime = (int) RTimeInMillis /1000;
                    timertxtView.setText(remainingTime + "");


                }

                @Override
                public void onFinish() {

                    showAlert("time is up","Reset");
                }
            };

            countDownTimer.start();





        }



        tapbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tries --;
                triestxtView.setText(tries + "");

                if (remainingTime > 0 && tries<=0){
                    Toast.makeText(MainActivity.this,"Congratulations you won",Toast.LENGTH_SHORT).show();

                    showAlert("You won","Reset");
//                    ResetGame();



                }



            }
        });






    }

    private void RestoreGame() {
        int rTries = tries;
        int rTime= remainingTime;

        timertxtView.setText(rTime + "");
        triestxtView.setText(rTries + "");

        countDownTimer = new CountDownTimer(remainingTime*1000,interval) {
            @Override
            public void onTick(long remainderTimeinMillis) {
                remainingTime =  (int)remainderTimeinMillis / 1000;
                timertxtView.setText(remainingTime + "");


            }

            @Override
            public void onFinish() {

            }
        };
        countDownTimer.start();










    }









    private void ResetGame() {
        if (countDownTimer!= null){
            countDownTimer.cancel();
            countDownTimer= null;
        }
        tries = 10;
        triestxtView.setText(tries+"");
//        timertxtView.setText(remainingTime + "");

        countDownTimer = new CountDownTimer(Duration,interval) {
            @Override
            public void onTick(long timeToFinish) {
                remainingTime = (int)timeToFinish/1000;
                triestxtView.setText(tries + "");
                timertxtView.setText(remainingTime+ "");


            }

            @Override
            public void onFinish() {
                showAlert("Time is up","Reset?");

            }
        };
        countDownTimer.start();



    }








    private void showAlert(String title, String message){


        AlertDialog alertDialog= new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        ResetGame();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


        alertDialog.setCancelable(false);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.info){
            Toast.makeText(this, "Info selected", Toast.LENGTH_SHORT).show();

        }


        return  true;
    }
}