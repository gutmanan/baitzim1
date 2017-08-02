package core;

import java.util.Arrays;
import utils.Constants;

/**
 * Class Branch ~ represent a single branch of the company
 * 
 * @author Java Course Team 2017 - Shai Gutman
 * @author University Of Haifa - Israel
 */
public class Branch {
	// -------------------------------Class Members------------------------------
	private int branchNumber;
	private String branchName;
	private Address branchAddress;
	private Receptionist[] receptionists;
	private Instructor[] instructors;
	private Room[] rooms;

	// -------------------------------Constructors------------------------------
	public Branch(int branchNumber, String branchName, Address branchAddress) {
		this.branchNumber = branchNumber;
		this.branchName = branchName;
		this.branchAddress = branchAddress;
		this.receptionists = new Receptionist[Constants.MAX_RESEPTIONIST_FOR_BRANCH];
		this.instructors = new Instructor[Constants.MAX_INSTRUCTORS_FOR_BRANCH];
		this.rooms = new Room[Constants.MAX_NUM_OF_ROOMS];
	}

	public Branch(int branchNumber) {
		this.branchNumber = branchNumber;
		this.receptionists = new Receptionist[Constants.MAX_RESEPTIONIST_FOR_BRANCH];
		this.instructors = new Instructor[Constants.MAX_INSTRUCTORS_FOR_BRANCH];
		this.rooms = new Room[Constants.MAX_NUM_OF_ROOMS];
	}

	// -------------------------------Getters And Setters------------------------------
	public int getBranchNumber() {
		return branchNumber;
	}

	public void setBranchNumber(int branchNumber) {
		this.branchNumber = branchNumber;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Address getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(Address branchAddress) {
		this.branchAddress = branchAddress;
	}

	public Receptionist[] getReceptionists() {
		return receptionists;
	}

	public void setReceptionists(Receptionist[] receptionists) {
		this.receptionists = receptionists;
	}

	public Instructor[] getInstructors() {
		return instructors;
	}

	public void setInstructors(Instructor[] instructors) {
		this.instructors = instructors;
	}

	public Room[] getRooms() {
		return rooms;
	}

	public void setRooms(Room[] rooms) {
		this.rooms = rooms;
	}

	// -------------------------------More Methods------------------------------
	/**
	 * This method adds a new instructor to the instructors array IF the given
	 * instructor doesn't already exists in the Branch.
	 * 
	 * @param instructor
	 * @return true if the instructor was added successfully, false otherwise
	 */
	public boolean addInstructor(Instructor instructor) {
		if (instructor != null) {
			for (Instructor i : instructors)
				if (i != null && i.equals(instructor))
					return false;
			for (int i = 0; i < instructors.length; i++) {
				if (instructors[i] == null) {
					instructors[i] = instructor;
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * This method removes a given instructor form the coach array.
	 * 
	 * @param instructor
	 * @return true if the instructor was removed successfully, false otherwise
	 */
	public boolean removeInstructor(Instructor instructor) {
		for (int i = 0; i < instructors.length; i++) {
			if (instructors[i] == instructor) {
				arrangeInstructorsLeft(i);
				return true;
			}
		}
		return false;
	}

	// Helper method to arrange left
	private void arrangeInstructorsLeft(int removedInd) {
		for (; removedInd < instructors.length - 1; removedInd++)
			instructors[removedInd] = instructors[removedInd + 1];
		instructors[removedInd] = null;
	}

	/**
	 * This method adds a new receptionist to the receptionists array IF the
	 * given receptionist doesn't already exists in the Branch.
	 * 
	 * @param recep
	 * @return true if the receptionist was added successfully, false otherwise
	 */
	public boolean addReceptionist(Receptionist recep) {
		if (recep != null) {
			for (Receptionist r : receptionists)
				if (r != null && r.equals(recep))
					return false;
			for (int i = 0; i < receptionists.length; i++) {
				if (receptionists[i] == null) {
					receptionists[i] = recep;
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * This method removes a given receptionist form the receptionist array.
	 * 
	 * @param recep
	 * @return true if the receptionist was removed successfully, false
	 *         otherwise
	 */
	public boolean removeReceptionist(Receptionist recep) {
		for (int i = 0; i < receptionists.length; i++) {
			if (receptionists[i] == recep) {
				arrangeReceptionistsLeft(i);
				return true;
			}
		}
		return false;
	}

	// Helper method to arrange left
	private void arrangeReceptionistsLeft(int removedInd) {
		for (; removedInd < receptionists.length - 1; removedInd++)
			receptionists[removedInd] = receptionists[removedInd + 1];
		receptionists[removedInd] = null;
	}

	/**
	 * This method adds a new room to the rooms array IF the given room doesn't
	 * already exists in the Branch.
	 * 
	 * @param room
	 * @return true if the room was added successfully, false otherwise
	 */
	public boolean addRoom(Room room) {
		if (room != null) {
			for (Room r : rooms)
				if (r != null && r.equals(room))
					return false;
			for (int i = 0; i < rooms.length; i++) {
				if (rooms[i] == null) {
					rooms[i] = room;
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * This method removes a given room form the rooms array.
	 * 
	 * @param room
	 * @return true if the room was removed successfully, false otherwise
	 */
	public boolean removeRoom(Room room) {
		for (int i = 0; i < rooms.length; i++) {
			if (rooms[i] == room) {
				arrangeRoomsLeft(i);
				return true;
			}
		}
		return false;
	}

	// Helper method to arrange left
	private void arrangeRoomsLeft(int removedInd) {
		for (; removedInd < rooms.length - 1; removedInd++)
			rooms[removedInd] = rooms[removedInd + 1];
		rooms[removedInd] = null;
	}

	// -------------------------------hashCode equals & toString------------------------------
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + branchNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Branch other = (Branch) obj;
		if (branchNumber != other.branchNumber)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Branch [branchNumber=" + branchNumber + ", branchName=" + branchName + ", branchAddress="
				+ branchAddress + ", receptionists=" + Arrays.toString(receptionists) + ", instructors="
				+ Arrays.toString(instructors) + ", rooms=" + Arrays.toString(rooms) + "]";
	}

}
