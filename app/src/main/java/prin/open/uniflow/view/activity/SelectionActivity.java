//package prin.open.uniflow.view.activity;
//
//import android.app.Service;
//import android.os.Bundle;
//import android.os.Vibrator;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import java.util.Random;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import prin.open.uniflow.R;
//import prin.open.uniflow.config.TypeConfig;
//import prin.open.uniflow.helper.BaseMVPActivity;
//import prin.open.uniflow.helper.FragmentFactory;
//import prin.open.uniflow.helper.PersistentData;
//import prin.open.uniflow.helper.customview.LoaderTextView;
//import prin.open.uniflow.listener.ISelectionView;
//import prin.open.uniflow.model.AppInitModel;
//import prin.open.uniflow.presenter.SelectionPresenter;
//import prin.open.uniflow.utils.ZJsonUtils;
//import prin.open.uniflow.view.fragment.BaseFragment;
//import tyrantgit.explosionfield.ExplosionField;
//
///**
// * 随机精选
// */
//public class SelectionActivity extends BaseMVPActivity<ISelectionView, SelectionPresenter> implements ISelectionView {
//    private static final String TAG = "SelectionActivity+";
//    @Bind(R.id.tv_type)
//    LoaderTextView mTvType;
//    @Bind(R.id.civ_icon)
//    ImageView mCivIcon;
//    @Bind(R.id.tv_menu)
//    TextView mTvMenu;
//    @Bind(R.id.tv_issues)
//    TextView mTvIssues;
//    @Bind(R.id.tv_read_times)
//    TextView mTvReadTimes;
//    @Bind(R.id.rl_random_content)
//    RelativeLayout mRlRandomContent;
//    @Bind(R.id.bt_flow)
//    Button mBtFlow;
////    @Bind(R.id.bt_reset)
////    FlowButton mBtReset;
////    @Bind(R.id.bt_share)
////    FlowButton mBtShare;
////    @Bind(R.id.bt_content)
////    FlowButton mBtContent;
//
//    private FragmentManager mFragmentManager;
//    private FragmentTransaction mTransaction;
//    private int mIssues;
//    private Random mRandom;
//    private ExplosionField mExplosionField;
//
//    @Override
//    public SelectionPresenter createPresenter() {
//        return new SelectionPresenter();
//    }
//
//    @Override
//    public ISelectionView createViewer() {
//        return this;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_selection);
//        ButterKnife.bind(this);
//        mExplosionField = ExplosionField.attach2Window(this);
//        initView();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        //首先获得界面类型
//        initData();
//    }
//
//    private void initTypeRandow() {
//        String modelStr = PersistentData.getInstance().getAppInitModel();
//        AppInitModel appInitModel = null;
//        if (!modelStr.isEmpty()) {
//            appInitModel = ZJsonUtils.instance().jsonToModel(modelStr, AppInitModel.class);
//        }
//        int issuesType = Integer.parseInt(appInitModel.getIssues_type());
//        Random typeRandom = new Random();
//        int typeId = typeRandom.nextInt(issuesType) + 1;
//
//        //设置随机请求ID
//        mRandom = new Random();
//        switch (typeId) {
//            case TypeConfig.BOOK:
//                mIssues = Integer.parseInt(appInitModel.getBook_issues());
//                break;
//            case TypeConfig.WEBPAGE:
//                mIssues = Integer.parseInt(appInitModel.getWebpage_issues());
//                break;
//        }
//        int mQueryId = mRandom.nextInt(mIssues) + 1;
//        mTransaction = mFragmentManager.beginTransaction();
//        BaseFragment fragment = FragmentFactory.createFragment(typeId);
//        fragment.setRandomIssue(mQueryId);
//        mTransaction.replace(R.id.rl_random_content, fragment);
//        mTransaction.commit();
//    }
//
//    private void initView() {
//        mTvMenu.setText("精选");
//        mTvMenu.setTextSize(14);
//        mTvIssues.setText("阅读");
//        mFragmentManager = getSupportFragmentManager();
//        mTransaction = mFragmentManager.beginTransaction();
//        final Vibrator vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
//        mBtFlow.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
////                vibrator.vibrate(100);
////                showNextFlow();
//                return false;
//            }
//        });
//    }
//
//    /**
//     * 显示按钮的下一级交互
//     */
////    private void showNextFlow() {
////        //分享按钮
////        mBtShare.setVisibility(View.VISIBLE);
////        ObjectAnimator shareAnimator = ObjectAnimator.ofFloat(mBtShare, "translationX", 0, -160);
////        shareAnimator.setDuration(400);
////        shareAnimator.setInterpolator(new BounceInterpolator());
////        shareAnimator.start();
////
////        //内容切换按钮
////        mBtContent.setVisibility(View.VISIBLE);
////        ObjectAnimator contentAnimtor = ObjectAnimator.ofFloat(mBtContent, "translationX", 0, 160);
////        contentAnimtor.setDuration(400);
////        contentAnimtor.setInterpolator(new BounceInterpolator());
////        contentAnimtor.start();
////
////        mBtReset.setVisibility(View.VISIBLE);
////    }
////
////    private void resetNextFlow() {
////        mBtShare.setVisibility(View.GONE);
////        mBtContent.setVisibility(View.GONE);
////        mBtReset.setVisibility(View.GONE);
////    }
//    private void initData() {
//        initTypeRandow();
//    }
//
//    public void updateTitle(String title) {
//        mTvType.setText(title);
//    }
//
//    public void updateReadTimes(String times) {
//        mTvReadTimes.setText(times);
//    }
//
//    @Override
//    public void showSelectionRsp(int type) {
//    }
//
//    public void refresh() {
//        mTvType.resetLoader();
//    }
//
//    @OnClick({R.id.bt_flow})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.bt_flow:
//                initData();
//                break;
////            case R.id.bt_reset:
////                resetNextFlow();
////                break;
////            case R.id.bt_share:
////                ZAppUtil.redirectActivity(SelectionActivity.this, ShareActivity.class);
////                break;
////            case R.id.bt_content:
////                ZToastUtil.show("跳转到内容二");
////                explosionField.explode(mRlRandomContent);
////                break;
//        }
//    }
//}
