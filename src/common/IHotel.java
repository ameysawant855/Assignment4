package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

//Hotel Interface
public interface IHotel extends Remote{

	public int getAvailableRooms()throws RemoteException;
	
	public int getQuantityOfRooms() throws RemoteException;
	
	public void setAvailableRooms() throws RemoteException;
	
	public void setQuantityOfRooms() throws RemoteException;
}
