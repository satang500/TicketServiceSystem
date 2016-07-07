package com.walmart.ticketservice.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class ConfigUtil {

	public static int MIN_LEVEL;
	public static int MAX_LEVEL;

	public static String[] LEVEL_NAMES;
	public static double[] LEVEL_PRICES;
	public static int[] LEVEL_ROWS;
	public static int[] LEVEL_SEATS;
	
	public static long HOLD_EXPIRE_SEC;
	
	public ConfigUtil() {
		Configuration config;
		try {
			config = new PropertiesConfiguration("config.properties");
			
			HOLD_EXPIRE_SEC = config.getLong("hold.expire.sec");
			
			MIN_LEVEL = config.getInt("min.level");
			MAX_LEVEL = config.getInt("max.level");
			
			
			LEVEL_NAMES  = new String[MAX_LEVEL + 1];
			LEVEL_PRICES = new double[MAX_LEVEL + 1];
			LEVEL_ROWS   = new int[MAX_LEVEL + 1];
			LEVEL_SEATS  = new int[MAX_LEVEL + 1];
			
			for( int i = 0; i <= MAX_LEVEL; i++) {
				LEVEL_NAMES[i] = config.getString("level.name"+i);
				LEVEL_PRICES[i] = config.getDouble("level.price"+i);
				LEVEL_ROWS[i] = config.getInt("level.rows"+i);
				LEVEL_SEATS[i] = config.getInt("level.seats"+i);
			}
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
}
