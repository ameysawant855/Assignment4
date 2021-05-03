package backend;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.IAuthentication;
import common.IHotel;
import common.IQualityLevel;
import common.IRoom;


//this is the backend server. It contains the rebinding of Remote objects
public class RemoteHotel {

	  public static void main(String args[]) {
		 try {
			  IHotel hotel = new HotelImpl(); 
			  IRoom room = new Room_Impl();
			  IRoom small_room = new SmallerRoom();
			  IRoom large_room= new LargerRoom();
			  Registration_Impl registration = new Registration_Impl(); 
			  ReservationImpl reservation = new ReservationImpl(); 
			  SessionImpl session = new SessionImpl();
			  QualityLevelImpl qualitylevel = new QualityLevelImpl();
			  AuthenticationImpl iauth = new AuthenticationImpl();
			  
			  Registry rgster = LocateRegistry.createRegistry(2021);
			  Naming.rebind("//localhost:2021/hotelregistry", hotel);
			  Naming.rebind("//localhost:2021/roomregistry", room);
			  Naming.rebind("//localhost:2021/smallroomregistry", small_room);
			  Naming.rebind("//localhost:2021/largeroomregistry", large_room);
			  Naming.rebind("//localhost:2021/registrationregistry", registration);
			  Naming.rebind("//localhost:2021/reservationregistry", reservation);
			  Naming.rebind("//localhost:2021/sessionregistry", session);
			  Naming.rebind("//localhost:2021/qualitylevelregistry", qualitylevel);
			  Naming.rebind("//localhost:2021/authenticationregistry", iauth);
	  
			  System.out.println("Server Connection Successful!");
			  System.out.println("Waiting for client..");
			  }
	  
		  catch (Exception e) { 
			  System.out.println("Server error: " + e.getMessage());
		 	  e.printStackTrace(); 
		 	  
		 	  }
	  
	  	}
	 
}
