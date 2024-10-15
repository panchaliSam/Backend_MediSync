package com.bs.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private int appointmentId;
    private int hospitalId;
    private int doctorId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private int patientId;
    private int paymentId;
    
    // New fields for additional data
    private String hospitalName;
    private String doctorName;
    private String specialization;
    private String patientName;
    private double paymentAmount;

    // New constructor to initialize all fields
    public Appointment(int appointmentId, String hospitalName, String doctorName, String specialization,
                      LocalDate appointmentDate, LocalTime appointmentTime, String patientName, double paymentAmount) {
        this.appointmentId = appointmentId;
        this.setHospitalName(hospitalName);
        this.setDoctorName(doctorName);
        this.setSpecialization(specialization);
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.setPatientName(patientName);
        this.setPaymentAmount(paymentAmount);
    }

	// Getter and Setter methods
    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
}
