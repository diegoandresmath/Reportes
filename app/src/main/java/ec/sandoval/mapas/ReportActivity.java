package ec.sandoval.mapas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ec.sandoval.srv.BackGroudTask;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("DLC", "ReportActivity.onCreate.onClick -> antes");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Log.d("DLC", "ReportActivity.onCreate.BackGroudTask -> 0");
        BackGroudTask backGroudTask = new BackGroudTask(this);
        Log.d("DLC", "ReportActivity.onCreate.BackGroudTask -> 1");
        backGroudTask.execute("get_info");
        Log.d("DLC", "ReportActivity.onCreate.BackGroudTask -> 2");
    }
}
