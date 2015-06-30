package com.bbs404.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bbs404.R;
import com.bbs404.entity.CommentInfo;
import com.bbs404.service.UserService;
import com.bbs404.ui.view.ViewHolder;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;

/**
 * Created in com.bbs404.adapter.
 * User: YuanYin
 * Date: 2015/6/8
 * Time: 11:14
 */
public class CommentAdapter extends BaseListAdapter<CommentInfo> {
    PrettyTime prettyTime = new PrettyTime();
    CommentInfo commentInfo;

    public CommentAdapter(Context ctx) {
        super(ctx);
    }

    public CommentAdapter(Context ctx, List<CommentInfo> datas) {
        super(ctx, datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.main_post_item, null, false);
        }
        commentInfo = datas.get(position);
        LinearLayout container = ViewHolder.findViewById(convertView, R.id.post_item_container);
        ImageView avatar = ViewHolder.findViewById(convertView, R.id.avatar_view);
        TextView name = ViewHolder.findViewById(convertView, R.id.name_text);
        TextView time = ViewHolder.findViewById(convertView, R.id.time_text);
        TextView context = ViewHolder.findViewById(convertView, R.id.content_text);
        ImageView comment = ViewHolder.findViewById(convertView, R.id.comment_view);
        TextView commentNumber = ViewHolder.findViewById(convertView, R.id.comment_number);
        comment.setVisibility(View.GONE);
        commentNumber.setVisibility(View.GONE);
        container.setClickable(false);
        context.setMaxLines(100);

        UserService.displayAvatar(commentInfo.getAvatarUrl(), avatar);
        name.setText(commentInfo.getName());
        time.setText(prettyTime.format(commentInfo.getCreateAt()));
        context.setText(commentInfo.getContent());

        return convertView;
    }
}
