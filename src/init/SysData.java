package init;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import utils.E_Cities;
import utils.Constants;
import utils.E_Levels;
import utils.E_Periods;
import utils.E_Rooms;
import core.Address;
import core.Branch;
import core.Instructor;
import core.Customer;
import core.RoomRun;
import core.Receptionist;
import core.Room;
import core.Subscription;

/**
 * This SysData object ~ represents the class system
 * 
 * @author Java Course Team 2017 - Shai Gutman
 * @author University Of Haifa - Israel
 */
public class SysData {
	// -------------------------------Class
	// Members------------------------------
	private ArrayList<Instructor> instructors;
	private ArrayList<Receptionist> receptionists;
	private ArrayList<Branch> branches;
	private ArrayList<Customer> customers;
	private ArrayList<RoomRun> roomRuns;

	// -------------------------------Constructors------------------------------
	public SysData() {
		instructors = new ArrayList<Instructor>();
		receptionists = new ArrayList<Receptionist>();
		branches = new ArrayList<Branch>();
		customers = new ArrayList<Customer>();
		roomRuns = new ArrayList<RoomRun>();
	}

	// -----------------------------------------Getters--------------------------------------
	public ArrayList<Branch> getBranches() {
		return branches;
	}

	public ArrayList<Instructor> getInstructors() {
		return instructors;
	}

	public ArrayList<Receptionist> getReceptionists() {
		return receptionists;
	}

	public ArrayList<Customer> getCustomers() {
		return customers;
	}

	public ArrayList<RoomRun> getRoomRuns() {
		return roomRuns;
	}

	// -------------------------------Add && Remove
	// Methods------------------------------
	/**
	 * This method adds a new branch to our company IFF the branch doesn't
	 * already exist and the details are valid.
	 * 
	 * @param branchNumber
	 * @param branchName
	 * @param city
	 * @param street
	 * @param houseNumber
	 * @param phoneNumber
	 * @return true if the branch was added successfully, false otherwise
	 */
	public boolean addBranch(int branchNumber, String branchName, E_Cities city, String country, String street,
			int houseNumber, String[] phoneNumber) {
		if (branchName != null && branchNumber > 0 && city != null && street != null && houseNumber > 0
				&& phoneNumber != null && country != null) {
			Branch branchToAdd = new Branch(branchNumber);
			if (!branches.contains(branchToAdd)) {
				Address branchAddress = new Address(country, city, street, houseNumber, phoneNumber);
				branchToAdd = new Branch(branchNumber, branchName, branchAddress);
				branches.add(branchToAdd);
				return true;
			}
		}
		return false;
	} // ~ END OF addBranch

	/**
	 * Creates and adds a new instructor into the relevant data-structure
	 * 
	 * @param instructor
	 * @return true IF the instructor was added successfully, false otherwise
	 */
	public boolean addInstructor(Instructor instructor) {
		// Check validity first
		if (instructor != null && instructor.getEmployeeNumber() > 0 && !instructors.contains(instructor)) {
			instructors.add(instructor);
			return true;
		}
		return false;
	} // ~ END OF addCoach

	/**
	 * Creates and adds a new receptionist into the relevant data-structure
	 * 
	 * @param coach
	 * @return true IF the coach was added successfully, false otherwise
	 */
	public boolean addReceptionist(Receptionist respt) {
		// Check validity first
		if (respt != null && respt.getEmployeeNumber() > 0 && !receptionists.contains(respt)) {
			receptionists.add(respt);
			return true;
		}
		return false;
	} // ~ END OF addCoach

	/**
	 * Creates and adds a new customer into the relevant data-structure. ID
	 * number length needs to be as it's represented in Constants class and
	 * contains only digits.
	 * 
	 * @see Constants #ID_NUMBER_SIZE
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param birthDate
	 * @param password
	 * @param email
	 * @param customerAddress
	 * @return true if the customer was added successfully, false otherwise
	 */
	public boolean addCustomer(String id, String firstName, String lastName, Date birthDate, String password, URL email,
			Address customerAddress) {
		// Check validity first
		if (id != null && firstName != null && lastName != null && birthDate != null && password != null
				&& email != null && customerAddress != null) {
			if (id.length() == Constants.ID_NUMBER_SIZE) {
				for (int i = 0; i < id.length(); i++)
					if (!Character.isDigit(id.charAt(i)))
						return false;
				// Creating a new customer with his full constructor
				Customer customer = new Customer(id, firstName, lastName, birthDate, password, email, customerAddress);
				if (!customers.contains(customer)) {
					customers.add(customer); // Add this customer
					return true;
				}
			}
		}
		return false;
	}// ~ END OF addCustomer

