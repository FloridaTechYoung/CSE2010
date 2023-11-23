/*
Author: Younghoon Cho
Email: ycho2021@my.fit.edu	
Course: CSE 2010
Section:34
Description of this file: HW4
*/



import java.util.Stack;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
public class HW4 {

	public static void main(String[] args) throws FileNotFoundException{
		
		/**
		 * Key : PatientInfo (int severLevel,int arriveTime,String name)
		 * Value : NameOfPatient
		 */
		HeapPriorityQueue<PatientInfo, String> patientHeapList = new HeapPriorityQueue<PatientInfo, String>(); 
		Scanner scan = new Scanner(new File(args[0])); // get the data file first
		
		
		// a stack of docterList for the doctors who are available to work
		Stack<Doctor> availableDoctorStack = new Stack<>(); 
		// push doctors
		availableDoctorStack.push(new Doctor("Alice"));
		availableDoctorStack.push(new Doctor("Bob"));
		Stack<Doctor> doctorInTreatmentStack = new Stack<Doctor>();
		int startTime; // get the initial startTime to make inputs only when the time and the inputLine times are equal
		
		// scan line until PatientArrives bc there is a potential for the doctor to arrive first
		while (true) {
			String line = scan.nextLine();
			String words[] = line.split(" ");
			
			if (words[0].equals("DoctorArrives")) { 
				availableDoctorStack.push(new Doctor(words[1])); // push new doctor
			} else {
				startTime = Integer.parseInt(words[1]);
				int severeLevel = Integer.parseInt(words[3]);
				String patientName = words[2];
				PatientInfo firstPatient = new PatientInfo(severeLevel, startTime, patientName);
				patientHeapList.insert(firstPatient, patientName);
				
				break;
			}
		}
		
		// scan the whole line into an arrayList
		// then convert to an array to have the possibility to go back when the time is a mismatch
		ArrayList<String> scanList = new ArrayList<String>();
		while (scan.hasNextLine()) {
			scanList.add(scan.nextLine());
		}
		scan.close();
		String[] scanListArray = scanList.toArray(new String[scanList.size()]);
		
		/*
		 * PatientArrives 0934 Jill 2
		 * PatientArrives 0944 Bill 3
		 */
		
		
		int i = 0;
		/**
		 * @availableDoctorStackIterator
		 * 
		 * run the while loop even when there is no array left while doctor is on care
		 */
		while (scanListArray.length > i || !doctorInTreatmentStack.isEmpty()) {
			
			// DoctorTreatment start should occur
			// DoctorStartsTreatingPatient 0934 Bob Jill
						
			Iterator<Doctor> availableDoctorStackIterator = availableDoctorStack.iterator();
			while (availableDoctorStackIterator.hasNext() && !patientHeapList.isEmpty()) {
				int currentPatientSevereLevel = patientHeapList.min().getKey().severeLevel;
				Doctor doc = availableDoctorStack.pop();
				doc.startTreatment(currentPatientSevereLevel, patientHeapList.min().getKey().name, startTime);
				patientHeapList.removeMin();
				doctorInTreatmentStack.add(doc);
			}
						
			/**
			 * 
			 * take each line if there is an array left
			 * only adds when currentTime and input is the same (time == startTime)
			 * 
			 */
			if (i < scanListArray.length) {
				String line = scanListArray[i];
				String[] words = line.split(" ");
				String command = words[0];
				int time = Integer.parseInt(words[1]);
				
				if  (time == startTime) {
					switch (command) {
					
					/*
					 * 
					 * PatientArrives 0934 Jill 2
					 */
					case "PatientArrives" :
						startTime = Integer.parseInt(words[1]);
						int severeLevel = Integer.parseInt(words[3]);
						String patientName = words[2];
						PatientInfo firstPatient = new PatientInfo(severeLevel, startTime, patientName);
						patientHeapList.insert(firstPatient, patientName);
						patientHeapList.heapify();
						// System.out.println("PatientArrives " + words[1] + " " + words[2] + " " + words[3]);
						i++;
						break;
						
						/*
						 * 
						 * DoctorArrives 1030 Nelson
						 */
					case "DoctorArrives" :
						availableDoctorStack.push(new Doctor(words[2]));
						System.out.println("DoctorArrives " + words[1] + " " + words[2]);
						i++;
						break;
						
						/*
						 * 
						 * PatientDepartsAfterNurseTreatment 1117 Mark
						 */
					case "PatientDepartsAfterNurseTreatment" :
						patientHeapList.removeMax();
						System.out.println("PatientDepartsAfterNurseTreatment " + words[1] + " " + words[2]);
						i++;
						break;
					}
				}
			}
			
			/**
			 * 
			 * match doctors with patients when there is an available doctor and a patient at the heaplist
			 * 
			 */
			while (availableDoctorStackIterator.hasNext() && !patientHeapList.isEmpty()) {
				int currentPatientSevereLevel = patientHeapList.min().getKey().severeLevel;
				Doctor doc = availableDoctorStack.pop();
				doc.startTreatment(currentPatientSevereLevel, patientHeapList.min().getKey().name, startTime);
				patientHeapList.removeMin();
				doctorInTreatmentStack.add(doc);
			}
			
			/**
			 * for every time, run the doctorInTreatmentStack with an iterator
			 * for each doc.treating deduct one minute
			 * when number reaches 0, print out
			 */
			Iterator<Doctor> doctorInTreatmentIterator = doctorInTreatmentStack.iterator();
			while (doctorInTreatmentIterator.hasNext()) {
				Doctor doc = doctorInTreatmentIterator.next();
				boolean isFinished = doc.treating(startTime);
				if (isFinished) {
					doctorInTreatmentIterator.remove();
					availableDoctorStack.push(doc);
								
				}
			}
			
			/**
			 * time formatter
			 */
			startTime++;
			if ((startTime % 100) >= 60) {
				startTime = startTime + 100 - 60;
			}
			if (startTime >= 2400) {
				startTime = 0;
			}
			
				
		}
		
		
	}

}
