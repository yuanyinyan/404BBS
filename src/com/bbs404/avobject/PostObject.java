package com.bbs404.avobject;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVGeoPoint;
import com.bbs404.entity.Info;
import com.bbs404.entity.PostInfo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created in com.bbs404.avobject.
 * User: YuanYin
 * Date: 2015/6/7
 * Time: 15:00
 */
@AVClassName("Post")
public class PostObject extends InfoObject {
    public final static String ADD_IMAGE_PLUS = "IMAGE_PLUS";//加载图片的示意图标
    public static final String AUDIO = "AUDIO";
    public static final String AUDIO_LEN = "AUDIO_LEN";
    public static final String IMAGES = "IMAGES";
    public static final String LIKES = "LIKES";
    public static final String COMMENTS = "COMMENTS";

    public PostObject() {
        super();
    }

    @Override
    public Info getInfo() {
        return getPostInfo();
    }

    public PostInfo getPostInfo() {
        return new PostInfo(this);
    }

    public String getContent() {
        return getString(CONTENT);
    }

    public void setContent(String content) {
        put(CONTENT, content);
    }

    public AVFile getAudio() {
        return getAVFile(AUDIO);
    }

    public String getAudioUrl() {
        if (getAudio() != null)
            return getAudio().getUrl();
        else
            return null;
    }

    public void setAudio(AVFile avFile) {
        put(AUDIO, avFile);
    }

    public void setAudio(String avFilePath) {
        try {
            final AVFile audioFile = AVFile.withAbsoluteLocalPath(avFilePath, avFilePath);
            audioFile.save();
            setAudio(audioFile);
        } catch (Exception e) {
            put(AUDIO, null);
        }
    }

    public int getAudioLen() {
        return getInt(AUDIO_LEN);
    }

    public void setAudioLen(int audioLen) {
        put(AUDIO_LEN, audioLen);
    }

    public ArrayList<AVFile> getImages() {
        try {
            return (ArrayList<AVFile>) get(IMAGES);
        } catch (Exception e) {
            return null;
        }
    }

    public void setImages(List<AVFile> imagesFiles) {
        addAll(IMAGES, imagesFiles);
    }

    public void setImages(ArrayList<String> ImagesList) {
        if (ImagesList == null) return;
        if (ImagesList.isEmpty()) return;

        final List<AVFile> fileList = new LinkedList<AVFile>();
        for (String f : ImagesList) {
            if (!f.equals(ADD_IMAGE_PLUS))
                try {
                    final AVFile file = AVFile.withAbsoluteLocalPath(f, f);
                    file.save();
                    fileList.add(file);
                } catch (Exception e) {
                }
        }
        if (!fileList.isEmpty())
            setImages(fileList);
    }

    public ArrayList<String> getImageUrls() {
        ArrayList<AVFile> Image_Files = getImages();
        ArrayList<String> avImageUrls = new ArrayList<String>();
        if (Image_Files != null) {
            for (AVFile avfile : Image_Files) {
                if (avfile != null) {
                    avImageUrls.add(avfile.getUrl());
                }
            }
        }
        return avImageUrls;
    }

    public int getLikes() {
        return getInt(LIKES);
    }

    public int getComments() {
        return getInt(COMMENTS);
    }

}
