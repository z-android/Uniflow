package prin.open.uniflow.presenter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import prin.open.uniflow.helper.BasePresenter;
import prin.open.uniflow.listener.IBookView;
import prin.open.uniflow.model.BookObject;
import prin.open.uniflow.utils.ZJsonUtils;
import prin.open.uniflow.utils.ZLogUtil;

/**
 * Created by zhongzihuan on 2016/10/9.
 */
public class BookPresenter extends BasePresenter<IBookView> {

    private static final String TAG = "BookPresenter+";

    /**
     * 获得书本信息
     */
    public void getBookRsp() {
        BmobQuery<BookObject> bookQuery = new BmobQuery<>();
        bookQuery.getObject("hGDV555I", new QueryListener<BookObject>() {
            @Override
            public void done(BookObject bookObject, BmobException e) {
                if (e == null) {
                    ZLogUtil.i(TAG, ZJsonUtils.instance().objToJson(bookObject));
                    if (getRealView() != null) {
                        getRealView().showBookRsp(bookObject);
                    }
                } else {
                    ZLogUtil.i(TAG, "数据获取失败");
                }
            }
        });
    }

    /**
     * 根据期刊号获取书本信息
     */
    public void getBookRspByIssueId(String issue) {
        ZLogUtil.i(TAG, "获取信息的期刊号" + issue);
        BmobQuery<BookObject> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("issue_id", issue);
        bmobQuery.setLimit(1);
        bmobQuery.findObjects(new FindListener<BookObject>() {
            @Override
            public void done(List<BookObject> list, BmobException e) {
                if (getRealView() == null) {
                    return;
                }

                if (e == null) {
                    if (list != null && list.size() > 0) {
                        ZLogUtil.i(TAG, "获得的书籍信息" + ZJsonUtils.instance().objToJson(list.get(0)));
                        getRealView().showBookRsp(list.get(0));
                    }
                } else {
                    getRealView().showBookRsp(null);
                    ZLogUtil.i(TAG, "数据获取失败");
                }
            }
        });
    }

    public void updateReadTime(String id, String times) {
        BookObject bookObject = new BookObject();
        int hasReadTimes = Integer.parseInt(times);
        hasReadTimes++;
        bookObject.setBk_read_times(String.valueOf(hasReadTimes));
        bookObject.update(id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ZLogUtil.i(TAG, "更新成功");
                } else {
                    ZLogUtil.i(TAG, "更新失败");
                }
            }
        });
    }
}
