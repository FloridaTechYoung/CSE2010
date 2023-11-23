/*
Author: Younghoon Cho
Email: ycho2021@my.fit.edu	
Course: CSE 2010
Section:34
Description of this file: HW5
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HW5 {

	public static void main(String[] args) throws FileNotFoundException{

		Scanner scan = new Scanner(new File(args[0])); // get the data file first
		
		
		// for debug usage
//		Scanner scan = new Scanner("AddEvent 101910 Breakfast\n"
//				+ "AddEvent 101913 Lunch\n"
//				+ "AddEvent 101918 Dinner\n"
//				+ "GetEvent 101914\n"
//				+ "AddEvent 101911 Data Structures\n"
//				+ "AddEvent 102007 Exercise\n"
//				+ "PrintSkipList\n"
//				+ "AddEvent 101915 Homework\n"
//				+ "CancelEvent 102007\n"
//				+ "AddEvent 102013 Relax\n"
//				+ "AddEvent 102011 Read a Book\n"
//				+ "GetEvent 101915\n"
//				+ "AddEvent 101914 Call Mom\n"
//				+ "PrintSkipList");
		// declare a new skipList
		SkipListMap map = new SkipListMap();
		
		// Time editor
		ControlTime ctime = new ControlTime();
		
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			String[] splitLine = line.split(" ");
			String command = splitLine[0];
			int time;
			int time2;
			
			switch (command) {
            case "AddEvent": // AddEvent 101910 BreakFast AddEvent(int time,String event)
            	time = Integer.parseInt(splitLine[1]);
            	String event = splitLine[2];
            	for (int i = 3; i < splitLine.length; i++) {
            		event = event + " " + splitLine[i];
            	}
            	System.out.print(line + " ");
            	map.AddEvent(time,event); // add Event
            	System.out.println();
                break;
            case "CancelEvent": // CancelEvent 101918
            	time = Integer.parseInt(splitLine[1]);
            	System.out.print(line + " ");
            	map.findAndCancelEvent(time);
            	System.out.println();
                break;
            case "GetEvent": // GetEvent 101914
            	System.out.print(line + " ");
            	time = Integer.parseInt(splitLine[1]);
            	map.findEvent(time);
            	System.out.println();
            	break;
            case "GetEventsBetweenTimes": // GetEventsBetweenTimes 101916 102012
            	System.out.print(line + " ");
            	time = Integer.parseInt(splitLine[1]);
            	time2 = Integer.parseInt(splitLine[2]);
            	map.getEventsBetweenTimes(time, time2);
            	System.out.println();
                break;
            case "NotifyEventsWithin2hours": //NotifyEventsWithin2hours 101913
            	System.out.print(line + " ");
            	time = Integer.parseInt(splitLine[1]);
            	time2 = ctime.controlTime(time + 2);
            	map.getEventsBetweenTimes(time, time2);
            	System.out.println();
            	break;
            case "GetEventsForOneDay": // GetEventsForOneDay 1019 -> 101900 101924
            	System.out.print(line + " ");
            	time = Integer.parseInt(splitLine[1])*100;
            	time2 = time + 24;
            	map.getEventsBetweenTimes(time, time2);
            	System.out.println();
            	break;
            case "GetEventsForTheRestOfTheDay": // GetEventsForTheRestOfTheDay 102006
            	System.out.print(line + " ");
            	time = Integer.parseInt(splitLine[1]);
            	time2 = (time/100)*100 + 24;
            	map.getEventsBetweenTimes(time, time2);
            	System.out.println();
            case "PrintSkipList":
            	System.out.println(line);
            	map.printSkipList();
            	break;
            default:
                System.out.println("Unknown action: " + command);
        }

		}
		
	}
	
	

}


