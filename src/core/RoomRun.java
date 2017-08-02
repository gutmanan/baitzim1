package core;

import java.util.Arrays;
import java.util.Date;
import utils.E_Levels;

/**
 * Class RoomRun ~ represent a single RoomRun in the company
 * 
 * @author Java Course Team 2017 - Shai Gutman
 * @author University Of Haifa - Israel
 */
public class RoomRun {
	// -------------------------------Class Members------------------------------
	private int roomRunNum;
	private Date startDate;
	private E_Levels Level;
	private Instructor instructor;
	private Room room;
	private Customer[] participated;

	// -------------------------------Constructors------------------------------
	public RoomRun(int roomRunNum, Date startDate, E_Levels level, Instructor instructor, Room room) {
		this.roomRunNum = roomRunNum;
		this.startDate = startDate;
		this.Level = level;
		this.room = room;
		this.setInstructor(instructor);
		this.participated = new Customer[this.room.getMaxNumOfParticipants()];
	}

	public RoomRun(int roomRunNum) {
		this.roomRunNum = roomRunNum;
		this.participated = new Customer[this.room.getMaxNumOfParticipants()];
	}

	// -------------------------------Getters And Setters------------------------------
	public int getRoomRunNum() {
		return roomRunNum;
	}

	public void setRoomRunNum(int roomRunNum) {
		this.roomRunNum = roomRunNum;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public E_Levels getLevel() {
		return Level;
	}

	public void setLevel(E_Levels level) {
		Level = level;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		boolean ableToTeach = false;
		for (int i = 0; i < instructor.getTypes().length; i++) {
			if (instructor.getTypes()[i] == this.room.getRoomType()) {
				ableToTeach = true;
			}
		}
		if (this.Level.getLevel() <= instructor.getLevel() && ableToTeach) {
			this.instructor = instructor;
		}
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Customer[] getParticipated() {
		return participated;
	}

	public void setParticipated(Customer[] participated) {
		this.participated = participated;
	}

	// -------------------------------More Methods------------------------------
	/**
	 * this method adds a participant to this room rum if there is enough space
	 * 
	 * @param participantToAdd
	 * @return true if the participant was added successfully, false otherwise
	 */
	public boolean addParticipant(Customer participantToAdd) {
		if (participantToAdd != null && this.hasFreeSpace() && getNextIndex() >= 0) {
			for (Customer p : participated) {
				if (p != null && p.equals(participantToAdd))
					return false;
			}
			this.participated[getNextIndex()] = participantToAdd;
			return true;
		}
		return false;
	}

	/**
	 * This method removes a participant from the room run array if he exists
	 * there
	 * 
	 * @param participantToRemove
	 * @return true if the participant was deleted, false otherwise
	 */
	public boolean removeParticipant(Customer participantToRemove) {
		if (participantToRemove != null) {
			for (int i = 0; i < participated.length; i++) {
				if (participated[i] == participantToRemove) {
					arrangeLeft(i);
					return true;
				}
			}
		}
		return false;
	}

	// Helper method to arrange left
	private void arrangeLeft(int removedInd) {
		for (; removedInd < participated.length - 1; removedInd++)
			participated[removedInd] = participated[removedInd + 1];
		participated[removedInd] = null;
	}

	/**
	 * this method check if there is an empty cell in the participated array
	 * 
	 * @return true if there is space, false otherwise
	 */
	public boolean hasFreeSpace() {
		for (Customer p : participated) {
			if (p == null)
				return true;
		}
		return false;
	}

	/**
	 * This method finds the next empty cell in registered array
	 * 
	 * @return the index of the cell, if there is no empty cell return -1
	 */
	public int getNextIndex() {
		for (int i = 0; i < participated.length; i++) {
			if (participated[i] == null)
				return i;
		}
		return -1;
	}

	// -------------------------------hashCode equals & toString------------------------------
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + roomRunNum;
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
		RoomRun other = (RoomRun) obj;
		if (roomRunNum != other.roomRunNum)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RoomRun [roomRunNum=" + roomRunNum + ", startDate=" + startDate + ", Level=" + Level + ", instructor="
				+ instructor + ", room=" + room + ", participated=" + Arrays.toString(participated) + "]";
	}

}
