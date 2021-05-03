package frontend.controller;

import common.IAuthentication;

//This class represents the ClientController class in the FrontController and Authorization pattern
public class ClientController {

	private IAuthentication auth;

	public ClientController(IAuthentication auth) {
		this.auth = auth;
	}
	
	//this method is used call the RMI based object of class AuthenticationImpl for authenticating the user at the backend
	public boolean login(String username, String password, int role) {
		 return auth.isAuthenticUser(username, password, role);
		}
	
	//this method is used call the RMI based object of class AuthenticationImpl for authenticating the user at the backend
	public boolean register(String username, String password, String firstname, String lastname, int role) {
		return auth.isUniqueUser(username, password, firstname, lastname, role);
		
	}

	//logout activity
	public void logout() {
		auth.logout();
	}
}
