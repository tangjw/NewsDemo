package com.zonsim.newsdemo.bean;

import java.util.List;

/**
 * Created by tang-jw on 2016/6/1.
 */
public class BannerListBean extends BaseResponseBean {
	
	/**
	 * total : 4
	 * banner : [{"path":"/attached/image/20160414/20160414164208_759.jpg","belong":2,"modifyuser":12,"modifytimelong":1460623378678,"id":4,"createtimelong":1459931088470,"status":0},{"path":"/attached/image/20160414/20160414164355_418.jpg","belong":2,"modifyuser":12,"modifytimelong":1460623448488,"id":5,"createtimelong":1459931121389,"status":0},{"path":"/attached/image/20160414/20160414164552_674.jpg","belong":2,"modifyuser":12,"modifytimelong":1460623558250,"id":6,"createtimelong":1459931134692,"status":0},{"path":"/attached/image/20160414/20160414164529_979.jpg","belong":2,"modifyuser":12,"modifytimelong":1460623542619,"id":8,"createtimelong":1460541451643,"status":0}]
	 */
	
	private int total;
	/**
	 * path : /attached/image/20160414/20160414164208_759.jpg
	 * belong : 2
	 * modifyuser : 12
	 * modifytimelong : 1460623378678
	 * id : 4
	 * createtimelong : 1459931088470
	 * status : 0
	 */
	
	private List<BannerBean> banner;
	
	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	public List<BannerBean> getBanner() {
		return banner;
	}
	
	public void setBanner(List<BannerBean> banner) {
		this.banner = banner;
	}
	
	public static class BannerBean {
		private String path;
		private int belong;
		private int modifyuser;
		private long modifytimelong;
		private int id;
		private long createtimelong;
		private int status;
		
		public String getPath() {
			return path;
		}
		
		public void setPath(String path) {
			this.path = path;
		}
		
		public int getBelong() {
			return belong;
		}
		
		public void setBelong(int belong) {
			this.belong = belong;
		}
		
		public int getModifyuser() {
			return modifyuser;
		}
		
		public void setModifyuser(int modifyuser) {
			this.modifyuser = modifyuser;
		}
		
		public long getModifytimelong() {
			return modifytimelong;
		}
		
		public void setModifytimelong(long modifytimelong) {
			this.modifytimelong = modifytimelong;
		}
		
		public int getId() {
			return id;
		}
		
		public void setId(int id) {
			this.id = id;
		}
		
		public long getCreatetimelong() {
			return createtimelong;
		}
		
		public void setCreatetimelong(long createtimelong) {
			this.createtimelong = createtimelong;
		}
		
		public int getStatus() {
			return status;
		}
		
		public void setStatus(int status) {
			this.status = status;
		}
	}
}
