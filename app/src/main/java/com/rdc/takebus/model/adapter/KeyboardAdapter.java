package com.rdc.takebus.model.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.rdc.takebus.R;
import com.rdc.takebus.presenter.tbinterface.OnKeyboardPressListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 键盘按键的适配器
 */
public class KeyboardAdapter extends BaseAdapter implements View.OnClickListener {
    private static final String TAG = "KeyboardAdapter";
    private List<String> mNumList;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private OnKeyboardPressListener mOnKeyboardPressListener;

    public static final String DELETE = "delete";

    public KeyboardAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);

        mNumList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            mNumList.add("" + i);
        }
        mNumList.add("");
        mNumList.add("0");
        mNumList.add("delete");
    }

    @Override
    public int getCount() {
        return mNumList.size();
    }

    @Override
    public Object getItem(int position) {
        return mNumList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.layout_keyboard_num, null);
            holder = new ViewHolder();
            holder.tvNum = (TextView) convertView.findViewById(R.id.tv_keyboard_num);
            holder.ibDelete = (ImageButton) convertView.findViewById(R.id.ib_keyboard_delete);
            convertView.setTag(R.string.tag_view, holder);
        } else {
            holder = (ViewHolder) convertView.getTag(R.string.tag_view);
        }
        final String numStr = mNumList.get(position);
        holder.tvNum.setText(numStr);
        if (TextUtils.isEmpty(numStr)) {
            convertView.setBackgroundResource(R.color.standardColorWhite);
            holder.ibDelete.setVisibility(View.INVISIBLE);
        } else {
            if (DELETE.equals(numStr)) {
                holder.ibDelete.setVisibility(View.VISIBLE);
                holder.tvNum.setVisibility(View.INVISIBLE);
            } else {
                holder.ibDelete.setVisibility(View.GONE);
                holder.tvNum.setVisibility(View.VISIBLE);
            }
            convertView.setBackgroundResource(R.drawable.bg_keyboard_key_selector);
        }
        convertView.setTag(R.string.tag_click, numStr);
        convertView.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        Log.e(TAG, "点击");
        String num = (String) v.getTag(R.string.tag_click);
        if (!TextUtils.isEmpty(num) && mOnKeyboardPressListener != null) {
            mOnKeyboardPressListener.onKeyboardPress(num);
        }
    }

    private class ViewHolder {
        TextView tvNum;
        ImageButton ibDelete;
    }


    public void setOnKeyboardPressListener(OnKeyboardPressListener onKeyboardPressListener) {
        this.mOnKeyboardPressListener = onKeyboardPressListener;
    }
}
