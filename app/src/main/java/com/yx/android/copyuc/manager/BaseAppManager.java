package com.yx.android.copyuc.manager;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by yx on 2016/1/13 0013.
 */
public class BaseAppManager {
    private static final String TAG = BaseAppManager.class.getSimpleName();
    private static BaseAppManager instance = null;
    private static List<Activity> mActivities = new LinkedList();

    private BaseAppManager() {
    }

    public static synchronized BaseAppManager getInstance() {
        if(instance == null) {
            instance = new BaseAppManager();
        }

        return instance;
    }

    public int size() {
        return mActivities.size();
    }

    public synchronized Activity getForwardActivity() {
        return this.size() > 0?(Activity)mActivities.get(this.size() - 1):null;
    }

    public synchronized void addActivity(Activity activity) {
        mActivities.add(activity);
    }

    public synchronized void removeActivity(Activity activity) {
        if(mActivities.contains(activity)) {
            mActivities.remove(activity);
        }

    }

    public synchronized void clear() {
        for(int i = mActivities.size() - 1; i > -1; --i) {
            Activity activity = (Activity)mActivities.get(i);
            this.removeActivity(activity);
            activity.finish();
            i = mActivities.size();
        }

    }

    public synchronized void clearToTop() {
        for(int i = mActivities.size() - 2; i > -1; --i) {
            Activity activity = (Activity)mActivities.get(i);
            this.removeActivity(activity);
            activity.finish();
            i = mActivities.size() - 1;
        }

    }
}
