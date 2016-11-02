package prin.open.uniflow.presenter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import prin.open.uniflow.helper.BasePresenter;
import prin.open.uniflow.listener.IWebPageView;
import prin.open.uniflow.model.WebPagerObject;
import prin.open.uniflow.utils.ZJsonUtils;
import prin.open.uniflow.utils.ZLogUtil;

/**
 * Created by zhongzihuan on 2016/10/12.
 */
public class WebPagePresenter extends BasePresenter<IWebPageView> {

    private static final String TAG = "WebPagePresenter+";

    /**
     * 获取WebPage界面所需信息
     */
    public void getWebPagerRspByIssueId(String issue) {
        BmobQuery<WebPagerObject> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("issue_id", issue);
        bmobQuery.setLimit(1);
        bmobQuery.findObjects(new FindListener<WebPagerObject>() {
            @Override
            public void done(List<WebPagerObject> list, BmobException e) {
                if (e == null) {
                    if (list != null && list.size() > 0) {
                        if (getRealView() != null) {
                            ZLogUtil.i(TAG, "获得的网页信息" + ZJsonUtils.instance().objToJson(list.get(0)));
                            getRealView().showWebPagerRsp(list.get(0));
                        }
                    }
                } else {
                    ZLogUtil.i(TAG, "数据获取失败");
                }
            }
        });
    }

    public void updateReadTimes(String id, String times) {
        WebPagerObject object = new WebPagerObject();
        int hasReadTimes = Integer.parseInt(times);
        hasReadTimes++;
        object.setWp_read_times(String.valueOf(hasReadTimes));
        object.update(id, new UpdateListener() {
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
