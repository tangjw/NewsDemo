package com.zonsim.newsdemo.bean;

import java.util.List;

/**
 * CopyRight
 * Created by tang-jw on 5/29.
 */

public class NewsListBean extends BaseResponseBean {
	
	
	/**
	 * summary : 日前，技工院校高技能人才培养联盟成立大会暨第一次全体会议在河北邢台召开。会议通过了联盟章程，选举了联盟领导机构和成员单位。原人社部副部长王晓初出席会议并致辞。 
	 * author_header : /attached/image/20160506/20160506163324_149.png
	 * author : 中国劳动保障报 
	 * id : 11
	 * title : 技工院校高技能人才培养联盟成立
	 * summary_image : /attached/image/20160506/20160506163750_32.png
	 * createtimelong : 1462523873081
	 */
	
	private List<NewsBean> news;
	
	public List<NewsBean> getNews() {
		return news;
	}
	
	public void setNews(List<NewsBean> news) {
		this.news = news;
	}
	
	public static class NewsBean {
		private String summary;
		private String author_header;
		private String author;
		private int id;
		private String title;
		private String summary_image;
		private long createtimelong;
		
		public String getSummary() {
			return summary;
		}
		
		public void setSummary(String summary) {
			this.summary = summary;
		}
		
		public String getAuthor_header() {
			return author_header;
		}
		
		public void setAuthor_header(String author_header) {
			this.author_header = author_header;
		}
		
		public String getAuthor() {
			return author;
		}
		
		public void setAuthor(String author) {
			this.author = author;
		}
		
		public int getId() {
			return id;
		}
		
		public void setId(int id) {
			this.id = id;
		}
		
		public String getTitle() {
			return title;
		}
		
		public void setTitle(String title) {
			this.title = title;
		}
		
		public String getSummary_image() {
			return summary_image;
		}
		
		public void setSummary_image(String summary_image) {
			this.summary_image = summary_image;
		}
		
		public long getCreatetimelong() {
			return createtimelong;
		}
		
		public void setCreatetimelong(long createtimelong) {
			this.createtimelong = createtimelong;
		}
	}
}
