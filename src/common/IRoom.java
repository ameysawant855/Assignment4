package common;

import java.rmi.Remote;

import backend.Command_Quality;

//Room Interface
public interface IRoom extends Remote{

	public void updateDescription(String room_no, String description);
	
	public void allocateRoom(String guestName, String room_no, String action);
	
	public String reserveRoom(String checkin, String checkout, int room_no);

	public void getRoomDetails(String room_no);

	public void getAvailableRoomType(String checkin, String checkout) throws Exception;
	
	public boolean isRoomBookable(String checkin, String checkout, int room_no);
	
	
	
}
