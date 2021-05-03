package backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;

import common.IAuthentication;
import common.IReservation;
import common.IRoom;

//this class represents a guest reservation at the hotel
public class ReservationImpl implements IReservation, Serializable {

	private static final long serialVersionUID = 4342792015859638823L;
	int room_number, cost, reservation_id;
	String reservation_status;
	Date checkin, checkout;

	// guest makes a reservation
	@Override
	public boolean makeReservation(IRoom room, String checkinDate, String checkoutDate, int room_no,
			IAuthentication iauth) {
		String booking_number = null;
		if (true == room.isRoomBookable(checkinDate, checkoutDate, room_no)) {
			booking_number = room.reserveRoom(checkinDate, checkoutDate, room_no);
			return createReservationRecord(iauth, checkinDate, checkoutDate, room_no, room, booking_number);
		} else {
			return false;
		}
	}

	// guest cancels a reservation
	@Override
	public void cancelReservation(IAuthentication iauth) {
		// TODO Auto-generated method stub
		String username = iauth.getSessionUsername();
		FileInputStream in = null;
		FileOutputStream out = null;

		String booking_number = getReservationDetails(iauth, "booking_number");
		String room_number = getReservationDetails(iauth, "room_number");

		System.out.println("Do you want to delete booking for Room " + room_number + "? (Y/N)");
		Scanner scan = new Scanner(System.in);
		String confirm = scan.next();

		if (confirm.equalsIgnoreCase("Y")) {
			File fl = new File("/home/amsawant/Assignment4/Assignment4/src/reservations/"+ username + ".properties");
			if (fl.isFile()) {
				if (true == fl.delete()){
					System.out.println("Reservation record deleted!");
				}
			}
			String roomfilepath = null;
			if(room_number.startsWith("1")) {
				roomfilepath = "/home/amsawant/Assignment4/Assignment4/smallrooms/Room_" + room_number + ".properties";
			} else if(room_number.startsWith("2")){
				roomfilepath = "/home/amsawant/Assignment4/Assignment4/largerooms/Room_" + room_number + ".properties";
			}
			
			
			File roomfile = new File(roomfilepath);
			try {
				in = new FileInputStream(roomfile);
				Properties prop = new Properties();
				try {
					prop.load(in);
					prop.remove(booking_number);
					String count = Integer.toString(Integer.parseInt(prop.getProperty("booking")) - 1);
					prop.setProperty("booking", count);

					out = new FileOutputStream(roomfile);
					prop.store(out, "");
					System.out.println("Room reservation removed!");
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	// returns a reservation cost
	@Override
	public void getReservationCost() {
		// TODO Auto-generated method stub
		System.out.println("Reservation Cost is:");
	}

	//creates a file in reservations package by the username of the user
	public boolean createReservationRecord(IAuthentication iauth, String checkin, String checkout, int room_no,
			IRoom room, String booking_number) {
		FileInputStream in = null;
		String username = iauth.getSessionUsername();
		int cost = 0;
		String room_file = null;
		if (room instanceof SmallerRoom) {
			room_file = "/home/amsawant/Assignment4/Assignment4/smallrooms/Room_" + room_no + ".properties";
		} else if (room instanceof LargerRoom) {
			room_file = "/home/amsawant/Assignment4/Assignment4/largerooms/Room_" + room_no + ".properties";
		}

		try {
			in = new FileInputStream(room_file);
			Properties roomprop = new Properties();
			try {
				roomprop.load(in);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy", Locale.ENGLISH);
				LocalDate queryCheckinDate = LocalDate.parse(checkin, formatter);
				LocalDate queryCheckoutDate = LocalDate.parse(checkout, formatter);
				cost = Integer.parseInt((String) roomprop.get("price"))
						* (int) ChronoUnit.DAYS.between(queryCheckinDate, queryCheckoutDate);

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String usernameFile = "/home/amsawant/Assignment4/Assignment4/src/reservations/" + username + ".properties";
		File fl = new File(usernameFile);
		FileOutputStream out = null;

		if (fl.isFile() == true) {
			System.out.println("Only one reservation per guest is allowed at a time!");
			return false;
		} else {
			Properties prop = new Properties();
			prop.put("checkin", checkin);
			prop.put("checkout", checkout);
			prop.put("room_number", Integer.toString(room_no));
			prop.put("status", "scheduled");
			prop.put("cost", Integer.toString(cost));
			prop.put("booking_number", booking_number);
			try {
				out = new FileOutputStream(usernameFile);
				try {
					prop.store(out, "");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return true;
		}
	}

	//getsReservationDetails from the user's reservation file.
	public String getReservationDetails(IAuthentication iauth, String detail) {
		String status = null;
		FileInputStream in = null;
		String username = iauth.getSessionUsername();
		String usernameFile = "/home/amsawant/Assignment4/Assignment4/src/reservations/" + username + ".properties";
		File fl = new File(usernameFile);

		if (fl.isFile() == true) {
			try {
				in = new FileInputStream(usernameFile);
				Properties prop = new Properties();
				try {
					prop.load(in);
					status = prop.getProperty(detail);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		return status;
	}

	//makes changes to the room file and the guest's reservation records
	public void modifyReservationRecord(IAuthentication iauth, String checkin, String checkout, int room_no,
			IRoom room) {
		FileInputStream in = null;
		FileOutputStream out = null;
		String reservation_status, booking = null;
		String roomfile = null, usernameFile = null;
		String username = iauth.getSessionUsername();
		usernameFile = "/home/amsawant/Assignment4/Assignment4/src/reservations/" + username + ".properties";
		if (room instanceof SmallerRoom) {
			roomfile = "/home/amsawant/Assignment4/Assignment4/smallrooms/Room_" + room_no + ".properties";
		} else if (room instanceof LargerRoom) {
			roomfile = "/home/amsawant/Assignment4/Assignment4/largerooms/Room_" + room_no + ".properties";
		}

		reservation_status = getReservationDetails(iauth, "status");
		booking = getReservationDetails(iauth, "booking_number");
		System.out.println("!===================================================================================!\n");

		if (reservation_status == null) {
			System.out.println("There is no reservation under your name!");
		} else if (reservation_status.equals("scheduled")) {
			File fl = new File(roomfile);
			File res_fl = new File(usernameFile);
			if (fl.isFile() == true) {
				Properties prop = new Properties();
				try {
					in = new FileInputStream(fl);
					try {
						prop.load(in);
						prop.setProperty(booking, checkin + "_" + checkout);
						out = new FileOutputStream(fl);
						prop.store(out, "");
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			if (res_fl.isFile() == true) {
				Properties prop = new Properties();
				try {
					in = new FileInputStream(res_fl);
					try {
						prop.load(in);
						prop.setProperty("checkin", checkin);
						prop.setProperty("checkout", checkout);
						out = new FileOutputStream(res_fl);
						prop.store(out, "");
						out.close();
						System.out.println("Reservation dates changed");
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		} else if (reservation_status.equals("started")) {
			System.out.println("Cannot modify once checked in!");
		}
	}
	
	//modifies reservation
	@Override
	public void modifyReservation(IRoom room, String checkin, String checkout, int room_number, IAuthentication iauth) {
		modifyReservationRecord(iauth, checkin, checkout, room_number, room);
	}
}
