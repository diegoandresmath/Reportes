package ec.sandoval.mapas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ec.sandoval.srv.BackGroudTask;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        BackGroudTask backGroudTask = new BackGroudTask(this);
        backGroudTask.execute("get_info");
    }
}
