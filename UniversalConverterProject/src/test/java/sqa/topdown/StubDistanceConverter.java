package sqa.topdown;

import sqa.main.DistanceConverter;

public class StubDistanceConverter extends DistanceConverter {

	public static final double STUB_RETURN_VALUE = 999.0;

	@Override
	public double convert(double distanceValue, String fromUnit, String toUnit) {
		return STUB_RETURN_VALUE;
	}
}
