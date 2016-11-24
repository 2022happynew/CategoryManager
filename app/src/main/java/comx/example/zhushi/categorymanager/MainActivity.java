package comx.example.zhushi.categorymanager;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import comx.example.zhushi.categorymanager.adapter.DragGridAdapter;
import comx.example.zhushi.categorymanager.adapter.OtherGridAdapter;
import comx.example.zhushi.categorymanager.view.DragGridView;
import comx.example.zhushi.categorymanager.view.OtherGridView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    //常用游戏分类view
    private DragGridView dragGridView;
    //更多游戏分类view
    private OtherGridView otherGridView;

    //常用分类列表
    private List<String> commonCategoryList;
    //更多分类列表
    private List<String> otherCategoryList;

    //常用游戏分类adapter
    private DragGridAdapter dragAdapter;
    //更多游戏分类adapter
    private OtherGridAdapter otherAdapter;

    private boolean isMove = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initList();
        dragGridView = (DragGridView) findViewById(R.id.common_used_gridView);
        otherGridView = (OtherGridView) findViewById(R.id.other_gridView);

        dragAdapter = new DragGridAdapter(this, commonCategoryList);
        dragGridView.setAdapter(dragAdapter);
        dragGridView.setOnItemClickListener(this);

        otherAdapter = new OtherGridAdapter(this, otherCategoryList);
        otherGridView.setAdapter(otherAdapter);
        otherGridView.setOnItemClickListener(this);
    }

    private void initList() {
        commonCategoryList = new ArrayList<>();
        otherCategoryList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            commonCategoryList.add("common item " + i);
        }

        for (int i = 0; i < 20; i++) {
            otherCategoryList.add("other item " + i);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        if (isMove) {
            return;
        }

        switch (parent.getId()) {
            case R.id.common_used_gridView:
                String commonItem = (String) parent.getAdapter().getItem(position);
                final ImageView commonMoveImageView = getView(view);

                if (commonMoveImageView != null) {
                    otherAdapter.addItem(commonItem);
                    otherAdapter.setVisible(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dragAdapter.setRemove(position);
                            //起点坐标
                            int[] startLocation = new int[2];
                            View curStartView = dragGridView.getChildAt(position);
                            curStartView.getLocationInWindow(startLocation);

                            //终点坐标
                            int[] endLocation = new int[2];
                            View curEndView = otherGridView.getChildAt(otherGridView.getFirstVisiblePosition());
                            curEndView.getLocationInWindow(endLocation);

                            MoveAnim(commonMoveImageView, startLocation, endLocation, dragGridView);
                        }
                    }, 100L);
                }
                break;

            case R.id.other_gridView:
                String otherItem = (String) parent.getAdapter().getItem(position);
                final ImageView otherMoveImageView = getView(view);
                if (otherMoveImageView != null) {
                    dragAdapter.addItem(otherItem);
                    dragAdapter.setVisible(false);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            otherAdapter.setRemove(position);
                            //起点坐标
                            int[] startLocation = new int[2];
                            View curStartView = otherGridView.getChildAt(position);
                            curStartView.getLocationInWindow(startLocation);

                            //终点坐标
                            int[] endLocation = new int[2];
                            View curEndView = dragGridView.getChildAt(dragGridView.getLastVisiblePosition());
                            curEndView.getLocationInWindow(endLocation);

                            MoveAnim(otherMoveImageView, startLocation, endLocation, otherGridView);
                        }
                    }, 100L);
                }
                break;
        }
    }

    /**
     * 移动动画
     *
     * @param moveImageView
     * @param startLocation
     * @param endLocation
     * @param clickGridView
     */
    private void MoveAnim(ImageView moveImageView, int[] startLocation, int[] endLocation, final GridView
            clickGridView) {
        int[] initLocation = new int[2];
        moveImageView.getLocationInWindow(initLocation);
        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View mMoveView = getMoveView(moveViewGroup, moveImageView, initLocation);
        TranslateAnimation moveAnimation = new TranslateAnimation(startLocation[0], endLocation[0], startLocation[1],
                endLocation[1]);
        moveAnimation.setDuration(300L);

        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(true);
        moveAnimationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(moveAnimationSet);
        moveAnimationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isMove = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (clickGridView instanceof DragGridView) {
                    otherAdapter.setVisible(true);
                    otherAdapter.notifyDataSetChanged();
                    dragAdapter.remove();

                } else {
                    dragAdapter.setVisible(true);
                    dragAdapter.notifyDataSetChanged();
                    otherAdapter.remove();
                }

                moveViewGroup.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        moveViewGroup.removeView(mMoveView);
                    }
                }, 50L);

                isMove = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 获取移动的VIEW，放入对应ViewGroup布局容器
     *
     * @param moveViewGroup
     * @param moveImageView
     * @param initLocation
     * @return
     */
    private View getMoveView(ViewGroup moveViewGroup, ImageView moveImageView, int[] initLocation) {
        int x = initLocation[0];
        int y = initLocation[1];
        moveViewGroup.addView(moveImageView);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = x;
        mLayoutParams.topMargin = y;
        moveImageView.setLayoutParams(mLayoutParams);

        return moveImageView;
    }

    /**
     * 创建移动的ITEM对应的ViewGroup布局容器
     */
    private ViewGroup getMoveViewGroup() {
        ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
        LinearLayout moveLinearLayout = new LinearLayout(this);
        moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.MATCH_PARENT));
        moveViewGroup.addView(moveLinearLayout);

        return moveLinearLayout;
    }

    /**
     * 获取点击的Item的对应View，
     *
     * @param view
     * @return
     */
    private ImageView getView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(this);
        iv.setImageBitmap(cache);

        return iv;
    }

}
