package com.ebs.rfid.zebra.model;


import java.io.Serializable;


// Prefilterクラス
public class Prefilter implements Serializable {
    private String target;
    private String action;
    private TagPattern tag_pattern;

    // コンストラクタ、ゲッター、セッター
    public Prefilter() {}

    public Prefilter(String target, String action, TagPattern tag_pattern) {
        this.target = target;
        this.action = action;
        this.tag_pattern = tag_pattern;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public TagPattern getTag_pattern() {
        return tag_pattern;
    }

    public void setTag_pattern(TagPattern tag_pattern) {
        this.tag_pattern = tag_pattern;
    }
}


