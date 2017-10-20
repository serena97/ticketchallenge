import java.util.Scanner;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;
import java.util.TreeMap;
import java.util.Map;


public class Main {
  Random random = new Random();
  ArrayList<Event> events = new ArrayList<Event>();
  TreeMap<Integer, Event> tmap = new TreeMap<Integer, Event>();

  public static void main(String[] args) {
    Main main = new Main();
    main.seedDB(); //seed db with map object containing loc and [tickets], cached? singleton?

    System.out.println("Please input Coordinates");
    Scanner input = new Scanner(System.in);
    String coordinate = input.nextLine();
    //check coordinates to see if it's int and += 10 range
    String[] parts = coordinate.split(",");
    String x = parts[0];
    String y = parts[1];
    System.out.println(x+","+y);
    System.out.println("Closest Events to ("+x+","+y+"):");
    String[] outputs = main.findNearestEvents(Integer.parseInt(x),Integer.parseInt(y));
    for(String eventTicket : outputs) {
      System.out.println(eventTicket);
    }

  }

  public void seedDB() {
    //generate random location with no duplicates using set
    Set<Coordinates> locSet = new HashSet<Coordinates>();
    int idCounter = 0;
    while(locSet.size() <= 50) { // seed 50 events
      int x = random.nextInt(10) - 10; //-10 min, 10 max
      int y = random.nextInt(10) - 10;
      Coordinates loc = new Coordinates(x,y);
      boolean added = locSet.add(loc); // returns true if set did not already contain the specified element
      if(added) {
        double[] prices = generateRandomTickets();
        idCounter++;
        Event event = new Event(idCounter, prices, loc);
        events.add(event);
      }
    }
  }

  public String[] findNearestEvents(int x, int y) {
    //calculate manhattandistance for each event
    //make treemap<manhattandistance,event>
    String[] output = new String[5];

    for(Event e: events) {
      Integer dist = Math.abs(x - e.getLocation().getX()) + Math.abs(y - e.getLocation().getY());
      tmap.put(dist, e);
    }

    int counter = 0;
    for(Map.Entry<Integer, Event> entry : tmap.entrySet()) {
      if(counter == 5) {
        break;
      }
      Event e = entry.getValue();
      String eventID = e.getStringID();
      String ticket = Double.toString(e.returnCheapestTicket());
      String manD = Integer.toString(entry.getKey());
      output[counter] = "Event " + eventID + " - $" + ticket + ","+"Distance " + manD;
      counter++;
    }

    return output;
  }

  public double[] generateRandomTickets() {
    int numTickets = random.nextInt(10) + 1;
    double[] tickets = new double[numTickets];
    for(int i = 0; i < numTickets; i++) {
      double price = Math.random() * 100;
      tickets[i] = price;
    }
    return tickets;
  }


}
