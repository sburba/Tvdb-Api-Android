package com.sburba.tvdbapi;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.sburba.tvdbapi.model.TvdbItem;
import com.sburba.tvdbapi.util.ThreadPreconditions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("unused")
public class TvdbItemAdapter<T extends TvdbItem> extends BaseAdapter {

    private static final int NO_RESOURCE = -1;

    private Context mContext;
    private ImageLoader mImageLoader;
    private int mResource;
    private int mTitleViewResourceId;
    private int mImageViewResourceId;
    private int mDescViewResourceId;
    private List<T> mItemList;

    public TvdbItemAdapter(Context context, ImageLoader imageLoader, int resource,
                           int titleViewResourceId) {
        init(context, imageLoader, resource, titleViewResourceId, NO_RESOURCE, NO_RESOURCE);
    }

    public TvdbItemAdapter(Context context, ImageLoader imageLoader, int resource,
                           int titleViewResourceId, int networkImageViewResourceId) {
        init(context, imageLoader, resource, titleViewResourceId, networkImageViewResourceId,
             NO_RESOURCE);
    }

    public TvdbItemAdapter(Context context, ImageLoader imageLoader, int resource,
                           int titleViewResourceId, int networkImageViewResourceId,
                           int descViewResourceId) {
        init(context, imageLoader, resource, titleViewResourceId, networkImageViewResourceId,
             descViewResourceId);
    }

    private void init(Context context, ImageLoader imageLoader, int resource,
                      int titleViewResourceId, int networkImageViewResourceId,
                      int descViewResourceId) {
        mContext = context;
        mImageLoader = imageLoader;
        mResource = resource;
        mTitleViewResourceId = titleViewResourceId;
        mImageViewResourceId = networkImageViewResourceId;
        mDescViewResourceId = descViewResourceId;
        mItemList = new ArrayList<T>();
    }

    public void add(T item) {
        ThreadPreconditions.checkOnMainThread();
        mItemList.add(item);
        notifyDataSetChanged();
    }

    public void addAll(Collection<? extends T> items) {
        ThreadPreconditions.checkOnMainThread();
        mItemList.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public T getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }
        TvdbItem item = getItem(position);

        TextView titleView = ViewHolder.get(convertView, mTitleViewResourceId);
        titleView.setText(item.getTitleText());
        if (mImageViewResourceId != NO_RESOURCE) {
            NetworkImageView imageView = ViewHolder.get(convertView, mImageViewResourceId);
            imageView.setImageUrl(item.getImageUrl(), mImageLoader);
        }
        if (mDescViewResourceId != NO_RESOURCE) {
            TextView descView = ViewHolder.get(convertView, mDescViewResourceId);
            descView.setText(item.getDescText());
        }
        return convertView;
    }

    private static class ViewHolder {
        @SuppressWarnings("unchecked")
        public static <T extends View> T get(View view, int id) {
            SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<View>();
                view.setTag(viewHolder);
            }
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (T) childView;
        }
    }
}
