/**
 * Oluwatobi Babatunde James
 * 3025513
 * Assignment 1
 * **/
public class Lecturer extends Thread{
	//Declare class variables
	Classroom classroom;
	private String lecturer;
	
	
   //An overloaded constructor
	public Lecturer(String lecturer, Classroom[] classList) {
		Classroom[] classroomList = classList;
		int num=(int)(Math.random()*4);
		classroom=classroomList[num];
		this.lecturer=lecturer;
		setLecturerName(this.lecturer);
	}
	
	//The run method 

	public void run() {
		
		//Calling on the enter method from the classroom object to implement lecturer enters class
		classroom.enter(this);
		
		//sleep for some seconds 
		try {
			int t = (int)(Math.random()*7000);
			Thread.sleep(t+3000);
		} catch (InterruptedException e) {}
		
		//start lecture
		classroom.startLecture();
		
		//sleep for some seconds 
		try {
			int t = (int)(Math.random()*5000);
			Thread.sleep(t+2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			//Calling on the leave method from the classroom object to implement lecturer leave class
			classroom.leave(this.lecturer);
		}


	}
	
	
	//Set the lecturer name
	public void setLecturerName(String name) {
		classroom.setLecturerName(name);
	}
	
	


	
}
