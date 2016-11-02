package prin.open.uniflow.helper;

import android.os.Bundle;
import android.support.annotation.Nullable;

import prin.open.uniflow.view.fragment.BaseFragment;

/**
 * Created by zhongzihuan on 2016/10/9.
 */
public abstract class BaseMvpFragment<ViewLayerType extends IBaseView, PresenterLayerType extends BasePresenter> extends BaseFragment implements IBaseView {

    protected PresenterLayerType mPresenter;
    protected ViewLayerType mViewer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewer = createViewer();
        mPresenter = createPresenter();

        if (mPresenter != null && mViewer != null) {
            mPresenter.attachView(mViewer);
        }
    }

    protected abstract PresenterLayerType createPresenter();

    protected abstract ViewLayerType createViewer();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    protected int getViewId() {
        return 0;
    }
}
