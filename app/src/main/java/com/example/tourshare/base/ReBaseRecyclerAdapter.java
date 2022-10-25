package com.example.tourshare.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;




public abstract class ReBaseRecyclerAdapter<T> extends RecyclerView.Adapter<ReViewHolder>{
    private Context context;
         private List<T> list;
         private LayoutInflater inflater;
         private int itemLayoutId;
         private boolean isScrolling;
         private OnItemClickListener listener;
         private OnItemLongClickListener longClickListener;
         private RecyclerView recyclerView;
                //RecyclerView attach
                 @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
                 super.onAttachedToRecyclerView(recyclerView);
                this.recyclerView = recyclerView;
             }

                 @Override
         public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
                super.onDetachedFromRecyclerView(recyclerView);
                 this.recyclerView = null;
            }


               public interface OnItemClickListener {
               void onItemClick(RecyclerView parent, View view, int position);
           }

               public interface OnItemLongClickListener {
               boolean onItemLongClick(RecyclerView parent, View view, int position);
             }

                 /**
           * Insert
           *
           * @param item
           * @param position
           */
                 public void insert(T item, int position) {
                 list.add(position, item);
                 notifyItemInserted(position);
             }

                 /**
           * Delete
           *
           * @param position
           */
                 public void delete(int position) {
                 list.remove(position);
                 notifyItemRemoved(position);
                 notifyItemRangeChanged(0,list.size());
                 }

                 public ReBaseRecyclerAdapter(Context context, List<T> list, int itemLayoutId) {
                 this.context = context;
                 this.list = list;
                 this.itemLayoutId = itemLayoutId;
                 inflater = LayoutInflater.from(context);

                 //        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                 //            @Override
                 //            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                 //                super.onScrollStateChanged(recyclerView, newState);
                 //                isScrolling = !(newState == RecyclerView.SCROLL_STATE_IDLE);
                 //                if (!isScrolling) {
                 //                    notifyDataSetChanged();
                 //                }
                 //            }
                 //        });
             }

                 @Override
         public ReViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                 View view = inflater.inflate(itemLayoutId, parent, false);
                 return ReViewHolder.getRecyclerHolder(context, view);
             }

                 @Override
         public void onBindViewHolder(final ReViewHolder holder, int position) {

                 if (listener != null){
//                         holder.itemView.setBackgroundResource(R.drawable.icon_stub);//设置背景
                     }
                 holder.itemView.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                                 if (listener != null && view != null && recyclerView != null) {
                                         int position = recyclerView.getChildAdapterPosition(view);
                                         listener.onItemClick(recyclerView, view, position);
                                     }
                             }
                     });


                 holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                         @Override
                         public boolean onLongClick(View view) {
                                 if (longClickListener != null && view != null && recyclerView != null) {
                                         int position = recyclerView.getChildAdapterPosition(view);
                                         longClickListener.onItemLongClick(recyclerView, view, position);
                                         return true;
                                     }
                                 return false;
                             }
                     });
//                     recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                         @Override
//                         public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                             super.onScrollStateChanged(recyclerView, newState);
//                             switch (newState) {
//                                 case RecyclerView.SCROLL_STATE_DRAGGING:
//                                     Glide.with(context).resumeRequests();
//                                     break;
//                                 case RecyclerView.SCROLL_STATE_SETTLING:
//                                     Glide.with(context).pauseRequests();
//                                     break;
//                                 case RecyclerView.SCROLL_STATE_IDLE:
//                                     Glide.with(context).resumeRequests();
//                                     break;
//                             }
//                         }
//                     });

                 convert(holder, list.get(position), position, isScrolling);

             }

                 @Override
         public int getItemCount() {
                 return list == null ? 0 : list.size();
             }

                 public void setOnItemClickListener(OnItemClickListener listener) {
                 this.listener = listener;
             }

                 public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
                 this.longClickListener = longClickListener;
             }

                 /**
           * RecyclerView adapter
           *
           * @param holder      ViewHolder
           * @param item
           * @param position
           * @param isScrolling
           */public abstract void convert(ReViewHolder holder, T item, int position, boolean isScrolling);
}
