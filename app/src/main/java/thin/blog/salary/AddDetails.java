package thin.blog.salary;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class AddDetails extends AppCompatActivity {
    static final int DATE_DIALOG_ID = 0;
    static final int IN_TIME_DIALOG_ID = 1;
    static final int OUT_TIME_DIALOG_ID = 2;

    //constant variables for indexing arrays
    static final int CONST_HOUR = 0;
    static final int CONST_MINUTE = 1;
    static final int CONST_YEAR = 0;
    static final int CONST_MONTH = 1;
    static final int CONST_DAY = 2;

    Toolbar toolbar;
    Calendar calendar;
    TextView date, inTime, outTime, totalHoursWorked, regularHoursWorked, otHoursWorked;
    Button addDetails;

    boolean isInTimeSet, isOutTimeSet;

    int[][] currentDateTime = new int[2][];

    int[] selectedDate = new int[3];
    int[] selectedInTime = new int[2];
    int[] selectedOutTime = new int[2];

    int[] cTotalHoursWorked, cRegularHoursWorked, cOtHoursWorked;
    SimpleDateFormat dateFormat, timeFormat;
    GregorianCalendar end, start;

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            selectedDate[CONST_YEAR] = year;
            selectedDate[CONST_MONTH] = monthOfYear;
            selectedDate[CONST_DAY] = dayOfMonth;
            setDateOnView(date, year, monthOfYear, dayOfMonth);
        }
    };

    private TimePickerDialog.OnTimeSetListener onInTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            isInTimeSet = true;
            selectedInTime[CONST_HOUR] = hourOfDay;
            selectedInTime[CONST_MINUTE] = minute;
            start.set(selectedDate[CONST_YEAR], selectedDate[CONST_MONTH], selectedDate[CONST_DAY], hourOfDay, minute);
            setTimeOnView(inTime, hourOfDay, minute);
            if (isOutTimeSet) {
                calculateWorkedHours();
            }
        }
    };
    private TimePickerDialog.OnTimeSetListener onOutTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            isOutTimeSet = true;
            selectedOutTime[CONST_HOUR] = hourOfDay;
            selectedOutTime[CONST_MINUTE] = minute;
            end.set(selectedDate[CONST_YEAR], selectedDate[CONST_MONTH], selectedDate[CONST_DAY], hourOfDay, minute);
            boolean isValid = calculateWorkedHours();
            if (isValid) {
                setTimeOnView(outTime, hourOfDay, minute);
            }
        }
    };

    private static int[] getHoursAndMinutes(long minutes) {
        int[] data = new int[2];
        if (minutes < -1) {
            return data;
        }
        data[CONST_HOUR] = (int) (minutes / 60);
        data[CONST_MINUTE] = (int) (minutes % 60);
        return data;
    }

    private boolean calculateWorkedHours() {
        long difference = end.getTimeInMillis() - start.getTimeInMillis();
        if (difference <= 0) {
            createToast("Invalid Time Selected", Toast.LENGTH_SHORT);
            resetViews();
            return false;
        }
        long seconds = difference / 1000;
        long minutes = seconds / 60;
        cTotalHoursWorked[CONST_HOUR] = getHoursAndMinutes(minutes)[CONST_HOUR];
        cTotalHoursWorked[CONST_MINUTE] = getHoursAndMinutes(minutes)[CONST_MINUTE];
        totalHoursWorked.setText("Total : " + cTotalHoursWorked[CONST_HOUR] + " Hours " + cTotalHoursWorked[CONST_MINUTE] + " Minutes");
        calculateRegularAndOt(minutes);
        return true;
    }

    private void resetViews() {
        outTime.setText("Out Time");
        totalHoursWorked.setText("Total Hours Worked");
        regularHoursWorked.setText("Regular Hours Worked");
        otHoursWorked.setText("OT Hours Worked");
        cTotalHoursWorked[CONST_HOUR] = 0;
        cTotalHoursWorked[CONST_MINUTE] = 0;
        cOtHoursWorked[CONST_HOUR] = 0;
        cOtHoursWorked[CONST_MINUTE] = 0;
        cRegularHoursWorked[CONST_HOUR] = 0;
        cRegularHoursWorked[CONST_MINUTE] = 0;

    }

    private void setTimeOnView(TextView time, int hourOfDay, int minute) {
        if (time.getId() == R.id.in_time) {
            String formatted = timeFormat.format(start.getTime());
            inTime.setText("In Time : " + formatted);
        } else if (time.getId() == R.id.out_time) {
            String formatted = timeFormat.format(end.getTime());
            outTime.setText("Out Time : " + formatted);
        }

    }

    private void setDateOnView(TextView date, int year, int month, int day) {
        GregorianCalendar dateToSet = new GregorianCalendar(year, month, day);
        String formatted = dateFormat.format(dateToSet.getTime());
        date.setText("Date : " + formatted);
    }

    private void calculateRegularAndOt(long minutes) {
        regularHoursWorked.setText("Regular Hours Worked");
        otHoursWorked.setText("OT Hours Worked");
        if (minutes < 240) {
            cRegularHoursWorked[CONST_HOUR] = getHoursAndMinutes(0)[CONST_HOUR];
            cRegularHoursWorked[CONST_MINUTE] = getHoursAndMinutes(0)[CONST_MINUTE];
            cOtHoursWorked[CONST_HOUR] = getHoursAndMinutes(minutes)[CONST_HOUR];
            cOtHoursWorked[CONST_MINUTE] = getHoursAndMinutes(minutes)[CONST_MINUTE];
        } else if (minutes > 480) {
            cRegularHoursWorked[CONST_HOUR] = getHoursAndMinutes(480)[CONST_HOUR];
            cRegularHoursWorked[CONST_MINUTE] = getHoursAndMinutes(480)[CONST_MINUTE];
            minutes = minutes - 480;
            cOtHoursWorked[CONST_HOUR] = getHoursAndMinutes(minutes)[CONST_HOUR];
            cOtHoursWorked[CONST_MINUTE] = getHoursAndMinutes(minutes)[CONST_MINUTE];
        } else if (minutes > 240 && minutes < 480) {
            cRegularHoursWorked[CONST_HOUR] = getHoursAndMinutes(240)[CONST_HOUR];
            cRegularHoursWorked[CONST_MINUTE] = getHoursAndMinutes(240)[CONST_MINUTE];
            minutes = minutes - 240;
            cOtHoursWorked[CONST_HOUR] = getHoursAndMinutes(minutes)[CONST_HOUR];
            cOtHoursWorked[CONST_MINUTE] = getHoursAndMinutes(minutes)[CONST_MINUTE];
        }
        regularHoursWorked.setText("Regular : " + cRegularHoursWorked[CONST_HOUR] + " Hours " + cRegularHoursWorked[CONST_MINUTE] + " Minutes");
        otHoursWorked.setText("OT : " + cOtHoursWorked[CONST_HOUR] + " Hours " + cOtHoursWorked[CONST_MINUTE] + " Minutes");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);
        setToolbar();
        initialize();
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Details");
    }

    private void initialize() {
        date = (TextView) findViewById(R.id.date);
        inTime = (TextView) findViewById(R.id.in_time);
        outTime = (TextView) findViewById(R.id.out_time);
        totalHoursWorked = (TextView) findViewById(R.id.total_hours_worked);
        otHoursWorked = (TextView) findViewById(R.id.ot_hours_worked);
        regularHoursWorked = (TextView) findViewById(R.id.regular_hours_worked);
        addDetails = (Button) findViewById(R.id.add_details);

        isInTimeSet = false;
        isOutTimeSet = false;

        cTotalHoursWorked = new int[2];
        cRegularHoursWorked = new int[2];
        cOtHoursWorked = new int[2];
        calendar = Calendar.getInstance();
        currentDateTime[0] = new int[3];
        currentDateTime[1] = new int[2];
        currentDateTime[0][CONST_YEAR] = calendar.get(Calendar.YEAR);
        currentDateTime[0][CONST_MONTH] = calendar.get(Calendar.MONTH);
        currentDateTime[0][CONST_DAY] = calendar.get(Calendar.DAY_OF_MONTH);
        currentDateTime[1][CONST_HOUR] = calendar.get(Calendar.HOUR_OF_DAY);
        currentDateTime[1][CONST_MINUTE] = calendar.get(Calendar.MINUTE);

        selectedDate[CONST_YEAR] = currentDateTime[0][CONST_YEAR];
        selectedDate[CONST_MONTH] = currentDateTime[0][CONST_MONTH];
        selectedDate[CONST_DAY] = currentDateTime[0][CONST_DAY];
        selectedInTime[0] = selectedOutTime[0] = currentDateTime[1][CONST_HOUR];
        selectedInTime[1] = selectedOutTime[1] = currentDateTime[1][CONST_MINUTE];
        dateFormat = new SimpleDateFormat("dd-MMMM-yyyy", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getDefault());
        timeFormat = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        timeFormat.setTimeZone(TimeZone.getDefault());
        start = new GregorianCalendar();
        end = new GregorianCalendar();
        setDateOnView(date, selectedDate[CONST_YEAR], selectedDate[CONST_MONTH], selectedDate[CONST_DAY]);
    }

    public void dialogDecision(View v) {
        switch (v.getId()) {
            case R.id.date:
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.in_time:
                showDialog(IN_TIME_DIALOG_ID);
                break;
            case R.id.out_time:
                if (isInTimeSet) {
                    showDialog(OUT_TIME_DIALOG_ID);
                } else {
                    createToast("Set In-Time First", Toast.LENGTH_SHORT);
                }
                break;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, datePickerListener, currentDateTime[0][CONST_YEAR], currentDateTime[0][CONST_MONTH], currentDateTime[0][CONST_DAY]);
            case IN_TIME_DIALOG_ID:
                return new TimePickerDialog(this, onInTimeSetListener, currentDateTime[1][CONST_HOUR], currentDateTime[1][CONST_MINUTE], false);
            case OUT_TIME_DIALOG_ID:
                return new TimePickerDialog(this, onOutTimeSetListener, selectedInTime[CONST_HOUR], selectedInTime[CONST_MINUTE], false);
        }
        return null;
    }

    private void createToast(String message, int duration) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.toast_layout_root));
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(message);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(duration);
        toast.setView(layout);
        toast.show();
    }

    private String getDateForDatabase(GregorianCalendar date) {
        String data = new String();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.format(date.getTime());
        data = date.get(Calendar.YEAR) + "-" + date.get(Calendar.MONTH) + "-" + date.get(Calendar.DAY_OF_MONTH) + " " + date.get(Calendar.HOUR_OF_DAY) + ":" + date.get(Calendar.MINUTE) + ":" + date.get(Calendar.SECOND);
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
        return data;
    }
}
