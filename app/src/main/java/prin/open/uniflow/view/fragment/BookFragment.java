package prin.open.uniflow.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import prin.open.uniflow.R;
import prin.open.uniflow.helper.BaseMvpFragment;
import prin.open.uniflow.helper.ZImageLoader;
import prin.open.uniflow.listener.IBookView;
import prin.open.uniflow.model.BookObject;
import prin.open.uniflow.presenter.BookPresenter;
import prin.open.uniflow.utils.ZToastUtil;
import prin.open.uniflow.view.activity.IssueBookActivity;
/**
 * Created by zhongzihuan on 2016/10/9.
 * 一本书
 */
public class BookFragment extends BaseMvpFragment<IBookView, BookPresenter> implements IBookView {

    private static final String TAG = "BookFragment+";
    @Bind(R.id.iv_cover)
    ImageView mIvCover;
    @Bind(R.id.tv_from)
    TextView mTvFrom;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_author)
    TextView mTvAuthor;
    @Bind(R.id.tv_content_count)
    TextView mTvContentCount;
    @Bind(R.id.tv_price)
    TextView mTvPrice;
    @Bind(R.id.tv_publish_time)
    TextView mTvPublishTime;
    @Bind(R.id.tv_grade)
    TextView mTvGrade;
    @Bind(R.id.tv_content_section_1)
    TextView mTvContentSection1;
    @Bind(R.id.tv_content_section_2)
    TextView mTvContentSection2;
    @Bind(R.id.tv_content_section_3)
    TextView mTvContentSection3;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static BookFragment newInstance() {

        return new BookFragment();
    }

    @Override
    protected BookPresenter createPresenter() {
        return new BookPresenter();
    }

    @Override
    protected IBookView createViewer() {
        return this;
    }

    @Override
    protected int getViewId() {
        return R.layout.fragment_book;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        mPresenter.getBookRspByIssueId(String.valueOf(mRandomIssue));
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void showBookRsp(BookObject bookObject) {
        if (bookObject != null) {
//            if (PersistentData.getInstance().getParentActivity().equals(SelectionActivity.class.getSimpleName())) {
//                ((SelectionActivity) getActivity()).stopRefreshing();
//            }
            ((IssueBookActivity) getActivity()).refresh();
            ((IssueBookActivity) getActivity()).updateTitle(bookObject.getBk_from());
            ((IssueBookActivity) getActivity()).updateStep();
            ((IssueBookActivity) getActivity()).updateReadTimes(bookObject.getBk_read_times());

            mTvPrice.setText(getString(R.string.bk_price, bookObject.getBk_price()));
            mTvGrade.setText(getString(R.string.bk_grade, bookObject.getBk_grade()));
            mTvAuthor.setText(getString(R.string.bk_author, bookObject.getBk_author()));
            ZImageLoader.loadImage(mIvCover, bookObject.bk_cover_url);
            mTvName.setText(getString(R.string.bk_name, bookObject.getBk_name()));
            mTvContentCount.setText(getString(R.string.bk_content_count, bookObject.getBk_word_count()));
            mTvPublishTime.setText(getString(R.string.bk_publish_date, bookObject.getBk_publish_date()));
            mTvFrom.setText(getString(R.string.bk_from, bookObject.getBk_from()));
            mTvContentSection1.setText(bookObject.getBk_content_section_1() == null ? "" : "    " + bookObject.getBk_content_section_1());
            mTvContentSection2.setText(bookObject.getBk_content_section_2() == null ? "" : "    " + bookObject.getBk_content_section_2());
            mTvContentSection3.setText(bookObject.getBk_content_section_3() == null ? "" : "    " + bookObject.getBk_content_section_3());

            //内容获取成功  则增加一次浏览
            mPresenter.updateReadTime(bookObject.getObjectId(), bookObject.getBk_read_times());
        } else {
            ZToastUtil.show("内容获取失败");
        }
    }

}
