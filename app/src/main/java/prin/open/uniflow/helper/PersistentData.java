package prin.open.uniflow.helper;

import android.content.Context;
import android.content.SharedPreferences;

import prin.open.uniflow.AppApplication;


/**
 * Created by prin on 2015/11/10.
 * 数据是持久化的处理
 * （1）cookie的存储，获取
 */
public class PersistentData {
    private static final String mAppPrefix = "prin_uniflow";
    private static PersistentData mPersistentData = null;
    private SharedPreferences mSharedPreferences;
    private AppApplication mApplication = AppApplication.getApplication();


    private PersistentData() {
        mSharedPreferences = mApplication.getSharedPreferences(
                "persistentData", Context.MODE_PRIVATE);
    }

    public static PersistentData getInstance() {
        if (mPersistentData == null) {
            mPersistentData = new PersistentData();
        }
        return mPersistentData;
    }

    public void setParentActivity(Class clazz) {
        mSharedPreferences.edit().putString(mAppPrefix + "parent_activity", clazz.getSimpleName()).apply();
    }

    public String getParentActivity() {
        return mSharedPreferences.getString(mAppPrefix + "parent_activity", "");
    }

    /**
     * 当前可获取期刊存储
     */
    public void setAppInitModel(String model) {
        mSharedPreferences.edit().putString(mAppPrefix + "app_init_model", model).apply();
    }

    public String getAppInitModel() {
        return mSharedPreferences.getString(mAppPrefix + "app_init_model", "");
    }


}
