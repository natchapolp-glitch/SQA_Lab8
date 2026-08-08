package sqa.main;
/*
 * Lab assignment#8 Integration testing
 *
 * Convert value in one unit to other units
 *
 * @author Asst.Prof. Chitsutha Soomlek, College of Computing, KKU
 * @version 1.2 - added package-private setters so lower modules can be
 *                substituted with stubs during top-down integration testing.
 *                Runtime behaviour for normal callers (no-arg constructor,
 *                which wires up the real converters) is unchanged.
 */

public class UniversalConverter {

		private DistanceConverter distanceConverter;
		private WeightConverter weightConverter;
		private TemperatureConverter tempConverter;

		//Constructor
		public UniversalConverter() {
			this.distanceConverter = new DistanceConverter();
			this.weightConverter = new WeightConverter();
			this.tempConverter = new TemperatureConverter();
		}

		// Test seams used only by the top-down integration tests to inject stubs
		// in place of the real lower-level modules. Public so the sqa.topdown
		// test package (a different package from sqa.main) can call them.
		public void setDistanceConverter(DistanceConverter d) { this.distanceConverter = d; }
		public void setWeightConverter(WeightConverter w) { this.weightConverter = w; }
		public void setTemperatureConverter(TemperatureConverter t) { this.tempConverter = t; }

		/*
		 * Convert a value from one unit to another unit
		 *
		 * @parameter value = the value to convert
		 * @parameter choice = selected choice (what to convert)
		 * @parameter fromUnit = the unit of the value, e.g, meter
		 * @parameter toUnit = the unit to convert the value to, e.g., kilometer
		 * @return the converted value
		*/
		public double convert(double value, String choice, String fromUnit, String toUnit) {
			double result = 0.0;

			if (choice.equals("Distance")) {
				result = distanceConverter.convert(value, fromUnit, toUnit);
			} else if (choice.equals("Weight")) {
				result = weightConverter.convert(value, fromUnit, toUnit);
			} else if (choice.equals("Temperature")) {
				result = tempConverter.convert(value, fromUnit, toUnit);
			}

			return result;
		}
}
