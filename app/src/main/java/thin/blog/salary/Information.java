package thin.blog.salary;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Information extends AppCompatActivity {
    TimePickerDialog.OnTimeSetListener onTimeSetListener;
    int hour, min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        TextView inTime = (TextView) findViewById(R.id.in_time);
        TextView outTime = (TextView) findViewById(R.id.out_time);
        Button timer = (Button) findViewById(R.id.button);
        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                min = minute;
                Toast.makeText(getApplicationContext(), hourOfDay + ":" + minute, Toast.LENGTH_LONG).show();
            }
        };

        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                return new TimePickerDialog(this, onTimeSetListener, hour, min, false);
        }
        return null;
    }

}
