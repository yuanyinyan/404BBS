package com.bbs404.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.bbs404.entity.PostInfo;
import com.bbs404.ui.view.xlist.XListViewPostItem;

import java.util.List;

/**
 * Created in com.bbs404.adapter.
 * User: YuanYin
 * Date: 2015/6/7
 * Time: 15:34
 */
public class MainPostAdapter extends BaseListAdapter<PostInfo> {

    public MainPostAdapter(Context ctx, List<PostInfo> datas) {
        super(ctx, datas);
    }

    public MainPostAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PostInfo postInfo = datas.get(position);

        if (convertView == null) {
            convertView = new XListViewPostItem(ctx, postInfo);
        }
        XListViewPostItem postItem = (XListViewPostItem) convertView;
        return postItem.initPost(convertView, postInfo);
    }

}
