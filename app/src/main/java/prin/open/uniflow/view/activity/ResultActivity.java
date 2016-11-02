package prin.open.uniflow.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import prin.open.uniflow.R;
import prin.open.uniflow.config.GlobalConfig;
import prin.open.uniflow.helper.PersistentData;
import prin.open.uniflow.helper.ZImageLoader;
import prin.open.uniflow.helper.customview.LoaderImageView;
import prin.open.uniflow.helper.customview.LoaderTextView;
import prin.open.uniflow.model.AppInitModel;
import prin.open.uniflow.utils.SETimeUtils;
import prin.open.uniflow.utils.ZAppUtil;
import prin.open.uniflow.utils.ZJsonUtils;
import prin.open.uniflow.utils.ZLogUtil;
import prin.open.uniflow.utils.ZToastUtil;

/**
 * Created by zhongzihuan on 2016/10/9.
 * 一本书
 */
public class ResultActivity extends FragmentActivity {

    private static final String TAG = "ResultActivity+";
    @Bind(R.id.iv_loading)
    LoaderImageView mIvLoading;
    @Bind(R.id.tv_next_book)
    TextView mTvNextBook;
    @Bind(R.id.tv_count_down)
    TextView mTvCountDown;
    @Bind(R.id.tv_type)
    LoaderTextView mTvType;
    @Bind(R.id.civ_icon)
    ImageView mCivIcon;
    @Bind(R.id.tv_menu)
    TextView mTvMenu;
    @Bind(R.id.tv_issues)
    TextView mTvIssues;
    @Bind(R.id.tv_read_times)
    LoaderTextView mTvReadTimes;

    private Context mContext;
    public CountDownTimer timeCountDown;
    private long mMillisInFuture;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);
        mContext = this;
        ButterKnife.bind(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        initData();
        String appInit = PersistentData.getInstance().getAppInitModel();
        AppInitModel appInitModel = null;
        if (!TextUtils.isEmpty(appInit)) {
            appInitModel = ZJsonUtils.instance().jsonToModel(appInit, AppInitModel.class);
        }
        ZImageLoader.loadImage(mIvLoading, appInitModel.getResult_gif());
        mTvMenu.setText(appInitModel.getSumary());
        mMillisInFuture = SETimeUtils.getDateByFormat(appInitModel.getUpdate_at(), SETimeUtils.sDateFormatYMDHMS).getTime();
        if (timeCountDown == null) {
            initTimeAndStart();
        } else {
            timeCountDown.cancel();
            initTimeAndStart();
        }
    }

    private void initTimeAndStart() {
        timeCountDown = new CountDownTimer(mMillisInFuture, 1000L) {
            @Override
            public void onTick(long millisUntilFinished) {
                ZLogUtil.i(TAG, millisUntilFinished + "");
            }

            @Override
            public void onFinish() {
                backHome();
            }
        };
        timeCountDown.start();
    }

    /**
     * 初始化应用数据
     */
    public void backHome() {
        BmobQuery<AppInitModel> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(GlobalConfig.APP_INIT_ID, new QueryListener<AppInitModel>() {
            @Override
            public void done(AppInitModel model, BmobException e) {
                if (e == null) {
                    ZLogUtil.i(TAG, ZJsonUtils.instance().objToJson(model));
                    ZAppUtil.redirectActivity(mContext, IssueBookActivity.class);
                } else {
                    ZToastUtil.show("获得数据失败");
                }
            }
        });
    }


    private void initData() {

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
