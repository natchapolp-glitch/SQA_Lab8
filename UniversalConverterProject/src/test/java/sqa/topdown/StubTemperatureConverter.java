package sqa.topdown;
/*
 * ณัชพล เพ็งพล 673380267-4
 */
import sqa.main.TemperatureConverter;

public class StubTemperatureConverter extends TemperatureConverter {

	public static final double STUB_RETURN_VALUE = 777.0;

	@Override
	public double convert(double tempValue, String fromUnit, String toUnit) {
		return STUB_RETURN_VALUE;
	}
}
