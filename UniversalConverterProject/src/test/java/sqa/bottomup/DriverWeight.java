package sqa.bottomup;

import sqa.main.WeightConverter;

public class DriverWeight {

	private final WeightConverter weightConverter = new WeightConverter();

	public double run(double value, String fromUnit, String toUnit) {
		return weightConverter.convert(value, fromUnit, toUnit);
	}
}
