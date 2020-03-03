package com.orvif.website3.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SavedRequest implements Serializable {
	private boolean isPost;
	private String baseUrl;
	private Map<String, List<String>> postParams;
	private Map<String, List<String>> getParams;

	public SavedRequest() {
		postParams = new HashMap<>();
		getParams = new HashMap<>();
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public boolean isPost() {
		return isPost;
	}

	public void setPost(boolean post) {
		isPost = post;
	}

	public Map<String, List<String>> getPostParams() {
		return postParams;
	}

	public void setPostParams(Map<String, List<String>> postParams) {
		this.postParams = postParams;
	}

	public Map<String, List<String>> getGetParams() {
		return getParams;
	}

	public void setGetParams(Map<String, List<String>> getParams) {
		this.getParams = getParams;
	}
}
