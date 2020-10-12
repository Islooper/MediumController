package com.example.mediumcontroller.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediumcontroller.R;
import com.example.mediumcontroller.bean.Scene;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by looper on 2020/10/8.
 */
public class SceneAdapter extends RecyclerView.Adapter<SceneAdapter.LinearViewHolder> {
    Context context;
    List<Scene> list;

    public SceneAdapter(Context context , List<Scene> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SceneAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.sence_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SceneAdapter.LinearViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        switch (list.get(position).getColor()){
            case "#050505":
                holder.background.setBackgroundResource(R.drawable.bg_round_black);
            break;
            case "#2196F3":
                holder.background.setBackgroundResource(R.drawable.bg_round_blue);
                break;
            case "#009688":
                holder.background.setBackgroundResource(R.drawable.bg_round_green);
                break;
            case "#FF5722":
                holder.background.setBackgroundResource(R.drawable.bg_round_orange);
                break;
            case "#673AB7":
                holder.background.setBackgroundResource(R.drawable.bg_round_purple);
                break;
            case "#FFEB3B":
                holder.background.setBackgroundResource(R.drawable.bg_round_yellow);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class LinearViewHolder extends RecyclerView.ViewHolder{
        ImageView delete;
        TextView name;
        TextView edit;
        LinearLayout background;
        TextView play;
        public LinearViewHolder(@NonNull final View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.del);
            name = itemView.findViewById(R.id.name);
            edit = itemView.findViewById(R.id.edit);
            background = itemView.findViewById(R.id.background);
            play = itemView.findViewById(R.id.play);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        onItemClickListener.onItemClick(itemView , list.get(getLayoutPosition()),getLayoutPosition());
                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        onItemClickListener.onItemDel(itemView , list.get(getLayoutPosition()) , getLayoutPosition());
                    }
                }
            });

            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        onItemClickListener.onItemPlay(itemView , list.get(getLayoutPosition()) , getLayoutPosition());
                    }
                }
            });



        }
    }


    //点击 RecyclerView 某条的监听
    public interface OnItemClickListener{

        /**
         * 当RecyclerView某个被点击的时候回调
         * @param view 点击item的视图
         * @param scene 点击得到的数据
         */
        void onItemClick(View view, Scene scene , int i);
        void onItemDel(View view, Scene scene , int i);
        void onItemPlay(View view, Scene scene , int i);

    }

    private OnItemClickListener onItemClickListener;

    /**
     * 设置RecyclerView某个的监听
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
