package com.bbs404.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.baidu.mapapi.map.Text;
import com.bbs404.R;
import com.bbs404.avobject.User;
import com.bbs404.entity.PostInfo;
import com.bbs404.service.UserService;
import com.bbs404.ui.activity.EditCommentActivity;
import com.bbs404.ui.activity.PersonInfoActivity;
import com.bbs404.ui.activity.PostDetailActivity;
import com.bbs404.ui.view.ViewHolder;
import com.bbs404.util.Utils;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;

/**
 * Created in com.bbs404.adapter.
 * User: YuanYin
 * Date: 2015/6/7
 * Time: 15:34
 */
public class MainPostAdapter extends BaseListAdapter<PostInfo> implements View.OnClickListener {
    PrettyTime prettyTime = new PrettyTime();
    PostInfo postinfo;

    public MainPostAdapter(Context ctx, List<PostInfo> datas) {
        super(ctx, datas);
    }

    public MainPostAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.main_post_item, null, false);
        }
        postinfo = datas.get(position);
        LinearLayout container = ViewHolder.findViewById(convertView, R.id.post_item_container);
        ImageView avatar = ViewHolder.findViewById(convertView, R.id.avatar_view);
        TextView name = ViewHolder.findViewById(convertView, R.id.name_text);
        TextView time = ViewHolder.findViewById(convertView, R.id.time_text);
        TextView context = ViewHolder.findViewById(convertView, R.id.content_text);
        ImageView comment = ViewHolder.findViewById(convertView, R.id.comment_view);

        UserService.displayAvatar(postinfo.getAvatarUrl(), avatar);
        name.setText(postinfo.getName());
        time.setText(prettyTime.format(postinfo.getCreateAt()));
        context.setText(postinfo.getContent());
        context.getEllipsize();

        container.setOnClickListener(this);
        avatar.setOnClickListener(this);
        comment.setOnClickListener(this);

        //TODO 支持图片、语音、点赞
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post_item_container:
                PostDetailActivity.goPostDetailActivity(ctx, postinfo);
                break;
            case R.id.avatar_view:
                PersonInfoActivity.goPersonInfo(ctx, postinfo.getUserId());
                break;
            case R.id.comment_view:
                EditCommentActivity.goEditCommentActivity(ctx, postinfo);
                break;
        }
    }
}
