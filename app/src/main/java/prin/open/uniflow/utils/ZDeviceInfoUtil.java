package prin.open.uniflow.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by Administrator on 2016/6/11.
 */

public class ZDeviceInfoUtil {

    public static int getVersionCode(Context context)//获取版本号(内部识别号)
    {
        try {
            PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    public static NetworkInfo getActiveNetwork(Context context) {
        if (context == null) {
            return null;
        }
        ConnectivityManager mConnMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mConnMgr == null) {
            return null;
        }
        NetworkInfo aActiveInfo = mConnMgr.getActiveNetworkInfo();
        return aActiveInfo;
    }

    public static String getNetType(Context context) {
        try {
            ConnectivityManager conn = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conn != null) {
                NetworkInfo info = conn.getActiveNetworkInfo();
                if (info != null) {
                    String type = info.getTypeName().toLowerCase(
                            Locale.getDefault());
                    if (type.equals("wifi")) {
                        return type;
                    } else if (type.equals("mobile")) {
                        String apn = info.getExtraInfo().toLowerCase(
                                Locale.getDefault());
                        if (apn != null
                                && (apn.equals("cmwap") || apn.equals("3gwap")
                                || apn.equals("uniwap") || apn
                                .equals("ctwap"))) {
                            return "wap";
                        } else {
                            return apn;
                        }
                    }
                }
            }
        } catch (Exception e) {

            ZLogUtil.logException(e);
            return "";
        }
        return "";
    }

    /**
     * 描述：判断网络是否有效.
     *
     * @param context
     *            the context
     * @return true, if is network available
     */
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * Gps是否打开 需要<uses-permission
     * android:name="android.permission.ACCESS_FINE_LOCATION" />权限
     *
     * @param context
     *            the context
     * @return true, if is gps enabled
     */
    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 判断当前网络是否是移动数据网络.
     *
     * @param context
     *            the context
     * @return boolean
     */
    public static boolean isMobile(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    public static boolean isExistSdCard() {
        try {
            return Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED);
        } catch (Exception e) {
            ZLogUtil.logException(e);
        }
        return false;
    }

    public static String getDeviceId(Context context) {
        return Settings.Secure
                .getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getSysVersion(Context context) {
        String release = android.os.Build.VERSION.RELEASE;
        return release;
    }

    public static String getDeviceName(Context context) {
        String model = android.os.Build.MODEL;
        return model;
    }

    public static String getCpuName() {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader("/proc/cpuinfo");
            br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            for (int i = 0; i < array.length; i++) {
            }
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fr != null)
                try {
                    fr.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return null;
    }

    public static boolean isSimExist(Context context) {
        final TelephonyManager mTelephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        int simState = mTelephonyManager.getSimState();

        if (simState == TelephonyManager.SIM_STATE_ABSENT
                || simState == TelephonyManager.SIM_STATE_UNKNOWN) {
            return false;
        }

        return true;
    }



}
