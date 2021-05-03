package frontend;

import java.rmi.RemoteException;
import java.util.Scanner;

import common.IAuthentication;
import common.IHotel;
import common.IReservation;
import common.IRoom;
import common.ISession;

//this class represents the view of a guest and his/her actions
public class GuestView {

	private IAuthentication iauth;
	private ISession sd;
	private IReservation res;
	private IHotel ho;
	private IRoom small_room;
	private IRoom large_room;

	public GuestView(IReservation res, ISession sd, IAuthentication auth, IHotel ho, IRoom small_room, IRoom large_room) {

		this.sd = sd;
		this.iauth = auth;
		this.res = res;
		this.ho = ho;
		this.small_room = small_room;
		this.large_room = large_room;
	}


	// choice of guest user's actions
	public int guestMenuChoice() {
		System.out.println("\nWhat would you like to do?");
		System.out.println("\n 1 Browse for Rooms");
		System.out.println("\n 2 Make reservation");
		System.out.println("\n 3 Modify reservation");
		System.out.println("\n 4 Cancel Reservation");
		System.out.println("\n 5 Check-in");
		System.out.println("\n 6 Check-out");
		System.out.println("\n 7 Pay Bill");
		System.out.println("\n 8 Log out");

		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();
		return choice;
	}
	
	//search parameters for browsing rooms
	public void searchPreferences() throws Exception {
		
		System.out.println("Which type of room are you looking for? Please enter choice");
		System.out.println("1 Small Room");
		System.out.println("2 Large Room");
		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();
		
		System.out.println("Enter Checkin Date (mm-dd-yyyy): ");
		String checkin = scan.next();
		System.out.println("Enter Checkout Date (mm-dd-yyyy): ");
		String checkout = scan.next();
		
		if( choice == 1 ) {
			small_room.getAvailableRoomType(checkin, checkout);
		} else if( choice ==2 ) {
			large_room.getAvailableRoomType(checkin, checkout);
		}
	}
	
	//provides input for reservation preferences
	public void reservationPreferences(IAuthentication iauth) throws Exception{
		boolean status = false;
		System.out.println("Which type of room to book? Please enter choice");
		System.out.println("1 Small Room");
		System.out.println("2 Large Room");
		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();
		System.out.println("Enter Checkin Date (mm-dd-yyyy): ");
		String checkin = scan.next();
		System.out.println("Enter Checkout Date (mm-dd-yyyy): ");
		String checkout = scan.next();
		System.out.println("Enter room number");
		int room_number = scan.nextInt();
		
		if(choice == 1) {
			status = res.makeReservation(small_room,checkin,checkout, room_number, iauth);
			}
		else if (choice == 2) {
			status = res.makeReservation(large_room,checkin,checkout, room_number, iauth);
		}
		if(status == true) {
			System.out.println("Room reservation successful!");
		}
	}
	
	//provides parameters for modification of the reservation
	public void modifyReservationPreferences(IAuthentication iauth) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter Checkin Date (mm-dd-yyyy): ");
		String checkin = scan.next();
		System.out.println("Enter Checkout Date (mm-dd-yyyy): ");
		String checkout = scan.next();
		System.out.println("Enter room number");
		int room_number = scan.nextInt();
		if(Integer.toString(room_number).startsWith("1")) {
			res.modifyReservation(small_room, checkin, checkout, room_number, iauth);
		} else {
			res.modifyReservation(large_room, checkin, checkout, room_number, iauth);
		}
		
	}
	
	// routes the code as per the request
	public void viewOfGuest() throws Exception {
		int choice = guestMenuChoice();
		while (choice != 8) {
			switch (choice) {
			
			case 1:
				if (iauth.getSessionRole().equals("2")) {
					searchPreferences();
				}
				break;
				
			case 2:
				if (iauth.getSessionRole().equals("2")) {
					reservationPreferences(iauth);
				}
				break;

			case 3:
				if (iauth.getSessionRole().equals("2")) {
					modifyReservationPreferences(iauth);
				}
				break;

			case 4:
				if (iauth.getSessionRole().equals("2")) {
					res.cancelReservation(iauth);
				}
				break;

			case 5:
				if (iauth.getSessionRole().equals("2")) {
					sd.guest_checkin(iauth);
				}
				break;

			case 6:
				if (iauth.getSessionRole().equals("2")) {
					sd.guest_checkout(iauth);
				}
				break;

			case 7:
				if (iauth.getSessionRole().equals("2")) {
					sd.payBill(iauth);
				}
				break;

			default:
				break;
			}
			choice = guestMenuChoice();
		}
		if (choice == 8) {
				iauth.logout();
		}
	}

}
