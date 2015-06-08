package com.bbs404.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bbs404.avobject.ChatGroup;
import com.bbs404.ui.view.ViewHolder;
import com.bbs404.R;

import java.util.List;

public class GroupAdapter extends BaseListAdapter<ChatGroup> {
  public GroupAdapter(Context ctx, List<ChatGroup> datas) {
    super(ctx, datas);
  }

  @Override
  public View getView(int position, View conView, ViewGroup parent) {
    if (conView == null) {
      //conView = View.inflate(ctx, R.layout.group_item,null);
      conView = inflater.inflate(R.layout.group_item, null, false);
    }
    TextView nameView = ViewHolder.findViewById(conView, R.id.name);
    ChatGroup chatGroup = datas.get(position);
    nameView.setText(chatGroup.getTitle());
    return conView;
  }
}
