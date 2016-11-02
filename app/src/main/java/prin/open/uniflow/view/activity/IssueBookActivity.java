package prin.open.uniflow.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import prin.open.uniflow.R;
import prin.open.uniflow.config.TypeConfig;
import prin.open.uniflow.helper.BaseMVPActivity;
import prin.open.uniflow.helper.FragmentFactory;
import prin.open.uniflow.helper.PersistentData;
import prin.open.uniflow.helper.customview.LoaderTextView;
import prin.open.uniflow.listener.ISelectionView;
import prin.open.uniflow.model.AppInitModel;
import prin.open.uniflow.presenter.SelectionPresenter;
import prin.open.uniflow.utils.ZJsonUtils;
import prin.open.uniflow.view.fragment.BaseFragment;
import tyrantgit.explosionfield.ExplosionField;

public class IssueBookActivity extends BaseMVPActivity<ISelectionView, SelectionPresenter> implements ISelectionView {
    private static final String TAG = "SelectionActivity+";
    @Bind(R.id.tv_type)
    LoaderTextView mTvType;
    @Bind(R.id.civ_icon)
    ImageView mCivIcon;
    @Bind(R.id.tv_menu)
    TextView mTvMenu;
    @Bind(R.id.tv_issues)
    TextView mTvIssues;
    @Bind(R.id.tv_read_times)
    TextView mTvReadTimes;
    @Bind(R.id.rl_random_content)
    RelativeLayout mRlRandomContent;
    @Bind(R.id.bt_flow)
    Button mBtFlow;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;
    private int mIssues;
    private Random mRandom;
    private ExplosionField mExplosionField;
    private int mPage;
    private int mIssuePage;
    private int mIssueId;
    private Context mContext;
    private String mSumary;

    @Override
    public SelectionPresenter createPresenter() {
        return new SelectionPresenter();
    }

    @Override
    public ISelectionView createViewer() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_book);
        ButterKnife.bind(this);
        mContext = this;
        mExplosionField = ExplosionField.attach2Window(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //首先获得界面类型
        initData();
        mTransaction = mFragmentManager.beginTransaction();
        BaseFragment fragment = FragmentFactory.createFragment(TypeConfig.BOOK);
        fragment.setRandomIssue(mIssueId);
        mTransaction.replace(R.id.rl_random_content, fragment);
        mTransaction.commit();
    }

    private void initView() {
        mTvMenu.setText("精选");
        mTvMenu.setTextSize(14);
        mTvIssues.setText("阅读");
        mFragmentManager = getSupportFragmentManager();
        mTransaction = mFragmentManager.beginTransaction();
    }

    private void initData() {
        String modelStr = PersistentData.getInstance().getAppInitModel();
        AppInitModel appInitModel = null;
        if (!modelStr.isEmpty()) {
            appInitModel = ZJsonUtils.instance().jsonToModel(modelStr, AppInitModel.class);
        }
        mIssueId = Integer.parseInt(appInitModel.getIssue_id());
        mSumary = appInitModel.getSumary();
    }

    public void updateTitle(String title) {
        mTvType.setText(title);
    }

    public void updateStep() {
        mPage++;
    }

    public void updateReadTimes(String times) {
        mTvReadTimes.setText(times);
    }

    @Override
    public void showSelectionRsp(int type) {
    }

    public void refresh() {
        mTvType.resetLoader();
    }


    @OnClick({R.id.bt_flow})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_flow:
                if (mPage == 1) {
                    mTransaction = mFragmentManager.beginTransaction();
                    BaseFragment fragment = FragmentFactory.createFragment(TypeConfig.WEBPAGE);
                    fragment.setRandomIssue(mIssueId);
                    mTransaction.replace(R.id.rl_random_content, fragment);
                    mTransaction.commit();
                } else {
                    mExplosionField.explode(mBtFlow);
                }
                break;
        }
    }
}