	/**
	 * Creates a new subscription, and add it to the relevant customer and
	 * receptionist.
	 * 
	 * @param subNumber
	 * @param custId
	 * @param period
	 * @param startDate
	 * @return true if the subscription was added to the customer, false
	 *         otherwise
	 */
	public boolean addSubToCustomer(int subNumber, String custId, int receptNumber, E_Periods period, Date startDate) {
		// Check validity first
		if (subNumber > 0 && receptNumber > 0 && custId.length() == Constants.ID_NUMBER_SIZE) {
			Customer customer = new Customer(custId);
			Receptionist receptionist = new Receptionist(receptNumber);
			if (customers.contains(customer) && receptionists.contains(receptionist)) {
				// Get the customer
				for (Customer c : customers) {
					if (c != null && customer.equals(c))
						customer = customers.get(customers.indexOf(c));
				}
				// Get the receptionist
				for (Receptionist r : receptionists) {
					if (r != null && receptionist.equals(r))
						receptionist = receptionists.get(receptionists.indexOf(r));
				}
				Subscription subscription = new Subscription(subNumber, customer, receptionist, period, startDate);
				if (customer.addSubscription(subscription)) {
					if (receptionist.addSubscription(subscription)) {
						return true;
					}
					customer.removeSubscription(subscription);
				}
			}
		}
		return false;
	} // ~ END OF addSubToCustomer

	/**
	 * This method connects an instructor to a branch IF the branch and the
	 * instructor exists.
	 * 
	 * @param instructorNumber
	 * @param branchNumber
	 * @return true if the connection was added successfully, false otherwise
	 */
	public boolean connectCoachToBranch(int instructorNumber, int branchNumber) {
		// Check validity first
		if (instructorNumber > 0 && branchNumber > 0) {
			Instructor instructor = new Instructor(instructorNumber);
			Branch branch = new Branch(branchNumber);
			if (instructors.contains(instructor) && branches.contains(branch)) {
				// Get the branch
				for (Branch br : branches) {
					if (br != null && branch.equals(br))
						branch = branches.get(branches.indexOf(br));
				}
				// Get the instructor
				for (Instructor i : instructors) {
					if (i != null && instructor.equals(i))
						instructor = instructors.get(instructors.indexOf(i));
				}
				if (branch.addInstructor(instructor)) {
					instructor.setWorkBranch(branch);
					return true;
				}
			}
		}
		return false;
	}// ~ END OF connectAgentToBranch

	/**
	 * This method connects a Receptionist to a branch IF the branch and the
	 * Receptionist exists.
	 * 
	 * @param receptionistNumber
	 * @param branchNumber
	 * @return true if the connection was added successfully, otherwise false
	 */
	public boolean connectReceptionistToBranch(int receptionistNumber, int branchNumber) {
		// Check validity first
		if (receptionistNumber > 0 && branchNumber > 0) {
			Receptionist receptionist = new Receptionist(receptionistNumber);
			Branch branch = new Branch(branchNumber);
			if (receptionists.contains(receptionist) && branches.contains(branch)) {
				// Get the branch
				for (Branch br : branches) {
					if (br != null && branch.equals(br))
						branch = branches.get(branches.indexOf(br));
				}
				// Get the receptionist
				for (Receptionist r : receptionists) {
					if (r != null && receptionist.equals(r))
						receptionist = receptionists.get(receptionists.indexOf(r));
				}
				if (branch.addReceptionist(receptionist)) {
					receptionist.setWorkBranch(branch);
					return true;
				}
			}
		}
		return false;
	}// ~ END OF connectReceptionistToBranch

	/**
	 * this method adds a room to a branch IF the branch already exist
	 * 
	 * @param roomNum
	 * @param maxNumOfTrainers
	 * @param roomType
	 * @param branchNum
	 * @return true if the room was added to the branch, false other
	 */
	public boolean addRoomToBranch(int roomNum, int maxNumOfParticipants, int minNumOfParticipants, int timeLinit, E_Rooms roomType, int branchNum) {
		// Check validity first
		if (roomNum > 0 && maxNumOfParticipants > 0 && minNumOfParticipants > 0 && timeLinit > 0 && branchNum > 0) {
			Branch branch = new Branch(branchNum);
			if (branches.contains(branch)) {
				// Get the branch
				for (Branch br : branches) {
					if (br != null && branch.equals(br))
						branch = branches.get(branches.indexOf(br));
				}
				Room room = new Room(roomNum, maxNumOfParticipants, minNumOfParticipants, timeLinit, roomType, branch);
				return branch.addRoom(room);
			}
		}
		return false;
	}// ~ END OF addRoomToBranch

