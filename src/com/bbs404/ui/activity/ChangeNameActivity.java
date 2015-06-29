package com.bbs404.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.bbs404.R;
import com.bbs404.avobject.User;
import com.bbs404.util.Utils;

/**
 * Created in com.bbs404.ui.activity.
 * User: YuanYin
 * Date: 2015/6/29
 * Time: 22:53
 */
public class ChangeNameActivity extends BaseActivity {
    private EditText et;
    private User user;
    public static final int NICKNAME_REQUEST = 3;
    public static final String NICK_NAME = "nickname";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_space_name_layout);
        initActionBar("修改用户名");
        user = User.curUser();

        et = (EditText) findViewById(R.id.editText);
        et.setText(User.curUser().getUsername());
        et.setSelection(et.getText().length());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_done:
                String str = et.getText().toString();
                if (!str.equalsIgnoreCase("")) {
                    save_username();
                } else {
                    showError("提示","不能没有用户名！");
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save_username() {
        user.setUsername(et.getText().toString());
        user.setFetchWhenSave(true);
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    finish();
                } else {
                    switch (e.getCode()) {
                        case AVException.USERNAME_TAKEN:
                            showError("提示","这个名字已经存在了！");
                            break;
                        default:
                            showError("提示", "网络不给力！");
                            break;
                    }

                }

            }
        });
    }

    private void showError(String errorTitle, String errorMessage) {
        new AlertDialog.Builder(this)
                .setTitle(errorTitle)
                .setMessage(errorMessage)
                .setNegativeButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).show();
    }

}
