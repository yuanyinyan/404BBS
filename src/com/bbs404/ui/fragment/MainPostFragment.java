package com.bbs404.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.bbs404.R;
import com.bbs404.adapter.MainPostAdapter;
import com.bbs404.adapter.NearPeopleAdapter;
import com.bbs404.avobject.PostObject;
import com.bbs404.avobject.User;
import com.bbs404.entity.Info;
import com.bbs404.entity.PostInfo;
import com.bbs404.service.UserService;
import com.bbs404.ui.activity.EditPostActivity;
import com.bbs404.ui.activity.PostDetailActivity;
import com.bbs404.ui.view.xlist.XListView;
import com.bbs404.util.ChatUtils;
import com.bbs404.util.NetAsyncTask;
import com.bbs404.util.Utils;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created in com.bbs404.ui.fragment.
 * User: YuanYin
 * Date: 2015/6/7
 * Time: 15:27
 */
public class MainPostFragment extends BaseFragment implements AdapterView.OnItemClickListener, XListView.IXListViewListener {
    XListView listView;
    MainPostAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.discover_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        headerLayout.showTitle(R.string.leave_message);
        headerLayout.showRightImageButton(R.drawable.base_action_bar_add_bg_selector, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.goActivity(getActivity(), EditPostActivity.class);
            }
        });
        initXListView();
    }

    private void initXListView() {
        listView = (XListView) getView().findViewById(R.id.list_near);
//        listView.setOnItemClickListener(this);
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(this);
        adapter = new MainPostAdapter(ctx);
        listView.setAdapter(adapter);
        PauseOnScrollListener listener = new PauseOnScrollListener(UserService.imageLoader, true, true);
        listView.setOnScrollListener(listener);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        queryPosts();
    }

    @Override
    public void onLoadMore() {
        queryPosts();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        PostInfo postInfo = (PostInfo) adapter.getItem(position - 1);
//        PostDetailActivity.goPostDetailActivity(ctx, postInfo);

//        Utils.toast("跳到留言详情页");
    }

    private void queryPosts() {
        AVQuery<PostObject> query = PostObject.getQuery(PostObject.class);
        query.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.orderByDescending(PostObject.CREATE_AT);
        query.include(PostObject.IMAGES);
        query.include(PostObject.USER);
        query.include(PostObject.AUDIO);
        query.setLimit(10); //返回的数据条数

        query.findInBackground(new FindCallback<PostObject>() {
            public void done(List<PostObject> avObjects, AVException e) {
                if (e == null) {
                    List<Info> postInfos = new ArrayList<Info>();
                    for (PostObject avO : avObjects) {
                        PostInfo postInfo = new PostInfo(avO);
                        postInfos.add(postInfo);
                    }
                    ChatUtils.handleListResult(listView, adapter, postInfos);
                } else {

                    if (e.getCode() == AVException.INVALID_JSON)
                        Utils.toastCheckNetwork(ctx);
                    else
                        Utils.toast("查询失败！" + e.getCode() + e.getMessage());
                }
                stopLoadMore();
                stopRefresh();
            }
        });
    }

    private void stopLoadMore() {
        if (listView.getPullLoading()) {
            listView.stopLoadMore();
        }
    }

    private void stopRefresh() {
        if (listView.getPullRefreshing()) {
            listView.stopRefresh();
        }
    }

}
