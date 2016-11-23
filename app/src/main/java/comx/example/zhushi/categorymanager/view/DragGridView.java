package comx.example.zhushi.categorymanager.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 可长按拖动的view
 * Created by zhushi on 2016/11/23.
 */

public class DragGridView extends GridView {
    public DragGridView(Context context) {
        super(context);
    }

    public DragGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 在ScrollView内，所以要进行计算高度
     */
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
