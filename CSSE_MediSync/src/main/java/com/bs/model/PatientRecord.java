package com.bs.model;

public class PatientRecord {
	
	private int record_id;
	private String patient_name; 
    private String hospital_name; 
    private String doctor_name;
	private int appointment_id;
    private String diagnosis;
    private String medicines;
    private String lab_test_report_link;
    
    public PatientRecord() {
		super();
	}

    public PatientRecord(int record_id, String patient_name, String hospital_name, String doctor_name, int appointment_id, String diagnosis, String medicines, String lab_test_report_link) {
	this.record_id = record_id;
	this.patient_name = patient_name;
	this.hospital_name = hospital_name;
	this.doctor_name = doctor_name;
	this.appointment_id = appointment_id;
	this.diagnosis = diagnosis;
	this.medicines = medicines;
	this.lab_test_report_link = lab_test_report_link;
	}

	public int getRecord_id() {
		return record_id;
	}

	public void setRecord_id(int record_id) {
		this.record_id = record_id;
	}

	public int getAppointment_id() {
		return appointment_id;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getMedicines() {
		return medicines;
	}

	public void setMedicines(String medicines) {
		this.medicines = medicines;
	}

	public String getLab_test_report_link() {
		return lab_test_report_link;
	}

	public void setLab_test_report_link(String lab_test_report_link) {
		this.lab_test_report_link = lab_test_report_link;
	}

	public String getPatient_name() {
		return patient_name;
	}

	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}

	public String getHospital_name() {
		return hospital_name;
	}

	public void setHospital_name(String hospital_name) {
		this.hospital_name = hospital_name;
	}

	public String getDoctor_name() {
		return doctor_name;
	}

	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}

	public void setAppointment_id(int appointment_id) {
		this.appointment_id = appointment_id;
	}
	
}
