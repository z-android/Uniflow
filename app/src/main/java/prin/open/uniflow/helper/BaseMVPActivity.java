package prin.open.uniflow.helper;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;
import prin.open.uniflow.R;

/**
 * Created by prin on 2016/8/22.
 */
public abstract class BaseMVPActivity<ViewLayerType extends IBaseView, PresenterLayer extends BasePresenter<ViewLayerType>> extends FragmentActivity implements IBaseView {

    protected PresenterLayer mPresenter;
    protected ViewLayerType mViewer;
    protected SparseArray<BasePresenter> mPresenterArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建presenter
        mViewer = createViewer();
        mPresenter = createPresenter();
        if (mViewer != null && mPresenter != null) {
            mPresenter.attachView(mViewer);
        }
    }

    public abstract PresenterLayer createPresenter();

    public abstract ViewLayerType createViewer();

    /**
     * 初始化Ptr下拉刷新
     * @param content
     */
    protected void initPtrRefresh(final PtrFrameLayout content){
        content.disableWhenHorizontalMove(true);
        //header设置
        final MaterialHeader header = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, PtrLocalDisplay.dp2px(10));
        header.setPtrFrameLayout(content);
        content.addPtrUIHandler(header);

        //UltraPTR
        content.setLoadingMinTime(1000);
        content.setDurationToCloseHeader(300);
        content.setHeaderView(header);
        content.addPtrUIHandler(header);
        content.setPinContent(false);

    }


}
