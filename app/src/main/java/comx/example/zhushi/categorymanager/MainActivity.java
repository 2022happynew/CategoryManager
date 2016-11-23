package comx.example.zhushi.categorymanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import comx.example.zhushi.categorymanager.adapter.DragGridAdapter;
import comx.example.zhushi.categorymanager.adapter.OtherGridAdapter;
import comx.example.zhushi.categorymanager.view.DragGridView;
import comx.example.zhushi.categorymanager.view.OtherGridView;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initList();
        dragGridView = (DragGridView) findViewById(R.id.common_used_gridView);
        otherGridView = (OtherGridView) findViewById(R.id.other_gridView);

        dragAdapter = new DragGridAdapter(this, commonCategoryList);
        dragGridView.setAdapter(dragAdapter);

        otherAdapter = new OtherGridAdapter(this, otherCategoryList);
        otherGridView.setAdapter(otherAdapter);
    }

    private void initList() {
        commonCategoryList = new ArrayList<>();
        otherCategoryList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            commonCategoryList.add("  common item" + i);
        }

        for (int i = 0; i < 20; i++) {
            otherCategoryList.add("  other item" + i);
        }
    }
}
