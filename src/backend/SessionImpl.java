package backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Collectors;

import common.IAuthentication;
import common.ISession;


//this class represents the actions a user can take
public class SessionImpl implements ISession, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6809474862249295687L;

	// registers another admin
	@Override
	public void add_Admin(String username, String password, String firstname, String lastname) {

		String concat_info = username + " " + password + " " + firstname + " " + lastname + " " + "1" + "\n";
		Path path = Paths.get("/home/amsawant/Assignment4/Assignment4/UserData.txt");
		try {
			Files.write(path, concat_info.getBytes(), StandardOpenOption.APPEND);
			System.out.println("Admin added sucessfully!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// registers another guest
	@Override
	public void add_Guest(String username, String password, String firstname, String lastname) {
		String concat_info = username + " " + password + " " + firstname + " " + lastname + " " + "2" + "\n";
		Path path = Paths.get("/home/amsawant/Assignment4/Assignment4/UserData.txt");
		try {
			Files.write(path, concat_info.getBytes(), StandardOpenOption.APPEND);
			System.out.println("Guest added sucessfully!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// removes another guest
	@Override
	public void remove_Guest(String username) {

		File file = new File("/home/amsawant/Assignment4/Assignment4/UserData.txt");
		List<String> out;
		try {
			out = Files.lines(file.toPath()).filter(line -> !line.contains(username)).collect(Collectors.toList());
			Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
			System.out.println("Guest removed successfully!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	// performs checkin
	@Override
	public void guest_checkin(IAuthentication iauth) {
		FileInputStream in = null;
		FileOutputStream out = null;
		String username = iauth.getSessionUsername();
		String usernameFile = "/home/amsawant/Assignment4/Assignment4/src/reservations/" + username + ".properties";

		try {
			Properties prop = new Properties();
			File fl = new File(usernameFile);
			if (fl.isFile()) {
				in = new FileInputStream(fl);
				prop.load(in);
				String res_status = prop.getProperty("status");
				if (res_status.equalsIgnoreCase("scheduled")) {
					prop.setProperty("status", "started");
					System.out.println("Checkin successful! Enjoy the stay!");
				} else if (res_status.equalsIgnoreCase("started")) {
					System.out.println("Already checked in!");
				} else if (res_status.equalsIgnoreCase("completed")) {
					System.out.println("You have checked out for this reservation! Pay Bill");
				}
				out = new FileOutputStream(fl);
				prop.store(out, "");
			} else {
				System.out.println("No reservation found! Please make a reservation and return");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// performs checkout
	@Override
	public void guest_checkout(IAuthentication iauth) {
		String username = iauth.getSessionUsername();
		String usernameFile = "/home/amsawant/Assignment4/Assignment4/src/reservations/" + username + ".properties";
		FileInputStream in = null;
		FileOutputStream out = null;

		Properties prop = new Properties();
		File fl = new File(usernameFile);
		if (fl.isFile()) {

			try {
				in = new FileInputStream(fl);
				prop.load(in);
				String res_status = prop.getProperty("status");
				if (res_status.equalsIgnoreCase("started")) {
					prop.setProperty("status", "completed");
					out = new FileOutputStream(fl);
					prop.store(out, "");
					System.out.println("Checkout successful! Please Pay the bill");
				} else if (res_status.equalsIgnoreCase("completed")) {
					System.out.println("Already checked out! Please Pay the bill");
				} else if (res_status.equalsIgnoreCase("scheduled")) {
					System.out.println("Complete checkin first!");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					out.close();
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("Reservation not found!");
		}
	}

	// performs bill payment
	@Override
	public void payBill(IAuthentication iauth) {
		String username = iauth.getSessionUsername();
		String usernameFile = "/home/amsawant/Assignment4/Assignment4/src/reservations/" + username + ".properties";
		FileInputStream in = null;
		FileOutputStream out = null;

		Properties prop = new Properties();
		File fl = new File(usernameFile);
		if (fl.isFile()) {

			try {
				in = new FileInputStream(fl);
				prop.load(in);
				String res_status = prop.getProperty("status");
				if (res_status.equalsIgnoreCase("completed")) {

					System.out.println("Your bill amount is: $" + prop.getProperty("cost"));
					System.out.println("Confirm the bill payment and pay amount?(Y/N)");
					Scanner scan = new Scanner(System.in);
					if (scan.next().equalsIgnoreCase("Y")) {
						prop.setProperty("status", "bill_paid");
						System.out.println("Bill paid successfully!");
					}
					out = new FileOutputStream(fl);
					prop.store(out, "");
					out.close();

				} else if (res_status.equalsIgnoreCase("started")) {
					System.out.println("Perform checkout first!");
				} else if (res_status.equalsIgnoreCase("scheduled")) {
					System.out.println("Perform checkin first!");
				} else if (res_status.equalsIgnoreCase("bill_paid")) {
					System.out.println("Bill already paid!");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("Reservation not found!");
		}
	}

}
