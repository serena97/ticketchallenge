import java.util.Arrays;

public class Event {
  int id;
  //assuming that the tickets only contain info of price
  double[] tickets;
  Coordinates location;

  public Event(int id, double[] tickets, Coordinates location) {
    this.id = id;
    this.tickets = tickets;
    this.location = location;
  }

  public String getStringID() {
    //make id 3 digits, by padding with trailing 0s if needed
    String str = String.format("%03d", id);
    return str;
  }

  public double[] getTickets() {
    return tickets;
  }

  public Coordinates getLocation() {
    return location;
  }

  public double returnCheapestTicket(){
     Arrays.sort(tickets);
     return tickets[0];
  }

}
