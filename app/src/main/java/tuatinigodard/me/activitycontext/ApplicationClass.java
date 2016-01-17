package tuatinigodard.me.activitycontext;

import android.app.Application;

/**
 * Created by GODARD Tuatini on 16/01/16.
 */
public class ApplicationClass extends Application {

    private static ApplicationClass instance;

    public static ApplicationClass getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
