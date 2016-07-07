package com.walmart.ticketservice;

import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import com.walmart.ticketservice.model.SeatHold;
import com.walmart.ticketservice.service.TicketService;
import com.walmart.ticketservice.service.TicketServiceImpl;
import com.walmart.ticketservice.util.ConfigUtil;

public class App extends ConfigUtil{
	TicketService ticketService;
	static int MIN_LEVEL;
	static int MAX_LEVEL; 
	static App app;
	static Scanner sc;
	
	public App() {
    	MIN_LEVEL = ConfigUtil.MIN_LEVEL;
    	MAX_LEVEL = ConfigUtil.MAX_LEVEL; 
		ticketService = new TicketServiceImpl();
		sc = new Scanner(System.in);
	}
	
	public static void main( String[] args ) {
    	app = new App();
        int sel = showMainMenu();

        do {
            switch (sel) {
                case 1:
                	app.showSubMenuOptions(1);
                    break;
                case 2:
                	app.showSubMenuOptions(2);
                    break;
                case 3:
                	app.showSubMenuOptions(3);
                    break;
                case -2:
                	quitProgram();
                    break;
            }
            sel = showMainMenu();
        }  while ( sel != -2 );
        sc.close();
    }

    public void showSubMenuOptions(int sel) {
         switch (sel) {
            case 1:
                do {
                    System.out.print("\nINPUT> Type Level Number between " + MIN_LEVEL + " ~ " + MAX_LEVEL + " and Hit Enter [Type -1 to Main/-2 to Quit]: ");
                    int level = -1;

                    if( sc.hasNextInt() ) 
                		level = sc.nextInt();
                	if(level == -1 ) break;
                	if(level == -2 ) {
                		quitProgram();
                		//break;
                	}

                	if( !isValidLevel(level, level))
                        System.out.println("OUTPUT> Invalid level input. Plese type valid level number.");
                    else {
                        Optional<Integer> venueLevel = Optional.of(level);
                        int availableSeats = app.ticketService.numSeatsAvailable(venueLevel);
                        System.out.println("OUTPUT> availableSeats = " + availableSeats);
                    }
                } while(true);
                
                break;
            case 2:
                int numSeats = 0;
                int minLvl = -1;
                int maxLvl = -1;
                String customerEmail = null;

                do {
                    System.out.print("\nINPUT> Type number of seats you want to hold and hit return [Type -1 to Main/-2 to Quit]: "); 
                    if( sc.hasNextInt() )
                    	numSeats = sc.nextInt();

                    if(numSeats == -1 ) 
                    	break;
                	if(numSeats == -2 ) {
                		quitProgram();
                	}

                    System.out.print("INPUT> Type Min level between " + MIN_LEVEL + " ~ " + MAX_LEVEL + " and hit return: "); 
                    if( sc.hasNextInt() )
                    	minLvl = sc.nextInt();

                    System.out.print("INPUT> Type Max Level between " + MIN_LEVEL + " ~ " + MAX_LEVEL + " and hit return: "); 
                    if( sc.hasNextInt() )
                    	maxLvl = sc.nextInt();
                    
                    System.out.print("INPUT> Type Customer Email Address: "); 
                    if( sc.hasNext() ) 
                    	customerEmail = sc.next();
                    
                    if( !isValidLevel(minLvl, maxLvl)) {
                        System.out.println("OUTPUT> Invalid level input. Plese type valid level number.");
                    }
                    else {
                        Optional<Integer> minLevel = Optional.of(minLvl);
                        Optional<Integer> maxLevel = Optional.of(maxLvl);
                        
                        SeatHold seatHold = app.ticketService.findAndHoldSeats(numSeats, minLevel, maxLevel, customerEmail);
                        if( seatHold != null )
                        	System.out.println("OUTPUT> Seat Hold Id: " + seatHold.getSeatHoldId());
                        else 
                        	System.out.println("OUTPUT> Seat Hold Id is failed. Please type correct inputs.");
                    }
                    
                } while( true ) ;
                break;
            
            case 3:
                do {
                	String seatHoldId = null;
                	String customerEmail2 = null;
                	
                    System.out.println("\n--------------------------------------------------------------------------------------");
                    System.out.println("------------------- Seat Hold ID List ( Choose one of them ) -------------------------");
                    displaySeatHoldIDs();
                    System.out.println("--------------------------------------------------------------------------------------");

                    System.out.print("\nINPUT> Choose One Seat Hold ID from Above and Hit Return [Type -1 to Main/-2 to Quit]: "); 
                    if( sc.hasNext() ) 
                    	seatHoldId = sc.next();
                    
                    if( seatHoldId.length() < 3) {
                    	if( seatHoldId.equals("-1") ) 
                    		break;
                    	else if( seatHoldId.equals("-2") ) {
                    		quitProgram();
                    	}
                    	else 
                    		continue;
                    }

                    System.out.print("INPUT> Type Customer Email Address: "); 
                    if( sc.hasNext() ) 
                    	customerEmail2 = sc.next();

                    String confNumber = app.ticketService.reserveSeats(seatHoldId, customerEmail2);

                    if( confNumber != null && !confNumber.isEmpty()) 
                    	System.out.println("OUTPUT> Reservation Confirmation Code: " + confNumber);
                    else
                    	System.out.println("OUTPUT> This hold id might have been already reserved and committed OR Invalid Hold ID.");

                } while( true );
                break;

            case -2:
                quitProgram();
                break;
        }
    }


    public  static int showMainMenu() {
        System.out.println("");
        System.out.println("**********************************************************************************");
        System.out.println("******************************      Main Menu       ******************************");
        System.out.println("**********************************************************************************");
        System.out.println("1. Find Number of Available Seats.");
        System.out.println("2. Find and Hold Available Seats");
        System.out.println("3. Reserve and Commit Held Seats");
        System.out.println("**********************************************************************************");
        System.out.print("INPUT> Choose a number from 1 ~ 3 and Hit Enter [-2 to Quit]: ");
        
        //Scanner sc = new Scanner(System.in);
        int sel = sc.nextInt();
        //sc.close();
        return sel;
    }
 
    public  static void quitProgram() {
        System.out.println("You have quit the program");
        System.exit(1);
    }
    
    public  void displaySeatHoldIDs() {
    	Set<String> seatHoldIdSet = app.ticketService.getSeatHoldIdSet();
    	for( String seatHoldId : seatHoldIdSet) {
    		System.out.println(seatHoldId);
    	}
    	System.out.println();
    }

	public boolean isValidLevel(int minLevel, int maxLevel ) {
		if( minLevel < MIN_LEVEL || minLevel > MAX_LEVEL || maxLevel < MIN_LEVEL || maxLevel > maxLevel)
			return false;
		if( minLevel > maxLevel )
			return false;
		return true;
	}
}
