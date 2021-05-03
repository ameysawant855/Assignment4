package backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

import common.IAuthentication;


//This class performs the authentication of the user.
public class AuthenticationImpl implements IAuthentication, Serializable{

	private static final long serialVersionUID = 4736437088501937317L;
	public String sessionRole = null;
	public boolean authentication_status = false;
	public String username = null;
	
	
	//following is a series of setter getter methods
	@Override
	public void setSessionUsername(String u_name) {
		this.username = u_name;
	}
	
	public String getSessionUsername() {
		return this.username;
	}
	
	
	public String getSessionRole() {
		return sessionRole;
	}

	public void setSessionRole(String sessionRole) {
		this.sessionRole = sessionRole;
	}
	
	public boolean getAuthentication_status() {
		return authentication_status;
	}

	public void setAuthentication_status(boolean authentication_status) {
		this.authentication_status = authentication_status;
	}

	//this function checks if the user is an authentic user or not
	@Override
	public boolean isAuthenticUser(String username, String password, int role) {
		boolean status = false;
		switch(role) {
		//for authenticating Admin
		case 1: 
			System.out.println("Authenticating Admin...");
			status = searchUserDataFile(username, password, Integer.toString(role));
			if(status == true ) {
				setSessionRole(Integer.toString(role));
			}
			setAuthentication_status(status);
			
			break;
			
			//for authenticating Guest	
		case 2:
			System.out.println("Authenticating Guest...");
			status = searchUserDataFile(username, password, Integer.toString(role));
			if(status == true ) {
				setSessionRole(Integer.toString(role));
			}
			setAuthentication_status(status);
			break;
			
			//for authenticating Employee
		case 3: 
			System.out.println("Authenticating Employee...");
			status = searchUserDataFile(username, password, Integer.toString(role));
			if(status == true ) {
				setSessionRole(Integer.toString(role));
			}
			setAuthentication_status(status);
			break;
			
		default:
			status = false;
			break;
		}
		
		if(authentication_status == true) {
			setSessionUsername(username);
		}
		return status;
	}
	
	
	//this method checks whether the registering user is a new one or not
	@Override
	public boolean isUniqueUser(String username, String password, String firstname, String lastname, int role) {
		boolean status = false;
		switch(role) {
		case 1: 
			System.out.println("Registering Admin...");
			status = addUniqueUserToFile(username, password, firstname, lastname, Integer.toString(role));
			if(status == true ) {
				setSessionRole(Integer.toString(role));
			}
			setAuthentication_status(status);
			
			break;
			
		case 2:
			System.out.println("Registering Guest...");
			status = addUniqueUserToFile(username, password, firstname, lastname, Integer.toString(role));
			if(status == true ) {
				setSessionRole(Integer.toString(role));
			}
			setAuthentication_status(status);
			break;
			
		case 3: 
			System.out.println("Registering Employee...");
			status = addUniqueUserToFile(username, password, firstname, lastname, Integer.toString(role));
			if(status == true ) {
				setSessionRole(Integer.toString(role));
			}
			setAuthentication_status(status);
			break;
			
		default:
			status = false;
			break;
		}
		if(authentication_status == true) {
			setSessionUsername(username);
		}
		
		return status;
	}

	//this method searches for the credentials of the user in the UserData.txt file
	public boolean searchUserDataFile(String uname, String pwd, String userrole) {
		String data = null;
		boolean status = false;
		String username=null, password=null, firstname=null, lastname=null, role=null;
		
		
		try {
		      File myObj = new File("/home/amsawant/Assignment4/Assignment4/UserData.txt");
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		    	
		    	username=null;
		    	password=null;
		    	firstname=null;
		    	lastname=null; 
		    	role=null;
		    	
		        data = myReader.nextLine();
		        String splitdata[] = data.split(" ");
		        username = splitdata[0].toString();
		        password = splitdata[1].toString();
		        firstname = splitdata[2].toString();
		        lastname = splitdata[3].toString();
		        role = splitdata[4].toString();
		    
		        if(!(userrole.equalsIgnoreCase(role))) {
		        	if(username.equals(uname) == true && pwd.equals(password) == true ) {
		        		System.out.println("You need to register for this role!");
		        		break;
		        	}
		        }
		        else if(userrole.equalsIgnoreCase(role) == true) {
		        	if( username.equals(uname) == true && pwd.equals(password) == true ) {
		        		System.out.println("Welcome " + firstname + " " + lastname );
		        		status = true;
		        		break;
		        	} 	
		        }
		        
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("Unknown error occured");
		      e.printStackTrace();
		    }
		return status;
	}
	
	//this method adds the new user to the UserData.txt file for keeping Login Data and user credentials
	public boolean addUniqueUserToFile(String uname, String pwd, String fname, String lname, String userRole) {
		String data = null;
		boolean status = false, userFound = false;
		String username=null, password=null, firstname=null, lastname=null, role=null;
		
		
		String concat_info;
		
		try {
			File myObj = new File("/home/amsawant/Assignment4/Assignment4/UserData.txt");
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		    	
		    	username=null;
		    	password=null;
		    	firstname=null;
		    	lastname=null; 
		    	role=null;
		    	
		        data = myReader.nextLine();
		        String splitdata[] = data.split(" ");
		        username = splitdata[0].toString();
		        password = splitdata[1].toString();
		        firstname = splitdata[2].toString();
		        lastname = splitdata[3].toString();
		        role = splitdata[4].toString();
		        
		        if( username.equals(uname) && firstname.equals(fname) && lastname.equals(lname)) {
		        	System.out.println("User already exists! Create new account for this role.");
		        	myReader.close();
		        	status = false;
		        	userFound = true;
		        	break;
		        }
		      }
		        
		        	if(userFound == false) {
		        		concat_info = uname + " " + pwd + " " + fname + " " + lname + " " + userRole +"\n";
			        	Path path = Paths.get("/home/amsawant/Assignment4/Assignment4/UserData.txt");
			            Files.write(path, concat_info.getBytes(), StandardOpenOption.APPEND);  //Append mode
			        	status = true;
		        	}
		      		
		      
		    } catch (IOException e) {
		      System.out.println("Unknown error occured");
		      e.printStackTrace();
		    }
		return status;
	}

	//this function logs out the user and sets the session variables to its original state.
	@Override
	public void logout() {
		System.out.println(" User logging out!");
		setSessionRole(null);
		setAuthentication_status(false);
		setSessionUsername(null);
		
	}


}
