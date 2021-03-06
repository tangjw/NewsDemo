package com.zonsim.newsdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.zonsim.newsdemo.MyApp;
import com.zonsim.newsdemo.R;
import com.zonsim.newsdemo.activity.NewsDetailActivity;
import com.zonsim.newsdemo.bean.BannerListBean;
import com.zonsim.newsdemo.bean.BaseResponseBean;
import com.zonsim.newsdemo.bean.NewsListBean;
import com.zonsim.newsdemo.net.HttpLoader;
import com.zonsim.newsdemo.utils.DensityUtil;
import com.zonsim.newsdemo.utils.MyToast;
import com.zonsim.newsdemo.widght.XListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class NewsPager extends BaseFragment implements XListView.IXListViewListener {
	
	private static final int CANCEL_REFRESHING = 201;
	private static final int POINT_CHANGE = 202;
	private XListView mXListView;
	private NewsAdapter mAdapter;
	
	private String serverUrl = "http://118.145.26.215:8090/edu";
	private String newsUrl = "http://118.145.26.215:8090/edu/lianyi/EduNews/querySummary.do";
//	private String bannerUrl = "http://118.145.26.215:8090/edu/lianyi/EduBanner/listAjax.do";
		private String bannerUrl = "http://192.168.1.233:8080/android/banners.json";
	private List<NewsListBean.NewsBean> mNews;
	private long mPreTime;
	
	private int prePosition;
	private LinearLayout mPointGroup;
	
	final private long CHANG_TIME = 3000;
	
	private ViewPager mViewPager;
	
	private List<BannerListBean.BannerBean> mBanners;
	private MyPagerAdapter mMyPagerAdapter;
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case CANCEL_REFRESHING:
					if (!(Boolean) msg.obj) {
						MyToast.show(MyApp.application, "网络出错了");
					}
					onLoad();
					break;
				
				case POINT_CHANGE:
					int item = mViewPager.getCurrentItem();
					mViewPager.setCurrentItem(item + 1);
					mHandler.sendEmptyMessageDelayed(POINT_CHANGE, CHANG_TIME);
					break;
				
				default:
					break;
			}
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.pager1_news, container, false);
		initView(contentView);
		initData();
		initListener();
		
		return contentView;
	}
	
	/**
	 * 初始化控件
	 */
	private void initView(View contentView) {
		mXListView = (XListView) contentView.findViewById(R.id.lv_refresh);
		//设置可以下拉刷新
		mXListView.setPullRefreshEnable(true);
		//设置可以加载更多
		mXListView.setPullLoadEnable(false);
		//设置自动加载更多
		mXListView.setAutoLoadEnable(false);
		//设置刷新时间
		mXListView.setRefreshTime(getTime());
		//设置自动刷新
//		mXListView.autoRefresh();
		
		View view = LayoutInflater.from(MyApp.application).inflate(R.layout.vp_header, null);
		mXListView.addHeaderView(view);
		mViewPager = (ViewPager) view.findViewById(R.id.vp_banner);
		mPointGroup = (LinearLayout) view.findViewById(R.id.ll_point_group);
		
	}
	
	/**
	 * 初始化数据
	 */
	private void initData() {
		getNewsList();
		getBanners();
	}
	
	/**
	 * 初始化监听
	 */
	private void initListener() {
		mXListView.setXListViewListener(this);
		mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				
			}
			
			@Override
			public void onPageSelected(int position) {
				
				int newPosition = position % mBanners.size();
				// 把当前选中的点给切换了换
				mPointGroup.getChildAt(prePosition).setEnabled(false);
				mPointGroup.getChildAt(newPosition).setEnabled(true);
				// 把当前的索引赋值给前一个索引变量, 方便下一次再切换
				prePosition = newPosition;
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
		mViewPager.setOnTouchListener(new BannerOnTouchListener());
		
		mXListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				Intent intent = new Intent(MyApp.application, NewsDetailActivity.class);
				intent.putExtra("id", mNews.get(position - 2).getId() + "");
				startActivity(intent);
			}
		});
		
	}
	
	@Override
	public void onRefresh() {
		mPreTime = System.currentTimeMillis();
//		System.out.println(System.c);
//		getBanners();
		getNewsList();
	}
	
	@Override
	public void onLoadMore() {
		
	}
	
	/**
	 * 从服务器获得新闻列表
	 */
	private void getNewsList() {
		HttpLoader.get(newsUrl, null, NewsListBean.class, 198, new HttpLoader.ResponseListener() {
			@Override
			public void onGetResponseSuccess(int requestCode, BaseResponseBean response) {
				if (requestCode == 198) {
					NewsListBean bean = (NewsListBean) response;
					mNews = bean.getNews();
					
					if (mAdapter == null) {
						mAdapter = new NewsAdapter();
						mXListView.setAdapter(mAdapter);
					} else {
						mAdapter.notifyDataSetChanged();
					}
				}
				
				long currentTime = System.currentTimeMillis();
				delayStart(currentTime, mPreTime, true);
			}
			
			@Override
			public void onGetResponseError(int requestCode, VolleyError error) {
				long currentTime = System.currentTimeMillis();
				delayStart(currentTime, mPreTime, false);
			}
		});
		delayStart(0, 3000, true);
	}
	
	/**
	 * 获取banner数据
	 */
	private void getBanners() {
		HttpLoader.get(bannerUrl, null, BannerListBean.class, 199, new HttpLoader.ResponseListener() {
			@Override
			public void onGetResponseSuccess(int requestCode, BaseResponseBean response) {
				if (199 == requestCode) {
					BannerListBean bean = (BannerListBean) response;
					mBanners = bean.getBanner();
					if (mMyPagerAdapter == null) {
						mMyPagerAdapter = new MyPagerAdapter();
						mViewPager.setAdapter(mMyPagerAdapter);
					} else {
						mMyPagerAdapter.notifyDataSetChanged();
					}
					if (mBanners.size() > 1) {
						initPoint();
					}
				}
			}
			
			@Override
			public void onGetResponseError(int requestCode, VolleyError error) {
				
			}
		});
	}
	
	/**
	 * XListView结束刷新
	 */
	private void onLoad() {
		mXListView.stopRefresh();
		mXListView.stopLoadMore();
		mXListView.setRefreshTime(getTime());
	}
	
	/**
	 * 获取当前时间
	 *
	 * @return 时间字符串
	 */
	private String getTime() {
		return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
	}
	
	@Override
	protected void onInvisible() {
		delayStart(0, 3000, true);
//		mHandler.removeCallbacksAndMessages(null);
		mHandler.removeMessages(POINT_CHANGE);
	}
	
	@Override
	protected void onVisible() {
		mHandler.sendEmptyMessageDelayed(POINT_CHANGE, CHANG_TIME);
	}
	
	/**
	 * 新闻的XListView Adapter
	 */
	private class NewsAdapter extends BaseAdapter {
		
		@Override
		public int getCount() {
			return mNews.size();
		}
		
		@Override
		public NewsListBean.NewsBean getItem(int position) {
			return mNews.get(position);
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(MyApp.application, R.layout.item_newslist, null);
				holder = new ViewHolder();
				holder.newImg = (ImageView) convertView.findViewById(R.id.iv_new);
				holder.newTitle = (TextView) convertView.findViewById(R.id.tv_title);
				holder.newDetail = (TextView) convertView.findViewById(R.id.tv_detail);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.newTitle.setText(mNews.get(position).getTitle());
			holder.newDetail.setText(mNews.get(position).getSummary());
			ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(holder.newImg,
					R.mipmap.ic_launcher, R.mipmap.ic_launcher);
			
			HttpLoader.getImageLoader().get(serverUrl + mNews.get(position).getSummary_image(), imageListener,
					DensityUtil.dip2px(MyApp.application, 120), DensityUtil.dip2px(MyApp.application, 100));
			
			return convertView;
		}
		
	}
	
	private static class ViewHolder {
		
		ImageView newImg;
		TextView newTitle;
		TextView newDetail;
	}
	
	/**
	 * 延时打开取消刷新
	 */
	private void delayStart(final long curTime, final long preTime, boolean flag) {
		Message msg = Message.obtain();
		msg.what = CANCEL_REFRESHING;
		msg.obj = flag;
		if ((curTime - preTime) < 1500) {
			mHandler.sendMessageDelayed(msg, 1500 - (curTime - preTime));
		} else {
			mHandler.sendMessage(msg);
		}
	}
	
	
	/**
	 * banner adapter
	 */
	private class MyPagerAdapter extends PagerAdapter {
		
		@Override
		public int getCount() {
//			return Integer.MAX_VALUE;
			return mBanners.size() < 2 ? mBanners.size() : Integer.MAX_VALUE;
		}
		
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imageView = new ImageView(MyApp.application);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			
			ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(imageView,
					R.mipmap.ic_launcher, R.mipmap.ic_launcher);
			HttpLoader.getImageLoader().get(serverUrl + mBanners.get(position % mBanners.size()).getPath(), imageListener);
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
					mHandler.removeCallbacksAndMessages(null);
					break;
				case MotionEvent.ACTION_CANCEL:
					mHandler.sendMessageDelayed(Message.obtain(), CHANG_TIME);
					mHandler.sendEmptyMessageDelayed(POINT_CHANGE, CHANG_TIME);
					break;
				case MotionEvent.ACTION_UP:
					mHandler.sendEmptyMessageDelayed(POINT_CHANGE, CHANG_TIME);
					break;
			}
			
			return false;
		}
		
	}
	
	/**
	 * 初始化小圆点
	 */
	private void initPoint() {
		mPointGroup.removeAllViews();
		View view;
		for (int i = 0; i < mBanners.size(); i++) {
			// 每循环一次需要向LinearLayout中添加一个点的view对象
			view = new View(MyApp.application);
			view.setBackgroundResource(R.drawable.point_bg);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					DensityUtil.dip2px(MyApp.application, 8), DensityUtil.dip2px(MyApp.application, 8));
			if (i != 0) {
				// 当前不是第一个点,需要设置左边距
				params.leftMargin = DensityUtil.dip2px(MyApp.application, 6);
			}
			view.setLayoutParams(params);
			view.setEnabled(false);
			mPointGroup.addView(view);
			
		}

//		int half = isFast?getRealCount()*3:Integer.MAX_VALUE/2;
		mViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % mBanners.size());
		mPointGroup.getChildAt(prePosition).setEnabled(true);
		mHandler.removeCallbacksAndMessages(null);
		mHandler.removeMessages(POINT_CHANGE);
		mHandler.sendEmptyMessageDelayed(POINT_CHANGE, CHANG_TIME);
	}
	
}

