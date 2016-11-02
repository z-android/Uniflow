package prin.open.uniflow.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import prin.open.uniflow.R;
import prin.open.uniflow.helper.BaseMvpFragment;
import prin.open.uniflow.listener.IWeiboView;
import prin.open.uniflow.presenter.WeiboPresenter;

/**
 * Created by zhongzihuan on 2016/10/9.
 * 微博Fragment
 */
public class WeiboFragment extends BaseMvpFragment<IWeiboView, WeiboPresenter> implements IWeiboView {
    @Override
    protected WeiboPresenter createPresenter() {
        return new WeiboPresenter();
    }

    @Override
    protected IWeiboView createViewer() {
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getViewId() {
        return R.layout.fragment_weibo;
    }

}
