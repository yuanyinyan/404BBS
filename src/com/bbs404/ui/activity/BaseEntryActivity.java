package com.bbs404.ui.activity;

import android.os.Bundle;
import com.bbs404.service.receiver.FinishReceiver;

public class BaseEntryActivity extends BaseActivity {
    FinishReceiver finishReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finishReceiver = FinishReceiver.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(finishReceiver);
    }
}
