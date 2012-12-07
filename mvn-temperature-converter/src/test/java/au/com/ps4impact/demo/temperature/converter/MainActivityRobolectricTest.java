package au.com.ps4impact.demo.temperature.converter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowActivity;
import com.xtremelabs.robolectric.shadows.ShadowToast;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class MainActivityRobolectricTest {
	
	private MainActivity mainActivity;
	
	@Before
	public void setUp() {
		mainActivity = new MainActivity();
		mainActivity.onCreate(null);
	}
	
	@Test
	public void shouldHaveButtonThatSaysCalculate() {
		View calcButton = getById(R.id.calcButton);
		assertEquals(Button.class, calcButton.getClass());
		assertEquals("Calculate", ((Button)calcButton).getText());
	}

	private View getById(int id) {
		return mainActivity.findViewById(id);
	}
	
	@Test
	public void shouldHaveRadioButtonLabelledCelcius() {
		View radioButton = getById(R.id.celciusRadioButton);
		assertEquals(RadioButton.class, radioButton.getClass());
		assertEquals("Fahrenheit to Celsius", ((RadioButton)radioButton).getText());
	}
	
	@Test
	public void shouldHaveRadioButtonLabelledFahrenheit() {
		View radioButton = getById(R.id.farenheitRadioButton);
		assertEquals(RadioButton.class, radioButton.getClass());
		assertEquals("Celsius to Fahrenheit", ((RadioButton)radioButton).getText());
	}
	
	@Test
	public void celciusShouldBeCheckedByDefault() {
		RadioButton celciusRadioButton = (RadioButton)getById(R.id.celciusRadioButton);
		RadioButton farenheitRadioButton = (RadioButton)getById(R.id.farenheitRadioButton);
		assertTrue(celciusRadioButton.isChecked());
		assertFalse(farenheitRadioButton.isChecked());
	}

	
	@Test
	public void thereShouldBeATextFieldForTheTemperature() {
		View tempField = getById(R.id.temperatureField);
		assertNotNull(tempField);
		assertEquals(EditText.class, tempField.getClass());
		//Sadly, you can't test the hint text with Robolectric..
	}
	
	@Test
	public void testConversionFromFahrenheitToCelcius() {		
		RadioButton celciusRadioButton = (RadioButton)getById(R.id.celciusRadioButton);
		checkConversion("14", "-10.0");
		
		Robolectric.clickOn(celciusRadioButton);		
		checkConversion("280", "137.77777");
		
		Robolectric.clickOn(celciusRadioButton);		
		checkConversion("666", "352.22223");
		
	}

	private void checkConversion(String originalValue, String expectedValue) {
		EditText tempField = (EditText) getById(R.id.temperatureField);
		Button calcButton = (Button)getById(R.id.calcButton);
		tempField.setText(originalValue);
		mainActivity.calcButtonClicked(calcButton);
		assertEquals(expectedValue,tempField.getText().toString());
	}
	
	@Test
	public void testConversionFromCelciusToFahrenheit() {
		RadioButton farenheitRadioButton = (RadioButton)getById(R.id.farenheitRadioButton);
		RadioButton celciusRadioButton = (RadioButton)getById(R.id.celciusRadioButton);
		Robolectric.clickOn(celciusRadioButton);		
		Robolectric.clickOn(farenheitRadioButton);
		checkConversion("64", "147.2");
		
		Robolectric.clickOn(celciusRadioButton);		
		Robolectric.clickOn(farenheitRadioButton);		
		checkConversion("141.88", "287.384");
		
		Robolectric.clickOn(celciusRadioButton);		
		Robolectric.clickOn(farenheitRadioButton);		
		checkConversion("-13", "8.6");
	}

	
	@Test
	public void testHittingCalculateWithoutATemperature() {
		Button calcButton = (Button)getById(R.id.calcButton);
		mainActivity.calcButtonClicked(calcButton);
		assertEquals("Please enter a temperature to convert",ShadowToast.getTextOfLatestToast());
	}

}
