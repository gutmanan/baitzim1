package Model;

import java.util.ArrayList;
import java.util.Date;

import utils.E_Rooms;

/**
 * Class Instructor ~ represent a single Instructor of the company, inheritor of
 * Employee
 * 
 * @author Java Course Team 2017 - Shai Gutman
 * @author University Of Haifa - Israel
 */
public class Instructor extends Employee {
	// -------------------------------Class
	// Members------------------------------
	private E_Rooms[] Types;
	private int Level;
	private ArrayList<RoomRun> roomRuns;

	// -------------------------------Constructors------------------------------
	public Instructor(int empNum, String firstName, String lastName, Date birthdate, Date startWorkingDate,
			String password, int level, Branch workBranch, Address address, E_Rooms[] types) {
		super(empNum, firstName, lastName, birthdate, startWorkingDate, password, address);
		this.Types = new E_Rooms[types.length];
		this.Types = types;
		setLevel(level);
		this.roomRuns = new ArrayList<RoomRun>();
		setWorkBranch(workBranch);
	}

	public Instructor(int empNum, String firstName, String lastName, Date birthdate, Date startWorkingDate,
			String password, int level, Address address, E_Rooms[] types) {
		super(empNum, firstName, lastName, birthdate, startWorkingDate, password, address);
		this.Types = new E_Rooms[types.length];
		this.Types = types;
		this.Level = level;
		this.roomRuns = new ArrayList<RoomRun>();
	}

	public Instructor(int empNum, String firstName, String lastName, Date birthdate, Date startWorkingDate,
			String password, int level, Address address) {
		super(empNum, firstName, lastName, birthdate, startWorkingDate, password, address);
		this.Level = level;
		this.roomRuns = new ArrayList<RoomRun>();
	}

	public Instructor(int empNum) {
		super(empNum);
	}

	// -------------------------------Getters And
	// Setters------------------------------
	public ArrayList<RoomRun> getRoomRuns() {
		return roomRuns;
	}

	public void setRoomRuns(ArrayList<RoomRun> roomRuns) {
		this.roomRuns = roomRuns;
	}

	public E_Rooms[] getTypes() {
		return Types;
	}

	public void setTypes(E_Rooms[] types) {
		Types = types;
	}

	public int getLevel() {
		return Level;
	}

	public void setLevel(int level) {
		if (level > 0 && level < 4) {
			this.Level = level;
			return;
		}
		this.Level = 1;
	}

	// -------------------------------More Methods------------------------------
	/**
	 * This method adds a RoomRun to this instructor roomRuns if he is available
	 * at the time, roomRun's level not above his level and roomRun's type is
	 * included in his types. Attention - a RoomRun duration depends on it's
	 * room
	 * 
	 * @param roomRun
	 * @return true if the roomRun was successfully added, false otherwise
	 */
	@SuppressWarnings("deprecation")
	public boolean addRoomRun(RoomRun roomRun) {
		if (roomRun == null)
			return false;
		boolean checkType = false;
		for (int i = 0; i < this.getTypes().length; i++)
			if (this.getTypes()[i] == roomRun.getRoom().getRoomType())
				checkType = true;
		if (!checkType)
			return false;
		return roomRuns.add(roomRun);
	}

	/**
	 * This method removes a roomRun if it exist and belongs to this instructor
	 * 
	 * @param roomRun
	 * @return true if the roomRun was successfully deleted, false otherwise
	 */
	public boolean deleteRoomRun(RoomRun roomRun) {
		if (roomRun != null && roomRun.getInstructor().equals(this) && roomRuns.contains(roomRun)) {
			roomRuns.remove(roomRuns.indexOf(roomRun));
			return true;
		}
		return false;
	}
}
