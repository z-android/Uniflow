package prin.open.uniflow.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import prin.open.uniflow.helper.FragmentFactory;
import prin.open.uniflow.view.fragment.BaseFragment;

/**
 * Created by zhongzihuan on 2016/10/10.
 */
public class IssuesPagerAdapter extends FragmentStatePagerAdapter {

    private FragmentManager mFragmentManager;
    private ArrayList<BaseFragment> mFragmentList;
    private int[] mFragmentTypes;

    public IssuesPagerAdapter(FragmentManager fm, int[] types) {
        super(fm);
        mFragmentManager = fm;
        mFragmentTypes = types;
        mFragmentList = new ArrayList<>();
        for (int i = 0; i < mFragmentTypes.length; i++) {
            instanceFragment(mFragmentTypes[i]);
        }
    }

    private void instanceFragment(int type) {
        mFragmentList.add(FragmentFactory.createFragment(type));
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

}
