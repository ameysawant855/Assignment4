package frontend;

import java.util.Scanner;

import common.IAuthentication;
import frontend.controller.LoginController;
import frontend.controller.RegisterController;

//this class represents the view of a user while performing registration
public class RegisterView {

	private IAuthentication iauth;

	public RegisterView( IAuthentication  auth ) {
		this.iauth = auth;
	}

	//for role selection
	public int registerMenuChoice() {
		System.out.println("\n Enter from below options:");
		System.out.println("\n1 Admin \n2 Guest \n3 Employee");
		int choice;
		Scanner sc = new Scanner(System.in);
		choice = sc.nextInt();
		return choice;
	}

	//for routing code as per the selection from the menu
	public int userRegistrationView() {
		int choice = registerMenuChoice();
		RegisterController rc = new RegisterController(iauth);
		if(choice != 4) {
			rc.register(choice);
		}
		return choice;
	} 
}
