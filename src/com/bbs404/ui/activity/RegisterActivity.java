package com.bbs404.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import com.bbs404.R;
import com.bbs404.avobject.User;
import com.bbs404.base.App;
import com.bbs404.service.UserService;
import com.bbs404.util.ChatUtils;
import com.bbs404.util.NetAsyncTask;
import com.bbs404.util.Utils;

public class RegisterActivity extends BaseEntryActivity {
    View registerButton;
    EditText usernameEdit, passwordEdit, emailEdit;
    RadioGroup sexRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry_register_activity);
        findView();
        initActionBar(App.ctx.getString(R.string.register));
        registerButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                register();
            }
        });
    }

    private void findView() {
        usernameEdit = (EditText) findViewById(R.id.usernameEdit);
        passwordEdit = (EditText) findViewById(R.id.passwordEdit);
        emailEdit = (EditText) findViewById(R.id.ensurePasswordEdit);
        registerButton = findViewById(R.id.btn_register);
        sexRadio = (RadioGroup) findViewById(R.id.sexRadio);
    }

    private void register() {
        final String name = usernameEdit.getText().toString();
        final String password = passwordEdit.getText().toString();
        String againPassword = emailEdit.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Utils.toast(R.string.username_cannot_null);
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Utils.toast(R.string.password_can_not_null);
            return;
        }
        if (!againPassword.equals(password)) {
            Utils.toast(R.string.password_not_consistent);
            return;
        }

        int checkedId = sexRadio.getCheckedRadioButtonId();
        final User.Gender gender;
        gender = checkedId == R.id.male ? User.Gender.Male : User.Gender.Female;

        new NetAsyncTask(ctx) {
            @Override
            protected void doInBack() throws Exception {
                User user = UserService.signUp(name, password);
                user.setGender(gender);
                user.setFetchWhenSave(true);
                user.save();
            }

            @Override
            protected void onPost(Exception e) {
                if (e != null) {
                    Utils.toast(App.ctx.getString(R.string.registerFailed) + e.getMessage());
                } else {
                    Utils.toast(R.string.registerSucceed);
                    ChatUtils.updateUserLocation();
                    MainActivity.goMainActivity(RegisterActivity.this);
                }
            }
        }.execute();
    }
}
