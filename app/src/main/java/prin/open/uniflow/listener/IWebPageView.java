package prin.open.uniflow.listener;

import prin.open.uniflow.helper.IBaseView;
import prin.open.uniflow.model.WebPagerObject;

/**
 * Created by zhongzihuan on 2016/10/12.
 */
public interface IWebPageView extends IBaseView {
    void showWebPagerRsp(WebPagerObject object);
}
