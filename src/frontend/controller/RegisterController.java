package frontend.controller;

import java.util.Scanner;

import common.IAuthentication;
import common.ISession;

//this controller sends requests to the ClientController for further interaction with Authentication module
public class RegisterController {
	private String username, password, firstname, lastname;
	
	
	private IAuthentication iauth;
	
	public RegisterController( IAuthentication  auth ) {
		this.iauth = auth;
	}

	public String enterUsername() {
		System.out.println("\nSet Username: ");
		Scanner sc = new Scanner(System.in);
		return sc.next();
	}

	public String enterPassword() {
		System.out.println("\nSet Password: ");
		Scanner sc = new Scanner(System.in);
		return sc.next();
	}
	
	public String enterFirstName() {
		System.out.println("\nEnter First name: ");
		Scanner sc = new Scanner(System.in);
		return sc.next();
	}
	
	public String enterLastName() {
		System.out.println("\nEnter Last name: ");
		Scanner sc = new Scanner(System.in);
		return sc.next();
	}

	public void register( int role ) {
		ClientController cc = new ClientController(iauth);
		username = enterUsername();
		password = enterPassword();
		firstname = enterFirstName();
		lastname = enterLastName();
		if ( true == cc.register(username, password, firstname, lastname, role)) {
			System.out.println("Registration Complete! Welcome " + firstname);
		} else {
			System.out.println("Registration Failed!");
		}
	}
	
	public void logout() {
		ClientController cc = new ClientController(iauth);
		cc.logout();
	}
}
