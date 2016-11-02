package prin.open.uniflow.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import prin.open.uniflow.R;
import prin.open.uniflow.adapter.IssuesPagerAdapter;
import prin.open.uniflow.helper.BaseMVPActivity;
import prin.open.uniflow.listener.IMainView;
import prin.open.uniflow.presenter.MainPresenter;

/**
 * 正刊Activity
 */
public class MainActivity extends BaseMVPActivity<IMainView, MainPresenter> implements IMainView {

    private static final String TAG = "MainActivity:";

    @Bind(R.id.tv_type)
    ShimmerTextView mTvType;
    @Bind(R.id.civ_icon)
    ImageView mCivIcon;
    @Bind(R.id.tv_menu)
    TextView mTvMenu;
    @Bind(R.id.tv_issues)
    TextView mTvIssues;
    @Bind(R.id.tv_read_times)
    TextView mTvReadTimes;
    @Bind(R.id.fl_content)
    FrameLayout mFlContent;
    @Bind(R.id.tv_flow)
    TextView mTvFlow;
    @Bind(R.id.vp_content)
    ViewPager mVpContent;

    private Shimmer mShimmer;
    private FragmentManager mManager;
    private IssuesPagerAdapter mIssuesPagerAdapter;

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public IMainView createViewer() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        //一次性获得所有数据  一个json数据集
        //内容数，内容的icon，出处，内容类型，内容阅览次数，
        mPresenter.getIssueRsp();
    }

    private void initView() {
        mShimmer = new Shimmer();
        mShimmer.setDuration(900)
                .setStartDelay(300)
                .setDirection(Shimmer.ANIMATION_DIRECTION_RTL);
        mShimmer.start(mTvType);


    }

    @Override
    protected void onPause() {
        super.onPause();
        mShimmer.cancel();
    }

    @Override
    public void showIssueRsp(int[] result) {
        if (result != null && result.length > 0) {
            //获取到最新内容
            mManager = getSupportFragmentManager();
            mIssuesPagerAdapter = new IssuesPagerAdapter(mManager, result);
            mVpContent.setAdapter(mIssuesPagerAdapter);
        } else {
            //提示获取失败，或者提示还未发布最新内容
            //无最新内容则走入随机精选模块

        }
    }
}
