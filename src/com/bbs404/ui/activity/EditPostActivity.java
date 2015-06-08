package com.bbs404.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import com.bbs404.R;
import com.bbs404.avobject.PostObject;
import com.bbs404.avobject.User;
import com.bbs404.entity.PostInfo;
import com.bbs404.util.SimpleNetTask;

import java.util.ArrayList;

/**
 * Created in com.bbs404.ui.activity.
 * User: YuanYin
 * Date: 2015/6/7
 * Time: 18:13
 */
public class EditPostActivity extends BaseActivity {
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_post_layout);
        initActionBar("发留言");

        editText = (EditText) findViewById(R.id.edit_text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send:
                new SimpleNetTask(this){

                    @Override
                    protected void doInBack() throws Exception {
                        savePost2Cloud();
                    }

                    @Override
                    protected void onSucceed() {
                        finish();
                    }
                }.execute();
                return true;
            default:
                super.onBackPressed();
                return true;
        }
    }

    private void savePost2Cloud() throws Exception {
        boolean hasInfo = false;

        final PostObject post = new PostObject();
        post.setUser(User.curUser());

        if (!editText.getText().toString().isEmpty()) {
            post.setContent(editText.getText().toString().replaceAll("\n", " "));
            hasInfo = true;
        }

        if (hasInfo)
            try {
                post.setFetchWhenSave(true);
                post.save();
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        else
            throw new NullPointerException();
    }

}
