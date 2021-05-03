package common;

import java.rmi.Remote;

//Authentication Interface
public interface IAuthentication extends Remote {

	public boolean isAuthenticUser(String username, String password, int role);
	
	public boolean isUniqueUser(String username, String password, String firstname, String lastname, int role);

	public void logout();
	
	public String getSessionRole();
	
	public void setSessionRole(String sessionRole);
	
	public boolean getAuthentication_status();
	
	public void setAuthentication_status(boolean authentication_status);

	public void setSessionUsername(String u_name);
	
	public String getSessionUsername();
}
