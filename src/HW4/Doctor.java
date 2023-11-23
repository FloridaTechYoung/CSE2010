/*
Author: Younghoon Cho
Email: ycho2021@my.fit.edu	
Course: CSE 2010
Section:34
Description of this file: HW4 Doctor.java
*/


public class Doctor {

	String doctorName;
	Boolean isAvailable;
	String patientName;
	int treatTime = -1;
	int remainTreatTime;
	int startTime;
	
	public Doctor(String doctorName) {
		this.doctorName = doctorName;
		isAvailable = true;
	}
	
	/*
	 * must call for every minute
	 * DoctorFinishesTreatmentAndPatientDeparts 1000 Alice Bill
	 */
	public boolean treating (int currentTime) {
		if (remainTreatTime == 0) {
			
			System.out.println("DoctorFinishesTreatmentAndPatientDeparts " + currentTime + " " + doctorName + " " + patientName);
			remainTreatTime = -1;
			isAvailable = true;
			patientName = null;
			
			return true;
		} else {
			remainTreatTime--;
			
			return false;
		}
	}
	
	/*
	 * Method when an treatment is started
	 * DoctorStartsTreatingPatient 0944 Alice Bill
	 */
	public void startTreatment(int severeLevel, String patientName, int startTime) {
		this.startTime = startTime-1; // set startTime
		
		System.out.println("DoctorStartsTreatingPatient " + startTime + " " + doctorName + " " + patientName);
		treatTime = (int) Math.pow(2, 7 - severeLevel);
		remainTreatTime = treatTime;
		this.patientName = patientName;
		isAvailable = false; // start treatment
	}
	
}
