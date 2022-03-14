package com.example.myyiji.DongTai;

import java.util.List;
import java.util.Map;

public class DongTaiRecycleItemMessage {
    private String content;
    private String time;
    private String personID;
    private int logo;
    private String video;
    private String music;
    private String textstate;
    private int[] picturesGroup;
    private int linklistimage;
    private String linklisttext;
    List<String> likelist;
    Map<String,String> remark;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getTextstate() {
        return textstate;
    }

    public void setTextstate(String textstate) {
        this.textstate = textstate;
    }

    public int[] getPicturesGroup() {
        return picturesGroup;
    }

    public void setPicturesGroup(int[] picturesGroup) {
        this.picturesGroup = picturesGroup;
    }

    public int getLinklistimage() {
        return linklistimage;
    }

    public void setLinklistimage(int linklistimage) {
        this.linklistimage = linklistimage;
    }

    public String getLinklisttext() {
        return linklisttext;
    }

    public void setLinklisttext(String linklisttext) {
        this.linklisttext = linklisttext;
    }

    public List<String> getLikelist() {
        return likelist;
    }

    public void setLikelist(List<String> likelist) {
        this.likelist = likelist;
    }

    public Map<String, String> getRemark() {
        return remark;
    }

    public void setRemark(Map<String, String> remark) {
        this.remark = remark;
    }

    public DongTaiRecycleItemMessage(String content, String time, String personID, int logo, String video, String music, String textstate, int[] picturesGroup, int linklistimage, String linklisttext, List<String> likelist, Map<String, String> remark) {
        this.content = content;
        this.time = time;
        this.personID = personID;
        this.logo = logo;
        this.video = video;
        this.music = music;
        this.textstate = textstate;
        this.picturesGroup = picturesGroup;
        this.linklistimage = linklistimage;
        this.linklisttext = linklisttext;
        this.likelist = likelist;
        this.remark = remark;
    }
}
