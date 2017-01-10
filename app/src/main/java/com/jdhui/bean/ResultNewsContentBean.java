package com.jdhui.bean;


import com.jdhui.mould.types.IMouldType;

/**
 * 快讯列表  接收后台返回数据的modle
 * news/list
 */
public class ResultNewsContentBean implements IMouldType {
	private String count; //产品总数
	private String content; //内容
	private String newsId; // 快讯id
	private String source; //来源
	private String publisherName; //发布人
	private String topic; //标题
	private String publishTime; //发布时间

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
}
	
