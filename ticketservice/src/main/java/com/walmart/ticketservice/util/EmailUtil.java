package com.walmart.ticketservice.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.walmart.ticketservice.model.Seat;

public class EmailUtil {
    private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);

	/**
	 * Send reserved seat information to the given customerEmail 
	 * @param emailContent
	 * @param customerEmail
	 * @return
	 */
	public boolean commit( String emailContent, String customerEmail ) {
		
		logger.info("\n\nEmail Sent to " + customerEmail + " with the Content below.");
		logger.info("=========================		Email Content 	======================");
		logger.info(emailContent);
		return true;
	}
	
	/**
	 * Create email content which describes the served set information and total price
	 * @param holdSeatList
	 * @return
	 */
	public String createEmailContent(List<Seat> holdSeatList) {
		int totalPrice = 0;
		
		StringBuilder sb = new StringBuilder();
		
		for( Seat seat : holdSeatList ) {
			int level = seat.getLevel().getLevel();
			int row = seat.getRow();
			int seatNumber = seat.getSeatNumber();
			double levelPrice= seat.getLevel().getLevelPrice();

			sb.append("Level: " + level + ", ");
			sb.append("Row: " + row + ", ");
			sb.append("Seat Number: " + seatNumber + ",  ");
			sb.append("Price for this level: " + levelPrice + " ");
			sb.append("\n");
			
			totalPrice += levelPrice;
		}
		sb.append("Total Price: " + totalPrice);
		
		return sb.toString();
	}

}
