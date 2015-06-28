package com.bbs404.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import com.bbs404.R;
import com.bbs404.adapter.NewFriendAdapter;
import com.bbs404.avobject.AddRequest;
import com.bbs404.avobject.User;
import com.bbs404.service.AddRequestService;
import com.bbs404.service.PreferenceMap;
import com.bbs404.util.NetAsyncTask;
import com.bbs404.util.SimpleNetTask;
import com.bbs404.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class NewFriendActivity extends BaseActivity implements OnItemLongClickListener {
    ListView listview;
    NewFriendAdapter adapter;
    List<AddRequest> addRequests = new ArrayList<AddRequest>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_new_friend_activity);
        initView();
        refresh();
    }

    private void refresh() {
        new NetAsyncTask(ctx) {
            List<AddRequest> subAddRequests;

            @Override
            protected void doInBack() throws Exception {
                subAddRequests = AddRequestService.findAddRequests();
            }

            @Override
            protected void onPost(Exception e) {
                if (e != null) {
                    e.printStackTrace();
                    Utils.toast(ctx, R.string.pleaseCheckNetwork);
                } else {
                    PreferenceMap preferenceMap = new PreferenceMap(ctx, User.curUserId());
                    preferenceMap.setAddRequestN(subAddRequests.size());
                    adapter.addAll(subAddRequests);
                }
            }
        }.execute();
    }

    private void initView() {
        initActionBar(R.string.new_friends);
        listview = (ListView) findViewById(R.id.newfriendList);
        listview.setOnItemLongClickListener(this);
        adapter = new NewFriendAdapter(this, addRequests);
        listview.setAdapter(adapter);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position,
                                   long arg3) {
        // TODO Auto-generated method stub
        AddRequest invite = (AddRequest) adapter.getItem(position);
        showDeleteDialog(position, invite);
        return true;
    }

    public void showDeleteDialog(final int position, final AddRequest addRequest) {
        new AlertDialog.Builder(ctx).setMessage(R.string.deleteFriendRequest)
                .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAddRequest(position, addRequest);
                    }
                }).setNegativeButton(R.string.cancel, null).show();
    }

    private void deleteAddRequest(final int position, final AddRequest addRequest) {
        new SimpleNetTask(ctx) {
            @Override
            public void onSucceed() {
                adapter.remove(position);
            }

            @Override
            protected void doInBack() throws Exception {
                addRequest.delete();
            }
        }.execute();

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}
