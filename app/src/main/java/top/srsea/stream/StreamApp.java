package top.srsea.stream;

import android.app.Application;

import top.srsea.lever.Lever;

public class StreamApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Lever.init(getApplicationContext());
    }
}
