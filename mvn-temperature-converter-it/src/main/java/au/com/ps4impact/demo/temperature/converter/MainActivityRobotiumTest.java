package au.com.ps4impact.demo.temperature.converter;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

/**
 * Robotium tests will fail if your emulator is locked. You can unlock it  
 * 
 */
public class MainActivityRobotiumTest extends ActivityInstrumentationTestCase2<MainActivity>{
	
	//These indexes come from the view hierarchy in the android monitor.
	private static final int TEMPERATURE_FIELD_INDEX = 0;
	private static final int CELCIUS_RADIO_BUTTON_INDEX = 0;
	private static final int FAHRENHEIT_RADIO_BUTTON_INDEX = 1;
	private static final int CALCULATE_BUTTON_INDEX = 2;
	
	//This is the dood who does all the fancy Robotium operations
	private Solo solo;

	public MainActivityRobotiumTest() {
		super(MainActivity.class);
	}
	
	public void setUp() {
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testConversionFromFahrenheitToCelcius() {
		testConversion("14", "-10.0");
		
		solo.clickOnRadioButton(CELCIUS_RADIO_BUTTON_INDEX);
		solo.clearEditText(getTempField());
		testConversion("280", "137.77777");
		
		solo.clickOnRadioButton(CELCIUS_RADIO_BUTTON_INDEX);
		solo.clearEditText(getTempField());
		testConversion("666", "352.22223");
	}
	
	public void testConversionFromCelciusToFahrenheit() {
		//TODO:
	}
	
	public void testCelsiusIsSelectedByDefault() {
		//TODO:
	}
	
	public void testTemperatureButtonsAreMutuallyExclusive() {
		//TODO:
	}
	
	public void testConversionWithNoTemperature() {
		//TODO:
	}

	private void testConversion(String tempToEnter, String expectedResult) {
		solo.enterText(getTempField(), tempToEnter);
		solo.clickOnButton("Calculate");
		
		String tempFieldContents = getTempField().getEditableText().toString();
		assertEquals(expectedResult, tempFieldContents);
	}

	private EditText getTempField() {
		EditText tempField = solo.getEditText(TEMPERATURE_FIELD_INDEX);
		return tempField;
	}
	
	
}
