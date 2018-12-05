package com.teslyuk.androidtutorial_contentprovider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.teslyuk.androidtutorial_contentprovider.adapter.LogListAdapter;
import com.teslyuk.androidtutorial_contentprovider.db.LogDataBaseWrapper;
import com.teslyuk.androidtutorial_contentprovider.model.LogModel;
import com.teslyuk.androidtutorial_contentprovider.util.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LogListAdapter.LogListAdapterEventListener {

    private static final String TAG = "DatabaseStorage";

    private ListView fileListView;
    private EditText textView;
    private Button addBtn;

    private List<LogModel> logs;
    private LogListAdapter adapter;
    LogDataBaseWrapper wrapper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        wrapper = new LogDataBaseWrapper(this);
    }

    private void initView() {
        fileListView = (ListView) findViewById(R.id.internal_file_list_lv);
        textView = (EditText) findViewById(R.id.internal_file_text_et);
        addBtn = (Button) findViewById(R.id.internal_add_file_btn);

        addBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLogList();
    }

    private void createLog() {
        Logger.d(TAG, "createLog");
        String logName = Calendar.getInstance().getTimeInMillis() + " millis";
        String text = textView.getText().toString();

        wrapper.addLog(new LogModel(text, logName));
    }

    private void updateLogList() {
        Logger.d(TAG, "updateLogList");
        logs = wrapper.getLogs();
        if (logs != null) {
            for (int i = 0; i < logs.size(); i++) {
                Logger.d(TAG, "i:" + i + " name: " + logs.get(i).getMessage());
            }
        } else
            logs = new ArrayList<>();

        if (adapter == null) {
            adapter = new LogListAdapter(this, logs);
            adapter.addListener(this);
            fileListView.setAdapter(adapter);
        } else {
            adapter.onDataUpdate(logs);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.internal_add_file_btn:
                createLog();
                updateLogList();
                break;
        }
    }

    @Override
    public void removeLog(int position) {
        if (logs != null && logs.get(position) != null)
            wrapper.removeLogById(logs.get(position).getId());
        updateLogList();
    }
}
