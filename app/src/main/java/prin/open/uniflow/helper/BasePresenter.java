package prin.open.uniflow.helper;

import java.lang.ref.SoftReference;

/**
 * Created by prin on 2016/4/6.
 * 业务逻辑处理类基类
 */
public class BasePresenter<V extends IBaseView> {
    private SoftReference<V> mView;

    /**
     * 将Presenter与View层绑定
     */
    public void attachView(V view) {
        this.mView = new SoftReference<>(view);
    }

    /**
     * 将Presenter于View层解绑
     */
    public void detachView() {
        if (mView != null) {
            this.mView.clear();
        }
        this.mView = null;
    }

    /**
     * 获取具体的View层对象
     */
    public V getRealView() {
        return mView != null ? mView.get() : null;
    }



}
