package core;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import utils.Constants;

/**
 * Class Customer ~ represent a single customer of the company
 * 
 * @author Java Course Team 2017 - Shai Gutman
 * @author University Of Haifa - Israel
 */
public class Customer {
	// -------------------------------Class Members------------------------------
	private String Id;
	private String firstName;
	private String lastName;
	private Date birthdate;
	private String Password;
	private URL Email;
	private Subscription[] subs;
	private Address customerAddress;

	// -------------------------------Constructors------------------------------
	public Customer(String id, String firstName, String lastName, Date birthdate, String password, URL email,
			Subscription sub, Address customerAddress) {
		this.Id = checkId(id);
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.Password = password;
		this.Email = email;
		this.customerAddress = customerAddress;
		this.subs = new Subscription[Constants.MIN_SUB_ORDERS];
		addSubscription(sub);
	}

	public Customer(String id, String firstName, String lastName, Date birthdate, String password, URL email,
			Address customerAddress) {
		this.Id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.Password = password;
		this.Email = email;
		this.customerAddress = customerAddress;
		this.subs = new Subscription[Constants.MIN_SUB_ORDERS];
	}

	public Customer(String id) {
		this.Id = id;
		this.subs = new Subscription[Constants.MIN_SUB_ORDERS];
	}

	// -------------------------------Getters And Setters------------------------------
	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public URL getEmail() {
		return Email;
	}

	public void setEmail(URL email) {
		Email = email;
	}

	public Subscription[] getSubs() {
		return subs;
	}

	public void setSubs(Subscription[] subs) {
		this.subs = subs;
	}

	public Address getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(Address customerAddress) {
		this.customerAddress = customerAddress;
	}

	// -------------------------------More Methods------------------------------
	/**
	 * Helper method for Query 1: This method finds the number of participated
	 * RoomRuns meaning PAST RoomRuns this customer participated in
	 * 
	 * @return participatedRoomRuns list if there are RoomRuns, empty list
	 *         otherwise
	 */
	public List<RoomRun> getParticipatedRoomRuns() {
		Date today = new Date();
		List<RoomRun> participatedRoomRuns = new ArrayList<RoomRun>();
		for (int i = 0; i < subs.length; i++) {
			if (subs[i] != null) {
				for (int j = 0; j < subs[i].getRoomRuns().length; j++) {
					RoomRun l = subs[i].getRoomRuns()[j];
					if (l != null && l.getStartDate().before(today)) {
						long minutesDiff = TimeUnit.MINUTES.convert(today.getTime() - l.getStartDate().getTime(),
								TimeUnit.MILLISECONDS);
						if (!(Math.abs(minutesDiff) < 120)) {
							participatedRoomRuns.add(l);
						}
					}
				}
			}
		}
		return participatedRoomRuns.isEmpty() ? Collections.<RoomRun>emptyList() : participatedRoomRuns;
	}

	/**
	 * This method adds a new Subscription to the subs array IF the given sub
	 * doesn't already exists in the customer's subs array.
	 * 
	 * @param sub
	 * @return true if this sub was successfully added, false otherwise
	 */
	public boolean addSubscription(Subscription sub) {
		if (sub != null) {
			for (Subscription s : subs)
				if (s != null && s.equals(sub))
					return false;
			subs[getNextIndex()] = sub;
			return true;
		}
		return false;
	}

	// Helper method to get next free index
	private int getNextIndex() {
		int i;
		for (i = 0; i < subs.length; i++) {
			if (subs[i] == null) {
				return i;
			}
		}
		expandCapacity();
		return i;
	}

	// Helper method to expand the capacity
	private void expandCapacity() {
		Subscription[] tempArray = new Subscription[subs.length * 2];
		System.arraycopy(subs, 0, tempArray, 0, subs.length);
		setSubs(tempArray);
	}

	/**
	 * This method removes an existing subscription from the subs array IF the
	 * sub exists, after deleting him from all related lessons.
	 * 
	 * @param sub
	 * @return true if this sub was removed successfully or false otherwise
	 */
	public boolean removeSubscription(Subscription sub) {
		if (sub != null) {
			for (int i = 0; i < subs.length; i++) {
				if (subs[i] != null && subs[i].equals(sub)) {
					// the subscription exists - subs[i] should be delete!
					this.arrangeLeft(i);
					return true;
				}
			}
		}
		return false;
	}

	// Helper method to arrange left
	private void arrangeLeft(int removedInd) {
		for (; removedInd < subs.length - 1; removedInd++)
			subs[removedInd] = subs[removedInd + 1];
		subs[removedInd] = null;
	}

	/**
	 * This method counts the number of the subscriptions that belongs to the
	 * customer.
	 * 
	 * @return customerSubs number of subscriptions
	 */
	public int getNumOfCustomerSubscriptions() {
		int customerSubs = 0;
		for (int j = 0; j < subs.length; j++) {
			if (subs[j] != null) {
				customerSubs++;
			}
		}
		return customerSubs;
	}

	/**
	 * This method adds a RoomRun to the roomRuns array of the customer IF he
	 * has a valid subscription.
	 * 
	 * @param roomRunToAdd
	 * @return true if the lesson was added successfully or false otherwise
	 */
	public boolean addRoomRun(RoomRun roomRunToAdd) {
		if (roomRunToAdd != null) {
			for (int i = 0; i < subs.length; i++) {
				if (subs[i] != null && roomRunToAdd.getStartDate().before(subs[i].getLastDay())) {
					for (RoomRun l : subs[i].getRoomRuns()) {
						if (l != null && roomRunToAdd.equals(l)) {
							return false;
						}
					}
				}
			}
			for (int i = 0; i < subs.length; i++) {
				if (subs[i] != null && roomRunToAdd.getStartDate().before(subs[i].getLastDay())) {
					return subs[i].addRoomRun(roomRunToAdd);
				}
			}
		}
		return false;
	}

	/**
	 * this method checks if the customer has a subscription that is registered
	 * to the given roomRun if so, delete it from the subscription roomRuns
	 * array
	 * 
	 * @param roomRunToDelete
	 * @return true if the lesson was canceled successfully or false otherwise
	 */
	public boolean deleteRoomRun(RoomRun roomRunToDelete) {
		if (roomRunToDelete != null) {
			for (int i = 0; i < subs.length; i++) {
				for (int j = 0; j < subs[i].getRoomRuns().length; j++) {
					if (subs[i].getRoomRuns()[j] == roomRunToDelete) {
						subs[i].deleteRoomRun(roomRunToDelete);
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * this method get a string and check if the string is a valid ID number.
	 * 
	 * @param id
	 * @return id if this is an id, "0" otherwise
	 */
	public static String checkId(String id) {
		if (id.length() == Constants.ID_NUMBER_SIZE) {
			for (int i = 0; i < id.length(); i++) {
				if (!Character.isDigit(id.charAt(i)))
					return "0";
			}
			return id;
		}
		return "0";
	}

	// -------------------------------hashCode equals & toString------------------------------
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Id == null) ? 0 : Id.hashCode());
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
		Customer other = (Customer) obj;
		if (Id == null) {
			if (other.Id != null)
				return false;
		} else if (!Id.equals(other.Id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		DateFormat formatter = new SimpleDateFormat("d/M/yyyy");
		return "Customer [ID Number=" + Id + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate="
				+ formatter.format(birthdate) + ", password=" + Password + ", email=" + Email + ", customerAddress="
				+ customerAddress + "]";
	}
}
