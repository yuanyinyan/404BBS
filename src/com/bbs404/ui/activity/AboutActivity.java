package com.bbs404.ui.activity;

import android.os.Bundle;
import com.bbs404.R;

/**
 * Created in com.bbs404.ui.activity.
 * User: YuanYin
 * Date: 2015/6/30
 * Time: 9:12
 */
public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_space_about_layout);
        initActionBar("关于404留言板");
    }
}
