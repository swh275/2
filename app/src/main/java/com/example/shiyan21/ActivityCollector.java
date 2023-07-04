package com.example.shiyan21;
import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    public  static void removeActivity(Activity activity){
        activities.add(activity);
    }

}