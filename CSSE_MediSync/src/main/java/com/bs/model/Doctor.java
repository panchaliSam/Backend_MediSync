package com.bs.model;

public class Doctor {
	private int doctor_id;
	private String doctor_name;
	private String specialization;
	private String contact_no;
	private int hospital_id;
	private double doctor_charge;
	
	public Doctor() {
		super();
	}

	public Doctor(int doctor_id, String doctor_name, String specialization, String contact_no, int hospital_id, double doctor_charge) {
		super();
		this.doctor_id = doctor_id;
		this.doctor_name = doctor_name;
		this.specialization = specialization;
		this.contact_no = contact_no;
		this.hospital_id = hospital_id;
		this.doctor_charge = doctor_charge;
	}
	
	public int getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}
	public String getDoctor_name() {
		return doctor_name;
	}
	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}
	public String getSpecialization() {
		return specialization;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	public String getContact_no() {
		return contact_no;
	}
	public void setContact_no(String contact_no) {
		this.contact_no = contact_no;
	}
	public int getHospital_id() {
		return hospital_id;
	}
	public void setHospital_id(int hospital_id) {
		this.hospital_id = hospital_id;
	}
	public double getDoctor_charge() {
		return doctor_charge;
	}
	public void setDoctor_charge(double doctor_charge) {
		this.doctor_charge = doctor_charge;
	}

}
