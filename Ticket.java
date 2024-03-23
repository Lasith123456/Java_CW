import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    private String row;
    private int seatNo;
    private double price;
    private Person person;

    public Ticket() {
    }

    public Ticket(String row, int seatNo, Person person) {
        this.row = row;
        this.seatNo = seatNo;
        this.person = person;
        setPrice();
        printTicketDetails();
    }

    public Ticket(String row, int seatNo, double price, Person person) {
        this.row = row;
        this.seatNo = seatNo;
        this.price = price;
        this.person = person;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice() {
        if (this.seatNo<=5){
            this.price = 200;
        } else if (this.seatNo<=9) {
            this.price=150;
        }else{
            this.price=180;
        }
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void printTicketDetails(){
        String ticketBody = "Ticket no : "+this.row+ " - "+ this.seatNo+", \nName : "+this.person.getName()+" "
                +this.person.getSurname()+", \nEmail : "+this.person.getEmail()+",\nPrice : "+this.price+".";

        try{
            FileWriter output = new FileWriter(this.row+" - "+this.seatNo+".txt");
            output.write(ticketBody);
            System.out.println("Success");
            output.close();
        }catch (IOException e){
            System.out.println("Error");
        }
    }

}
