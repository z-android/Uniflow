package prin.open.uniflow.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import prin.open.uniflow.R;
import prin.open.uniflow.config.GlobalConfig;
import prin.open.uniflow.helper.BaseMVPActivity;
import prin.open.uniflow.helper.PersistentData;
import prin.open.uniflow.helper.customview.Tip;
import prin.open.uniflow.listener.ISplashView;
import prin.open.uniflow.model.AppInitModel;
import prin.open.uniflow.presenter.SplashPresenter;
import prin.open.uniflow.utils.ZAppUtil;
import prin.open.uniflow.utils.ZDeviceInfoUtil;
import prin.open.uniflow.utils.ZJsonUtils;
import prin.open.uniflow.utils.ZToastUtil;

/**
 * 引导页
 */
public class SplashActivity extends BaseMVPActivity<ISplashView, SplashPresenter> implements ISplashView {


    @Bind(R.id.iv_logo)
    ImageView mIvLogo;
    @Bind(R.id.tv_tip)
    TextView mTvTip;

    private Context mContext;

    @Override
    public SplashPresenter createPresenter() {
        return new SplashPresenter();
    }

    @Override
    public ISplashView createViewer() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Bmob.initialize(this, GlobalConfig.BMOB_APP_ID);
        ButterKnife.bind(this);
        mContext = this;
        initView();
    }

    private void initView() {

    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        //查看网络环境
        //获取基本配置信息
        mPresenter.initConfig();
        //获得版本更新信息
        //开启友盟统计等服务
    }


    @Override
    public void showAppInitRsp(final AppInitModel model) {
        if (model != null) {
            PersistentData.getInstance().setAppInitModel(ZJsonUtils.instance().objToJson(model));
            //检查版本是否需要更新
            int versionCode = new Integer(model.getApp_version_code());
            if (ZDeviceInfoUtil.getVersionCode(this) < versionCode) {
                Tip.showConfirmDialog(this, "提示", model.getApp_update_tag(), false, new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Uri uri = Uri.parse(model.getApp_update_uri());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                }, new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Tip.dismiss();
                        ZAppUtil.redirectActivity(mContext, IssueBookActivity.class);
                        finish();
                        return;
                    }
                });
                return;
            }
        } else {
            ZToastUtil.show("应用初始化失败");
        }
        ZAppUtil.redirectActivity(this, IssueBookActivity.class);
        finish();
    }


}
