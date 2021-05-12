/**
 * Oluwatobi Babatunde James
 * 3025513
 * Assignment 1
 * **/
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Classroom {
	//Declare class variables
	private String roomNumber;
	private String lecturerName;
	private int roomCapacity;
	private boolean lectureInSession=false;
	//Semophore to control enter and exit into the class
	private final Semaphore controlClass;
	private final Semaphore controlLecturer=new Semaphore(1);
	private final Semaphore controlVistor=new Semaphore(30, true);

	private Boolean lecturerPresent=false;
	private Lock lock = new ReentrantLock();

	private AtomicInteger studentInClass=new AtomicInteger(0);
	private AtomicInteger studentSitInClass=new AtomicInteger(0);
	private AtomicInteger visitorInClass=new AtomicInteger(0);
	private AtomicInteger visitorSitInClass=new AtomicInteger(0);


	public Classroom(String roomNumber, int roomCapacity) {
		this.roomNumber = roomNumber;
		this.roomCapacity = roomCapacity;
		//System.out.println(this.roomCapacity);
		this.controlClass=new Semaphore(this.roomCapacity);
	}


	//Student try to enter into the class room
	public void enter(Student student) {

		if(student instanceof Student) {
			try {		
				controlClass.acquire();

				synchronized(lock) {
					while(getSession()) {
						lock.wait();	
					}

				}
				studentInClass.incrementAndGet();
			}
			catch(InterruptedException e){}

		}
	}
	
	//Visitor try to enter into the class room
	public void enter(Visitor visitor) {
		if(visitor instanceof Visitor) {
			try {
				controlVistor.acquire();
				controlClass.acquire();
				synchronized(lock) {
					while(getSession()) {
						lock.wait();
					}
				}
				visitorInClass.incrementAndGet();
			}
			catch(InterruptedException e){}
		}
	}
    
	//Lecturer try to enter into the class room
	public void enter(Lecturer lecturer) {
		if(lecturer instanceof Lecturer) {
			try {
				controlLecturer.acquire();
				setLecturer(true);
				inSession(true);
			}
			catch(InterruptedException e){}


		}
	}


    //Student sit down to receive class
	public void sitDown(Student student){
		if(student instanceof Student) {
			studentSitInClass.incrementAndGet();	
		}

	}
	
	//Visitor sit down to receive class
	public void sitDown(Visitor visitor){
		if(visitor instanceof Visitor) {
			visitorSitInClass.incrementAndGet();
		}
	}

	//This method returns the student class attendance
	public AtomicInteger studentAttendance() {
		return studentInClass;
	}

	//This method returns the visitor class attendance
	public AtomicInteger visitorAttendance() {
		return visitorInClass;
	}
	
	//This method implements the start lecture
	public void startLecture() {
		if(studentInClass.intValue() >0 && studentInClass.intValue()==studentSitInClass.intValue()) {
			inSession(true);
		}

	}
    
	//This methods implements the lecturer leave class and permit the students and visitors to leave the class
	public void leave(Lecturer lecturer) {
		if (lecturer instanceof Lecturer) {
			setLecturer(false);
			inSession(false);
			synchronized(lock) {
				lock.notifyAll();
			}	
			controlLecturer.release();
		}

	}
 
	//This method implements the Student leaving the class after the lecturer has left the class
	public void leave(Student student) {
		if (student instanceof Student) {
			synchronized(lock){
				if(getSession()) {
					try {
						lock.wait();
					}
					catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		studentInClass.decrementAndGet();
		studentSitInClass.decrementAndGet();
		controlClass.release();


	}
   
	//This method implements the Visitor leaving the class after the lecturer has left the class
	public void leave(Object visitor) {
		if(visitor instanceof Object) {
			synchronized(lock) {
				if(getSession()) {
					try {
						lock.wait();
					}
					catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		visitorInClass.decrementAndGet();
		visitorSitInClass.decrementAndGet();
		controlClass.release();
		controlVistor.release();
		

	}

    //This method set the state of the lecturer either true or false
	public void setLecturer(boolean state) {this.lecturerPresent=state;}
	
	//This method returns the state of the lecturer either true or false
	public Boolean getLecturer() {return this.lecturerPresent;}
	//This method set the name of the lecturer 
	public void setLecturerName(String name) {lecturerName=name;}
	//This method returns the name of the lecturer in the class
	public String getLectureName() {return lecturerName;}
	//This method return the room number of the class
	public String getRoomNumber() {return this.roomNumber;}
	//This method set the session to true or false if the lecture has started
	public void inSession(boolean value) {lectureInSession=value;}
	//This method returns the session state
	public Boolean getSession() { return lectureInSession;}



}
