package com.svs.willkommen;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class HIstograms {

	public static void main(String[] args) {
		double value = -109220.09238421;

		long time = System.currentTimeMillis();

		double result1 = 0.0;
		for (int i = 0; i < 100000000; i++) {
			result1 = BigDecimal.valueOf(value += 0.005).setScale(4, RoundingMode.HALF_DOWN).doubleValue();
		}
		System.out.println((System.currentTimeMillis() - time) + "  value== " + result1);

		value = -109220.09238421;
		time = System.currentTimeMillis();
		double result2 = 0.0;
		for (int i = 0; i < 100000000; i++) {
			result2 = round(value += 0.005, 4);
		}
		System.out.println((System.currentTimeMillis() - time) + "  value== " + result2);

	}

	static int minimumPrecisionPlaces(double d) {

		int places = 2;
		double temp = d;
		if (d > 0.0 && d < 1.0) {
			while ((temp = temp * 10) < 1.0)
				places++;
		} else if (d < 0.0 && d > -1.0) {
			while ((temp = temp * 10) > 1.0)
				places++;
		}
		return places;
	}

	static double round(double number, int places) {
		long rational_part = (long) number;
		long tens = inTens(places);
		double decimal_part = (number - rational_part) * tens;
		long rational_decimal_part = (long) decimal_part;
		double rounding_factor = (decimal_part - rational_decimal_part);
		if (rounding_factor >= 0) {
			rounding_factor = rounding_factor >= 0.5 ? 1.0 : 0.0;
		} else {
			rounding_factor = rounding_factor < -0.5 ? -1.0 : 0.0;
		}
		number = rational_part + (rational_decimal_part + rounding_factor) / tens;
		return number;
	}

	static long inTens(int f) {
		// Math.pow(...) have complex steps.
		switch (f) {
		case 1:
			return 10;
		case 2:
			return 100;
		case 3:
			return 1000;
		case 4:
			return 10000;
		case 5:
			return 100000;
		case 6:
			return 1000000;
		case 7:
			return 10000000;
		case 8:
			return 100000000;
		default:
			return 1;
		}
	}
}
