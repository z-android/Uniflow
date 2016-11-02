package prin.open.uniflow.helper.customview;

import android.graphics.Paint;

/**
 * Created by zhongzihuan on 2016/10/10.
 */
public interface  LoaderView {
    void setRectColor(Paint rectPaint);

    void invalidate();

    boolean valueSet();
}