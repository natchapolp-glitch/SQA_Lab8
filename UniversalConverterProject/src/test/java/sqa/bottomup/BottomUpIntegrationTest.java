package sqa.bottomup;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
class BottomUpIntegrationTest {

	@Test
	@DisplayName("BU-1: DistanceConverter via driver, kilometer->meter")
	void bu1_distance_kmToMeter() {
		DriverDistance driver = new DriverDistance();
		double actual = driver.run(1, "kilometer", "meter");
		assertEquals(1000.0, actual, 0.0001);
	}

	@Test
	@DisplayName("BU-2: DistanceConverter via driver, mile->kilometer")
	void bu2_distance_mileToKm() {
		DriverDistance driver = new DriverDistance();
		double actual = driver.run(10, "mile", "kilometer");
		assertEquals(16.09, actual, 0.01);
	}

	@Test
	@DisplayName("BU-3: WeightConverter via driver, kilogram->gram (defect: multiplier reversed)")
	void bu3_weight_kgToGram_expectsCorrectMath() {
		DriverWeight driver = new DriverWeight();
		double actual = driver.run(5, "kilogram", "gram");
		assertEquals(5000.0, actual, 0.0001, "Defect: kilogram->gram multiplier is reversed in WeightConverter");
	}

	@Test
	@DisplayName("BU-4: WeightConverter via driver, ounce->lbs (defect: 'once' typo instead of 'ounce')")
	void bu4_weight_ounceToLbs_expectsCorrectMath() {
		DriverWeight driver = new DriverWeight();
		double actual = driver.run(16, "ounce", "lbs");
		assertEquals(1.0, actual, 0.0001, "Defect: WeightConverter checks \"once\" instead of \"ounce\"");
	}

	@Test
	@DisplayName("BU-5: TemperatureConverter via driver, 0C->F (no defect - truncation cancels out at 0)")
	void bu5_temperature_zeroCtoF_isCorrectByCoincidence() {
		DriverTemperature driver = new DriverTemperature();
		double actual = driver.run(0, "C", "F");
		assertEquals(32.0, actual, 0.0001);
	}

	@Test
	@DisplayName("BU-6: TemperatureConverter via driver, 100C->F (defect: int division 9/5)")
	void bu6_temperature_hundredCtoF_expectsCorrectMath() {
		DriverTemperature driver = new DriverTemperature();
		double actual = driver.run(100, "C", "F");
		assertEquals(212.0, actual, 0.0001, "Defect: TemperatureConverter C->F uses integer division 9/5");
	}

	@Test
	@DisplayName("BU-7: TemperatureConverter via driver, 32F->C (no defect - truncation cancels out at 0)")
	void bu7_temperature_thirtyTwoFtoC_isCorrectByCoincidence() {
		DriverTemperature driver = new DriverTemperature();
		double actual = driver.run(32, "F", "C");
		assertEquals(0.0, actual, 0.0001);
	}

	@Test
	@DisplayName("BU-8: TemperatureConverter via driver, 212F->C (defect: int division 5/9)")
	void bu8_temperature_twoTwelveFtoC_expectsCorrectMath() {
		DriverTemperature driver = new DriverTemperature();
		double actual = driver.run(212, "F", "C");
		assertEquals(100.0, actual, 0.0001, "Defect: TemperatureConverter F->C uses integer division 5/9");
	}
	
	@Test
	@DisplayName("BU-9: Full integration via DriverMain, Distance (no defect)")
	void bu9_fullIntegration_distance() {
		DriverMain driver = new DriverMain();
		double actual = driver.run(10, "Distance", "kilometer", "meter");
		assertEquals(10000.0, actual, 0.0001);
	}

	@Test
	@DisplayName("BU-10: Full integration via DriverMain, Weight (defect persists after integration)")
	void bu10_fullIntegration_weight_expectsCorrectMath() {
		DriverMain driver = new DriverMain();
		double actual = driver.run(5, "Weight", "kilogram", "gram");
		assertEquals(5000.0, actual, 0.0001, "Defect confirmed present after full integration");
	}
}
