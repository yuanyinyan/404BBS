package com.bbs404.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import com.bbs404.R;
import com.bbs404.adapter.ChatMsgAdapter;

public class ImageBrowerActivity extends BaseActivity {
    String url;
    String path;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_image_brower_layout);
        imageView = (ImageView) findViewById(R.id.imageView);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        path = intent.getStringExtra("path");
        ChatMsgAdapter.displayImageByUri(imageView, path, url);
    }
}
