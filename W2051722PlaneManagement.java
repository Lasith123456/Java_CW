import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;


public class W2051722PlaneManagement {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //This is the tickets array which contains 52 empty spaces to store Ticket instances.
        Ticket[] tickets = new Ticket[52];

        //Configuration data
        String[] rows = {"A","B","C","D"};
        int maxSeatsPerRow = 14;

        boolean flag = true;
        while (flag){
            int option;
            mainMenu();
            try {
                option = Integer.parseInt(input.next().trim());
                switch (option){
                    case 1:
                        buyASeat(input,tickets);
                        break;
                    case 2:
                        cancelSeat(input, tickets);
                        break;
                    case 3:
                        findFirstAvailableSeat(tickets,rows,maxSeatsPerRow);
                        break;
                    case 4:
                        showSeatingPlan(tickets,rows,maxSeatsPerRow);
                        break;
                    case 5:
                        printTicketInformationAndTotalSales(tickets);
                        break;
                    case 6:
                        searchTicket(input,tickets);
                        break;
                    case 0:
                        flag=false;
                        break;
                    default:
                        System.out.println("Enter correct option");
                }
            }catch (NumberFormatException e){
                System.out.println("Enter correct option.");
            }
        }


    }

    /**
     * searchTicket(Scanner input, Ticket[] tickets) method allows user to search ticket based on the seat number.
     * This method prompt user to enter row and seat number which user need to search.
     * Then this method iterate over the Tickets array and search,
     * the ticket which contains the user entered row and seat number.
     * then this method prints all the details of the ticket.
     *
     * Parameters - Tickets array and Scanner object.
     * Returns - This method will not return any value.
     */

    private static void searchTicket(Scanner input, Ticket[] tickets) {
        NumberFormat ukFormat = NumberFormat.getCurrencyInstance(Locale.UK);
        String row = getRowFromUser(input);
        int seatNo = getSeatNoFromUser(input,row);

        int index = searchSeatFromSeatNumber(row,seatNo,tickets);

        if (index!= -1){
            Ticket ticket = tickets[index];
            System.out.println("Seat Not Available | "+ticket.getRow()+"-"+ticket.getSeatNo()+ " - "
                    + ticket.getPerson().getName()+" "+ticket.getPerson().getSurname() + ticket.getPerson().getEmail()
                    +" - "+ ukFormat.format(ticket.getPrice()));
        }
    }


    /**
     * printTicketInformationAndTotalSales(Ticket[] tickets) method provides tickets information and total sales.
     * Method iterate over the tickets array and print the ticket information and finally provide the total sales.
     * if any ticket not sold at the time of method calling it shows the message.
     *
     * Parameters - Tickets array.
     * Returns - This method will not return any value.
     */
    private static void printTicketInformationAndTotalSales(Ticket[] tickets) {
        NumberFormat ukFormat = NumberFormat.getCurrencyInstance(Locale.UK);
        double total=0;
        int i = 1;

        for (Ticket ticket:tickets){
            if (ticket != null){
                System.out.println(i+") "+ ticket.getRow()+"-"+ticket.getSeatNo()+"  "+ticket.getPerson().getName()+" "
                        +ticket.getPerson().getSurname()+"  Email :- "+ticket.getPerson().getEmail());
                total +=ticket.getPrice();
                i++;
            }
        }
        if (total == 0){
            System.out.println("No tickets sold yet.");
        }else {
            System.out.println("Total tickets sales : "+ ukFormat.format(total));
        }

    }

    /**
     * showSeatingPlan(Ticket[] tickets, String[] rows, int maxSeatsPerRow) method provides seating plan.
     * Method iterate over the tickets array and provides the seating plan available.
     * if the seat reserved plan shows the seat as " X ".
     * if the seat not reserved, then plan shows it as " O ".
     *
     * Parameters - Tickets array, row numbers array, maximum seats per row.
     * Returns - This method will not return any value.
     */
    private static void showSeatingPlan( Ticket[] tickets, String[] rows, int maxSeatsPerRow) {
        for (String row: rows){
            if(row.equals("A")||row.equals("D")){
                for (int i = 0 ; i<maxSeatsPerRow;i++){
                    if (searchSeatFromSeatNumber(row,(i+1),tickets) != -1){
                        System.out.print(" X ");
                    }else {
                        System.out.print(" O ");
                    }
                }
                System.out.println();
            }else {
                for (int i = 0 ; i<maxSeatsPerRow-2;i++){
                    if (searchSeatFromSeatNumber(row,(i+1),tickets) != -1){
                        System.out.print(" X ");
                    }else {
                        System.out.print(" O ");
                    }
                }
                System.out.println();
            }
        }
    }

    /**
     * findFirstAvailableSeat(Ticket[] tickets, String[] rows, int maxSeatsPerRow) method provides first available seat.
     * Method iterate over the tickets array and provides the first available seat row and seat number.
     * First method check the available seat in row A then B ,C and D.
     * seat rows with minimum seats contain maximum seats per row - 2 seats.
     *
     * Parameters - Tickets array, row numbers array, maximum seats per row.
     * Returns - This method will not return any value.
     */
    private static void findFirstAvailableSeat(Ticket[] tickets, String[] rows, int maxSeatsPerRow) {
        for (String row: rows){
            if(row.equals("A")||row.equals("D")){
                for (int i = 0 ; i<maxSeatsPerRow;i++){
                    if (searchSeatFromSeatNumber(row,(i+1),tickets) == -1){
                        System.out.println("Next available seat : "+ row + " - " + (i+1));
                        return;
                    }
                }
            }else {
                for (int i = 0 ; i<maxSeatsPerRow-2;i++){
                    if (searchSeatFromSeatNumber(row,(i+1),tickets) == -1){
                        System.out.println("Next available seat : "+ row + " - " + (i+1));
                        return;
                    }
                }
            }
        }
    }

    /**
     * cancelSeat(Scanner input, Ticket[] tickets) method use to cancel the ticket.
     * Method prompt user to enter row & seat number.
     * then check the seat status.
     * If the seat already reserved, method will cancel the reservation &
     * also delete the text files created on reservation of the seat
     *
     * Parameters - Scanner object, Tickets array
     * Returns - This method will not return any value.
     */

    private static void cancelSeat(Scanner input, Ticket[] tickets) {
        String row = getRowFromUser(input);
        int seatNo = getSeatNoFromUser(input,row);

        int index = searchSeatFromSeatNumber(row,seatNo,tickets);

        if (index != -1){
            tickets[index] = null;
            deleteFile(row,seatNo);
        }else {
            System.out.println("Entered ticket number not available in the system.");
        }
    }

    /**
     * deleteFile(String row, int seatNo) method use to delete the text files.
     * First, method search a text file according to the pattern with row and seat number.
     * then delete the txt file if exists.
     * If not, message will display
     *
     * Parameters - Row number (String), seat number (Integer)
     * Returns - This method will not return any value.
     */
    private static void deleteFile(String row, int seatNo){
        File file = new File(row+" - "+seatNo+".txt");

        if (file.exists()){
            file.delete();
            System.out.println("Ticket file deleted.");
        }else{
            System.out.println("File not exists");
        }
    }

    /**
     * buyASeat(Scanner input,Ticket[] tickets) method allow user to purchase of a seat
     * by prompting the user for seat selection and attempting to store a new ticket.
     *
     * First method checks the availability of the seat according to the user entered row and seat number.
     * If the ticket is available, method will call createAndStoreNewTicket(row,seatNo,input,tickets).
     *
     * Parameters - Scanner object and Tickets array.
     * Returns - This method will not return any value.
     */
    private static void buyASeat(Scanner input,Ticket[] tickets) {
        String row = getRowFromUser(input);
        int seatNo = getSeatNoFromUser(input,row);

        if (searchSeatFromSeatNumber(row,seatNo,tickets)==-1){
            createAndStoreNewTicket(row,seatNo,input,tickets);
        }

    }

    /**
     * createAndStoreNewTicket(String row, int seatNo, Scanner input, Ticket[] tickets) creates a new ticket and store the ticket on Tickets array   .
     *
     * This method prompts the user to enter personal information (name, last name, and email).
     *Then method calls getIndexToStoreTicket and get the index of first available null space and store the new ticket.
     * while creating the new Ticket, method create new person object and store the person details on it.
     * Then Tickets object points the person object created by the method.
     *
     *Parameters - String row, int seatNo, Scanner object & Tickets array.
     * Returns - No return values on this method.
     */
    private static void createAndStoreNewTicket(String row, int seatNo, Scanner input, Ticket[] tickets) {

        System.out.print("Enter name : ");
        String name= input.next();
        System.out.print("Enter last name : ");
        String surname = input.next();

        String email;
        do {
            System.out.print("Enter your email : ");
            email = input.next();
        }while (!validateEmail(email));

        int index = getIndexToStoreTicket(tickets);

        if (index!= -1){
            tickets[index] = new Ticket(row,seatNo,new Person(name,surname,email));
        }
    }

    /**
     * getIndexToStoreTicket(Ticket[] tickets) method finds the first available index to store a new ticket in tickets array.
     *
     * This method returns the index of fist available null space in tickets array.
     *
     * Parameters - Tickets array.
     * Returns - this method returns the index of first available null space or null space not available, method will return -1.
     */
    private static int getIndexToStoreTicket(Ticket[] tickets) {
        int x = 0;
        for (Ticket ticket:tickets){
            if (ticket==null){
                return x;
            }
            x++;
        }
        return -1;
    }

    /**
     * validateEmail(String email) validates an email address by based on the presence of "@" and ".".
     *
     * Parameters -  email address (String).
     * Returns - Method returns true if the email is valid or false if the email is not valid.
     */

    private static boolean validateEmail(String email) {
       return email.contains("@") && email.contains(".");
    }

    /**
     * searchSeatFromSeatNumber(String row, int seatNo, Ticket[] tickets) method for Searches for a ticket
     * by row and seat number in tickets array.
     *
     * This method iterates through tickets array and,
     * search for a ticket that matches the given row number and the seat number already validated.
     *
     * Parameters -  row number (String), seat number (Integer) & tickets array.
     * Returns - Method returns the index of the ticket if matching ticket found from the tickets array.
     * If matching ticket not found, method returns -1.
     */
    private static  int searchSeatFromSeatNumber(String row, int seatNo, Ticket[] tickets){
        int x = 0;

        for (Ticket ticket: tickets){
            if (ticket!=null){
                if (ticket.getRow().equals(row) && ticket.getSeatNo()==seatNo){
                    return x;
                }
            }
            x++;
        }
        return -1;
    }

    /**
     * getSeatNoFromUser(Scanner input, String row) prompts the user to enter a valid seat number.
     * This method requests from the user to input a seat number.
     * The input is validated using the validateSeatNumber(int seatNo, String row) method.
     * If the input is not a valid seat number, the user is prompted again to enter the seat number.
     * seat counts: rows "A" and "D" can have seat numbers from 1 to 14, while other rows are limited to 1 to 12.
     *
     * Parameters - Scanner object / String row number (already Validated)
     * Method returns the seat number after passing the all the validations.
     */
    private static int getSeatNoFromUser(Scanner input, String row){
        int seatNo;
        while (true){
            try {
                System.out.print("Enter seat number");
                seatNo = Integer.parseInt(input.next());
                if (validateSeatNumber(seatNo, row)){
                    return seatNo;
                }
            }catch (NumberFormatException e) {
                System.out.println("Enter correct seat number");
            }
        }
    }

    /**
     * getRowFromUser(Scanner input) prompts the user to enter a valid row number until a correct one is provided.
     *
     * This method request the user to input a row number. Then validates the input using the
     * validateRowNumber(String row) method. If the input is not a valid row ("A", "B", "C", or "D"),
     * it prompts again the user to enter the correct row number . The method ensures the input is trimmed and converted
     * to upper case
     * before validation.
     * Parameters - Scanner object .
     * This method returns the row number(String) if the entered value validated.
     */
    private static String getRowFromUser(Scanner input){
        String row;
        do {
            System.out.print("Enter row number : ");
            row = input.next().trim().toUpperCase();
        }while (!validateRowNumber(row));
        return row;
    }

    /**
     * validateSeatNumber(int seatNo, String row) validates the given seat number according to the given row number.
     *
     * For rows "A" and "D", valid seat numbers are from 1 to 14 .
     * For other rows, valid seat numbers are from 1 to 12 .
     * parameters -  seatNo The seat number to validate (Integer) & Row number (String).
     * This method returns true when the seat number valid. if not method returns false.
     */
    private static boolean validateSeatNumber(int seatNo, String row) {
        if (row.equals("A")||row.equals("D")){
            if (seatNo>0 && seatNo <=14){
                return true;
            }
        }else{
            if (seatNo>0 && seatNo<=12){
                return true;
            }
        }
        return false;
    }

    /**
     * validateRowNumber(String row) method validates the given row number.
     *
     * This checks if the received value is a valid row number, which should be one of "A", "B", "C", or "D".
     * parameters -  The row number to validate(String).
     * This method returns true when the row number valid. if not method returns false.
     */
    private static boolean validateRowNumber(String row) {
        if (row.equals("A") || row.equals("B") || row.equals("C") || row.equals("D")) {
            return true;
        }else {
            return false;
        }
    }

    private static void mainMenu(){
        System.out.println("*****************************************");
        System.out.println("*\t\t\t\tMain Menu\t\t\t\t*");
        System.out.println("*****************************************");
        System.out.println("\t1) Buy a seat");
        System.out.println("\t2) Cancel a seat");
        System.out.println("\t3) Find first available seat");
        System.out.println("\t4) Show seating plan");
        System.out.println("\t5) Print ticket information and total sales");
        System.out.println("\t6) Search ticket");
        System.out.println("\t0) Quit");
        System.out.println("*****************************************");
        System.out.print("Enter option : ");
    }
}
