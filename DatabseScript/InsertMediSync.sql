-- Insert Data into Patient Table
INSERT INTO patient (patient_name, age, dob, contact_no, emergency_contact_no, emergency_relation, allergy) VALUES
('Chandana Perera', 30, '1994-05-15', '+94771234567', '+94771234568', 'Wife', 'None'),
('Nimali Senanayake', 28, '1996-03-22', '+94771234569', '+94771234570', 'Sister', 'Penicillin'),
('Kumarasiri Rajapaksa', 45, '1979-11-30', '+94771234571', '+94771234572', 'Friend', 'Dust'),
('Sharmila Fernando', 55, '1968-01-10', '+94771234573', '+94771234574', 'Daughter', 'Nuts');

-- Insert Data into Hospital Table
INSERT INTO hospital (hospital_name, hospital_charge) VALUES
('Nawaloka Hospital', 25000.00),
('Asiri Hospital', 30000.50),
('Lady Ridgeway Hospital', 22000.75),
('Durdans Hospital', 27000.00);

-- Insert Data into Doctor Table
INSERT INTO doctor (doctor_name, specialization, contact_no, hospital_id, doctor_charge) VALUES
('Dr. Anura Silva', 'Cardiology', '+94771234575', 1, 10000.00),
('Dr. Saman Kumara', 'Orthopedics', '+94771234576', 2, 12000.50),
('Dr. Thilini Jayawardena', 'Pediatrics', '+94771234577', 3, 15000.00),
('Dr. Chathura Fernando', 'General Medicine', '+94771234578', 4, 8000.00);

-- Insert Data into Payment Table
INSERT INTO payment (amount, payment_date, payment_method) VALUES
(25000.00, '2024-10-01', 'Credit Card'),
(30000.50, '2024-10-02', 'Cash'),
(22000.75, '2024-10-03', 'Debit Card'),
(27000.00, '2024-10-04', 'Mobile Payment');

-- Insert Data into Appointment Table
INSERT INTO appointment (hospital_id, doctor_id, appointment_date, appointment_time, patient_id, payment_id) VALUES
(1, 1, '2024-10-10', '10:00:00', 1, 1),
(2, 2, '2024-10-11', '11:00:00', 2, 2),
(3, 3, '2024-10-12', '12:00:00', 3, 3),
(4, 4, '2024-10-13', '09:30:00', 4, 4);

INSERT INTO doctor_availability (doctor_id, hospital_id, available_date, start_time, end_time)
VALUES 
(1, 3, '2024-10-20', '09:00:00', '12:00:00'),
(1, 3, '2024-10-21', '10:00:00', '14:00:00'),
(2, 2, '2024-10-20', '11:00:00', '16:00:00');

INSERT INTO patient_record (patient_id, hospital_id, doctor_id, appointment_id, diagnosis, medicines, lab_test_report_link) VALUES
(1, 1, 1, 1, 'Fever and Cough', 'Paracetamol, Cough Syrup', 'https://drive.google.com/file/d/1wX7FsiHV5XEoQPz4Bl9emCpVOitge1sT/view'),
(3, 2, 2, 3, 'Hypertension', 'Amlodipine, Hydrochlorothiazide', 'https://drive.google.com/file/d/1wX7FsiHV5XEoQPz4Bl9emCpVOitge1sT/view'),
(4, 1, 3, 4, 'Diabetes Type 2', 'Metformin', 'https://drive.google.com/file/d/1wX7FsiHV5XEoQPz4Bl9emCpVOitge1sT/view'),
(2, 3, 2, 29, 'Asthma', 'Inhaler', 'https://drive.google.com/file/d/1wX7FsiHV5XEoQPz4Bl9emCpVOitge1sT/view');

INSERT INTO patient_record (patient_id, hospital_id, doctor_id, appointment_id, diagnosis, medicines, lab_test_report_link) VALUES
(21, 2, 1, 33, 'Skin Rash', 'Antihistamine Cream', 'https://drive.google.com/file/d/1wX7FsiHV5XEoQPz4Bl9emCpVOitge1sT/view');

delete from patient_record where patient_id = 21;