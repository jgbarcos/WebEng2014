package formats.xml;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import static formats.xml.PhoneType.*;

public class AddPerson {

	public final static String DEFAULT_FILE_NAME = "address_book.xml";

	// This function fills in a Person message based on user input.
	static Person PromptForAddress(BufferedReader stdin, PrintStream stdout)
			throws IOException {
		Person person = new Person();

		stdout.print("Enter person ID: ");
		person.setId(Integer.valueOf(stdin.readLine()));

		stdout.print("Enter name: ");
		person.setName(stdin.readLine());

		stdout.print("Enter email address (blank for none): ");
		String email = stdin.readLine();
		if (email.length() > 0) {
			person.setEmail(email);
		}

		while (true) {
			stdout.print("Enter a phone number (or leave blank to finish): ");
			String number = stdin.readLine();
			if (number.length() == 0) {
				break;
			}

			PhoneNumber phoneNumber = new PhoneNumber();
			phoneNumber.setNumber(number);

			stdout.print("Is this a mobile, home, or work phone? ");
			String type = stdin.readLine();
			if (type.equals("mobile")) {
				phoneNumber.setType(MOBILE);
			} else if (type.equals("home")) {
				phoneNumber.setType(HOME);
			} else if (type.equals("work")) {
				phoneNumber.setType(WORK);
			} else {
				stdout.println("Unknown phone type.  Using default.");
			}

			person.addPhone(phoneNumber);
		}

		return person;
	}

	// Main function: Reads the entire address book from a file,
	// adds one person based on user input, then writes it back out to the same
	// file.
	public static void main(String[] args) throws Exception {
		JAXBContext context = JAXBContext.newInstance(AddressBook.class);
		String filename = DEFAULT_FILE_NAME;
		if (args.length > 0) {
			filename = args[0];
		}

		AddressBook addressBook = new AddressBook();

		// Read the existing address book.
		Unmarshaller um = context.createUnmarshaller();
		try {
			addressBook = (AddressBook) um.unmarshal(new FileReader(filename));
		} catch (FileNotFoundException e) {
			System.out.println(filename
					+ ": File not found.  Creating a new file.");
		}

		// Add an address.
		addressBook.addPerson(PromptForAddress(new BufferedReader(
				new InputStreamReader(System.in)), System.out));

		// Write the new address book back to disk.
		FileWriter output = new FileWriter(filename);
		Marshaller ma = context.createMarshaller();
		ma.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		ma.marshal(addressBook, output);
		output.close();
	}
}
