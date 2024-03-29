package com.example.baothuc;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.tv.TvInputManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Button btnHenGio,btnNgung;
    TextView txtHienThi;
    TimePicker timePicker;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnHenGio = (Button)findViewById(R.id.btnHenGio);
        btnNgung = (Button)findViewById(R.id.btnNgung);
        txtHienThi = (TextView)findViewById(R.id.txtHienThi);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        calendar = Calendar.getInstance();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        final Intent intent = new Intent(MainActivity.this,Receiver.class);
        btnHenGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE,timePicker.getCurrentMinute());

                int gio = timePicker.getCurrentHour();
                int phut = timePicker.getCurrentMinute();

                String string_giơ = String.valueOf(gio);
                String string_phut = String.valueOf(phut);

                if (gio > 12 ){
                    string_giơ = String.valueOf(gio - 12);
                }
                if (phut < 10 ){
                    string_phut = "0" + String.valueOf(phut);
                }
                intent.putExtra("extra","on");

                pendingIntent = PendingIntent.getBroadcast(
                        MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT
                );
                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

                txtHienThi.setText("Giờ bạn đặt bây giờ là:" + string_giơ + ":" + string_phut);
            }
        });

        btnNgung.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                txtHienThi.setText("Ngưng");
                alarmManager.cancel(pendingIntent);
                intent.putExtra("extra","off");
                sendBroadcast(intent);

            }
        });
    }
}
