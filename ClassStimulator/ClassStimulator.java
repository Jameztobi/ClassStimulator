
/**
 * Oluwatobi Babatunde James
 * 3025513
 * Assignment 1
 * **/
public class ClassStimulator {

	public static void main(String[] args) {

		System.out.println("==================================================================================");
		System.out.printf("%-15s%-15s%-15s%-15s%-15s%n","Classroom", "Lecturer", "InSession", "Students","Visitor" );
		System.out.println("==================================================================================");
		
		//An array of classes
		Classroom[] classroomList = {
				new Classroom("W201", 60),
				new Classroom("W202", 60),
				new Classroom("W101", 20),
				new Classroom("W102", 30)
		};

        //An array of visitor threads
		Visitor[] visitorList = new Visitor[15];
		for (int i = 0; i < visitorList.length; i++) {
			visitorList[i] = new Visitor(classroomList);
			//start the threads
			visitorList[i].start();
		}

		 //An array of student threads
		Student[] studentList = new Student[200];
		for (int i = 0; i < studentList.length; i++) {
			studentList[i] = new Student(classroomList);
			//start the threads
			studentList[i].start();
		}


		//An array of lecturers
		Lecturer[] lecturerList = {
				new Lecturer("Osama", classroomList ),
				new Lecturer("Barry", classroomList ),
				new Lecturer("Faheem", classroomList),
				new Lecturer("Alex", classroomList),
				new Lecturer("Aqeel", classroomList),
				new Lecturer("Waseem", classroomList)
		};

        //Create and start the array of lecturer threads
		for (Lecturer lecturer: lecturerList)
			lecturer.start();

		//Create and start an array of monitor threads
		Monitor monitor = new Monitor(classroomList);
		monitor.start();
	}
}