package sqa.topdown;
/*
 * ณัชพล เพ็งพล 673380267-4
 */
import sqa.main.WeightConverter;

public class StubWeightConverter extends WeightConverter {

	public static final double STUB_RETURN_VALUE = 888.0;

	@Override
	public double convert(double massValue, String fromUnit, String toUnit) {
		return STUB_RETURN_VALUE;
	}
}
