package common;

import java.rmi.Remote;

import backend.Room_Impl;

//Reservation Interface
public interface IReservation extends Remote {

	public boolean makeReservation(IRoom room, String checkin, String checkout, int room_number, IAuthentication iauth);
	
	public void modifyReservation(IRoom room, String checkin, String checkout, int room_number, IAuthentication iauth);
	
	public void cancelReservation(IAuthentication iauth);
	
	public void getReservationCost();

}
