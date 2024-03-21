public class Ticket {
    private String row;
    private int seatNo;
    private double price;
    private Person person;

    public Ticket() {
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

    public void setPrice(double price) {
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
