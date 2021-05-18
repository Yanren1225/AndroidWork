package ren.imyan.topline.customview.wraprecyclerview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;


//<T>表示泛型为任意类型，表示动态，但是继承后面父类重写方法
public class WrapAdapter<T extends RecyclerView.Adapter> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
//    1. 声明
    private final T mRealAdapter;
//    1-1 构造器
    public WrapAdapter(T adapter) {
        super();
        mRealAdapter = adapter;
    }
//    1-2 mRealAdapter的获取方法
    public T getWrapAdapter() {
        return mRealAdapter;
    }
    private boolean isStaggerGrid;//是否交错显示
    //3. 线性集合，泛型为填充视图信息类,并创建泛型类别类
    private ArrayList<FixedViewInfo> mHeaderViewInfos = new ArrayList<>();
    public class FixedViewInfo{
        public View view;
        public int viewType;
    }
//    4.声明默认头部视图视图类别，<<为左移运算，相当于-1×二的十次方=-1024，整形存储的，一种节省内存的存储方式
    private static final int BASE_HEADER_VIEW_TYPE = -1 << 10;

//    9. ViewHoder编写
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isHeader(viewType)) {
            int whichHeader = Math.abs(viewType - BASE_HEADER_VIEW_TYPE);
            View headerView = mHeaderViewInfos.get(whichHeader).view;
            return createHeaderFooterViewHolder(headerView);
        } else {
            return mRealAdapter.onCreateViewHolder(parent, viewType);
        }
    }
// 10. 头部的脚注holder，布局参数
    private RecyclerView.ViewHolder createHeaderFooterViewHolder(View headerView) {
        if (isStaggerGrid) {
            //流布局管理器配置流布局样式
            StaggeredGridLayoutManager.LayoutParams params = new
                    StaggeredGridLayoutManager.LayoutParams(
                    StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT,
                    StaggeredGridLayoutManager.LayoutParams.WRAP_CONTENT);
            params.setFullSpan(true);
            headerView.setLayoutParams(params);
        }
        return new RecyclerView.ViewHolder(headerView) {
        };
    }
//11. 回收视图调用它在指定位置显示数据
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position < mHeaderViewInfos.size()) {
        } else if (position < mHeaderViewInfos.size() + mRealAdapter.getItemCount())
        {
            mRealAdapter.onBindViewHolder(holder,position - mHeaderViewInfos.size());
        }
    }

    @Override
    public int getItemCount() {
        return mHeaderViewInfos.size() + mRealAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position)) {
            return mHeaderViewInfos.get(position).viewType;
        } else {
            return mRealAdapter.getItemViewType(position - mHeaderViewInfos.size());
        }
    }

    //    5. 编写方法addHeaderView增加头部视图
    public void addHeaderView(View view) {
        if (null == view) {
            throw new IllegalArgumentException("the view to add must not be null!");
        }
        final FixedViewInfo info = new FixedViewInfo();//视图不为空时创建条目信息填充类对象
        info.view = view;
        info.viewType = BASE_HEADER_VIEW_TYPE + mHeaderViewInfos.size();
        mHeaderViewInfos.add(info);
        notifyDataSetChanged();
    }
//    6. 自定义方法广告正好适配宽度
    public void adjustSpanSize(RecyclerView recycler) {
        if (recycler.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager layoutManager = (GridLayoutManager) recycler.getLayoutManager();
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    boolean isHeaderOrFooter =isHeaderPosition(position);
                    return isHeaderOrFooter ? layoutManager.getSpanCount() : 1;
                }
            });
        }
        if (recycler.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            this.isStaggerGrid = true;
        }
    }
    //    7. 判断是否是头部位置
    private boolean isHeaderPosition(int position) {
        return position < mHeaderViewInfos.size();
    }
//    8. 判断是否为头部
    private boolean isHeader(int viewType) {
        return viewType >= BASE_HEADER_VIEW_TYPE  && viewType < (BASE_HEADER_VIEW_TYPE + mHeaderViewInfos.size());
    }

}
