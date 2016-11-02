package prin.open.uniflow.listener;

import prin.open.uniflow.helper.IBaseView;
import prin.open.uniflow.model.BookObject;

/**
 * Created by zhongzihuan on 2016/10/9.
 */
public interface IBookView extends IBaseView {

    void showBookRsp(BookObject bookObject);
}
