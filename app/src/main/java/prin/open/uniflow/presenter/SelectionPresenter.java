package prin.open.uniflow.presenter;

import java.util.Random;

import prin.open.uniflow.config.TypeConfig;
import prin.open.uniflow.helper.BasePresenter;
import prin.open.uniflow.listener.ISelectionView;

/**
 * Created by zhongzihuan on 2016/10/10.
 */
public class SelectionPresenter extends BasePresenter<ISelectionView> {

    /**
     * 随机获取精选内容，不断刷新获取一页精选内容
     * @param typeId
     */
    public void getRandomIssueRsp(int typeId) {

        if (getRealView() != null) {
            getRealView().showSelectionRsp(TypeConfig.BOOK);
        }

    }
}
