package prin.open.uniflow;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import prin.open.uniflow.config.GlobalConfig;
import prin.open.uniflow.helper.ZImageLoader;
import prin.open.uniflow.utils.ZLogUtil;
import prin.open.uniflow.utils.ZToastUtil;

/**
 * Created by zhongzihuan on 2016/10/10.
 */
public class AppApplication extends Application {

    private Context mBaseContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        mBaseContext = getBaseContext();
        ZToastUtil.init(this);
        ZImageLoader.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private static AppApplication mApplication;

    public static AppApplication getApplication() {
        return mApplication;
    }

    private void initLogs() {
        if (!GlobalConfig.Debug) {
            ZLogUtil.closeAll();
        } else {
            ZLogUtil.openAll();
        }
    }
}
