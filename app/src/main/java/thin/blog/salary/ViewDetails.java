package thin.blog.salary;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

public class ViewDetails extends AppCompatActivity {
    Toolbar toolbar;
    Calendar calendar;
    Button display;
    int curDay, curMonth, curYear;
    int[] from = new int[3];
    int[] to = new int[3];
    DatePicker fromPicker, toPicker;

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int syear, int smonthOfYear, int sdayOfMonth) {
            if (view.getId() == R.id.fromPicker) {
                from[0] = syear;
                from[1] = smonthOfYear;
                from[2] = sdayOfMonth;
            } else if (view.getId() == R.id.toPicker) {
                to[0] = syear;
                to[1] = smonthOfYear;
                to[2] = sdayOfMonth;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        initialize();
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewDetails.this, Overview.class);
                Bundle bundle = new Bundle();
                bundle.putIntArray("FROM_TIME", from);
                bundle.putIntArray("TO_TIME", to);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void initialize() {
        display = (Button) findViewById(R.id.display);
        calendar = Calendar.getInstance();
        curDay = calendar.get(Calendar.DAY_OF_MONTH);
        curMonth = calendar.get(Calendar.MONTH);
        curYear = calendar.get(Calendar.YEAR);
        fromPicker = (DatePicker) findViewById(R.id.fromPicker);
        toPicker = (DatePicker) findViewById(R.id.toPicker);
        fromPicker.updateDate(curYear, curMonth - 1, 1);
        toPicker.updateDate(curYear, curMonth, 1);
    }

}
