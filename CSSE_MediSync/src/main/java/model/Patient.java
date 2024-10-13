package model;

public class Patient {

	private int patientID;
	private String patientName;
	private int patientAge;
	private String patientEmail;
	private String medicalHistory;
	private String allergies;
	private String emergencyContact;
	private String relation;
	
	
	
	public Patient() {
		super();
	}

	public Patient(int patientID, String patientName, int patientAge, String patientEmail, String medicalHistory, String allergies, String emergencyContact, String relation) {
		super();
		this.patientID = patientID;
		this.patientName = patientName;
		this.patientAge = patientAge;
		this.patientEmail = patientEmail;
		this.medicalHistory = medicalHistory;
		this.allergies = allergies;
		this.emergencyContact = emergencyContact;
		this.relation = relation;
	}
	
	public int getPatientID() {
		return patientID;
	}
	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public int getPatientAge() {
		return patientAge;
	}
	public void setPatientAge(int patientAge) {
		this.patientAge = patientAge;
	}
	public String getPatientEmail() {
		return patientEmail;
	}
	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}
	public String getMedicalHistory() {
		return medicalHistory;
	}
	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}
	public String getAllergies() {
		return allergies;
	}
	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}
	public String getEmergencyContact() {
		return emergencyContact;
	}
	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	
}
