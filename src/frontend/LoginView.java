package frontend;

import java.util.Scanner;

import common.IAuthentication;
import frontend.controller.LoginController;

//this class represents the view when a user tries to login
public class LoginView {
	
	private IAuthentication iauth;
	
	public LoginView( IAuthentication  auth ) {
		this.iauth = auth;
	}
	
	//to select choice of role from the menu
	public int loginMenuChoice() {
		System.out.println("\n Enter from below options:");
		System.out.println("\n1 Admin \n2 Guest \n3 Employee");
		int choice;
		Scanner sc = new Scanner(System.in);
		return choice = sc.nextInt();
	}
	
	//to route the code as per the choice of action selected
	public int userLoginView() {
		int choice = loginMenuChoice();
		LoginController lc = new LoginController(iauth);
		if(choice != 4) {
			lc.login(choice);
		}
		return choice;
	}
	

}
