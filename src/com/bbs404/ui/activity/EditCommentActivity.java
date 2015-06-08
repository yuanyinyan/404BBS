package com.bbs404.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import com.bbs404.R;
import com.bbs404.avobject.User;
import com.bbs404.entity.CommentInfo;
import com.bbs404.entity.PostInfo;
import com.bbs404.service.PostService;
import com.bbs404.util.SimpleNetTask;

/**
 * Created in com.bbs404.ui.activity.
 * User: YuanYin
 * Date: 2015/6/8
 * Time: 10:33
 */
public class EditCommentActivity extends BaseActivity {
    EditText editText;
    PostInfo postInfo;

    public static void goEditCommentActivity(Context ctx, PostInfo postInfo) {
        Intent intentComment = new Intent(ctx, EditCommentActivity.class);
        intentComment.putExtra("post", postInfo);
        ctx.startActivity(intentComment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_post_layout);
        initActionBar("写评论");

        postInfo = (PostInfo) getIntent().getSerializableExtra("post");

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
                new SimpleNetTask(this) {

                    @Override
                    protected void doInBack() throws Exception {
                        saveComment2Cloud();
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

    private CommentInfo saveComment2Cloud() throws Exception {

        final CommentInfo comment = new CommentInfo();
        comment.setUserId(User.curUserId());
        comment.setComment(editText.getText().toString().replaceAll("\n", " "));
        comment.setPostInfo(postInfo);
        PostService.SaveComment(comment);

        return comment;
    }


}
