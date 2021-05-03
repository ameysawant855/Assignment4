package backend;

//Bed Interface. This is the abstract class required in Template Pattern
public abstract class Bed {
	 public abstract String getTypeOfBeds(String room_no);
	 
	 public abstract String getNumberOfBeds(String room_no);
	 
	 //common functionality required by both types of bed
	 public void getBedsInformation(String room_no) {
		 System.out.println("Type of bed: " + getTypeOfBeds(room_no));
		 System.out.println("Number of beds: " + getNumberOfBeds(room_no));
	 }
}
