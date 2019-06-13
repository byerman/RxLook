package com.huaweisoft.retrofitdemo.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.huaweisoft.retrofitdemo.R;
import com.huaweisoft.retrofitdemo.util.UIUtils;

import java.util.List;

/**
 * @author baiaj
 * 弹出listview的适配器
 */
public class PoPUpAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mDatas;
    private ItemClickListener itemClickListener;

    public PoPUpAdapter(Context context,List<String> datas,ItemClickListener listener) {
        super();
        mContext = context;
        mDatas = datas;
        itemClickListener = listener;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_popup,null);
            viewHolder = new ViewHolder();
            viewHolder.btnOper = view.findViewById(R.id.btn_operator);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.btnOper.setText(mDatas.get(position));
        viewHolder.btnOper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onClick(position);
            }
        });
        return view;
    }

    public interface ItemClickListener {
        void onClick(int position);
    }

    public void notify(List<String> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public class ViewHolder{
        public Button btnOper;
    }


}
