package prin.open.uniflow.presenter;

import prin.open.uniflow.helper.BasePresenter;
import prin.open.uniflow.listener.IMainView;

/**
 * Created by zhongzihuan on 2016/10/9.
 */
public class MainPresenter extends BasePresenter<IMainView> {

    /**
     * 跳转到下个界面
     */
    public void goNextPage() {

    }

    /**
     * 跳转到上个界面
     */
    public void goLastPage() {

    }

    /**
     * 分享本页内容
     */
    public void goSharePage() {

    }

    /**
     * 获得期刊信息
     */
    public void getIssueRsp() {
        if (getRealView() != null) {
            int[] result = new int[4];
            getRealView().showIssueRsp(result);
        }
    }


}
