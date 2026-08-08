# Universal Converter — Lab#8 Integration Testing

CP353201 Software Quality Assurance, 1/2569

## Program structure

```
                 UniversalConverter
                /        |          \
   DistanceConverter WeightConverter TemperatureConverter
```

`UniversalConverter` is the single root/top module. It has no parent (nothing
above it yet — a GUI or CLI would normally call it). `DistanceConverter`,
`WeightConverter`, and `TemperatureConverter` are its three direct children,
all at the same depth, and each is a leaf (calls nothing else).

## How to build/run

```
mvn test
```

(Requires Maven with internet access to resolve `junit-jupiter` 5.10.2 from
Maven Central the first time.)

## Layout

- `src/main/java/sqa/main/` — the four production classes. `DistanceConverter`,
  `WeightConverter`, and `TemperatureConverter` are unmodified from the
  starter code. `UniversalConverter` has three small **public setters** added
  (`setDistanceConverter`, `setWeightConverter`, `setTemperatureConverter`) so
  tests can inject stubs — this is the only change to production logic, and
  the no-arg constructor still wires up the real converters by default.
- `src/test/java/sqa/topdown/` — `StubDistanceConverter`, `StubWeightConverter`,
  `StubTemperatureConverter` (each returns a distinct fixed value: 999.0,
  888.0, 777.0) and `TopDownIntegrationTest.java`, which tests
  `UniversalConverter` first with all three children stubbed, then
  progressively swaps in the real Distance, Weight, and Temperature modules.
- `src/test/java/sqa/bottomup/` — `DriverDistance`, `DriverWeight`,
  `DriverTemperature` (each drives one leaf module in isolation) and
  `DriverMain` (drives the fully-integrated `UniversalConverter`, standing in
  for the not-yet-built caller/GUI), plus `BottomUpIntegrationTest.java`,
  which tests each leaf module standalone first and finishes with full
  integration.

## Defects found by these integration tests

Both test suites deliberately assert the mathematically correct answer, so a
handful of tests **fail on purpose** — that failure is the test suite doing
its job. Three real defects in the starter code were uncovered:

1. **`WeightConverter`: kilogram↔gram multiplier is reversed.**
   `kilogram → gram` uses `1.0/1000` (should be `1000`), and
   `gram → kilogram` uses `1000` (should be `1.0/1000`). Example: 5 kg should
   convert to 5000 g; the code returns 0.005.
2. **`WeightConverter`: typo `"once"` instead of `"ounce"`.**
   Any conversion *from* ounces silently falls through with no matching
   branch, so the multiplier stays at its default of `1.0` and the value is
   returned unconverted. Example: 16 oz → lbs should be 1.0; the code returns
   16.0.
3. **`TemperatureConverter`: integer division.**
   `tempValue*(9/5)+32` and `(tempValue-32)*(5/9)` use `int` literals, so Java
   truncates `9/5` to `1` and `5/9` to `0` before the multiplication ever
   happens. Every Fahrenheit↔Celsius/Kelvin conversion is wrong except where
   the input happens to make the error cancel out (e.g. 0°C → 32°F still
   works by coincidence, since `0 * 1 = 0 * 1.8`).

`DistanceConverter` has no known defects — all conversions checked matched
the correct math.

Bottom-up testing catches defects #1–#3 earlier than top-down testing does,
because each leaf module is exercised standalone before ever being wired into
`UniversalConverter`.