	/**
	 * This method add a new roomRun to SysData Hint- think of all the things
	 * that are related to a roomRun or should store the roomRun, and don't
	 * forget to rollBack if needed
	 * 
	 * @param lessonNum
	 * @param lessonName
	 * @param dateTime
	 * @param level
	 * @param coachNum
	 * @param maxStudent
	 * @param branchNum
	 * @param roomNum
	 * @return true if a lesson was added, false other
	 */
	public boolean addRoomRun(int lessonNum, Date dateTime, E_Levels level, int coachNum, int branchNum, int roomNum) {
		// Check validity first
		if (lessonNum > 0 && dateTime != null && level != null && coachNum > 0 && branchNum > 0 && roomNum > 0) {
			RoomRun roomRun = new RoomRun(lessonNum);
			Branch branch = new Branch(branchNum);
			Instructor instructor = new Instructor(coachNum);
			Room room = new Room(roomNum);
			if (branches.contains(branch) && instructors.contains(instructor) && !roomRuns.contains(roomRun)) {
				// Get the branch
				for (Branch br : branches) {
					if (br != null && branch.equals(br))
						branch = branches.get(branches.indexOf(br));
				}
				// Get the coach
				for (Instructor i : instructors) {
					if (i != null && instructor.equals(i))
						instructor = instructors.get(instructors.indexOf(i));
				}
				// Get the room
				boolean flag = false;
				for (Room r : branch.getRooms()) {
					if (r != null && room.equals(r)) {
						room = r;
						flag = true;
					}
				}
				if (flag) {
					roomRun = new RoomRun(lessonNum, dateTime, level, instructor, room);
					if (instructor.addRoomRun(roomRun)) {
						if (room.addRoomRun(roomRun)) {
							roomRuns.add(roomRun);
							return true;
						}
						instructor.removeLesson(roomRun);
					}
				}
			}
		}
		return false;
	}

	/**
	 * This method adds a customer to a specific lesson if his subscription
	 * fits, he has no other roomRuns at the time, and there is a free space in
	 * the class Hint: if needed, don't forget to rollback
	 * 
	 * @param custNum
	 * @param lessonNum
	 * @return true if the customer is registered to the lesson, false other
	 */
	public boolean addCustomerToLesson(String custNum, int lessonNum) {
		// Check validity first
		if (custNum.length() == Constants.ID_NUMBER_SIZE && lessonNum > 0) {
			Customer customer = new Customer(custNum);
			RoomRun roomRun = new RoomRun(lessonNum);
			if (customers.contains(customer) && roomRuns.contains(roomRun)) {
				// Get the branch
				for (Customer c : customers) {
					if (c != null && customer.equals(c))
						customer = customers.get(customers.indexOf(c));
				}
				// Get the coach
				for (RoomRun l : roomRuns) {
					if (l != null && roomRun.equals(l))
						roomRun = roomRuns.get(roomRuns.indexOf(l));
				}
				if (customer.addRoomRun(roomRun)) {
					if (roomRun.addParticipant(customer)) {
						return true;
					}
					customer.deleteRoomRun(roomRun);
				}
			}
		}
		return false;
	}

	/**
	 * This method change the address of existing customer IFF the customer
	 * already exist and the Address details are valid.
	 * 
	 * @param passportNumber
	 * @param country
	 * @param city
	 * @param street
	 * @param houseNumber
	 * @param phonenumbers
	 * @return true if succeeded, otherwise false.
	 */
	public boolean changeCustomerAddress(String id, String country, E_Cities city, String street, int houseNumber,
			String[] phonenumbers) {
		// Check validity first
		if (id.length() == Constants.ID_NUMBER_SIZE && country != null && city != null && street != null
				&& houseNumber > 0 && phonenumbers != null) {
			Customer customer = new Customer(id);
			if (customers.contains(customer)) {
				for (Customer c : customers) {
					if (c != null && customer.equals(c))
						customer = customers.get(customers.indexOf(c));
				}
				customer.setCustomerAddress(new Address(country, city, street, houseNumber, phonenumbers));
				return true;
			}
		}
		return false;
	} // ~ END OF changeCustomerAddress

	/**
	 * This method cancels a subscription from the system using the subNumber
	 * (Primary Key). The subscription will be canceled IFF all related objects
	 * will delete from SysData
	 * 
	 * @param subNumber
	 * @return true if subscription was canceled, false otherwise
	 */
	public boolean cancelSubscription(int subNumber) {
		// Check validity first
		if (subNumber > 0) {
			Subscription subscription = new Subscription(subNumber);
			for (Customer c : customers) {
				if (c != null) {
					for (int i = 0; i < c.getSubs().length; i++) {
						if (c.getSubs()[i] != null) {
							if (c.getSubs()[i] == subscription) {
								subscription = c.getSubs()[i];
							}
						}
					}
				}
				if (subscription == null) {
					return false;
				}
				if (c.removeSubscription(subscription)) {
					return true;
				}
			}
		}
		return false;
	}// ~ END OF cancelSubscription
		// -------------------------------Queries------------------------------
		// ===================================================
		// HW_1_Queries
		// ===================================================

	/**
	 * This method returns all roomRuns of the most active customer. Most active
	 * customer is the customer with the most ATTENDED roomRuns A lesson will be
	 * counted as attended if its date has past already
	 * 
	 * @return
	 */
	public List<RoomRun> getAllLessonsOfMostActiveCustomer() {
		int numberOfLessons = 0;
		Customer profitableCustomer = null;
		for (Customer customer : customers) {
			if (customer.getParticipatedRoomRuns().size() > numberOfLessons) {
				numberOfLessons = customer.getParticipatedRoomRuns().size();
				profitableCustomer = customer;
			}
		}
		return profitableCustomer.getParticipatedRoomRuns();
	} // ~ END OF getAllLessonsOfMostActiveCustomer
	
}// ~ END OF Class SysData
