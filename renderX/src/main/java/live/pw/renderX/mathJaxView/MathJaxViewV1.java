package live.pw.renderX.mathJaxView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MathJaxViewV1 extends MathJaxView {

    /**
     * If mathView is not clickable then use this
     */
    public MathJaxViewV1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}