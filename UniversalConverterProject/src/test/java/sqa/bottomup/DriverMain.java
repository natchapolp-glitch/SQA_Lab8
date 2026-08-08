package sqa.bottomup;

import sqa.main.UniversalConverter;

public class DriverMain {

	private final UniversalConverter converter = new UniversalConverter();

	public double run(double value, String choice, String fromUnit, String toUnit) {
		return converter.convert(value, choice, fromUnit, toUnit);
	}
}
