package comx.example.zhushi.categorymanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import comx.example.zhushi.categorymanager.R;

/**
 * Created by zhushi on 2016/11/23.
 */

public class DragGridAdapter extends BaseAdapter {
    private Context context;
    private List<String> commonCategoryList;
    private int removePosition = -1;
    private boolean isVisible = true;

    public DragGridAdapter(Context context, List<String> commonCategoryList) {
        this.context = context;
        this.commonCategoryList = commonCategoryList;
    }

    @Override
    public int getCount() {
        return commonCategoryList.size();
    }

    @Override
    public String getItem(int position) {
        return commonCategoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.category_item_view, null);
        TextView categoryName = (TextView) convertView.findViewById(R.id.category_name);
        categoryName.setText(getItem(position));

        if (removePosition == position) {
            categoryName.setVisibility(View.INVISIBLE);
        }

        if (!isVisible && (position == commonCategoryList.size() - 1)) {
            categoryName.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    public void setRemove(int position) {
        removePosition = position;
        notifyDataSetChanged();
    }

    public void remove() {
        commonCategoryList.remove(removePosition);
        removePosition = -1;
        notifyDataSetChanged();
    }

    public void addItem(String item) {
        commonCategoryList.add(item);
        notifyDataSetChanged();
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
