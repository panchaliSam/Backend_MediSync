create database MediSync;

use MediSync;

CREATE TABLE patient (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_name VARCHAR(100) NOT NULL,
    age INT,
    dob DATE,
    contact_no VARCHAR(15),  -- includes country code (e.g., +1, +44)
    emergency_contact_no VARCHAR(15),
    emergency_relation VARCHAR(50),
    allergy VARCHAR(255)
);

CREATE TABLE hospital (
    hospital_id INT AUTO_INCREMENT PRIMARY KEY,
    hospital_name VARCHAR(100) NOT NULL,
    hospital_charge DECIMAL(10, 2)  -- Hospital charge added
);

CREATE TABLE doctor (
    doctor_id INT AUTO_INCREMENT PRIMARY KEY,
    doctor_name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100),
    contact_no VARCHAR(15),
    hospital_id INT,
    doctor_charge DECIMAL(10, 2),    -- Doctor charge added
    FOREIGN KEY (hospital_id) REFERENCES hospital(hospital_id)
);

CREATE TABLE payment (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(10, 2) NOT NULL,
    payment_date DATE,
    payment_method VARCHAR(50)
);


CREATE TABLE appointment (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
    hospital_id INT,
    doctor_id INT,
    appointment_date DATE,
    appointment_time TIME,
    patient_id INT,
    payment_id INT,
    FOREIGN KEY (hospital_id) REFERENCES hospital(hospital_id),
    FOREIGN KEY (doctor_id) REFERENCES doctor(doctor_id),
    FOREIGN KEY (patient_id) REFERENCES patient(patient_id),
    FOREIGN KEY (payment_id) REFERENCES payment(payment_id)
);

CREATE TABLE patient_record (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    hospital_id INT,
    doctor_id INT,
    appointment_id INT,
    diagnosis TEXT,
    medicines TEXT,
    lab_test_report_link VARCHAR(255),
    FOREIGN KEY (patient_id) REFERENCES patient(patient_id),
    FOREIGN KEY (hospital_id) REFERENCES hospital(hospital_id),
    FOREIGN KEY (doctor_id) REFERENCES doctor(doctor_id),
    FOREIGN KEY (appointment_id) REFERENCES appointment(appointment_id)
);

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,  -- Store hashed passwords for security
    role ENUM('admin', 'doctor', 'patient') NOT NULL,  -- Different roles
    hospital_id INT,  -- References hospital_id if the user is a doctor
    patient_id INT,   -- References patient_id if the user is a patient
    FOREIGN KEY (hospital_id) REFERENCES hospital(hospital_id) ON DELETE CASCADE,
    FOREIGN KEY (patient_id) REFERENCES patient(patient_id) ON DELETE CASCADE
);

