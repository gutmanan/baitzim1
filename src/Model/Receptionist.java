package Model;

import java.time.Year;
import java.util.Date;

import utils.Constants;

/**
 * Class Receptionist ~ represent a single Receptionist of the company,
 * inheritor of Employee
 * 
 * @author Java Course Team 2017 - Shai Gutman
 * @author University Of Haifa - Israel
 */
public class Receptionist extends Employee {
	// -------------------------------Class Members------------------------------
	private Subscription[] Subscriptions;

	// -------------------------------Constructors------------------------------
	public Receptionist(int empNum, String firstName, String lastName, Date birthdate, Date startWorkingDate,
			String password, Address address) {
		super(empNum, firstName, lastName, birthdate, startWorkingDate, password, address);
		this.Subscriptions = new Subscription[Constants.MIN_SUB_ORDERS];
	}

	public Receptionist(int empNum) {
		super(empNum);
	}

	// -------------------------------Getters And Setters------------------------------
	public Subscription[] getSubscriptions() {
		return Subscriptions;
	}

	public void setSubscriptions(Subscription[] subscriptions) {
		this.Subscriptions = subscriptions;
	}

	// -------------------------------More Methods------------------------------
	/**
	 * This method adds a subscription to the subscription's array IF it does
	 * not already exists
	 * 
	 * @param sub
	 * @return true if the subscription was added successfully, false otherwise
	 */
	public boolean addSubscription(Subscription sub) {
		if (sub != null) {
			for (Subscription s : Subscriptions) {
				if (s != null && s.equals(sub) || !this.equals(sub.getReceptionist()))
					return false;
			}
			int getNext = getNextIndex();
			if (getNext < Subscriptions.length) {
				Subscriptions[getNext] = sub;
				return true;
			}
		}
		return false;
	}

	/**
	 * This method deletes a subscription from the subscriptions array
	 * 
	 * @param lessonToCancel
	 * @return true if the lesson was deleted successfully, false otherwise
	 */
	public boolean removeSubscription(Subscription sub) {
		if (sub != null && this.equals(sub.getReceptionist())) {
			for (int i = 0; i < Subscriptions.length; i++) {
				if (Subscriptions[i] == sub) {
					arrangeLeft(i);
					return true;
				}
			}
		}
		return false;
	}

	// Helper method to expand capacity
	private void expandCapacity() {
		Subscription[] tempArray = new Subscription[(Subscriptions.length) * 2];
		System.arraycopy(Subscriptions, 0, tempArray, 0, Subscriptions.length);
		setSubscriptions(tempArray);
	}

	// Helper method to arrange left
	private void arrangeLeft(int removedInd) {
		for (; removedInd < Subscriptions.length - 1; removedInd++)
			Subscriptions[removedInd] = Subscriptions[removedInd + 1];
		Subscriptions[removedInd] = null;
	}

	// Helper method to get next free index
	private int getNextIndex() {
		int i;
		for (i = 0; i < Subscriptions.length; i++) {
			if (Subscriptions[i] == null) {
				return i;
			}
		}
		expandCapacity();
		return i;
	}

	/**
	 * This method counts the number of subscriptions the receptionist handled
	 * in January of this year. only the subscriptions with the relevant dates.
	 * 
	 * @return numOfAssignments at january of this year
	 */
	@SuppressWarnings("deprecation")
	public int getNumberOfThisYearJanuaryAssignments() {
		int numOfAssignments = 0;
		for (int i = 0; i < Subscriptions.length; i++) {
			if (Subscriptions[i] != null && Subscriptions[i].getStartDate().getMonth() == 0
					&& Subscriptions[i].getStartDate().getYear() + 1900 == Year.now().getValue()) {
				numOfAssignments++;
			}
		}
		return numOfAssignments;
	}
}
