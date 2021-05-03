package frontend.controller;

import java.util.Scanner;

import common.IAuthentication;
import common.ISession;

//this controller sends requests to the ClientController for further interaction with Authentication module
public class LoginController {
	private String username, password;
	
	
	private IAuthentication iauth;
	
	public LoginController( IAuthentication  auth ) {
		this.iauth = auth;
	}

	public String enterUsername() {
		System.out.println("\nEnter Username: ");
		Scanner sc = new Scanner(System.in);
		return sc.next();
	}

	public String enterPassword() {
		System.out.println("\nEnter Password: ");
		Scanner sc = new Scanner(System.in);
		return sc.next();
	}

	public void login( int role ) {
		ClientController cc = new ClientController(iauth);
		username = enterUsername();
		password = enterPassword();
		if ( false == cc.login(username, password, role) ) {
			System.out.println("Login failed!");
		}
	}
	
	public void logout() {
		ClientController cc = new ClientController(iauth);
		cc.logout();
	}
}
