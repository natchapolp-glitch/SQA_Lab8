package sqa.bottomup;
/*
 * ณัชพล เพ็งพล 673380267-4
 */
import sqa.main.WeightConverter;

public class DriverWeight {

	private final WeightConverter weightConverter = new WeightConverter();

	public double run(double value, String fromUnit, String toUnit) {
		return weightConverter.convert(value, fromUnit, toUnit);
	}
}
