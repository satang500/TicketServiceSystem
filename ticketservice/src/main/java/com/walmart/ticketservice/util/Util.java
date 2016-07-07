package com.walmart.ticketservice.util;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.walmart.ticketservice.model.SeatHold;

public class Util extends ConfigUtil {

	/**
	 * Given Optional<Integer> minLvl, Optional<Integer> maxLvl, check edge cases and returns appropriate levels
	 * @param minLvl
	 * @param maxLvl
	 * @return the integer type minLevel and maxLevel
	 */
	public int[] getRangeOfLevel(Optional<Integer> minLvl, Optional<Integer> maxLvl) {
		int[] L = new int[2];
		int minLevel = ConfigUtil.MIN_LEVEL;
		int maxLevel = ConfigUtil.MAX_LEVEL;
		
		if(minLvl.isPresent() && maxLvl.isPresent()) {
			minLevel = minLvl.get();
			maxLevel = maxLvl.get();
			
			if( minLevel < ConfigUtil.MIN_LEVEL || minLevel > ConfigUtil.MAX_LEVEL ) 	minLevel = ConfigUtil.MIN_LEVEL;
			if( maxLevel < ConfigUtil.MIN_LEVEL || maxLevel > ConfigUtil.MAX_LEVEL ) 	maxLevel = ConfigUtil.MAX_LEVEL;
			if( minLevel > maxLevel ) minLevel = ConfigUtil.MIN_LEVEL;
		}
		
		if(minLvl.isPresent() && !maxLvl.isPresent()) {
			minLevel = minLvl.get();
			maxLevel =  ConfigUtil.MAX_LEVEL;
			if( minLevel > maxLevel ) minLevel = ConfigUtil.MIN_LEVEL;
		}
		
		if( !minLvl.isPresent() && maxLvl.isPresent()) {
			minLevel = ConfigUtil.MIN_LEVEL;
			maxLevel = maxLvl.get();
			if( minLevel > maxLevel ) maxLevel = ConfigUtil.MAX_LEVEL;
		}
		
		if( !minLvl.isPresent() && !maxLvl.isPresent()) {
			minLevel = ConfigUtil.MIN_LEVEL;
			maxLevel = ConfigUtil.MAX_LEVEL;
		}
		
		L[0] = minLevel;
		L[1] = maxLevel;
		return L;
	}
	
	/**
	 * Create unique ID
	 * @return
	 */
	public String createId() {
		UUID uuid = UUID.randomUUID();
		return String.valueOf(uuid);
	}

	/**
	 * Print map content for testing
	 * @param map
	 */
	public void printSeatHoldMap( Map<String, SeatHold> map ) {
		for( Map.Entry<String, SeatHold> entry : map.entrySet() ) {
			System.out.println( "<holdID = " + entry.getKey() + ": holdSeat = " + entry.getValue() + ">");
		}
	}
		
}
