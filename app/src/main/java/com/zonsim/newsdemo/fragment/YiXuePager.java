package com.zonsim.newsdemo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zonsim.newsdemo.R;
import com.zonsim.newsdemo.utils.DensityUtil;

public class YiXuePager extends BaseFragment {
    
    private GridView gridView;
    private ViewPager viewpager;
    private LinearLayout pointGroup;
    private int prePosition;
    
    final private long CHANG_TIME = 4000;
    
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            
            int item = viewpager.getCurrentItem();
            viewpager.setCurrentItem(item + 1);
            handler.sendMessageDelayed(Message.obtain(), CHANG_TIME);
        }
    };
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager2_yixue, null);
        gridView = (GridView) view.findViewById(R.id.gridview);
        viewpager = (ViewPager) view.findViewById(R.id.vp_banner_2);
        pointGroup = (LinearLayout) view.findViewById(R.id.ll_point_group);
        initData();
        initPoint();
        initListener();
        
        return view;
    }
    
    private void initData() {
        gridView.setAdapter(new MyGridAdapter());
        viewpager.setAdapter(new MyPagerAdapter());
    }
    
    
    /**
     * 初始化监听
     */
    private void initListener() {
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                
            }
            
            @Override
            public void onPageSelected(int position) {
                int newPosition = position % 3;
                // 把当前选中的点给切换了换
                pointGroup.getChildAt(prePosition).setEnabled(false);
                pointGroup.getChildAt(newPosition).setEnabled(true);
                // 把当前的索引赋值给前一个索引变量, 方便下一次再切换
                prePosition = newPosition;
            }
            
            @Override
            public void onPageScrollStateChanged(int state) {
                
            }
        });
        viewpager.setOnTouchListener(new BannerOnTouchListener());
    }
    
    /**
     * 初始化小圆点
     */
    private void initPoint() {
        View view;
        for (int i = 0; i < 3; i++) {
            // 每循环一次需要向LinearLayout中添加一个点的view对象
            view = new View(getContext());
            view.setBackgroundResource(R.drawable.point_bg);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    DensityUtil.dip2px(getContext(), 8), DensityUtil.dip2px(getContext(), 8));
            if (i != 0) {
                // 当前不是第一个点,需要设置左边距
                params.leftMargin = DensityUtil.dip2px(getContext(), 6);
            }
            view.setLayoutParams(params);
            view.setEnabled(false);
            pointGroup.addView(view);
            
        }
        
        pointGroup.getChildAt(prePosition).setEnabled(true);
        
    }
    
    @Override
    protected void onInvisible() {
        handler.removeCallbacksAndMessages(null);
    }
    
    @Override
    protected void onVisible() {
        handler.sendMessageDelayed(Message.obtain(), CHANG_TIME);
    }
    
    /**
     * banner1  adapter
     */
    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }
        
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(getContext());
            if (position % 3 == 0) {
                imageView.setImageResource(R.mipmap.ic_launcher);
            }
            if (position % 3 == 1) {
                imageView.setImageResource(R.mipmap.ic_launcher);
            }
            if (position % 3 == 2) {
                imageView.setImageResource(R.mipmap.ic_launcher);
            }
            
            container.addView(imageView);
            
            return imageView;
        }
        
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    
    /**
     * Banner触摸事件, 触摸停止轮播, 松开继续
     */
    private class BannerOnTouchListener implements View.OnTouchListener {
        
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    handler.removeCallbacksAndMessages(null);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    handler.sendMessageDelayed(Message.obtain(), CHANG_TIME);
                    break;
                case MotionEvent.ACTION_UP:
                    handler.sendMessageDelayed(Message.obtain(), CHANG_TIME);
                    break;
            }
            
            return false;
        }
        
    }
    
    /**
     * news adapter
     */
    private class MyGridAdapter extends BaseAdapter {
        
        @Override
        public int getCount() {
            return 9;
        }
        
        @Override
        public Object getItem(int position) {
            return null;
        }
        
        @Override
        public long getItemId(int position) {
            return position;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_gridview, null);
            }

            return convertView;
        }
    }
    
    
}
