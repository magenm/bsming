package com.qding.community.common.weixin.vo.recv;

public class RecvGeoMessage extends RecvMessage {

	/**
	 * 地理位置纬度
	 */
	private double locationX;
	/**
	 * 地理位置经度
	 */
	private double locationY;
	/**
	 * 地图缩放大小
	 */
	private int scale;
	/**
	 * 地理位置信息
	 */
	private String label;

	public RecvGeoMessage(RecvMessage msg, double locationX, double locationY, int scale, String label) {
		super(msg);
		this.locationX = locationX;
		this.locationY = locationY;
		this.scale = scale;
		this.label = label;
	}

	public double getLocationX() {
		return locationX;
	}

	public void setLocationX(double locationX) {
		this.locationX = locationX;
	}

	public double getLocationY() {
		return locationY;
	}

	public void setLocationY(double locationY) {
		this.locationY = locationY;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
