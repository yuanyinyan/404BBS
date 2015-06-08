package com.bbs404.base;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import com.avos.avoscloud.*;
import com.bbs404.avobject.*;
import com.bbs404.service.ChatService;
import com.bbs404.service.GroupService;
import com.bbs404.service.UserService;
import com.bbs404.ui.activity.LoginActivity;
import com.bbs404.util.Logger;
import com.bbs404.util.PhotoUtil;
import com.baidu.mapapi.SDKInitializer;
import com.bbs404.util.AVOSUtils;
import com.bbs404.util.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.*;

public class App extends Application {
    public static final String DB_NAME = "chat.db3";
    public static final int DB_VER = 3;
    public static boolean debug = true;
    public static App ctx;
    private static Map<String, User> usersCache = new HashMap<String, User>();
    public static Map<String, ChatGroup> chatGroupsCache = new HashMap<String, ChatGroup>();
    List<User> friends = new ArrayList<User>();

    @Override
    public void onCreate() {
        super.onCreate();
        ctx = this;
        Utils.fixAsyncTaskBug();
        AVOSCloud.initialize(this, "ruop8j96cvs5tq2sl940lkdlcqli0raq9nx1fnpf2pmw75vn",
                "6o34gl6g2sgygik6aqwda1cm3fh9ciomz1oy2n4nhy6p3gm2");
        AVObject.registerSubclass(User.class);
        AVObject.registerSubclass(AddRequest.class);
        AVObject.registerSubclass(ChatGroup.class);
        AVObject.registerSubclass(UpdateInfo.class);
        AVObject.registerSubclass(PostObject.class);
        AVObject.registerSubclass(CommentObject.class);

        AVInstallation.getCurrentInstallation().saveInBackground();
        PushService.setDefaultPushCallback(ctx, LoginActivity.class);
        AVOSCloud.setDebugLogEnabled(debug);
        if (App.debug) {
            Logger.level = Logger.VERBOSE;
        } else {
            Logger.level = Logger.NONE;
        }
        initImageLoader(ctx);
        initBaidu();
        openStrictMode();
    }

    public void openStrictMode() {
        if (App.debug) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                            //.penaltyDeath()
                    .build());
        }
    }

    private void initBaidu() {
        SDKInitializer.initialize(this);
    }

    public static App getInstance() {
        return ctx;
    }

    /**
     * 初始化ImageLoader
     */
    public static void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context,
                "leanchat/Cache");
        ImageLoaderConfiguration config = PhotoUtil.getImageLoaderConfig(context, cacheDir);
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    public static User lookupUser(String userId) {
        return usersCache.get(userId);
    }

    public static void registerUserCache(String userId, User user) {
        usersCache.put(userId, user);
    }

    public static void registerUserCache(User user) {
        registerUserCache(user.getObjectId(), user);
    }

    public static void registerBatchUserCache(List<User> users) {
        for (User user : users) {
            registerUserCache(user);
        }
    }

    public static ChatGroup lookupChatGroup(String groupId) {
        return chatGroupsCache.get(groupId);
    }

    public static void registerChatGroupsCache(List<ChatGroup> chatGroups) {
        for (ChatGroup chatGroup : chatGroups) {
            chatGroupsCache.put(chatGroup.getObjectId(), chatGroup);
        }
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }
}
