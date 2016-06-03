package com.zonsim.newsdemo.fragment;

import android.support.v4.app.Fragment;

/**
 * 重写Fragment, 设置Fragment的可见状态
 * Created by tang-jw on 4/21.
 */
public abstract class BaseFragment extends Fragment {
    
    /**
     * Fragment当前是否可见
     */
    private boolean isVisible;
    
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        } 
        
    }
    
    /**
     * 可见
     */
    protected abstract void onInvisible();
    
    /**
     * 不可见
     */
    protected abstract void onVisible();
    
    
}
