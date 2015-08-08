package com.hyman.gridview_item_rotate.config;

import android.app.Application;

import com.hyman.gridview_item_rotate.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by hyman on 2015/8/8.
 */
public class RotateApplication extends Application{

    private RotateApplication application;

    public RotateApplication getInstance() {
        return this.application;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        application = RotateApplication.this;

        initImageLoader();
    }

    private void initImageLoader() {

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder() //
                .showImageForEmptyUri(R.mipmap.empty_img) //
                .showImageOnFail(R.mipmap.empty_img) //
                .cacheInMemory(true) //
                .cacheOnDisc(true) //
                .build();//

        File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())//
                .memoryCache(new LruMemoryCache(5 * 1024 * 1024))//
                .memoryCacheSize(10 * 1024 * 1024)//
                .defaultDisplayImageOptions(defaultOptions)//
                .discCache(new UnlimitedDiscCache(cacheDir))//
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator())//
                .build();
        ImageLoader.getInstance().init(config);
    }
}
