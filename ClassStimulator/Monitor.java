/**
 * Oluwatobi Babatunde James
 * 3025513
 * Assignment 1
 * **/
public class Monitor extends Thread {
	//Declare class variable
	Classroom classroom[];

	//overloaded constructor 
	public Monitor(Classroom[] classList) {
		classroom = classList;
	}
	
	//run method 
	public void run() {
		//while loop
		while(true) {
			//a for loop that iterates and print out the class variables
			for(int i=0; i<classroom.length; i++) {
				System.out.printf("%-15s%-15s%-15s%-15s%-15s%n", classroom[i].getRoomNumber(), classroom[i].getLectureName(), classroom[i].getSession(), classroom[i].studentAttendance(),classroom[i].visitorAttendance() );
				try {
					Thread.sleep(4000);
				}
				catch (InterruptedException e) {}
			}
			
		}
		
	}
}
