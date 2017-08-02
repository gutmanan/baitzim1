package init;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;

import core.Address;
import core.Instructor;
import core.Customer;
import core.RoomRun;
import core.Receptionist;
import utils.E_Cities;
import utils.E_Levels;
import utils.E_Periods;
import utils.E_Rooms;
import utils.MyFileLogWriter;

/**
 * This MainClass object - represents the program runner
 * 
 * @author Java Course Team 2017 - Shai Gutman
 * @author University Of Haifa - Israel
 */
public class MainClass {

	/**
	 * The command read from the input file
	 */
	private static String command;

	/**
	 * To check if the command updated the objects
	 */
	private static boolean isUpdated;

	/**
	 * The date format
	 */
	private static DateFormat df;

	/**
	 * The date & time format
	 */
	private static DateFormat dft;

	/**
	 * The main object for the program
	 */
	private static SysData SysData;

	/**
	 * Scanner for input
	 */
	private static Scanner input;

	/**
	 * The main method of this system, gets input from input.txt and Writes
	 * output to output.txt file
	 * 
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws IOException, ParseException, ClassNotFoundException {
		// Create Scanner for the text file named "input.txt"
		input = new Scanner(new File("input.txt"));
		// Define Date format
		df = new SimpleDateFormat("dd/MM/yyyy");
		df.setTimeZone(TimeZone.getTimeZone("Asia/Jerusalem"));
		// Define Date & Time format
		dft = new SimpleDateFormat("dd/MM/yyyy;HH:mm");
		dft.setTimeZone(TimeZone.getTimeZone("Asia/Jerusalem"));
		// Writer buffer creation used after update
		MyFileLogWriter.initializeMyFileWriter();
		// if the file has next - not empty
		if (input.hasNext()) {
			SysData = new SysData();
		}
		/*
		 * read the commands. loop while input file [input.hasnext()] and the
		 * SysData is not null
		 */
		while (input.hasNext() && SysData != null) {
			/*
			 * read the command, must be first at line because command value
			 * determine which method will be activated in SysData object.
			 */
			command = input.next();
			isUpdated = false;
			// ================ Building Command ================
			if (command.equals("addBranch")) {
				// create and add new Branch to IEscape
				int branchNumber = Integer.parseInt(input.next());
				String branchName = input.next();

				String country = input.next();
				E_Cities city = E_Cities.valueOf(input.next());
				String street = input.next();
				int housNumber = Integer.parseInt(input.next());
				String[] phoneNumber = { input.next() };

				isUpdated = SysData.addBranch(branchNumber, branchName, city, country, street, housNumber, phoneNumber);
				if (isUpdated) { // if adding successfully, then true returned,
					// the following message is written to the output file
					MyFileLogWriter.writeToFileInSeparateLine(
							"addBranch returns: " + "Successfully added Branch " + branchNumber + " to SysData");
				} else {
					MyFileLogWriter.writeToFileInSeparateLine(
							"addBranch returns: " + "Failed to add new Branch " + branchNumber + " to SysData");
				}
			}

			// ================ Building Command ================
			else if (command.equals("addInstructor")) {
				// create and add new Agent to IEscape
				int employeeNumber = Integer.parseInt(input.next());
				String firstName = input.next();
				String lastName = input.next();
				Date birthDate = df.parse(input.next());
				Date startWorkingDate = df.parse(input.next());
				String password = input.next();
				int level = Integer.parseInt(input.next());
				String[] temp = input.next().split(",");
				E_Rooms[] types = new E_Rooms[temp.length];
				for (int i = 0; i < temp.length; i++)
					types[i] = E_Rooms.valueOf(temp[i]);
				String country = input.next();
				E_Cities city = E_Cities.valueOf(input.next());
				String street = input.next();
				int housNumber = Integer.parseInt(input.next());
				String[] phoneNumber = { input.next() };

				Address address = new Address(country, city, street, housNumber, phoneNumber);

				Instructor instructor = new Instructor(employeeNumber, firstName, lastName, birthDate, startWorkingDate,
						password, level, address, types);

				isUpdated = SysData.addInstructor(instructor);
				if (isUpdated) { // if adding successfully, then true returned,
					// the following message is written to the output file
					MyFileLogWriter.writeToFileInSeparateLine(
							"addCoach returns: " + "Successfully added coach " + employeeNumber + " to SysData");
				} else {
					MyFileLogWriter.writeToFileInSeparateLine(
							"addCoach returns: " + "Failed to add new coach " + employeeNumber + " to SysData");
				}
			}

			// ================ Building Command ================
			else if (command.equals("addReceptionist")) {
				// create and add new Agent to IEscape
				int employeeNumber = Integer.parseInt(input.next());
				String firstName = input.next();
				String lastName = input.next();
				Date birthDate = df.parse(input.next());
				Date startWorkingDate = df.parse(input.next());
				String password = input.next();
				String country = input.next();
				E_Cities city = E_Cities.valueOf(input.next());
				String street = input.next();
				int housNumber = Integer.parseInt(input.next());
				String[] phoneNumber = { input.next() };

				Address address = new Address(country, city, street, housNumber, phoneNumber);

				Receptionist respt = new Receptionist(employeeNumber, firstName, lastName, birthDate, startWorkingDate,
						password, address);

				isUpdated = SysData.addReceptionist(respt);
				if (isUpdated) { // if adding successfully, then true returned,
					// the following message is written to the output file
					MyFileLogWriter.writeToFileInSeparateLine("addReceptionist returns: "
							+ "Successfully added receptionist " + employeeNumber + " to SysData");
				} else {
					MyFileLogWriter.writeToFileInSeparateLine("addReceptionist returns: "
							+ "Failed to add new receptionist " + employeeNumber + " to SysData");
				}
			}

			// ================ Building Command ================
			else if (command.equals("addCustomer")) {
				// create and add new Customer to IEscape
				String id = input.next();
				String firstName = input.next();
				String lastName = input.next();
				Date birthDate = df.parse(input.next());
				String password = input.next();
				URL email = new URL(input.next());

				String country = input.next();
				E_Cities city = E_Cities.valueOf(input.next());
				String street = input.next();
				int housNumber = Integer.parseInt(input.next());
				String[] phoneNumber = { input.next() };

				Address address = new Address(country, city, street, housNumber, phoneNumber);

				isUpdated = SysData.addCustomer(id, firstName, lastName, birthDate, password, email, address);
				if (isUpdated) { // if adding successfully, then true returned,
					// the following message is written to the output file
					MyFileLogWriter.writeToFileInSeparateLine("addCustomer returns: "
							+ "Successfully added Customer with passport " + id + " to SysData");
				} else {
					MyFileLogWriter.writeToFileInSeparateLine(
							"addCustomer returns: " + "Failed to add new Customer with passport " + id + " to SysData");
				}
			}

			// ================ Building Command ================
			else if (command.equals("addSubToCustomer")) {
				// create and add new Flight to IEscape
				int subNumber = Integer.parseInt(input.next());
				String custId = input.next();
				int receptNumber = Integer.parseInt(input.next());
				E_Periods period = E_Periods.valueOf(input.next());
				Date startDate = df.parse(input.next());

				isUpdated = SysData.addSubToCustomer(subNumber, custId, receptNumber, period, startDate);
				if (isUpdated) { // if adding successfully, then true returned,
					// the following message is written to the output file
					MyFileLogWriter.writeToFileInSeparateLine("addSubToCustomer returns: "
							+ "Successfully added subscription " + subNumber + " to SysData");
				} else {
					MyFileLogWriter.writeToFileInSeparateLine("addSubToCustomer returns: "
							+ "Failed to add new subscription " + subNumber + " to SysData");
				}
			}

			// ================ Building Command ================
			else if (command.equals("connectInstructorToBranch")) {
				// Connect between an existing instructor and an existing branch
				int instructorNumber = Integer.parseInt(input.next());
				int branchNumber = Integer.parseInt(input.next());

				isUpdated = SysData.connectCoachToBranch(instructorNumber, branchNumber);
				if (isUpdated) { // if adding successfully, then true returned,
					// the following message is written to the output file
					MyFileLogWriter.writeToFileInSeparateLine(
							"connectCoachToBranch returns: " + "Successfully connected between instructor "
									+ instructorNumber + " and branch " + branchNumber);
				} else {
					MyFileLogWriter.writeToFileInSeparateLine(
							"connectCoachToBranch returns: " + "Failed to connected between instructor "
									+ instructorNumber + " and branch " + branchNumber);
				}
			}

			// ================ Building Command ================
			else if (command.equals("connectReceptionistToBranch")) {
				// Connect between an existing receptionist and an existing branch
				int receptionistNumber = Integer.parseInt(input.next());
				int branchNumber = Integer.parseInt(input.next());

				isUpdated = SysData.connectReceptionistToBranch(receptionistNumber, branchNumber);
				if (isUpdated) { // if adding successfully, then true returned,
					// the following message is written to the output file
					MyFileLogWriter.writeToFileInSeparateLine(
							"connectReceptionistToBranch returns: " + "Successfully connected between receptionist "
									+ receptionistNumber + " and branch " + branchNumber);
				} else {
					MyFileLogWriter.writeToFileInSeparateLine(
							"connectReceptionistToBranch returns: " + "Failed to connected between receptionist "
									+ receptionistNumber + " and branch " + branchNumber);
				}
			}

			// ================ Building Command ================
			else if (command.equals("addRoomToBranch")) {
				// add a room to an existing branch
				int roomNum = Integer.parseInt(input.next());
				int maxNumOfParticipants = Integer.parseInt(input.next());
				int minNumOfParticipants = Integer.parseInt(input.next());
				int timeLimit = Integer.parseInt(input.next());
				E_Rooms roomType = E_Rooms.valueOf(input.next());
				int branchNum = Integer.parseInt(input.next());

				isUpdated = SysData.addRoomToBranch(roomNum, maxNumOfParticipants, minNumOfParticipants, timeLimit,
						roomType, branchNum);
				if (isUpdated) { // if changing successfully, then true
									// returned,
					// the following message is written to the output file
					MyFileLogWriter.writeToFileInSeparateLine("addRoomToBranch returns: " + "Successfully added room: "
							+ roomNum + " to branch " + branchNum + " in SysData");

				} else {
					MyFileLogWriter.writeToFileInSeparateLine("addRoomToBranch returns: " + "Failed to add room: "
							+ roomNum + " to branch " + branchNum + " in SysData");

				}
			}
			
			// ================ Building Command ================
			else if (command.equals("addRoomRun")) {
				// add a roomRun to an existing room in a specific branch
				int roomRunNum = Integer.parseInt(input.next());
				//E_Lessons lessonName = E_Lessons.valueOf(input.next());
				Date dateTime = dft.parse(input.next());
				E_Levels level = E_Levels.valueOf(input.next());
				int coachNum = Integer.parseInt(input.next());
				//int maxStudent = Integer.parseInt(input.next());
				int branchNum = Integer.parseInt(input.next());
				int roomNum = Integer.parseInt(input.next());

				isUpdated = SysData.addRoomRun(roomRunNum, dateTime, level, coachNum, branchNum, roomNum);
				if (isUpdated) { // if added successfully, then true returned,
					// the following message is written to the output file
					MyFileLogWriter.writeToFileInSeparateLine(
							"addLesson returns: " + "Successfully added RoomRun: " + roomRunNum + " at " + dateTime
									+ " to room " + roomNum + " in branch " + branchNum + " in SysData");

				} else {
					MyFileLogWriter.writeToFileInSeparateLine(
							"addLesson returns: " + "Failed to add RoomRun: " + roomRunNum + " at " + dateTime
									+ " to room " + roomNum + " in branch " + branchNum + " in SysData");
				}
			}

			// ================ Building Command ================
			else if (command.equals("addCustomerToLesson")) {
				// add a customer to an existing lesson
				String custNum = input.next();
				int lessonNum = Integer.parseInt(input.next());

				isUpdated = SysData.addCustomerToLesson(custNum, lessonNum);
				if (isUpdated) { // if added successfully, then true returned,
					// the following message is written to the output file
					MyFileLogWriter.writeToFileInSeparateLine("addCustomerToLesson returns: "
							+ "Successfully added customer " + custNum + " to lesson: " + lessonNum + " in SysData");

				} else {
					MyFileLogWriter.writeToFileInSeparateLine("addCustomerToLesson returns: "
							+ "Failed to add customer " + custNum + " to lesson: " + lessonNum + " in SysData");
				}
			}

			// ================ Building Command ================
			else if (command.equals("changeCustomerAddress")) {
				// Change an existing customer's address
				String customerId = input.next();
				String country = input.next();
				E_Cities city = E_Cities.valueOf(input.next());
				String street = input.next();
				int housNumber = Integer.parseInt(input.next());
				String[] phoneNumber = { input.next() };

				isUpdated = SysData.changeCustomerAddress(customerId, country, city, street, housNumber, phoneNumber);
				if (isUpdated) { // if changing successfully, then true
									// returned,
									// the following message is written to the
									// output file
					MyFileLogWriter.writeToFileInSeparateLine("changeCustomerAddress returns: "
							+ "Successfully changed address of customer: " + customerId + " "
							+ SysData.getCustomers().get(SysData.getCustomers().indexOf(new Customer(customerId))));

				} else {
					MyFileLogWriter.writeToFileInSeparateLine("changeCustomerAddress returns: "
							+ "Failed to change address of customer with id: " + customerId);

				}
			}

			// ================ Building Command ================
			else if (command.equals("cancelSubscription")) {
				// Cancel an existing subscription
				int subNumber = Integer.parseInt(input.next());

				isUpdated = SysData.cancelSubscription(subNumber);
				if (isUpdated) { // if canceling successfully, then true
									// returned,
					// the following message is written to the output file
					MyFileLogWriter.writeToFileInSeparateLine(
							"cancelSubscription returns: " + "Successfully canceled subscription " + subNumber);
				} else {
					MyFileLogWriter.writeToFileInSeparateLine(
							"cancelSubscription returns: " + "Failed to cancel subscription " + subNumber);
				}
			}

			// ================ Query Command ================
			else if (command.equals("getAllLessonsOfMostActiveCustomer")) {
				List<RoomRun> mostLessons = SysData.getAllLessonsOfMostActiveCustomer();
				MyFileLogWriter.writeToFileInSeparateLine(
						"=======\nQuery_1\n=======\ngetAllLessonsOfMostActiveCustomer returns:");
				if (mostLessons.size() != 0) {
					MyFileLogWriter.writeToFileInSeparateLine("the following lessons:");
					int i = 1;
					for (RoomRun l : mostLessons)
						if (l != null)
							MyFileLogWriter.writeToFileInSeparateLine(
									(i++) + "\t- " + l.getRoomRunNum() + " on " + l.getStartDate());

				} else
					MyFileLogWriter.writeToFileInSeparateLine("No customers were found");
			}
/*
			// ================ Query Command ================
			else if (command.equals("getAllCustomersFromBranchXbutNotFromBranchY")) {
				// Return all customers that practiced in branch X but didn't
				// practice in branch Y
				int branchXnumber = Integer.parseInt(input.next());
				int branchYnumber = Integer.parseInt(input.next());

				List<Customer> XonlyCustomers = SysData.getAllCustomersFromBranchXbutNotFromBranchY(branchXnumber,
						branchYnumber);
				MyFileLogWriter.writeToFileInSeparateLine(
						"=======\nQuery_2\n=======\ngetAllCustomersFromBranchXbutNotFromBranchY returns:");
				if (XonlyCustomers != null && XonlyCustomers.size() != 0) {
					MyFileLogWriter.writeToFileInSeparateLine("the following customers ed on branch " + +branchXnumber
							+ " (and not in branch " + branchYnumber + "): ");
					int i = 1;

					for (Customer customer : XonlyCustomers)
						MyFileLogWriter.writeToFileInSeparateLine((i++) + "\t- " + customer);

				} else
					MyFileLogWriter.writeToFileInSeparateLine("No customers were found");
			}

			// ================ Query Command ================
			else if (command.equals("getTheMostPopularLessonType")) {
				//E_Lessons lesson = SysData.getTheMostPopularLessonType();
				MyFileLogWriter
						.writeToFileInSeparateLine("=======\nQuery_3\n=======\ngetTheMostPopularLessonType returns:");
				if (lesson != null) {
					MyFileLogWriter.writeToFileInSeparateLine("The most popular lesson type is: \n" + lesson);
				} else
					MyFileLogWriter.writeToFileInSeparateLine("No lessons were found");
			}

			// ================ Query Command ================
			else if (command.equals("getReceptionistOfTheMonth")) {
				Receptionist recep = SysData.getReceptionistOfTheMonth();
				MyFileLogWriter
						.writeToFileInSeparateLine("=======\nQuery_4\n=======\ngetReceptionistOfTheMonth returns:");
				if (recep != null) {
					MyFileLogWriter.writeToFileInSeparateLine("The receptionist Of the month is: \n" + recep);
				} else
					MyFileLogWriter.writeToFileInSeparateLine("No receptionist won this month");
			}

			// ================ Query Command ================
			else if (command.equals("getAllSuperSeniorCoaches")) {
				Instructor[] superCoaches = SysData.getAllSuperSeniorCoaches();
				MyFileLogWriter
						.writeToFileInSeparateLine("=======\nQuery_5\n=======\ngetAllSuperSeniorCoaches returns:");
				if (superCoaches != null && superCoaches[0] != null) {
					MyFileLogWriter.writeToFileInSeparateLine("The super senior coaches of the month is:");
					for (int i = 0; i < superCoaches.length; i++)
						if (superCoaches[i] != null)
							MyFileLogWriter.writeToFileInSeparateLine(superCoaches[i].toString());
				} else
					MyFileLogWriter.writeToFileInSeparateLine("No super senior coaches for this month");
			}

			// ================ Query Command ================
			else if (command.equals("getTopJanuaryReceptionists")) {
				Receptionist topReceptionist = SysData.getTopJanuaryReceptionists();
				MyFileLogWriter
						.writeToFileInSeparateLine("=======\nQuery_6\n=======\ngetTopJanuaryReceptionists returns:");
				if (topReceptionist != null) {
					MyFileLogWriter.writeToFileInSeparateLine("The top january receptionist of the year is:");
					MyFileLogWriter.writeToFileInSeparateLine(topReceptionist.toString());
				} else
					MyFileLogWriter.writeToFileInSeparateLine("No top january receptionist for this year");
			}
*/
			else if (command.equals("//")) {
				// Separate line command
				MyFileLogWriter.writeToFileInSeparateLine("\n");
				input.nextLine();
				// ignores line starts by '//' the result is empty lines in the
				// output
			}
			// ================ Command ================
			else
				System.out.printf("Command %s does not exist\n", command);
		} // ~ end of clause - while input has next

		MyFileLogWriter.saveLogFile(); // save the output file
		input.close(); // close connection to input file
		System.out.println("[End Of process]");
		System.out.println("\n NOTICE:\n\t\"End of process\" " + "does NOT mean that all your methods are correct.\n"
				+ "\n==>\t You NEED to check the \"output.txt\" file");

	}// END OF ~ main
}// ~ END OF Class MainClass
