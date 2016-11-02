package prin.open.uniflow.helper;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import prin.open.uniflow.R;
import prin.open.uniflow.helper.customview.FlowButton;

/**
 * Created by zhongzihuan on 2016/10/9.
 */
public class FlowLayout extends RelativeLayout {
    private static final String TAG = "FlowView+";
    private final ViewDragHelper mDragHelper;
    public FlowButton mFlowTv;
    private Point mAutoBackOriginPos = new Point();
    private OnDragActionListener mListener;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDragHelper = ViewDragHelper.create(this, 1.0f, new FlowDragCallBack());
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mFlowTv = (FlowButton) findViewById(R.id.bt_flow);
    }

    private class FlowDragCallBack extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mFlowTv;
        }

        public int clampViewPositionHorizontal(View child, int left, int dx) {
            final int leftBound = getPaddingLeft();
            final int rightBound = getWidth() - mFlowTv.getWidth() - leftBound;

            final int newLeft = Math.min(Math.max(left, leftBound), rightBound);

            return newLeft;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topBound = getPaddingTop();
            final int bottomBound = getHeight() - mFlowTv.getHeight();
            final int newTop = Math.min(Math.max(top, topBound), bottomBound);
            return newTop;
        }

        //手指释放时回调


        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (releasedChild == mFlowTv) {
                //手指释放 可以自动回去
                mDragHelper.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
                invalidate();
            }

        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            mDragHelper.captureChildView(mFlowTv, pointerId);
        }

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mAutoBackOriginPos.x = mFlowTv.getLeft();
        mAutoBackOriginPos.y = mFlowTv.getTop();
    }

    public void setOnDragActionListener(OnDragActionListener listener) {
        mListener = listener;
    }

    public interface OnDragActionListener {
        void onDragToLeft();

        void onDragToRight();

        void onDragToTop();
    }

}
