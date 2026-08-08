package sqa.bottomup;
/*
 * ณัชพล เพ็งพล 673380267-4
 */
import sqa.main.DistanceConverter;

public class DriverDistance {

	private final DistanceConverter distanceConverter = new DistanceConverter();

	public double run(double value, String fromUnit, String toUnit) {
		return distanceConverter.convert(value, fromUnit, toUnit);
	}
}
