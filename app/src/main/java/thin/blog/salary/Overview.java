package thin.blog.salary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class Overview extends AppCompatActivity {
    Toolbar toolbar;
    int data[] = new int[30];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView date = (TextView) findViewById(R.id.date);
        Calendar cal = Calendar.getInstance();
        int dd;
        int mm;
        int yy;
        dd = cal.get(Calendar.DAY_OF_MONTH);
        mm = cal.get(Calendar.MONTH);
        yy = cal.get(Calendar.YEAR);
        date.setText(dd + "-" + mm + "-" + yy);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<Data> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            data.add(new Data((int) Math.random(), String.valueOf(Math.random()), String.valueOf(Math.random()), String.valueOf(Math.random()), String.valueOf(Math.random()), String.valueOf(Math.random()), String.valueOf(Math.random())));
        }
        MyAdapter myAdapter = new MyAdapter(data);
        recyclerView.setAdapter(myAdapter);
        startActivity(new Intent(this, Information.class));

    }
}
