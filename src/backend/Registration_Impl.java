package backend;

import java.io.Serializable;

import common.IRegistration;

//represents the activity of registration
public class Registration_Impl implements IRegistration, Serializable{

	private static final long serialVersionUID = 7483029643314534841L;

	
	@Override
	public void register_admin() {
		// TODO Auto-generated method stub
		System.out.println("Admin has registered");
		
	}

	@Override
	public void register_guest() {
		// TODO Auto-generated method stub
		System.out.println("Guest has registered");
	}

	@Override
	public void register_employee() {
		// TODO Auto-generated method stub
		System.out.println("Employee has registered");
	}

	
}
