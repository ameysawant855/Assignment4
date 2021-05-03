package frontend.controller;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

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

//this method represents the FrontController in the FrontController and Authorization pattern. Its the first point of user interactions
public class FrontController {

	public static IHotel ho;
	public static IRoom small_room;
	public static IRoom large_room;
	public static ISession sd;
	public static IRegistration register_impl;
	public static IRoom room_impl;
	public static IReservation res;
	public static IQualityLevel iquality;
	public static IAuthentication iauth;

	//default user menu
	public static int defaultUserMenuChoice() {
		System.out.println("\n");
		System.out.println("Welcome to Online Hotel");
		System.out.println("Hi.. what would you like to do?");
		System.out.println("\n 1 Login");
		System.out.println("\n 2 Register");
		System.out.println("\n 3 Exit");

		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();

		return choice;
	}

	public static void main(String args[]) throws Exception {

		ho = (IHotel) Naming.lookup("//localhost:2021/hotelregistry");
		small_room = (IRoom) Naming.lookup("//localhost:2021/smallroomregistry");
		room_impl = (IRoom) Naming.lookup("//localhost:2021/roomregistry");
		large_room = (IRoom) Naming.lookup("//localhost:2021/largeroomregistry");
		sd = (ISession) Naming.lookup("//localhost:2021/sessionregistry");
		register_impl = (IRegistration) Naming.lookup("//localhost:2021/registrationregistry");
		res = (IReservation) Naming.lookup("//localhost:2021/reservationregistry");
		iquality = (IQualityLevel) Naming.lookup("//localhost:2021/qualitylevelregistry");
		iauth = (IAuthentication) Naming.lookup("//localhost:2021/authenticationregistry");

		int choice = defaultUserMenuChoice();

		
		Dispatcher dispatcher = new Dispatcher(ho, room_impl, small_room, large_room, res, iquality, sd, iauth, register_impl);
		while (choice != 3) {
			//dispatch requests as per user choice of menu actions
			switch (choice) {

			case 1:
				dispatcher.dispatchRequest("LOGIN");
				break;

			case 2:
				dispatcher.dispatchRequest("REGISTER");
				break;

			default:
				break;
			}
			choice = defaultUserMenuChoice();
		}
		System.out.println("Thank you! See you again");
	}
}
