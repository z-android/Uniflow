package prin.open.uniflow.view.fragment;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.ZoomButtonsController;

import java.lang.reflect.Field;

import butterknife.Bind;
import butterknife.ButterKnife;
import prin.open.uniflow.R;
import prin.open.uniflow.helper.BaseMvpFragment;
import prin.open.uniflow.listener.IWebPageView;
import prin.open.uniflow.model.WebPagerObject;
import prin.open.uniflow.presenter.WebPagePresenter;
import prin.open.uniflow.utils.ZJsonUtils;
import prin.open.uniflow.utils.ZLogUtil;
import prin.open.uniflow.utils.ZToastUtil;
import prin.open.uniflow.view.activity.IssueBookActivity;

/**
 * Created by zhongzihuan on 2016/10/9.
 * 一张网页
 */
public class WebpageFragment extends BaseMvpFragment<IWebPageView, WebPagePresenter> implements IWebPageView {

    private static final String TAG = "WebpageFragment+";
    @Bind(R.id.wv_content)
    WebView mWvContent;

    private String mUrl;
    private String mHtml;
    private ProgressBar mProgressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected WebPagePresenter createPresenter() {
        return new WebPagePresenter();
    }

    @Override
    protected IWebPageView createViewer() {
        return this;
    }

    public static WebpageFragment newInstance() {
        return new WebpageFragment();
    }


    @Override
    protected int getViewId() {
        return R.layout.fragment_webpage;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        mPresenter.getWebPagerRspByIssueId(String.valueOf(mRandomIssue));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showWebPagerRsp(WebPagerObject object) {
        if (object != null) {
            ZLogUtil.i(TAG, "获得的网页数据为" + ZJsonUtils.instance().objToJson(object));
            ((IssueBookActivity) getActivity()).refresh();
            ((IssueBookActivity) getActivity()).updateStep();
            ((IssueBookActivity) getActivity()).updateTitle(object.getWp_from());
            ((IssueBookActivity) getActivity()).updateReadTimes(object.getWp_read_times());

            ((IssueBookActivity) getActivity()).updateTitle(object.getWp_from());
            mUrl = object.getWp_url();
            mPresenter.updateReadTimes(object.getObjectId(),object.getWp_read_times());
            initWebView();
        } else {
            ZToastUtil.show("数据获取失败");
        }
    }

    @SuppressLint({"SetJavaScriptEnabled", "NewApi"})
    private void initWebView() {
        mProgressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 4));
        mProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bar_states));
        mProgressBar.setIndeterminate(false);
        mWvContent.addView(mProgressBar);

        WebSettings localWebSettings = this.mWvContent.getSettings();
        localWebSettings.setSupportZoom(true);
        localWebSettings.setBuiltInZoomControls(true);
        localWebSettings.setJavaScriptEnabled(true);
        localWebSettings.setLoadsImagesAutomatically(true);
        localWebSettings.setDomStorageEnabled(true);
        localWebSettings.setAppCacheEnabled(false);
        localWebSettings.setAllowFileAccess(true);
        localWebSettings.setLoadWithOverviewMode(true);


        //用于判断是否为Android 3.0系统, 然后隐藏缩放控件
        if (Build.VERSION.SDK_INT >= 11) {
            localWebSettings.setDisplayZoomControls(false);
            this.mWvContent.setOverScrollMode(View.OVER_SCROLL_NEVER);
        } else {
            this.setZoomControlGone(mWvContent);
        }

        mWvContent.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
//                if (!TextUtils.isEmpty(mUrl)) {
//                    setHeaderLeftTitle(view.getTitle());
//                }
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri localUri = Uri.parse(url);
                String str = localUri.getScheme();
                if ((TextUtils.isEmpty(str)) || ("http".equals(str)) || ("https".equals(str))) {

                    mWvContent.loadUrl(url);
                    return false;
                }
                return true;
            }
        });

        mWvContent.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                String url = view.getUrl();
                ZLogUtil.d("web_url", "title:  " + url);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                ZLogUtil.i(TAG + newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
        });

        if (!TextUtils.isEmpty(mUrl)) {
            mWvContent.loadUrl(mUrl);
        }
    }

    //Android 3.0(11) 以下使用以下方法:
    //利用java的反射机制
    public void setZoomControlGone(View view) {
        Class classType;
        Field field;
        try {
            classType = WebView.class;
            field = classType.getDeclaredField("mZoomButtonsController");
            field.setAccessible(true);
            ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(
                    view);
            mZoomButtonsController.getZoomControls().setVisibility(View.GONE);
            try {
                field.set(view, mZoomButtonsController);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
