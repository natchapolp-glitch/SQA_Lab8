package sqa.bottomup;

import sqa.main.TemperatureConverter;


public class DriverTemperature {

	private final TemperatureConverter temperatureConverter = new TemperatureConverter();

	public double run(double value, String fromUnit, String toUnit) {
		return temperatureConverter.convert(value, fromUnit, toUnit);
	}
}
