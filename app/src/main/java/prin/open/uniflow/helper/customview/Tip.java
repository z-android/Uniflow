package prin.open.uniflow.helper.customview;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by prin on 2016/9/5.
 */
public class Tip {

    private static MaterialDialog sMaterialDialog;
    public static final String TITLE = "提示";
    public static final String CONTENT = "处理中...";

    /**
     * 显示提示对话框
     */
    public static void showDialog(Context context, String content, MaterialDialog.SingleButtonCallback callback) {
        dismiss();
        sMaterialDialog = new MaterialDialog.Builder(context).title("提示")
        .cancelable(false)
        .content(content)
        .onPositive(callback)
        .show();
    }

    /**
     * 显示进度条对话框
     */
    public static void showProgress(Context context, String title, String content, boolean isCancelable) {
        dismiss();
        sMaterialDialog = new MaterialDialog.Builder(context)
                .title(title)
                .cancelable(isCancelable)
                .content(content)
                .progress(true, 0).show();
    }

    public static void showProgress(Context context, boolean isCancelable) {
        showProgress(context, TITLE, CONTENT, isCancelable);
    }

    public static void showProgress(Context context, String title, String content) {
        showProgress(context, title, content, true);
    }

    /**
     * 显示确认以及基本对话框
     */
    public static void showConfirmDialog(Context context, String title, String content, boolean isCancelable, MaterialDialog.SingleButtonCallback positiveCallBack, MaterialDialog.SingleButtonCallback negativeCallBack) {
        dismiss();
        sMaterialDialog = new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .cancelable(isCancelable)
                .positiveText("确认")
                .negativeText("取消")
                .onPositive(positiveCallBack)
                .onNegative(negativeCallBack)
                .show();
    }

    public static void showConfirmDialog(Context context) {
        showConfirmDialog(context, TITLE, CONTENT, true, null, null);
    }

    public static void showConfirmDialog(Context context, String title, String content) {
        showConfirmDialog(context, title, content, true, null, null);
    }

    public static void showConfirmDialog(Context context, String content) {
        showConfirmDialog(context, "提示", content, true, null, null);
    }


    public static void dismiss() {
        if (sMaterialDialog != null) {
            sMaterialDialog.dismiss();
        }
    }
}
