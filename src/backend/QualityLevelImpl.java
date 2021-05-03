package backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import java.util.Scanner;

import common.IQualityLevel;
import common.IRoom;

//this class represents the quality level associated with each type of room
public class QualityLevelImpl implements IQualityLevel, Serializable {

	private static final long serialVersionUID = -6588487750038390516L;
	private String dailyRate;

	

	// sets daily rate according to the quality level
	@Override
	public void set() {
		FileInputStream in = null; FileOutputStream out = null;
		System.out.println("Enter the quality level");
		Scanner scan = new Scanner(System.in);
		String quality = scan.next();
		System.out.println("Enter the daily rate");
		String rate = scan.next();
		for(int i=1; i <=10;i++) {
			String roomfilepath = null;
			if(quality.equals("1")) {
				roomfilepath = "/home/amsawant/Assignment4/Assignment4/smallrooms/Room_10"+i+".properties";
			} else if(quality.equals("2")){
				roomfilepath = "/home/amsawant/Assignment4/Assignment4/largerooms/Room_20"+i+".properties";
			}
			File fl = new File(roomfilepath);
			if(fl.isFile()) {
				Properties prop = new Properties();
				try {
					in = new FileInputStream(fl);
					prop.load(in);
					prop.setProperty("price", rate);
					out = new FileOutputStream(fl);
					prop.store(out, "");
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
		}
		System.out.println("Room rates with quality level " +quality+ " have been successfully updated!");
		
	}
}
