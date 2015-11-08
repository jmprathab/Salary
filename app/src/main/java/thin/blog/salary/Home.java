package thin.blog.salary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {
    Toolbar toolbar;
    Button addDetails, viewDetails;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setToolbar();
        initialize();
    }

    public void decision(View v) {
        switch (v.getId()) {
            case R.id.add_details:
                intent = new Intent(this, AddDetails.class);
                break;
            case R.id.view_details:
                intent = new Intent(this, ViewDetails.class);
                break;
        }
        startActivity(intent);
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void initialize() {
        addDetails = (Button) findViewById(R.id.add_details);
        viewDetails = (Button) findViewById(R.id.view_details);
    }
}
