package backend;


//This is the room type interface which has the method declaration for the Factory method pattern.
//It provides these methods to the SmallerRoom and LargerRoom classes for getting the required information from the data files.
public interface RoomTypeInterface {

	void allocate_Room(String guestUserName, String room_no,  String action);
	
	void setRoomTypeDescription(String room_no, String description);
	
	void getBedInfo(String room_no);

	void getRoomDetails(String room_no);
}
