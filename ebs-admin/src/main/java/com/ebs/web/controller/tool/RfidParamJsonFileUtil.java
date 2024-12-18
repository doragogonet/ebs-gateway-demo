package com.ebs.web.controller.tool;

import com.alibaba.fastjson.JSON;

import java.io.*;

import com.ebs.rfid.TagQuery;
import com.ebs.rfid.zebra.model.Reader;

public class RfidParamJsonFileUtil {
    private final static String JSON_FILE_NAME = "JSON_PARAMS.json";
    private final static String ROOT_JSON_FILE_NAME = System.getProperty("user.dir") + File.separator + JSON_FILE_NAME;

    /**
     * jsonファイルから読み込んで、Reader classに転換する
     * @return
     */
    public static TagQuery loadJsonFile() {
        TagQuery tagQuery = null;
        try {

            File file = new File(ROOT_JSON_FILE_NAME);
            if (file.exists()) {
                //プロジェクトのルートから
                tagQuery = JSON.parseObject(new FileInputStream(ROOT_JSON_FILE_NAME), TagQuery.class);
            } else {
                //プロジェクトのClasspathから
                tagQuery = JSON.parseObject(RfidParamJsonFileUtil.class.getClassLoader().getResourceAsStream(JSON_FILE_NAME), TagQuery.class);
            }
        } catch(Exception ex) {
            return null;
        }
        return tagQuery;
    }

    /**
     * json対象をファイルに書込み
     * @param tagQuery
     * @return
     */
    public static boolean writeJsonFile(TagQuery tagQuery) {
        try {
            //固定にプロジェクトのルートに放置
            if (tagQuery != null) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(ROOT_JSON_FILE_NAME, false))) {
                    writer.write(JSON.toJSONString(tagQuery));
                }
            }
        } catch(Exception ex) {
            return false;
        }
        return true;
    }
}
