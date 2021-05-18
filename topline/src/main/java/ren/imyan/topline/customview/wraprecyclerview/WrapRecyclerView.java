package ren.imyan.topline.customview.wraprecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class WrapRecyclerView extends RecyclerView {
    //2. 创建临时头部view的集合用于存放没有设置adapter之前的头部view
    private List<View> mTempHeaderView = new ArrayList<>();
    //3. 声明当前布局的数据适配器对象
    private WrapAdapter mWrapAdapter;//随后编写数据适配器类
    //4. 声明是否判断手机横向宽度常量
    private boolean shoudAdjustSpanSize;


    //1. 创建3个不同的构造方法并继承父类context
    public WrapRecyclerView(@NonNull Context context) {
        super(context);
    }
    public WrapRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public WrapRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //5.创建自定义控件中的setAdapter方法便于后续使用的时候使用这个方法进行数据填充
    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        if (adapter instanceof WrapAdapter) {
            mWrapAdapter = (WrapAdapter) adapter;
            super.setAdapter(adapter);
        } else {
            mWrapAdapter = new WrapAdapter(adapter);
            for (View view : mTempHeaderView) {
                mWrapAdapter.addHeaderView(view);
            }
            if (mTempHeaderView.size() > 0) {
                mTempHeaderView.clear();
            }
            super.setAdapter(mWrapAdapter);
        }
        if (shoudAdjustSpanSize) {
            mWrapAdapter.adjustSpanSize(this);
        }
        getWrappedAdapter().registerAdapterDataObserver(mDataObserver);
        mDataObserver.onChanged();
    }

//    6. 检索以前设置的wrap适配器，如果未设置适配器，则检索null。
    public WrapAdapter getmWrapAdapter() {
    return mWrapAdapter;
    }

//    7. Adapter类型的get方法
    public Adapter getWrappedAdapter() {
        if (mWrapAdapter == null) {
            throw new IllegalStateException("You must set a adapter before!");
        }
        return mWrapAdapter.getWrapAdapter();
    }

//    8. 添加标题视图
    public void addHeaderView(View view) {
        if (null == view) {
            throw new IllegalArgumentException("the view to add must not be null!");
        } else if (mWrapAdapter == null) {
            mTempHeaderView.add(view);
        } else {
            mWrapAdapter.addHeaderView(view);
        }
    }

//    9. 重写recyclerview中方法，设置布局样式StaggeredGridLayoutManager（流布局样式）
    @Override
    public void setLayoutManager(@Nullable LayoutManager layout) {
        super.setLayoutManager(layout);
        if (layout instanceof GridLayoutManager || layout instanceof
                StaggeredGridLayoutManager){
            this.shoudAdjustSpanSize = true;//根据传入的布局类，判断手机是否为横向
        }
    }

//    10. 新建Adapter的观察者模式，监视列表中条目的变化并通知
    /*
    列表是一个被观察者也就是这个RecyclerView的Adapter，因为Adapter持有了所有数据源，而每条Item要做的是观察这些数据源有没有变化，
    所以就叫被观察者，每个Item就叫观察者。当数据变化的时候，adapter就通知这条Item，数据源发生了变化了(这个通知的过程是
    notifyDataSetChanged来进行的),我们可以猜想，RecyclerView的内部的模式是观察者模式。
     */
    private final AdapterDataObserver mDataObserver = new AdapterDataObserver() {
        //10-1 条目改变
    @Override
    public void onChanged() {
        if(mWrapAdapter!=null){
            mWrapAdapter.notifyDataSetChanged();
        }
    }
        //10-2 条目范围改变
    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        mWrapAdapter.notifyItemRangeChanged(positionStart,itemCount);
    }
        //10-3 条目范围增加
    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        mWrapAdapter.notifyItemRangeInserted(positionStart, itemCount);
    }
    //10-4 条目范围减少
    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        mWrapAdapter.notifyItemRangeRemoved(positionStart,itemCount);
    }
    //10-5 条目移动
    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        mWrapAdapter.notifyItemMoved(fromPosition, toPosition);
    }
};
}
