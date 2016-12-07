package com.jdhui.bean.mybean;


import com.jdhui.mould.types.IMouldType;

/**
 * 更多--服务预约详情
 */
public class ServiceDetail2B implements IMouldType {

	private ServiceDetail3B hospitalBooking;
	private ServiceDetail3B geneticBooking;
	private ServiceDetail3B golfBooking;
	private ServiceDetail3B airplaneBooking;

	public ServiceDetail3B getHospitalBooking() {
		return hospitalBooking;
	}

	public void setHospitalBooking(ServiceDetail3B hospitalBooking) {
		this.hospitalBooking = hospitalBooking;
	}

	public ServiceDetail3B getGeneticBooking() {
		return geneticBooking;
	}

	public void setGeneticBooking(ServiceDetail3B geneticBooking) {
		this.geneticBooking = geneticBooking;
	}

	public ServiceDetail3B getGolfBooking() {
		return golfBooking;
	}

	public void setGolfBooking(ServiceDetail3B golfBooking) {
		this.golfBooking = golfBooking;
	}

	public ServiceDetail3B getAirplaneBooking() {
		return airplaneBooking;
	}

	public void setAirplaneBooking(ServiceDetail3B airplaneBooking) {
		this.airplaneBooking = airplaneBooking;
	}
}

	
