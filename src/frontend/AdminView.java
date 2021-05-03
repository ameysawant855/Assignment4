package frontend;

import java.rmi.RemoteException;
import java.util.Scanner;

import backend.Command_Quality;
import backend.QualityLevelImpl;
import backend.QualityLevelSetCommand;
import backend.Room_Impl;
import backend.SimpleCommandControl;
import common.IAuthentication;
import common.IHotel;
import common.IQualityLevel;
import common.IRoom;
import common.ISession;

//this is the view of an Admin and his/her actions
public class AdminView {
	private IRoom room_impl;
	private IHotel ho;
	private IQualityLevel iq;
	private ISession sd;
	private IAuthentication iauth;
	
	public AdminView(IRoom room_impl, IHotel ho, IQualityLevel iq, ISession sd, IAuthentication auth) {
		
		this.room_impl = room_impl;
		this.ho = ho;
		this.iq = iq;
		this.sd = sd;
		this.iauth = auth;
	}

	//to choose from the given menu actions
	public int adminMenuChoice() {

		System.out.println("\nWhat would you like to do?");
		System.out.println("\n 1 Update Room Allocation");
		System.out.println("\n 2 Update Room Description");
		System.out.println("\n 3 Update Room Deallocation");
		System.out.println("\n 4 Set Daily Rate for quality level");
		System.out.println("\n 5 Add Administrator");
		System.out.println("\n 6 Add Guest");
		System.out.println("\n 7 Remove Guest");
		System.out.println("\n 8 Logout");

		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();
		return choice;
	}

	//routes the requests according to the choice an admin makes
	public void viewOfAdmin() throws RemoteException {
		int choice = adminMenuChoice();

		while (choice != 8) {
			switch (choice) {

			case 1:
				if(iauth.getSessionRole().equals("1")) {
					System.out.println("Room is being allocated by admin");
					System.out.println("Enter the guest username");
					Scanner scan = new Scanner(System.in);
					String username = scan.next();
					System.out.println("Enter the room number");
					String room_no = scan.next();
					room_impl.allocateRoom(username, room_no, "allocate");
				} else {
					System.out.println("Module access disallowed");
				}
				
				break;

			case 2:
				if(iauth.getSessionRole().equals("1")) {
					System.out.println("Enter the room number");
					Scanner scan = new Scanner(System.in);
					String room_no = scan.next();
					System.out.println("Enter the description to be updated");
					scan.nextLine();
				    String description = scan.nextLine();
					room_impl.updateDescription(room_no, description);
				} else {
					System.out.println("Module access disallowed");
				}
				break;

			case 3:
				if(iauth.getSessionRole().equals("1")) {
					System.out.println("Room is being deallocated by admin");
					System.out.println("Enter the guest username");
					Scanner scan = new Scanner(System.in);
					String username = scan.next();
					System.out.println("Enter the room number");
					String room_no = scan.next();
					room_impl.allocateRoom(username, room_no, "deallocate");
				} else {
					System.out.println("Module access disallowed");
				}
				break;
				
			case 4:
				if(iauth.getSessionRole().equals("1")) {
					SimpleCommandControl scc = new SimpleCommandControl();
					scc.setQualityLevelCommand(new QualityLevelSetCommand((QualityLevelImpl) iq));
					scc.giveCommand();
				} else {
					System.out.println("Module access disallowed");
				}
				break;

			case 5:
				if(iauth.getSessionRole().equals("1")) {
					System.out.println("Enter admin details below:");
					Scanner scan = new Scanner(System.in);
					System.out.println("Enter username");
					String username = scan.next();
					System.out.println("Assign password");
					String password = scan.next();
					System.out.println("Enter first name");
					String firstname = scan.next();
					System.out.println("Enter last name");
					String lastname = scan.next();
					
					sd.add_Admin(username, password, firstname, lastname);
				} else {
					System.out.println("Module access disallowed");
				}
				break;

			case 6:
				
				if(iauth.getSessionRole().equals("1")) {
					
					System.out.println("Enter guest details below:");
					Scanner scan = new Scanner(System.in);
					System.out.println("Enter username");
					String username = scan.next();
					System.out.println("Assign password");
					String password = scan.next();
					System.out.println("Enter first name");
					String firstname = scan.next();
					System.out.println("Enter last name");
					String lastname = scan.next();
					sd.add_Guest(username, password, firstname, lastname);
				} else {
					System.out.println("Module access disallowed");
				}
				break;

			case 7:
				
				if(iauth.getSessionRole().equals("1")) {
					System.out.println("Enter username");
					Scanner scan = new Scanner(System.in);
					String username = scan.next();
					sd.remove_Guest(username);
				} else {
					System.out.println("Module access disallowed");
				}
				break;
			}
			choice = adminMenuChoice();
		}

		if (choice == 8) {
			iauth.logout();
		}
	}

}
