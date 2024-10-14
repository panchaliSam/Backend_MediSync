package com.bs.model;

public class Hospital {
	private int hospital_id;
	private String hospital_name;
	private double hospital_charge;
	
	public Hospital() {
		super();
	}

	public Hospital(int hospital_id, String hospital_name, double hospital_charge) {
		super();
		this.hospital_id = hospital_id;
		this.hospital_name = hospital_name;
		this.hospital_charge = hospital_charge;
	}
	
	public int getHospital_id() {
		return hospital_id;
	}
	public void setHospital_id(int hospital_id) {
		this.hospital_id = hospital_id;
	}
	public String getHospital_name() {
		return hospital_name;
	}
	public void setHospital_name(String hospital_name) {
		this.hospital_name = hospital_name;
	}
	public double getHospital_charge() {
		return hospital_charge;
	}
	public void setHospital_charge(double hospital_charge) {
		this.hospital_charge = hospital_charge;
	}
}
