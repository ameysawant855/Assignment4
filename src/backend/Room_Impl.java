package backend;


import java.io.Serializable;

import common.IQualityLevel;
import common.IRoom;

//represents a room in the hotel
public class Room_Impl implements IRoom, Serializable{
	
	private static final long serialVersionUID = -4274429047038859624L;
	boolean smoking_allowed;
	int room_number, price;
	String description, availability;
	
	//updates the room description in the hotel
	@Override
	public void updateDescription(String room_no, String description) {
		if(room_no.startsWith("1")) {
			RoomTypeFactory roomtypeFactory = new RoomTypeFactory();
			RoomTypeInterface small_room = roomtypeFactory.createRoomType("small_room");
			small_room.setRoomTypeDescription(room_no, description);
		} else if(room_no.startsWith("2")) {
			RoomTypeFactory roomtypeFactory = new RoomTypeFactory();
			RoomTypeInterface large_room = roomtypeFactory.createRoomType("large_room");
			large_room.setRoomTypeDescription(room_no, description);
		}
	}

	//an admin/employee can allocate the room using this method
	@Override
	public void allocateRoom(String guestName, String room_no, String action) {
		
		if(room_no.startsWith("1")) {
			RoomTypeFactory roomtypeFactory = new RoomTypeFactory();
			RoomTypeInterface small_room = roomtypeFactory.createRoomType("small_room");
			small_room.allocate_Room(guestName, room_no, action);
		} else if(room_no.startsWith("2")) {
			RoomTypeFactory roomtypeFactory = new RoomTypeFactory();
			RoomTypeInterface large_room = roomtypeFactory.createRoomType("large_room");
			large_room.allocate_Room(guestName, room_no, action);
		}
	}

	//gets details of the room
	@Override
	public void getRoomDetails(String room_no) {
		if(room_no.startsWith("1")) {
			RoomTypeFactory roomtypeFactory = new RoomTypeFactory();
			RoomTypeInterface small_room = roomtypeFactory.createRoomType("small_room");
			small_room.getRoomDetails(room_no);
			
		} else if(room_no.startsWith("2")) {
			RoomTypeFactory roomtypeFactory = new RoomTypeFactory();
			RoomTypeInterface large_room = roomtypeFactory.createRoomType("large_room");
			large_room.getRoomDetails(room_no);
			
		}
		
	}

	@Override
	public void getAvailableRoomType(String checkin, String checkout) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String reserveRoom(String checkin, String checkout, int room_no) {
		// TODO Auto-generated method stub
		return null;
		
	}

	@Override
	public boolean isRoomBookable(String checkin, String checkout, int room_no) {
		// TODO Auto-generated method stub
		return false;
	}


}
