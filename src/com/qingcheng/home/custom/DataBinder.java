package com.qingcheng.home.custom;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Class for binding view and data
 */
abstract public class DataBinder<T extends RecyclerView.ViewHolder> {

    private DataBindAdapter mDataBindAdapter;

    public DataBinder(DataBindAdapter dataBindAdapter) {
        mDataBindAdapter = dataBindAdapter;
    }

    abstract public T newViewHolder(ViewGroup parent);

    abstract public void addData(ItemData data);

    abstract public void updateData(ItemData data);

    abstract public void removeData(ItemData data);

    abstract public void bindViewHolder(T holder, int position);

    abstract public int getItemCount();

    abstract public int getItemViewType();

    public final void notifyDataSetChanged() {
        mDataBindAdapter.notifyDataSetChanged();
    }

    public final void notifyBinderDataSetChanged() {
        notifyBinderItemRangeChanged(0, getItemCount());
    }

    public final void notifyBinderItemChanged(int position) {
        mDataBindAdapter.notifyBinderItemChanged(this, position);
    }

    public final void notifyBinderItemRangeChanged(int positionStart, int itemCount) {
        mDataBindAdapter.notifyBinderItemRangeChanged(this, positionStart, itemCount);
    }

    public final void notifyBinderItemInserted(int position) {
        mDataBindAdapter.notifyBinderItemInserted(this, position);
    }

    public final void notifyBinderItemMoved(int fromPosition, int toPosition) {
        mDataBindAdapter.notifyBinderItemMoved(this, fromPosition, toPosition);
    }

    public final void notifyBinderItemRangeInserted(int positionStart, int itemCount) {
        mDataBindAdapter.notifyBinderItemRangeInserted(this, positionStart, itemCount);
    }

    public final void notifyBinderItemRemoved(int position) {
        mDataBindAdapter.notifyBinderItemRemoved(this, position);
    }

    public final void notifyBinderItemRangeRemoved(int positionStart, int itemCount) {
        mDataBindAdapter.notifyBinderItemRangeRemoved(this, positionStart, itemCount);
    }


    public abstract void destroy();
}
