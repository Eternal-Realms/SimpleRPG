package com.github.akagiant.simplerpg.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class Util {

	private Util() {
		//no instance
	}
	
	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();
		
		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	/**
	 *
	 * @param unsorted | A Map of unsorted values and their chance.
	 * @return a sorted Map<String, Double>
	 */
	public static Map<String, Double> sortLowestToHighest(Map<String, Double> unsorted) {
		return unsorted.entrySet().stream()
			.sorted(Comparator.comparingDouble(Map.Entry::getValue))
			.collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
				(a,b)->b, LinkedHashMap::new));
	}
	
}
