package frontend.controller;

import java.rmi.RemoteException;

import common.IAuthentication;
import common.IHotel;
import common.IQualityLevel;
import common.IRegistration;
import common.IReservation;
import common.IRoom;
import common.ISession;
import frontend.AdminView;
import frontend.EmployeeView;
import frontend.GuestView;
import frontend.LoginView;
import frontend.RegisterView;

//this class represents the Dispatcher from the FrontController and Authorization pattern 
public class Dispatcher {

	private IHotel ho;
	private IRoom room_impl;
	private IRoom small_room;
	private IRoom large_room;
	private IReservation res;
	private IQualityLevel iquality;
	private ISession sd;
	private IAuthentication iauth;
	private IRegistration register_impl;
	
	
	public Dispatcher(IHotel hotel, IRoom room, IRoom small_room, IRoom large_room, IReservation reservation, IQualityLevel iq, ISession session, IAuthentication auth, IRegistration reg) {
		this.ho = hotel;
		this.room_impl = room;
		this.small_room = small_room;
		this.large_room = large_room;
		this.res = reservation;
		this.iquality = iq;
		this.sd = session;
		this.iauth = auth;
		this.register_impl = reg;
	}
	
	//this method executes the requests according to the type of choice made in the menu
	public void dispatchRequest(String input) throws Exception {
		AdminView av = new AdminView(room_impl, ho, iquality, sd, iauth);
		GuestView gv = new GuestView(res, sd, iauth, ho, small_room, large_room);
		EmployeeView ev = new EmployeeView(iauth, room_impl);
		switch(input) {
			
			//dispatched login requests	
			case "LOGIN": 
				LoginView login = new LoginView(iauth);
				int loginchoice = login.userLoginView();
				while (loginchoice != 4 && iauth.getAuthentication_status() == true) {
					switch (loginchoice) {
					case 1:
						av.viewOfAdmin();
						loginchoice = 4;
						break;
					case 2:
						gv.viewOfGuest();
						loginchoice = 4;
						break;
					case 3:
						ev.viewOfEmployee();
						loginchoice = 4;
						break;
					default:
						break;
					}
				}
				break;
				
			//dispatched register requests	
			case "REGISTER": 
				RegisterView register = new RegisterView(iauth);
				int registerchoice = register.userRegistrationView();
				while (registerchoice != 4 && iauth.getAuthentication_status() == true) {
					switch (registerchoice) {
					case 1:
						av.viewOfAdmin();
						registerchoice = 4;
						break;
					case 2:
						gv.viewOfGuest();
						registerchoice = 4;
						break;
					case 3:
						ev.viewOfEmployee();
						registerchoice = 4;
						break;
					default:
						break;
					}
				}
				break;
				
			default:
				break;
		}
		
		
	}
}
