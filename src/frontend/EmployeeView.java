package frontend;

import java.util.Scanner;

import common.IAuthentication;
import common.IRoom;

//represents the view of an Employee

public class EmployeeView {
	
	private IAuthentication iauth;
	private IRoom room_impl;
	
	
	public EmployeeView(IAuthentication auth, IRoom room_impl) {
		this.iauth = auth;
		this.room_impl = room_impl;
	}
	
	//to choose from the menu actions
	public int employeeMenuChoice() {
		System.out.println("Hi Employee.. what would you like to do?");
		System.out.println("\n 1 Update Room Allocation");
		System.out.println("\n 2 Update Room De-allocation");
		System.out.println("\n 3 Logout");

		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();
		return choice;
	}
	
	//routes the requests as per the actions selected
	  public void viewOfEmployee() { 
	  
	  int employeechoice = employeeMenuChoice(); 
	  while(employeechoice != 3) {
		  switch(employeechoice) { 
		  	case 1: 
		  		if(iauth.getSessionRole().equals("3")) {
					System.out.println("Room is being allocated by employee");
					System.out.println("Enter the guest username");
					Scanner scan = new Scanner(System.in);
					String username = scan.next();
					System.out.println("Enter the room number");
					String room_no = scan.next();
					room_impl.allocateRoom(username, room_no, "allocate");
				}  else {
					System.out.println("Module access disallowed");
				}
		  	break;
		  	
		  	case 2:
		  		if(iauth.getSessionRole().equals("3")) {
					System.out.println("Room is being deallocated by employee");
					System.out.println("Enter the guest username");
					Scanner scan = new Scanner(System.in);
					String username = scan.next();
					System.out.println("Enter the room number");
					String room_no = scan.next();
					room_impl.allocateRoom(username, room_no, "deallocate");
				}  else {
					System.out.println("Module access disallowed");
				}
		  	default: 
		  		break; 
		  	}
		  employeechoice = employeeMenuChoice(); 
	  } 
	  if (employeechoice == 3) {
		iauth.logout();
		} 
		  
	  }
	 
	}

