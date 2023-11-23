/*
Author: Younghoon Cho
Email: ycho2021@my.fit.edu	
Course: CSE 2010
Section:34
Description of this file: HW4 PatientInfo.java
*/

public class PatientInfo implements Comparable<PatientInfo> {

	int severeLevel;
	int arriveTime;
	String name;
	
	/**
	 * Default constructor
	 * @param severeLevel 		The esi of the patient
	 * @param arriveTime 		the arriveTime of the patient
	 * @param name 				name of the patient
	 */
	public PatientInfo(int severeLevel, int arriveTime, String name) {
		System.out.println("PatientArrives " + arriveTime + " " + name + " " + severeLevel);
		this.severeLevel = severeLevel;
		this.arriveTime = arriveTime;
		this.name = name;
	}

	/**
	 * 
	 * compareTo method for the heap
	 */
	@Override
	public int compareTo(PatientInfo otherPatientInfo) {
		if (this.severeLevel > otherPatientInfo.severeLevel) {
			return 1;
		} else if (this.severeLevel < otherPatientInfo.severeLevel) {
			return -1;
		} else if (this.severeLevel == otherPatientInfo.severeLevel) {
			if (this.arriveTime > otherPatientInfo.arriveTime) {
				return 1;
			} else {
				return -1;
			}
		}
		return 0;
	}
}
