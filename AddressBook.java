import java.util.*;
import java.io.*;


public class AddressBook {
    // Storing all the contacts in an ArrayList
    private static ArrayList<Contact> contacts = new ArrayList<Contact>();

    // Data file name to store the contacts
    private static String file = "data.bin";

    // Scanner object for reading the user input
    private static Scanner sc = new Scanner(System.in);

    // The main function where the program starts
    public static void main(String[] args) throws Exception {
        // Load contacts from the file
        loadData();

        try {
            // Display the menu in a loop until the user decides to quit
            while (true) {
                displayMenu();
            }
        } finally {
            // Always close the Scanner
            if (sc != null) {
                sc.close();
            }
        }
    }

    // Load the contact data from a file
    private static void loadData() {
        // Try-with-resources to auto-close streams
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            // Reading the serialized ArrayList from the file
            contacts = (ArrayList<Contact>) ois.readObject();
            // Set the ID counter to the last contact's ID
            if (!contacts.isEmpty()) {
                Contact.setCount(contacts.get(contacts.size()-1).getId());
            }
        } catch (FileNotFoundException e) {
            // If the file doesn't exist, initialize contacts as an empty list.
            contacts = new ArrayList<Contact>();
        } catch (IOException e) {
            // Handle any IO exceptions
            System.err.println("Error while loading data: " + e.getMessage());
            contacts = new ArrayList<Contact>();
        } catch (ClassNotFoundException e) {
            // Handle any class not found exceptions
            System.err.println("Error: The Contact class is not found: " + e.getMessage());
        }
    }

    // Save the contact data to a file
    private static void saveData() {
        // Try-with-resources to auto-close streams
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            // Writing the ArrayList to the file
            oos.writeObject(contacts);
        } catch (IOException e) {
            // Handle any IO exceptions
            System.err.println("Error while saving data: " + e.getMessage());
        }
    }

    // Display the main menu and handle user choice
    private static void displayMenu() {
        // Display the menu options
        System.out.println("\nMain Window");
        System.out.println("============");
        System.out.println("Choose one of the following options:");
        System.out.println("\n(1) Add a new contact");
        System.out.println("(2) Search for a contact");
        System.out.println("(3) Display all contacts");
        System.out.println("(4) Quit");
        System.out.print("\nEnter Your Choice: ");

        // Read the user's choice
        int choice = -1;
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException e) {
            // Handle invalid user input
            System.out.println("\nInvalid input. Please enter a number between 1 and 4.");
            choice = 5;
        }
        sc.nextLine();  // Consume newline left-over

        // Perform an action based on the user's choice
        switch (choice) {
            case 1:
                // Add a new contact
                addContact();
                break;
            case 2:
                // Search for a contact
                searchContact();
                break;
            case 3:
                // Display all contacts
                displayAllContacts();
                break;
            case 4:
                // Quit the program
                System.out.println("Goodbye...");
                saveData();
                sc.close();
                System.exit(0);
                break;
            case 5:
                // Do nothing for invalid input
                break;
            default:
                // Handle invalid menu option
                System.out.println("\nInvalid option. Please choose a number between 1 and 4.");
                break;
        }
    }

    // Add a new contact
    private static void addContact() {
        // Prompt the user for contact details
        System.out.println("\nMain Window --> Add a new contact window: (Enter the following information)");
        System.out.println("===========================================================================");
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        System.out.print("Notes: ");
        String notes = sc.nextLine();
        System.out.println("----------------------------------------------------------------------------");

        // Create a new Contact object and add it to the ArrayList
        contacts.add(new Contact(name, email, phone, notes));
        // Save the contact list to a file
        saveData();

        System.out.println("Saved successfully... Press Enter to go back to the Main Window");
        sc.nextLine();
    }

    // Method to handle search functionality
    private static void searchContact() {
        // Present the user with search options
        System.out.println("\nMain Window —> Search for Contact Window: (Choose one of the following options)");
        System.out.println("================================================================================");
        System.out.println("(1) Search by Name");
        System.out.println("(2) Search by Email");
        System.out.println("(3) Search by Phone");
        System.out.print("\nEnter Your Choice: ");

        // Read the user's choice
        int choice = -1;
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException e) {
            // Handle invalid user input
            System.out.println("\nInvalid input. Please enter a number between 1 and 3.");
            choice = 4;
        }
        sc.nextLine();  // Consume newline left-over

        List<Contact> results;
        switch (choice) {
            // Each case corresponds to a different search option
            case 1:
                System.out.println("\nMain Window --> Search for Contact Window --> Search by Name");
                System.out.println("===============================================================");
                System.out.print("Enter Name: ");
                String name = sc.nextLine();
                results = findContactsByName(name);
                displaySearchResults(results);
                break;
            case 2:
                System.out.println("\nMain Window --> Search for Contact Window --> Search by Email");
                System.out.println("===============================================================");
                System.out.print("Enter Email: ");
                String email = sc.nextLine();
                results = findContactsByEmail(email);
                displaySearchResults(results);
                break;
            case 3:
                System.out.println("\nMain Window --> Search for Contact Window --> Search by Phone");
                System.out.println("===============================================================");
                System.out.print("Enter Phone: ");
                String phone = sc.nextLine();
                results = findContactsByPhone(phone);
                displaySearchResults(results);
                break;
            case 4:
                break;
            default:
                // Handle invalid option entries
                System.out.println("\nInvalid option. Please try again.");
                break;
        }
    }

    // Method to find contacts by name
    private static List<Contact> findContactsByName(String name) {
        List<Contact> results = new ArrayList<Contact>();
        // Iterate through the list of contacts
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                results.add(contact);
            }
        }
        return results;
    }

    // Method to find contacts by email
    private static List<Contact> findContactsByEmail(String email) {
        List<Contact> results = new ArrayList<Contact>();
        // Iterate through the list of contacts
        for (Contact contact : contacts) {
            if (contact.getEmail().equalsIgnoreCase(email)) {
                results.add(contact);
            }
        }
        return results;
    }

    // Method to find contacts by phone
    private static List<Contact> findContactsByPhone(String phone) {
        List<Contact> results = new ArrayList<Contact>();
        // Iterate through the list of contacts
        for (Contact contact : contacts) {
            if (contact.getPhone().equalsIgnoreCase(phone)) {
                results.add(contact);
            }
        }
        return results;
    }

    // Method to display search results
    private static void displaySearchResults(List<Contact> results) {
        if (results.size() == 0) {
            // Handle case with no search results
            System.out.println("\nNo contact found.");
            return;
        }
        System.out.println(".........................................................................................");
        System.out.println("ID\t| Name\t| Email\t| Phone\t| Notes\t");
        System.out.println(".........................................................................................");
        for (Contact contact : results) {
            System.out.println(contact);
        }
        System.out.println(".........................................................................................");

        System.out.println("\nChoose one of these options:");
        System.out.println("(1) To delete a contact");
        System.out.println("(2) Back to main Window");
        System.out.print("\nEnter Your Choice: ");
        int choice = sc.nextInt();
        sc.nextLine();  // Consume newline left-over

        if (choice == 1) {
            System.out.println("\nMain Window --> Search for Contact Window --> Search by Name --> Delete a Contact");
            System.out.println("===================================================================================");
            System.out.print("Enter the Contact ID: ");
            int id = sc.nextInt();
            sc.nextLine();  // Consume newline left-over
            deleteContact(id);
        }
    }

    // Method to delete a contact
    private static void deleteContact(int id) {
        // Remove the contact with the specified id
        contacts.removeIf(c -> c.getId() == id);
        saveData();
        System.out.println("\nDeleted... press Enter to go back to main window");
        sc.nextLine();
    }

    // Method to display all contacts
    private static void displayAllContacts() {
        System.out.println(".........................................................................................");
        System.out.println("ID\t| Name\t| Email\t| Phone\t| Notes\t");
        System.out.println(".........................................................................................");
        // Iterate through the list of contacts and display each one
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
        System.out.println(".........................................................................................");
        System.out.println("\nPress Enter to go back to the Main Window");
        sc.nextLine();
    }
}
