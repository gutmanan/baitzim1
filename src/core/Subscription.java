package core;

import java.util.Arrays;
import java.util.Date;

import utils.Constants;
import utils.E_Periods;

/**
 * Class Subscription ~ represent a single Subscription in the company Each
 * Subscription belongs to a specific participant
 * 
 * @author Java Course Team 2017 - Shai Gutman
 * @author University Of Haifa - Israel
 */
public class Subscription {
	// -------------------------------Class Members------------------------------
	private int number;
	private Customer resCustomer;
	private Receptionist receptionist;
	private E_Periods period;
	private Date startDate;
	private RoomRun[] roomRuns;

	// -------------------------------Constructors------------------------------
	public Subscription(int number, Customer customer, Receptionist receptionist, E_Periods period, Date startDate) {
		this.number = number;
		this.resCustomer = customer;
		this.receptionist = receptionist;
		this.period = period;
		this.startDate = startDate;
		this.roomRuns = new RoomRun[Constants.MIN_NUM_OF_ROOMRUNS];
	}

	public Subscription(int number) {
		this.number = number;
		this.roomRuns = new RoomRun[Constants.MIN_NUM_OF_ROOMRUNS];
	}

	// -------------------------------Getters And Setters------------------------------
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Receptionist getReceptionist() {
		return receptionist;
	}

	public void setReceptionist(Receptionist receptionist) {
		this.receptionist = receptionist;
	}

	public Customer getCustomer() {
		return resCustomer;
	}

	public void setCustomer(Customer resCustomer) {
		this.resCustomer = resCustomer;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public E_Periods getPeriod() {
		return period;
	}

	public void setPeriod(E_Periods period) {
		this.period = period;
	}

	public RoomRun[] getRoomRuns() {
		return roomRuns;
	}

	public void setRoomRuns(RoomRun[] roomRuns) {
		this.roomRuns = roomRuns;
	}

	// -------------------------------More Methods------------------------------
	/**
	 * This method calculate the last date the subscription is valid for
	 * 
	 * @return lastDate of the subscription if no nulls, null otherwise
	 * @see utils.E_Periods
	 */
	@SuppressWarnings("deprecation")
	public Date getLastDay() {
		if (this.getStartDate() != null && this.getPeriod() != null) {
			Date lastDay = (Date) this.getStartDate().clone();
			lastDay.setMonth(lastDay.getMonth() + this.getPeriod().getNumber());
			return lastDay;
		}
		return null;
	}

	/**
	 * This method adds a roomRun to the subscription's roomRuns array IF its
	 * new for this subscription
	 * 
	 * @param lessonToAdd
	 * @return true if the lesson was added successfully, false otherwise
	 */
	public boolean addRoomRun(RoomRun roomRunToAdd) {
		if (roomRunToAdd != null) {
			for (RoomRun rr : roomRuns)
				if (rr != null && rr.equals(roomRunToAdd))
					return false;
			roomRuns[getNextIndex()] = roomRunToAdd;
			return true;
		}
		return false;
	}

	/**
	 * Get the next empty cell in roomRuns to add a new roomRun to the array, if
	 * the array is out of range use the helper method to allocate new cells.
	 * 
	 * @return the next empty index
	 */
	private int getNextIndex() {
		int i;
		for (i = 0; i < roomRuns.length; i++) {
			if (roomRuns[i] == null) {
				return i;
			}
		}
		expandCapacity();
		return i;
	}

	/**
	 * This method expand the roomRuns array capacity using System.arrayCopy to
	 * a multiple size array.
	 */
	private void expandCapacity() {
		RoomRun[] tempArray = new RoomRun[roomRuns.length * 2];
		System.arraycopy(roomRuns, 0, tempArray, 0, roomRuns.length);
		setRoomRuns(tempArray);
	}

	/**
	 * This method delete a roomRun from the roomRuns array of this subscription
	 * 
	 * @param lessonToCancel
	 * @return true if the lesson was deleted successfully, false other
	 */
	public boolean deleteRoomRun(RoomRun roomRunToDelete) {
		if (roomRunToDelete != null) {
			for (int i = 0; i < roomRuns.length; i++) {
				if (roomRuns[i] == roomRunToDelete) {
					arrangeLeft(i);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * This method shifts all the roomRuns that come after the given index to
	 * the left.
	 * 
	 * @param removedInd
	 */
	private void arrangeLeft(int removedInd) {
		for (; removedInd < roomRuns.length - 1; removedInd++)
			roomRuns[removedInd] = roomRuns[removedInd + 1];
		roomRuns[removedInd] = null;
	}

	// -------------------------------hashCode equals & toString------------------------------
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
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
		Subscription other = (Subscription) obj;
		if (number != other.number)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Subscription [number=" + number + ", resCustomer=" + resCustomer + ", receptionist=" + receptionist
				+ ", period=" + period + ", startDate=" + startDate + ", roomRuns=" + Arrays.toString(roomRuns) + "]";
	}
}
