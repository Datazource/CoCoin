package com.nightonke.saver.ui;

import com.nightonke.saver.model.UploadInfo;

import cn.bmob.v3.BmobQuery;

/**
 * Created by Weiping on 2016/1/27.
 */

public class MyQuery {

    public BmobQuery<UploadInfo> query;
    private int task;

    public int getTask() {
        return task;
    }

    public void setTask(int task) {
        this.task = task;
    }
}
