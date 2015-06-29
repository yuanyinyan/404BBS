package com.bbs404.ui.view.xlist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bbs404.R;
import com.bbs404.entity.PostInfo;
import com.bbs404.service.UserService;
import com.bbs404.ui.activity.EditCommentActivity;
import com.bbs404.ui.activity.PersonInfoActivity;
import com.bbs404.ui.activity.PostDetailActivity;
import com.bbs404.ui.view.ViewHolder;
import org.ocpsoft.prettytime.PrettyTime;

/**
 * Created in com.bbs404.ui.view.xlist.
 * User: YuanYin
 * Date: 2015/6/28
 * Time: 16:17
 */
public class XListViewPostItem extends LinearLayout implements View.OnClickListener {
    PrettyTime prettyTime = new PrettyTime();
    private PostInfo postInfo;

    public XListViewPostItem(Context context) {
        super(context);
        init();
    }

    public XListViewPostItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XListViewPostItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public XListViewPostItem(Context context, PostInfo postInfo) {
        super(context);
        this.postInfo = postInfo;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.main_post_item, this);
    }

    public View initPost(View convertView, PostInfo postInfo) {
        this.postInfo = postInfo;
        LinearLayout container = ViewHolder.findViewById(convertView, R.id.post_item_container);
        ImageView avatar = ViewHolder.findViewById(convertView, R.id.avatar_view);
        TextView name = ViewHolder.findViewById(convertView, R.id.name_text);
        TextView time = ViewHolder.findViewById(convertView, R.id.time_text);
        TextView context = ViewHolder.findViewById(convertView, R.id.content_text);
        ImageView comment = ViewHolder.findViewById(convertView, R.id.comment_view);
        TextView commentNumber = ViewHolder.findViewById(convertView, R.id.comment_number);

        UserService.displayAvatar(postInfo.getAvatarUrl(), avatar);
        name.setText(postInfo.getName());
        time.setText(prettyTime.format(postInfo.getUpdateAt()));
        context.setText(postInfo.getContent());
        context.getEllipsize();
        if (postInfo.getComments() > 0) {
            commentNumber.setText(postInfo.getComments() + "");
        } else {
            commentNumber.setText("评论");
        }

        container.setOnClickListener(this);
        avatar.setOnClickListener(this);
        comment.setOnClickListener(this);
        commentNumber.setOnClickListener(this);

        //TODO 支持图片、语音、点赞
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post_item_container:
                PostDetailActivity.goPostDetailActivity(getContext(), postInfo);
                break;
            case R.id.avatar_view:
                PersonInfoActivity.goPersonInfo(getContext(), postInfo.getUserId());
                break;
            case R.id.comment_view:
                EditCommentActivity.goEditCommentActivity(getContext(), postInfo);
                break;
            case R.id.comment_number:
                EditCommentActivity.goEditCommentActivity(getContext(), postInfo);
                break;
        }
    }
}
