import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class W2051722PlaneManagement {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //This is the tickets array which contains 52 empty spaces to store Ticket instances.
        Ticket[] tickets = new Ticket[52];

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
                    case 3:break;
                    case 4:break;
                    case 5:break;
                    case 6:break;
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

    private static void cancelSeat(Scanner input, Ticket[] tickets) {
        String row = getRowFromUser(input);
        int seatNo = getSeatNoFromUser(input,row);

        int index = searchSeatFromSeatNumber(row,seatNo,tickets);

        if (index != -1){
            tickets[index] = null;
        }else {
            System.out.println("Entered ticket number not available in the system.");
        }
    }

    private static void buyASeat(Scanner input,Ticket[] tickets) {
        String row = getRowFromUser(input);
        int seatNo = getSeatNoFromUser(input,row);

        if (searchSeatFromSeatNumber(row,seatNo,tickets)==-1){
            createAndStoreNewTicket(row,seatNo,input,tickets);
        }

    }

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

    private static boolean validateEmail(String email) {
        Pattern emailPattern = Pattern.compile("^[\\w\\.]+@([\\w]+\\.)+[\\w]{2,4}");
        Matcher emailMatcher = emailPattern.matcher(email);

        return emailMatcher.matches();

    }

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

    private static String getRowFromUser(Scanner input){
        String row;
        do {
            System.out.print("Enter row number : ");
            row = input.next().trim().toUpperCase();
        }while (!validateRowNumber(row));
        return row;
    }

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

    private static boolean validateRowNumber(String row) {
        /*This method verify the reciving value  */
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
        System.out.print("Enter option");

    }
}
