package backend;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.IHotel;

//this class represents the Hotel properties
public class HotelImpl extends UnicastRemoteObject implements IHotel{
	
	private int total_smallrooms, total_largerooms, available_smallrooms, available_largerooms;
	
	
	protected HotelImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;

	int room_quantity = 200, available_rooms = 50;
	
	
	//this method provides the total number of available rooms
	@Override
	public int getAvailableRooms(){
		int available_rooms = this.available_rooms;
		System.out.println("There are " + available_rooms + " rooms available!");
		return available_rooms;
		
	}

	//this method provides the total number rooms
	@Override
	public int getQuantityOfRooms(){
		int room_quantity = this.room_quantity;
		System.out.println("There are a total of " + room_quantity + " rooms in the hotel");
		return room_quantity;
	}

	//this method sets the total number of available rooms
	@Override
	public void setAvailableRooms() {
		// TODO Auto-generated method stub
			System.out.println("New number of available rooms are set");		
		
	}

	//this method sets the total number of rooms
	@Override
	public void setQuantityOfRooms() {
		// TODO Auto-generated method stub
		System.out.println("New quantity of rooms is:");
	}

}
