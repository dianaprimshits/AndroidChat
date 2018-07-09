package com.bigsur.help29062018;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;


public class SettingsActivity extends AppCompatActivity {
    LinearLayout ll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        ll = (LinearLayout)findViewById(R.id.settingsActivityLayout);
        ll.setBackgroundResource(R.drawable.seetings_activity_background);

    }
}
