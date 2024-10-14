package com.bs.model;

public class PatientRecord {
	
	private int record_id;
    private int patient_id;
    private int hospital_id;
    private int doctor_id;
    private int appointment_id;
    private String diagnosis;
    private String medicines;
    private String lab_test_report_link;
    
	public PatientRecord() {
		super();
	}

	public PatientRecord(int record_id, int patient_id, int hospital_id, int doctor_id, int appointment_id,
			String diagnosis, String medicines, String lab_test_report_link) {
		super();
		this.record_id = record_id;
		this.patient_id = patient_id;
		this.hospital_id = hospital_id;
		this.doctor_id = doctor_id;
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

	public int getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(int patient_id) {
		this.patient_id = patient_id;
	}

	public int getHospital_id() {
		return hospital_id;
	}

	public void setHospital_id(int hospital_id) {
		this.hospital_id = hospital_id;
	}

	public int getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}

	public int getAppointment_id() {
		return appointment_id;
	}

	public void setAppointment_id(int appointment_id) {
		this.appointment_id = appointment_id;
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
}
