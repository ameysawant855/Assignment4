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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import common.IAuthentication;
import common.IQualityLevel;



////this represents a room with a view(SmallerRoom) with low quality
public class SmallerRoom extends Room_Impl implements RoomTypeInterface, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3033419525424989132L;
	int daily_rate, room_type_quantity = 10, available_room_types, room_type_price; 
	String type_desc = "This is small room";
	
	//gives available rooms for the checkin and checkout dates entered by the user
	public void getAvailableRoomType(String checkin, String checkout) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy", Locale.ENGLISH);
		LocalDate queryCheckinDate = LocalDate.parse(checkin, formatter);
		LocalDate queryCheckoutDate = LocalDate.parse(checkout, formatter);
	
		for( int i = 1; i <= 10; i++) {
				boolean Bookable = true;
				String roomname = "10"+i;
				String filename = "/home/amsawant/Assignment4/Assignment4/smallrooms/Room_"+roomname+".properties";
				FileInputStream in=null;
				try {
					in = new FileInputStream(filename);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Properties props = new Properties();
				
				try {
					props.load(in);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				this.room_number = Integer.parseInt(props.getProperty("room_number"));
				this.type_desc = props.getProperty("description");
				this.room_type_price = Integer.parseInt(props.getProperty("price"));
				
				int num_of_bookings = Integer.parseInt(props.getProperty("booking"));
				for(int booking_count = 1; booking_count <= num_of_bookings; booking_count++) {
					String bookingDetails = props.getProperty("booking"+booking_count);
					String[] bookingDetails1 = bookingDetails.split("_");
					LocalDate checkinDate = LocalDate.parse(bookingDetails1[0], formatter);
					LocalDate checkoutDate = LocalDate.parse(bookingDetails1[1], formatter);
					
					if(queryCheckinDate.isAfter(queryCheckoutDate)) {
						System.out.println("Checkout date should be after checkin date!");
					}
					else if((queryCheckinDate.isAfter(checkinDate) && queryCheckinDate.isBefore(checkoutDate)) ||
							(queryCheckoutDate.isAfter(checkinDate) && queryCheckoutDate.isBefore(checkoutDate)) ||
							(queryCheckinDate.isBefore(checkinDate) && queryCheckoutDate.isAfter(checkoutDate)) ||
							(queryCheckinDate.isBefore(checkinDate) && queryCheckoutDate.isBefore(checkoutDate) && queryCheckoutDate.isAfter(checkinDate)) ||
							(queryCheckinDate.isEqual(checkinDate) || queryCheckinDate.isEqual(checkoutDate)) ||
							(queryCheckoutDate.isEqual(checkoutDate) || queryCheckoutDate.isEqual(checkinDate)) ||
							(queryCheckinDate.isAfter(checkinDate) && queryCheckinDate.isBefore(checkoutDate) && queryCheckoutDate.isAfter(checkoutDate)) ||
							(queryCheckinDate.isAfter(checkinDate) && queryCheckinDate.isBefore(checkoutDate) && 
							queryCheckoutDate.isAfter(checkinDate) && queryCheckoutDate.isBefore(checkoutDate))){
						Bookable = false;
						break;
					}
				}
				
				if(Bookable != false) {
					getRoomDetails(roomname);
				} else {
					System.out.println("Room number: " +this.room_number + " is not available!\n");
				}
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		}
	}
	
	//gives Room Type price
	public void getRoomTypePrice() {
		System.out.println("This room is available at $" + this.room_type_price + " per night");
	}
	
	//gives roomtype description
	public void getRoomTypeDescription() {
		System.out.println("Description: " + this.type_desc);
	}
	
	//gives room number
	public void getRoomNumber() {
		System.out.println("Room number: " + this.room_number);
	}
	
	//gives all the room details
	@Override
	public void getRoomDetails(String room_no) {
		getRoomNumber();
		getRoomTypeDescription();
		getRoomTypePrice();
		getBedInfo(room_no);
		System.out.println("!===================================================================================!\n");
	}
	
	//adds booking info to the room file
	public String addBookingToFile(String path, String checkIn, String checkOut) {
    	String room_booking_number = null;
    	FileInputStream in = null;
    	FileOutputStream out = null;
		boolean Status = false;
    	try {
			Properties prop = new Properties();
			File fl = new File(path);
			in = new FileInputStream(fl);
			prop.load(in);
			int num_of_booking = Integer.parseInt(prop.getProperty("booking"));
			prop.setProperty("booking", Integer.toString(num_of_booking+1));
			prop.put("booking"+(num_of_booking+1), checkIn+"_"+checkOut);
			out = new FileOutputStream(fl);
			prop.store(out, "");
			room_booking_number = "booking"+ Integer.toString(num_of_booking+1);
    	} catch (Exception e) {
				e.printStackTrace();
			}
    	finally {
    		try {
    			out.close();
    			in.close();
    			Status = true;
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    	return room_booking_number;
	}
	
	//reserves a Room
	@Override
	public String reserveRoom(String checkin, String checkout, int room_no) {
		String filename = "/home/amsawant/Assignment4/Assignment4/smallrooms/Room_"+room_no+".properties";
		return addBookingToFile(filename, checkin, checkout);
		
	}
	
	//tells if the room is bookable for searched dates or not. Returns appropriate message according to the availability. 
	@Override
	public boolean isRoomBookable(String checkin, String checkout, int room_no) {
		
		boolean Bookable = true;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy", Locale.ENGLISH);
		LocalDate queryCheckinDate = LocalDate.parse(checkin, formatter);
		LocalDate queryCheckoutDate = LocalDate.parse(checkout, formatter);
		
		String filename = "/home/amsawant/Assignment4/Assignment4/smallrooms/Room_"+room_no+".properties";
		FileInputStream in=null;
		try {
			in = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties props = new Properties();
		try {
			props.load(in);
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
		
		int num_of_bookings = Integer.parseInt(props.getProperty("booking"));
		for(int booking_count = 1; booking_count <= num_of_bookings; booking_count++) {
			String bookingDetails = props.getProperty("booking"+booking_count);
			String[] bookingDetails1 = bookingDetails.split("_");
			LocalDate checkinDate = LocalDate.parse(bookingDetails1[0], formatter);
			LocalDate checkoutDate = LocalDate.parse(bookingDetails1[1], formatter);
			
			//check if the dates are overlapping with each other
			if(queryCheckinDate.isAfter(queryCheckoutDate)) {
				System.out.println("Checkout date should be after checkin date!");
			}
			else if((queryCheckinDate.isAfter(checkinDate) && queryCheckinDate.isBefore(checkoutDate)) ||
					(queryCheckoutDate.isAfter(checkinDate) && queryCheckoutDate.isBefore(checkoutDate)) ||
					(queryCheckinDate.isBefore(checkinDate) && queryCheckoutDate.isAfter(checkoutDate)) ||
					(queryCheckinDate.isBefore(checkinDate) && queryCheckoutDate.isBefore(checkoutDate) && queryCheckoutDate.isAfter(checkinDate)) ||
					(queryCheckinDate.isEqual(checkinDate) || queryCheckinDate.isEqual(checkoutDate)) ||
					(queryCheckoutDate.isEqual(checkoutDate) || queryCheckoutDate.isEqual(checkinDate)) ||
					(queryCheckinDate.isAfter(checkinDate) && queryCheckinDate.isBefore(checkoutDate) && queryCheckoutDate.isAfter(checkoutDate)) ||
					(queryCheckinDate.isAfter(checkinDate) && queryCheckinDate.isBefore(checkoutDate) && 
					queryCheckoutDate.isAfter(checkinDate) && queryCheckoutDate.isBefore(checkoutDate))){
				Bookable = false;
				break;
			}
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(Bookable != false) {
			return true;
		} else {
			return false;
		}
		
	}
	
	//allocates the room to a guest.
	@Override
	public void allocate_Room(String guestUserName, String room_no,  String action) {
		
		String room_number = room_no;
		String reservation_status = getReservationDetails(guestUserName, "status");
		String booking_number = getReservationDetails(guestUserName, "booking_number");
		
		FileInputStream in = null; FileOutputStream out = null;
		String room_file = "/home/amsawant/Assignment4/Assignment4/smallrooms/Room_"+room_number+".properties";
		File fl = new File(room_file);
		
		
		switch(action) {
		case "allocate":
			if(fl.isFile()) {
				if(reservation_status.equalsIgnoreCase("started")) {
					Properties prop = new Properties();
					try {
						in = new FileInputStream(room_file);
						prop.load(in);
						prop.setProperty("status", "occupied");
						out = new FileOutputStream(fl);
						prop.store(out, "");
						out.close();
						System.out.println("Room allocation successful!");
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
			} else {
				System.out.println("No such room!");
			}
			break;
	
		case "deallocate":
			if(fl.isFile()) {
				if(reservation_status.equalsIgnoreCase("bill_paid")) {
					Properties prop = new Properties();
					try {
						in = new FileInputStream(room_file);
						prop.load(in);
						prop.setProperty("status", "null");
						int num_of_booking = Integer.parseInt(prop.getProperty("booking"));
						prop.setProperty("booking", Integer.toString(num_of_booking-1));
						prop.remove(booking_number);
						out = new FileOutputStream(fl);
						prop.store(out, "");
						out.close();
						System.out.println("Room de-allocation successful!");
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
				String usernamefilepath = "/home/amsawant/Assignment4/Assignment4/src/reservations/"+guestUserName+".properties";
				File usernamefile = new File(usernamefilepath);
				
				if(usernamefile.isFile()) {
					usernamefile.delete();
					System.out.println("Reservation details deleted!");
				}
				
			} else {
				System.out.println("Payment to be paid first!");
			}
			break;
			
		default:
			break;
		}
	}
	
	//returns reservation details from the reservation file of the guest
	public String getReservationDetails(String username, String detail) {
		String status = null;
		FileInputStream in = null;
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

	//setsRoomTypeDescription
	@Override
	public void setRoomTypeDescription(String room_no, String description) {
		String roomfilepath = "/home/amsawant/Assignment4/Assignment4/smallrooms/Room_"+room_no+".properties";
		FileInputStream in = null; FileOutputStream out = null;
		
		File fl = new File(roomfilepath);
		
		if(fl.isFile()) {
			try {
				in = new FileInputStream(fl);
				Properties prop = new Properties();
				prop.load(in);
				prop.setProperty("description", description);
				out = new FileOutputStream(fl);
				prop.store(out, "");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					out.close();
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("No such room exists! Recheck");
		}
	}

	//returns the information regarding beds in the room. A part of Template method pattern.
	@Override
	public void getBedInfo(String room_no) {
		Bed bed = null;
		if(room_no.startsWith("1")) {
			bed = new TypeOneBed();
	} else if(room_no.startsWith("2")) {
			bed = new TypeTwoBed();
		}
		bed.getBedsInformation(room_no);
	}

}
