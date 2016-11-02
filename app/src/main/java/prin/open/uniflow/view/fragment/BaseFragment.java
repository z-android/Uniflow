package prin.open.uniflow.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import prin.open.uniflow.utils.ZDeviceInfoUtil;

/**
 * Created by zhongzihuan on 2016/10/9.
 */
public abstract class BaseFragment extends Fragment {

    private final String FRAGMENT_NAME = getClass().getSimpleName();
    protected Activity mActivity;
    protected Context mContext;
    private View mView;
    protected int mRandomIssue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(getViewId(), null);
        } else {
            ViewParent parent = mView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mView);
            }
        }
        initView(mView);
        return mView;
    }

    private void initView(View view) {

    }

    protected abstract int getViewId();

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 判断网络状态
     */
    protected boolean hasNetwork() {
        return ZDeviceInfoUtil.isNetworkAvailable(mContext);
    }

    public void setRandomIssue(int issue) {
        mRandomIssue = issue;
    }
}
