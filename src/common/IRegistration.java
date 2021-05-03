package common;

import java.rmi.Remote;

//Registration Interface
public interface IRegistration extends Remote{

	public void register_admin();
	
	public void register_guest();
	
	public void register_employee();
}
