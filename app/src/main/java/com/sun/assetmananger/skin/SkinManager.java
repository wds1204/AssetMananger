package com.sun.assetmananger.skin;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.sun.assetmananger.config.SPUtil;
import com.sun.assetmananger.config.SkinConfig;
import com.sun.assetmananger.skin.attr.SkinView;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Copyright (C), 2016-2019, 未来酒店
 * File: SkinManager.java
 * Author: wds_sun
 * Date: 2019-09-23 11:18
 * Description: 皮肤管理类
 */
public class SkinManager {

    private static SkinManager mInstance;

    static {
        mInstance = new SkinManager();
    }

    private Map<Activity, List<SkinView>> mSkinViews = new HashMap<>();


    private Context mContext;
    private SkinResource skinResource;

    public static SkinManager getInstance() {
        return mInstance;
    }

    public void init(Context context) {
        this.mContext = context.getApplicationContext();
        String skinPath = SPUtil.getSkinPath(mContext);
        Log.e("TAG", "skinPath init"+skinPath);
        if (TextUtils.isEmpty(skinPath)) {
            return;
        }

        File skinFile = new File(skinPath);
        if (!skinFile.exists()) {
            clearSkinInfo();
            return;
        }

        initSkinResource(skinPath);
    }

    public int loadSkin(String skinPath) {
        //校验签名
        //初始化资源管理器

        initSkinResource(skinPath);
        Set<Activity> activities = mSkinViews.keySet();
        Log.e("TAG", "mSkinViews"+mSkinViews);
        for ( Activity key : activities) {

            List<SkinView> skinViews = mSkinViews.get(key);
            for (SkinView skinView : skinViews) {
                skinView.skin();
            }
        }
        //保存皮肤状态
        savaSkinStatus(skinPath);

        return 0;
    }

    /**
     * 保存皮肤路径
     * @param skinPath
     */
    private void savaSkinStatus(String skinPath) {
        SPUtil.saveSkinPath(mContext,skinPath);
    }


    public List<SkinView> getSkinViews(Activity activity) {
        return mSkinViews.get(activity);
    }

    /**
     * 注册
     * @param skinViews
     * @param activity
     */
    public void registerSkinView(List<SkinView> skinViews, Activity activity) {
        mSkinViews.put(activity,skinViews);

    }

    public SkinResource getSkinResource() {
        return skinResource;
    }

    public boolean needChangeSkin() {
        String skinPath = SPUtil.getSkinPath(mContext);
        return skinPath!=null&&!TextUtils.isEmpty(skinPath)?true:false;
    }

    public void changeSkin(SkinView skinView) {
        String currentSkinPath=SPUtil.getSkinPath(mContext);

        if(!TextUtils.isEmpty(currentSkinPath)) {
            skinView.skin();
        }
    }

    /**
     * 初始化皮肤的Resource
     *
     * @param path
     */
    private void initSkinResource(String path) {
        skinResource = new SkinResource(mContext, path);
    }

    /**
     * 恢复默认皮肤
     */
    public void restoreDefault() {
        String currentSkinPath = SPUtil.getSkinPath(mContext);
        Log.e("TAG", "currentSkinPath==="+currentSkinPath);
        if (TextUtils.isEmpty(currentSkinPath)) {
            return;
        }

        String path = mContext.getPackageResourcePath();
        initSkinResource(path);
        changeSkin(path);
        clearSkinInfo();
    }

    /**
     * 清空皮肤信息
     */
    private void clearSkinInfo() {
        SPUtil.cleanSkinPath(mContext);
    }

    /**
     * 切换皮肤
     *
     * @param path 当前皮肤的路径
     */
    private void changeSkin(String path) {
        for (Activity activity : mSkinViews.keySet()) {
            List<SkinView> skinViews = mSkinViews.get(activity);
            for (SkinView skinView : skinViews) {
                skinView.skin();
            }
        }
    }
}
