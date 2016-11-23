package comx.example.zhushi.categorymanager.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by zhushi on 2016/11/23.
 */

public class OtherGridView extends GridView {
    public OtherGridView(Context context) {
        super(context);
    }

    public OtherGridView(Context context, AttributeSet attrs) {
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
