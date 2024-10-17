package com.bs.interfaces;

import java.util.List;

import com.bs.model.DoctorAvailability;

public interface IDoctorAvailabilityDAO {
	List<DoctorAvailability> getAvailableSlotsByDoctor(String doctorName);
}
