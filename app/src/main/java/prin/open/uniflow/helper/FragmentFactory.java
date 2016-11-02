package prin.open.uniflow.helper;

import prin.open.uniflow.config.TypeConfig;
import prin.open.uniflow.utils.ZLogUtil;
import prin.open.uniflow.view.fragment.BaseFragment;
import prin.open.uniflow.view.fragment.BookFragment;
import prin.open.uniflow.view.fragment.MovieFragment;
import prin.open.uniflow.view.fragment.WebpageFragment;

/**
 * Created by zhongzihuan on 2016/10/10.
 */
public class FragmentFactory {

    private static final String TAG = "FragmentFactory+";

    /**
     * 根据类型实例化不同的fragment
     *
     * @param type
     * @return
     */
    public static BaseFragment createFragment(int type) {
        BaseFragment fragment = null;
        switch (type) {
            case TypeConfig.BOOK:
                fragment = BookFragment.newInstance();
                ZLogUtil.i(TAG, "BookFragment newInstance");
                break;
            case TypeConfig.WEBPAGE:
                fragment = WebpageFragment.newInstance();
                ZLogUtil.i(TAG, "WebpageFragment newInstance");
                break;
            case TypeConfig.MOVIE:
                fragment = MovieFragment.newInstance();
                break;
        }
        return fragment;
    }

}
