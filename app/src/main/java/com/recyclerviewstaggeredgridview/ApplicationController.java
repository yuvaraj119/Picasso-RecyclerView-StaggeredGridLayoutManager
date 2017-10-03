package com.recyclerviewstaggeredgridview;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by CIPL0349 on 4/21/2016.
 */
public class ApplicationController extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //Do Application initialization over here
        Fabric.with(this, new Crashlytics());
    }

}
