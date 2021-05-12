/**
 * Oluwatobi Babatunde James
 * 3025513
 * Assignment 1
 * **/
public class Visitor extends Thread {
	//Declare class variable
	Classroom classroom;

	//overloaded constructor
	public Visitor(Classroom[] classList) {
		int num=(int)(Math.random()*4);
		Classroom[] classroomList = classList;
		classroom=classroomList[num];
	}

	public void run() {
		//Calling on the enter method from the classroom object to implement visitor enters class
		classroom.enter(this);

		//sleep for some seconds 
		try {
			int t = (int)(Math.random()*3000);
			Thread.sleep(t);
		} catch (InterruptedException e) {}

		//Calling on the sitDown method from the classroom object to implement visitor sit down class
		classroom.sitDown(this);

		//sleep for some seconds 
		try {
			int t = (int)(Math.random()*7000);
			Thread.sleep(t);
		} catch (InterruptedException e) {}
		
		//Calling on the leave method from the classroom object to implement visitor leave class
		classroom.leave(this);
	}

}
