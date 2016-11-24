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

public class OtherGridAdapter extends BaseAdapter {
    private Context context;
    private List<String> otherCategoryList;
    private boolean isVisible = true;
    private int removePosition = -1;

    public OtherGridAdapter(Context context, List<String> otherCategoryList) {
        this.context = context;
        this.otherCategoryList = otherCategoryList;
    }

    @Override
    public int getCount() {
        return otherCategoryList.size();
    }

    @Override
    public String getItem(int position) {
        return otherCategoryList.get(position);
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

        if (!isVisible && position == 0) {
            categoryName.setVisibility(View.INVISIBLE);
        }

        if (removePosition == position) {
            categoryName.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    public void addItem(String item) {
        otherCategoryList.add(0, item);
        notifyDataSetChanged();
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void setRemove(int position) {
        removePosition = position;
        notifyDataSetChanged();
    }

    public void remove() {
        otherCategoryList.remove(removePosition);
        removePosition = -1;
        notifyDataSetChanged();
    }
}
