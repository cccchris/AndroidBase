package cn.tthud.taitian.adapter;

import android.view.View;
import android.view.ViewGroup;

import cn.tthud.taitian.R;
import cn.tthud.taitian.base.BaseRecyclerViewAdapter;
import cn.tthud.taitian.base.BaseRecyclerViewHolder;
import cn.tthud.taitian.bean.StarXueyuanBean;
import cn.tthud.taitian.databinding.ItemCompanyListBinding;
import cn.tthud.taitian.databinding.ItemXueyuanBinding;
import cn.tthud.taitian.utils.ImageLoader;

/**
 * Created by wb on 2017/11/27.
 */

public class StarXueyuanAdapter extends BaseRecyclerViewAdapter<StarXueyuanBean> {

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_xueyuan);
    }

    private class ViewHolder extends BaseRecyclerViewHolder<StarXueyuanBean, ItemXueyuanBinding>{
        ViewHolder(ViewGroup parent, int item_android) {
            super(parent, item_android);
        }

        @Override
        public void onBindViewHolder(final StarXueyuanBean object, final int position) {
            binding.executePendingBindings();
            if(object.getImg() != null && object.getImg().length() != 0){
                ImageLoader.loadRect(object.getImg(),binding.ivImg);
            }else {
                binding.ivImg.setImageResource(R.mipmap.shangweidefault_square);
            }
            binding.tvTitle.setText(object.getTitle());
            binding.tvName.setText(object.getRealname());
            binding.llAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onClick(object,position);
                    }
                }
            });
        }
    }

}
