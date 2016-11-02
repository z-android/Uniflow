package prin.open.uniflow.presenter;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import prin.open.uniflow.config.GlobalConfig;
import prin.open.uniflow.helper.BasePresenter;
import prin.open.uniflow.listener.ISplashView;
import prin.open.uniflow.model.AppInitModel;
import prin.open.uniflow.utils.ZJsonUtils;
import prin.open.uniflow.utils.ZLogUtil;

/**
 * Created by zhongzihuan on 2016/10/10.
 */
public class SplashPresenter extends BasePresenter<ISplashView> {

    private static final String TAG = "SplashPresenter+";

    /**
     * 初始化应用数据
     */
    public void initConfig() {
        BmobQuery<AppInitModel> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(GlobalConfig.APP_INIT_ID, new QueryListener<AppInitModel>() {
            @Override
            public void done(AppInitModel model, BmobException e) {
                if (getRealView() == null) {
                    return;
                }
                if (e == null) {
                    ZLogUtil.i(TAG, ZJsonUtils.instance().objToJson(model));
                    getRealView().showAppInitRsp(model);
                } else {
                    ZLogUtil.i(TAG,"获得数据失败");
                    getRealView().showAppInitRsp(null);
                }
            }
        });
    }
}
