package com.rdc.takebus.entity;


import com.rdc.takebus.model.utils.AppConstants;

import java.io.Serializable;


public class Bus implements Serializable{
	private double latitude;
	private double longtitude;
	private String strBus;
	static {
		AppConstants.Buses.add(new Bus(23.049618, 113.406408, "环线1"));//大学城南
		AppConstants.Buses.add(new Bus(23.065753, 113.391883, "环线2"));//大学城北
		AppConstants.Buses.add(new Bus(23.046982, 113.393464, "环线3"));//中环西路
		AppConstants.Buses.add(new Bus(23.063152, 113.410505, "环线1"));//中环东路
		AppConstants.Buses.add(new Bus(23.064008, 113.398620, "环线2"));//内环东路
		AppConstants.Buses.add(new Bus(23.056950, 113.393940, "环线2"));//内环西路
	}

	public Bus(double latitude, double longtitude, String strBus) {
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.strBus = strBus;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}

	public String getStrBus() {
		return strBus;
	}

	public void setStrBus(String strCar) {
		this.strBus = strCar;
	}
}
