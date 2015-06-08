package com.bbs404.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.bbs404.R;
import com.bbs404.adapter.CommentAdapter;
import com.bbs404.adapter.MainPostAdapter;
import com.bbs404.avobject.CommentObject;
import com.bbs404.avobject.PostObject;
import com.bbs404.entity.CommentInfo;
import com.bbs404.entity.PostInfo;
import com.bbs404.service.UserService;
import com.bbs404.ui.fragment.MainPostFragment;
import com.bbs404.ui.view.ViewHolder;
import com.bbs404.ui.view.xlist.XListView;
import com.bbs404.util.ChatUtils;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created in com.bbs404.ui.activity.
 * User: YuanYin
 * Date: 2015/6/8
 * Time: 10:51
 */
public class PostDetailActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener {
    PrettyTime prettyTime = new PrettyTime();
    PostInfo postInfo;
    private XListView xListView;
    CommentAdapter adapter;

    public static void goPostDetailActivity(Context ctx, PostInfo postInfo) {
        Intent intent = new Intent(ctx, PostDetailActivity.class);
        intent.putExtra("post", postInfo);
        ctx.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail_layout);
        initActionBar("留言详情");

        initPost();
    }

    private void initPost() {
        postInfo = (PostInfo) getIntent().getSerializableExtra("post");
        LayoutInflater mInflater = LayoutInflater.from(ctx);
        LinearLayout root = (LinearLayout) mInflater.inflate(R.layout.main_post_item, null);
        ImageView avatar = (ImageView) root.findViewById(R.id.avatar_view);
        TextView name = (TextView) root.findViewById(R.id.name_text);
        TextView time = (TextView) root.findViewById(R.id.time_text);
        TextView context = (TextView) root.findViewById(R.id.content_text);
        ImageView comment = (ImageView) root.findViewById(R.id.comment_view);

        UserService.displayAvatar(postInfo.getAvatarUrl(), avatar);
        name.setText(postInfo.getName());
        time.setText(prettyTime.format(postInfo.getCreateAt()));
        context.setText(postInfo.getContent());
        context.setEllipsize(null);

        comment.setOnClickListener(this);

        xListView = (XListView) findViewById(R.id.xlistview_comment);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(this);
        adapter = new CommentAdapter(ctx);
        xListView.setAdapter(adapter);
        xListView.addHeaderView(root);
        onRefresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comment_view:
                EditCommentActivity.goEditCommentActivity(ctx, postInfo);
                break;
        }
    }

    @Override
    public void onRefresh() {
        LoadComments(false);
    }

    @Override
    public void onLoadMore() {
        LoadComments(true);
    }

    private void LoadComments(final boolean more) {
        AVQuery<CommentObject> query = CommentObject.getQuery(CommentObject.class);
        query.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);

        query.include(CommentObject.USER);
        query.include(CommentObject.POST);
        query.include(CommentObject.POST + "." + PostObject.IMAGES);
        query.include(CommentObject.POST + "." + PostObject.USER);
        query.include(CommentObject.POST + "." + PostObject.AUDIO);
        query.include(CommentObject.POST + "." + PostObject.USER + ".avatar");
        query.whereEqualTo(CommentObject.POST, postInfo.getPostObject());

        if (more) query.skip(adapter.getCount());

        query.orderByDescending(CommentObject.CREATE_AT);
        query.setLimit(10); //返回的数据条数

        query.findInBackground(
                new FindCallback<CommentObject>() {
                    public void done(List<CommentObject> avObjects, AVException e) {
                        List<CommentInfo> commentInfos = new ArrayList<CommentInfo>();
                        if (e == null) {
                            Log.d("成功--00-", "查询到" + avObjects.size() + " 条符合条件的数据");
                            for (CommentObject avO : avObjects) {
                                if (!adapter.getDatas().contains(avO.getCommentInfo()))
                                    commentInfos.add(avO.getCommentInfo());
                            }
                            if (!more) adapter.clear();

                            ChatUtils.handleListResult(xListView, adapter, commentInfos);
                            stopLoadMore();
                            stopRefresh();
                        } else {
                            Log.d("失败", "查询错误: " + e.getMessage());
                        }
                    }
                }
        );
    }

    private void stopLoadMore() {
        if (xListView.getPullLoading()) {
            xListView.stopLoadMore();
        }
    }

    private void stopRefresh() {
        if (xListView.getPullRefreshing()) {
            xListView.stopRefresh();
        }
    }

}
