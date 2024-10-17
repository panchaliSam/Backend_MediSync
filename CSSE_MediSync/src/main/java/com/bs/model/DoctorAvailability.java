package com.bs.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class DoctorAvailability {
    private int availabilityId;
    private int doctorId;
    private int hospitalId;
    private LocalDate availableDate; // Use LocalDate for date representation
    private LocalTime startTime; // Use LocalTime for time representation
    private LocalTime endTime; // Use LocalTime for time representation
    private boolean isAvailable;
    private String doctorName; // Optional: if you want to include doctor's name
    private String hospitalName; // Optional: if you want to include hospital's name
    private int allocatedPatients; // Count of allocated patients
        
	public DoctorAvailability() {
		super();
	}

	public DoctorAvailability(int availabilityId, int doctorId, int hospitalId, LocalDate availableDate,
			LocalTime startTime, LocalTime endTime, boolean isAvailable, String doctorName, String hospitalName,
			int allocatedPatients) {
		super();
		this.availabilityId = availabilityId;
		this.doctorId = doctorId;
		this.hospitalId = hospitalId;
		this.availableDate = availableDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isAvailable = isAvailable;
		this.doctorName = doctorName;
		this.hospitalName = hospitalName;
		this.allocatedPatients = allocatedPatients;
	}
	
	public DoctorAvailability(LocalDate availableDate, LocalTime startTime, LocalTime endTime, boolean isAvailable,
			String doctorName, String hospitalName, int allocatedPatients) {
		super();
		this.availableDate = availableDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isAvailable = isAvailable;
		this.doctorName = doctorName;
		this.hospitalName = hospitalName;
		this.allocatedPatients = allocatedPatients;
	}



	public int getAvailabilityId() {
		return availabilityId;
	}

	public void setAvailabilityId(int availabilityId) {
		this.availabilityId = availabilityId;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public int getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(int hospitalId) {
		this.hospitalId = hospitalId;
	}

	public LocalDate getAvailableDate() {
		return availableDate;
	}

	public void setAvailableDate(LocalDate availableDate) {
		this.availableDate = availableDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public int getAllocatedPatients() {
		return allocatedPatients;
	}

	public void setAllocatedPatients(int allocatedPatients) {
		this.allocatedPatients = allocatedPatients;
	}
    
    

}
