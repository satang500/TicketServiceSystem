package com.walmart.ticketservice.model;

import java.util.Optional;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.walmart.ticketservice.repository.TicketServiceRepositoryImpl;
import com.walmart.ticketservice.util.ConfigUtil;

public class TicketSystemTest2 extends ConfigUtil {
	static TicketSystem ticketSystem;

	@BeforeClass
	public static void init() {
		ticketSystem = new TicketSystem();
	}

	/**
	 * Sleep some seconds after two hold seat requests. 	( seatHoldMap size = 2 ==> size1 )
	 * During this sleep, this two hold seats are expired.  ( seatHoldMap size = 0 )
	 * third hold seat request. 							( seatHoldMap size = 0 ==> size2 )
	 */
	@Test
	public void makeAvailableOfExpiredHoldSeatsOfAllLevels() {
		int minLvl = ConfigUtil.MIN_LEVEL;
        int maxLvl = ConfigUtil.MAX_LEVEL;
        Optional<Integer> minLevel = Optional.of(minLvl);
        Optional<Integer> maxLevel = Optional.of(maxLvl);

        TicketServiceRepositoryImpl tsi = new TicketServiceRepositoryImpl();
		
		tsi.findAndHoldSeats(3, minLevel, maxLevel, "aaa.gmail.com");
		tsi.findAndHoldSeats(13, minLevel, maxLevel, "bbb.gmail.com");

		int holdIdSize1= TicketSystem.getSeatHoldMap().size();
		//tsi.viewTicketSystem();
		
		long sleepTime = ( ConfigUtil.HOLD_EXPIRE_SEC * 1000) + 10000;
		System.out.println("sleepTime in miliseconds = " + sleepTime);
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		tsi.findAndHoldSeats(5, minLevel, maxLevel, "CCC@gmail.com");
		int holdIdSize2 = TicketSystem.getSeatHoldMap().size();
		//tsi.viewTicketSystem();
		Assert.assertTrue(holdIdSize1 > holdIdSize2);
	}
}
