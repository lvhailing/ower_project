package com.jdhui.bean;

import java.io.File;

public class UpLoadPhotoBean {
	public UpLoadPhotoBean(File photo) {
		this.photo = photo;
	}
	private File photo;

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}
}
