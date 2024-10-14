package com.bs.model;

public class Patient {

	private int patient_id;
	private String patient_name;
	private int age;
	private String dob;
	private String contact_no;
	private String emergency_contact_no;
	private String emergency_relation;
	private String allergy;
	
	
	
	public Patient() {
		super();
	}

	public Patient(int patient_id, String patient_name, int age, String dob, String contact_no, String emergency_contact_no, String emergency_relation, String allergy) {
		super();
		this.patient_id = patient_id;
		this.patient_name = patient_name;
		this.age = age;
		this.dob = dob;
		this.contact_no = contact_no;
		this.emergency_contact_no = emergency_contact_no;
		this.emergency_relation = emergency_relation;
		this.allergy = allergy;
	}

	public int getPatient_id() {
		return patient_id;
	}



	public void setPatient_id(int patient_id) {
		this.patient_id = patient_id;
	}



	public String getPatient_name() {
		return patient_name;
	}



	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}



	public int getAge() {
		return age;
	}



	public void setAge(int age) {
		this.age = age;
	}



	public String getDob() {
		return dob;
	}



	public void setDob(String dob) {
		this.dob = dob;
	}



	public String getContact_no() {
		return contact_no;
	}



	public void setContact_no(String contact_no) {
		this.contact_no = contact_no;
	}



	public String getEmergency_contact_no() {
		return emergency_contact_no;
	}



	public void setEmergency_contact_no(String emergency_contact_no) {
		this.emergency_contact_no = emergency_contact_no;
	}



	public String getEmergency_relation() {
		return emergency_relation;
	}



	public void setEmergency_relation(String emergency_relation) {
		this.emergency_relation = emergency_relation;
	}



	public String getAllergy() {
		return allergy;
	}



	public void setAllergy(String allergy) {
		this.allergy = allergy;
	}
	
	
}