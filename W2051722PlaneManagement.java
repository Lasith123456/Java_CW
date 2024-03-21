import java.util.Scanner;

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
                    case 1:break;
                    case 2:break;
                    case 3:break;
                    case 4:break;
                    case 5:break;
                    case 6:break;
                    case 0:break;
                    default:
                        System.out.println("Enter correct option");
                }
            }catch (NumberFormatException e){
                System.out.println("Enter correct option.");
            }
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
