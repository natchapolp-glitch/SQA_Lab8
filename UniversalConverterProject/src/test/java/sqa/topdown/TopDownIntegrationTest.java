package sqa.topdown;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import sqa.main.DistanceConverter;
import sqa.main.WeightConverter;
import sqa.main.TemperatureConverter;
import sqa.main.UniversalConverter;

class TopDownIntegrationTest {
	
	private UniversalConverter buildFullyStubbedUC() {
		UniversalConverter uc = new UniversalConverter();
		uc.setDistanceConverter(new StubDistanceConverter());
		uc.setWeightConverter(new StubWeightConverter());
		uc.setTemperatureConverter(new StubTemperatureConverter());
		return uc;
	}

	@Test
	@DisplayName("TD-1: UniversalConverter -> Distance routed to stub")
	void td1_stage1_routesToDistanceStub() {
		UniversalConverter uc = buildFullyStubbedUC();
		double actual = uc.convert(10, "Distance", "kilometer", "meter");
		assertEquals(StubDistanceConverter.STUB_RETURN_VALUE, actual, 0.0001);
	}

	@Test
	@DisplayName("TD-2: UniversalConverter -> Weight routed to stub")
	void td2_stage1_routesToWeightStub() {
		UniversalConverter uc = buildFullyStubbedUC();
		double actual = uc.convert(5, "Weight", "kilogram", "gram");
		assertEquals(StubWeightConverter.STUB_RETURN_VALUE, actual, 0.0001);
	}

	@Test
	@DisplayName("TD-3: UniversalConverter -> Temperature routed to stub")
	void td3_stage1_routesToTemperatureStub() {
		UniversalConverter uc = buildFullyStubbedUC();
		double actual = uc.convert(0, "Temperature", "C", "F");
		assertEquals(StubTemperatureConverter.STUB_RETURN_VALUE, actual, 0.0001);
	}

	private UniversalConverter buildStage2() {
		UniversalConverter uc = new UniversalConverter();
		uc.setDistanceConverter(new DistanceConverter());          // real
		uc.setWeightConverter(new StubWeightConverter());          // stub
		uc.setTemperatureConverter(new StubTemperatureConverter());// stub
		return uc;
	}

	@Test
	@DisplayName("TD-4: Stage 2 - real Distance module now correct through UniversalConverter")
	void td4_stage2_realDistanceIntegrated() {
		UniversalConverter uc = buildStage2();
		double actual = uc.convert(10, "Distance", "kilometer", "meter");
		assertEquals(10000.0, actual, 0.0001);
	}
	

	private UniversalConverter buildStage3() {
		UniversalConverter uc = new UniversalConverter();
		uc.setDistanceConverter(new DistanceConverter());           // real
		uc.setWeightConverter(new WeightConverter());                // real
		uc.setTemperatureConverter(new StubTemperatureConverter());  // stub
		return uc;
	}

	@Test
	@DisplayName("TD-5: Stage 3 - real Weight module, kilogram->gram (defect: multiplier reversed)")
	void td5_stage3_weightKgToGram_expectsCorrectMath() {
		UniversalConverter uc = buildStage3();
		double actual = uc.convert(5, "Weight", "kilogram", "gram");
		assertEquals(5000.0, actual, 0.0001, "Defect: kilogram->gram multiplier is reversed in WeightConverter");
	}

	@Test
	@DisplayName("TD-6: Stage 3 - real Weight module, gram->kilogram (defect: multiplier reversed)")
	void td6_stage3_weightGramToKg_expectsCorrectMath() {
		UniversalConverter uc = buildStage3();
		double actual = uc.convert(1000, "Weight", "gram", "kilogram");
		assertEquals(1.0, actual, 0.0001, "Defect: gram->kilogram multiplier is reversed in WeightConverter");
	}

	private UniversalConverter buildFullIntegration() {
		UniversalConverter uc = new UniversalConverter();
		uc.setDistanceConverter(new DistanceConverter());
		uc.setWeightConverter(new WeightConverter());
		uc.setTemperatureConverter(new TemperatureConverter());
		return uc;
	}

	@Test
	@DisplayName("TD-7: Stage 4 - full integration, Celsius->Fahrenheit (defect: int division)")
	void td7_stage4_temperatureCtoF_expectsCorrectMath() {
		UniversalConverter uc = buildFullIntegration();
		double actual = uc.convert(100, "Temperature", "C", "F");
		assertEquals(212.0, actual, 0.0001, "Defect: TemperatureConverter C->F uses integer division 9/5");
	}

	@Test
	@DisplayName("TD-8: Stage 4 - full integration, Fahrenheit->Celsius (defect: int division)")
	void td8_stage4_temperatureFtoC_expectsCorrectMath() {
		UniversalConverter uc = buildFullIntegration();
		double actual = uc.convert(212, "Temperature", "F", "C");
		assertEquals(100.0, actual, 0.0001, "Defect: TemperatureConverter F->C uses integer division 5/9");
	}

	@Test
	@DisplayName("TD-9: Stage 4 - full integration, Celsius->Kelvin (no defect)")
	void td9_stage4_temperatureCtoK_isCorrect() {
		UniversalConverter uc = buildFullIntegration();
		double actual = uc.convert(0, "Temperature", "C", "K");
		assertEquals(273.15, actual, 0.0001);
	}

	@Test
	@DisplayName("TD-10: Stage 4 - full integration, Kelvin->Fahrenheit (defect: int division)")
	void td10_stage4_temperatureKtoF_expectsCorrectMath() {
		UniversalConverter uc = buildFullIntegration();
		double actual = uc.convert(100, "Temperature", "K", "F");
		assertEquals(-279.67, actual, 0.01, "Defect: TemperatureConverter K->F uses integer division 9/5");
	}
}
