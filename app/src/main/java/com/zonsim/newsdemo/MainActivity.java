package com.zonsim.newsdemo;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zonsim.newsdemo.fragment.NewsPager;
import com.zonsim.newsdemo.fragment.UserPager;
import com.zonsim.newsdemo.fragment.YiXuePager;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    
    private RadioGroup radioGroup;
    private ViewPager mViewPager;
    private ArrayList<Fragment> pagers;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
	private TextView mTextView;
	
	@Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
	
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//		    this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		    this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		    
	    }
    }
    
    private void initView() {
        radioGroup = (RadioGroup) findViewById(R.id.rg_bottom_tab);
        radioButton1 = (RadioButton) findViewById(R.id.rb_bottom_1);
        radioButton2 = (RadioButton) findViewById(R.id.rb_bottom_2);
        radioButton3 = (RadioButton) findViewById(R.id.rb_bottom_3);
	    mTextView = (TextView) findViewById(R.id.tv_title);
	    mViewPager = (ViewPager) findViewById(R.id.vp_content);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setCurrentItem(0);
	    mTextView.setText("行业资讯");
    }
    
    
    private void initData() {
        pagers = new ArrayList<>();
        pagers.add(new NewsPager());
        pagers.add(new YiXuePager());
        pagers.add(new UserPager());
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }
    
    private void initListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_bottom_1:
//                        mViewPager.setCurrentItem(0, false);   //是否有viewpager切换动画
                        mViewPager.setCurrentItem(0);
                        break;
                    
                    case R.id.rb_bottom_2:
                        mViewPager.setCurrentItem(1);
                        break;
                    
                    case R.id.rb_bottom_3:
                        mViewPager.setCurrentItem(2);
                        break;
                }
                
            }
        });
        
        
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                
            }
            
            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    radioButton1.setChecked(true);
	                mTextView.setText("行业资讯");
                }
                if (position == 1) {
                    radioButton2.setChecked(true);
	                mTextView.setText("关于益学");
                }
                if (position == 2) {
                    radioButton3.setChecked(true);
	                mTextView.setText("个人中心");
                }
            }
            
            @Override
            public void onPageScrollStateChanged(int state) {
            }
            
        });
    }
    
    private class MyPagerAdapter extends FragmentPagerAdapter {
    
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }
    
        @Override
        public Fragment getItem(int position) {
            return pagers.get(position);
        }
        
        @Override
        public int getCount() {
            return pagers.size();
        }
    }
    
    
    private long exitTime = 0;
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
            return;
        }
//        moveTaskToBack(false);
        super.onBackPressed();
    }
    
}
