package com.bbs404.util;

import android.os.Environment;

import java.io.File;

public class PathUtils {
  public static String getSDcardDir() {
    return Environment.getExternalStorageDirectory().getPath() + "/";
  }

  public static String checkAndMkdirs(String dir) {
    File file = new File(dir);
    if (file.exists() == false) {
      file.mkdirs();
    }
    return dir;
  }

  public static String getAppPath() {
    String dir = getSDcardDir() + "leanchat/";
    return checkAndMkdirs(dir);
  }

  public static String getAvatarDir() {
    String dir = getAppPath() + "avatar/";
    return checkAndMkdirs(dir);
  }

  public static String getAvatarTmpPath() {
    return getAvatarDir() + "tmp";
  }

  public static String getChatFileDir() {
    String dir = getAppPath() + "files/";
    return checkAndMkdirs(dir);
  }

  public static String getChatFilePath(String id) {
    String dir = getChatFileDir();
    String path = dir + id;
    return path;
  }

  public static String getRecordTmpPath() {
    return getChatFileDir() + "tmp";
  }

  public static String getUUIDFilePath() {
    return getChatFilePath(Utils.uuid());
  }

  public static String getTmpPath() {
    return getAppPath() + "tmp";
  }
}
