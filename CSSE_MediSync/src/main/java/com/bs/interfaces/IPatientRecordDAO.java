package com.bs.interfaces;

import java.util.List;

import com.bs.model.PatientRecord;

public interface IPatientRecordDAO {
	
	int insertPatientRecord(PatientRecord patientRecord);
	
    List<PatientRecord> selectAllPatientRecords();
    
    PatientRecord selectPatientRecord(int record_id);
    
    void updatePatientRecord(PatientRecord patientRecord);
    
    void deletePatientRecord(int record_id);
}
