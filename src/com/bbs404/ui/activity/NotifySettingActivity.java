package com.bbs404.ui.activity;

import android.os.Bundle;
import com.bbs404.R;

public class NotifySettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_space_setting_notify_layout);
        initActionBar(R.string.notifySetting);
    }
}
