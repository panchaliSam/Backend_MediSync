package com.bs.model;

public class Doctor {
    private int doctor_id;
    private String doctor_name;
    private String specialization;
    private String contact_no;
    private String hospital_name;
    private double doctor_charge;

    public Doctor() {
        super();
    }

    public Doctor(int doctor_id, String doctor_name, String specialization, String contact_no, String hospital_name, double doctor_charge) {
        super();
        setDoctor_id(doctor_id);
        setDoctor_name(doctor_name);
        setSpecialization(specialization);
        setContact_no(contact_no);
        setHospital_name(hospital_name);
        setDoctor_charge(doctor_charge);
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
        if (doctor_name == null || doctor_name.trim().isEmpty()) {
            throw new IllegalArgumentException("Doctor name cannot be null or empty");
        }
        this.doctor_name = doctor_name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        if (specialization == null || specialization.trim().isEmpty()) {
            throw new IllegalArgumentException("Specialization cannot be null or empty");
        }
        this.specialization = specialization;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        if (contact_no == null || !contact_no.matches("\\d{10}")) {
            throw new IllegalArgumentException("Contact number must be a 10-digit number");
        }
        this.contact_no = contact_no;
    }

    public double getDoctor_charge() {
        return doctor_charge;
    }

    public void setDoctor_charge(double doctor_charge) {
        if (doctor_charge <= 0) {
            throw new IllegalArgumentException("Doctor charge must be a positive value");
        }
        this.doctor_charge = doctor_charge;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        if (hospital_name == null || hospital_name.trim().isEmpty()) {
            throw new IllegalArgumentException("Hospital name cannot be null or empty");
        }
        this.hospital_name = hospital_name;
    }
}
