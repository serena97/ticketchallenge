import java.util.Arrays;

/**
* The Event class holds the unique numeric identifier,
* an array of tickets(essentially the prices) and the location.
*/
public class Event {
  int id;
  double[] tickets;
  Coordinates location;

  public Event(int id, double[] tickets, Coordinates location) {
    this.id = id;
    this.tickets = tickets;
    this.location = location;
  }

/**
* This method makes id 3 digits, by padding with trailing 0s if needed
* @return formatted id String
*/
  public String getStringID() {
    String str = String.format("%03d", id);
    return str;
  }

  public double[] getTickets() {
    return tickets;
  }

  public Coordinates getLocation() {
    return location;
  }

/**
* This method sorts the tickets from cheapest to most expensive
* @return formatted cheapest ticket
*/
  public String returnCheapestTicket(){
     Arrays.sort(tickets);
     double ticket = tickets[0];
     return ticket < 10 ? "0"+ticket : Double.toString(ticket);
  }

}
