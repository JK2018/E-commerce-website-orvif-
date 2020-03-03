package com.orvif.website3.bean;

public class Metadata {
	private String pageName;
	private String pageUrl;
	private String pageTitle;
	private String pageDescription;

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getPageDescription() {
		return pageDescription;
	}

	public void setPageDescription(String pageDescription) {
		this.pageDescription = pageDescription;
	}

	@Override
	public String toString() {
		return "Metadata{" +
				"pageName='" + pageName + '\'' +
				", pageUrl='" + pageUrl + '\'' +
				", pageTitle='" + pageTitle + '\'' +
				", pageDescription='" + pageDescription + '\'' +
				'}';
	}
}
