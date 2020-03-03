package com.orvif.website3.service;

import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SlideshowImage implements Serializable {
	//private List<String> imgFileList;
	private int position;
	private String url;
	private String alt;

	public SlideshowImage() {
	}

	public SlideshowImage(int position, String url, String alt) {
		this.position = position;
		this.url = url;
		this.alt = alt;
	}


	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	@Override
	public String toString() {
		return "SlideshowImage{" +
				"position=" + position +
				", url='" + url + '\'' +
				", alt='" + alt + '\'' +
				'}';
	}

}
