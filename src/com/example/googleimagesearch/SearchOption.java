package com.example.googleimagesearch;

import java.io.Serializable;

public class SearchOption implements Serializable{
	private static final long serialVersionUID = 263623452647383373L;
	private String imageSize;
	private String imageType;
	private String colorFilter;
	private String siteFilter;
	
	public SearchOption(String imageSize, String colorFilter, String imageType, String siteFilter){
		this.imageSize = imageSize;
		this.colorFilter = colorFilter;
		this.imageType = imageType;
		this.siteFilter = siteFilter;
	}

	public String toString(){
		return imageSize+imageType+colorFilter+siteFilter;
	}

	public String getImageSize() {
		return imageSize;
	}

	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public String getColorFilter() {
		return colorFilter;
	}

	public void setColorFilter(String colorFilter) {
		this.colorFilter = colorFilter;
	}

	public String getSiteFilter() {
		return siteFilter;
	}

	public void setSiteFilter(String siteFilter) {
		this.siteFilter = siteFilter;
	}

}