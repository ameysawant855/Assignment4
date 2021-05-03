package common;

import java.rmi.Remote;

//Session Interface
public interface ISession extends Remote {
	
	public void add_Admin(String username, String password, String firstname, String lastname);

	public void add_Guest(String username, String password, String firstname, String lastname);

	public void remove_Guest(String username);

	public void guest_checkin(IAuthentication iauth);
	
	public void guest_checkout(IAuthentication iauth);

	public void payBill(IAuthentication iauth);

}
