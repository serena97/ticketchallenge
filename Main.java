import java.util.Scanner;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;
import java.util.TreeMap;
import java.util.Map;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.io.*;

/**
* The program accepts a user location as a pair of coordinates, and returns a list of
* the 5 closest events, along with the cheapest ticket price for each event
*
* @author  Serena Chan
*
*/

public class Main {
  Random random;
  ArrayList<Event> events = new ArrayList<Event>();
  TreeMap<Integer, Event> tmap = new TreeMap<Integer, Event>();

/**
*  Main method that gets user input, checks whether coordinates
*  1. @exception NumberFormatException has correct format of x,y
*  2. if the x and y coordinates are integers ranging from -10 to +10
*  If they don't pass the check, then the program terminates and the user will have to rerun and input correct coordinates.
*  Else, the list of 5 nearest events, ticket price and relative manhattan distance are returned
*/
  public static void main(String[] args) {
    Main main = new Main();
    main.seedDB();

    while(true){
      System.out.println("Please input Coordinates:");
      Scanner input = new Scanner(System.in);
      String coordinate = input.nextLine();
      String[] parts = coordinate.split(",");
      try {
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        if(x > 10 || x < -10 || y > 10 || y < -10) {
          System.out.println("Please enter coordinates within valid range");
          return;
        }
        System.out.println("Closest Events to ("+x+","+y+"):");
        String[] outputs = main.findNearestEvents(x,y);
        for(String eventTicket : outputs) {
          System.out.println(eventTicket);
        }
      } catch(java.lang.NumberFormatException e) {
        System.out.println("Please input coordinates in the right format");
      }
    }

  }

  @SuppressWarnings("unchecked")
  public void loadEvents() {
    //unserialize the events arrayList
    try {
      FileInputStream fis = new FileInputStream("eventsfile.ser");
      ObjectInputStream ois = new ObjectInputStream(fis);
      events = (ArrayList<Event>) ois.readObject();
      ois.close();
      fis.close();
    } catch(IOException ioe) {
      System.out.println("Making eventsfile.ser");
      return;
    } catch(ClassNotFoundException c) {
      c.printStackTrace();
      return;
    }
  }

  public void saveEvents() {
    //serialize the events arrayList
    try {
      FileOutputStream fos= new FileOutputStream("eventsfile.ser");
      ObjectOutputStream oos= new ObjectOutputStream(fos);
      oos.writeObject(events);
      oos.close();
      fos.close();
    } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException ioe) {
      ioe.printStackTrace();
    }

  }

  /**
  * This method loads and saves events from stored arrayList, if there's no saved events randomly generate seed events
  */
  public void seedDB() {

    loadEvents();

    if(events.isEmpty()) {
      //generate random location with no duplicates using set
      random = new Random();
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

    saveEvents();
    // testNoDuplicates();
  }

  /**
  * This method finds the nearest events based on manhattan distance
  * assuming that there will always be more than 5 events generated (generated 50 events in seedDB), a list of 5 closest events can be returned
  * @param x is the x coordinate inputted
  * @param y is the y coordinate inputted
  * @return an array of Strings, with each String containing the event id, ticket price and distance to event
  */
  public String[] findNearestEvents(int x, int y) {
    String[] output = new String[5];
    for(Event e: events) {
      Integer dist = Math.abs(x - e.getLocation().getX()) + Math.abs(y - e.getLocation().getY());
      tmap.put(dist, e);
    }
    // printNearestDistance();
    int counter = 0;
    for(Map.Entry<Integer, Event> entry : tmap.entrySet()) {
      if(counter == 5) {
        break;
      }
      Event e = entry.getValue();
      String eventID = e.getStringID();
      String ticket = e.returnCheapestTicket();
      String manD = Integer.toString(entry.getKey());
      output[counter] = "Event " + eventID + " - $" + ticket + ","+"Distance " + manD;
      counter++;
    }
    //make sure all the entries are deleted in tmap so that same events won't be appended
    tmap.clear();
    return output;
  }

  /**
  * This method generates a random number of tickets from 1 to 10, and ticket prices from 1 to 100
  * @return an array of ticket prices, as we're assuming that the only information we have on the ticket is its price
  */
  public double[] generateRandomTickets() {
    random = new Random();
    int numTickets = random.nextInt(10) + 1;
    double[] tickets = new double[numTickets];
    for(int i = 0; i < numTickets; i++) {
      double price = Math.random() * 100 + 1;
      tickets[i] = round(price);
    }
    return tickets;
  }

  /**
  * This method rounds a number to 2 decimal places
  * @return rounded number to 2 dp
  */
  public double round(double value) {
    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(2, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }

  /*
  * Debug function, see that there are no events duplicated
  */
  public void testNoDuplicates(){
    System.out.print("events:");
    for(Event e : events) {
      System.out.println(e.getStringID());
    }
  }

  /**
  * print relative distances and event ids
  */
  public void printNearestDistance() {
    for(Map.Entry<Integer, Event> entry : tmap.entrySet()) {
      System.out.println("key: "+entry.getKey()+" ,value:"+entry.getValue()+"which is "+entry.getValue().getStringID());
    }
  }

}
