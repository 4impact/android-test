package au.com.ps4impact.demo.temperature.converter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private EditText textField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textField = (EditText) findViewById(R.id.temperatureField);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void calcButtonClicked(View view) {
		if (textField.getText().length() == 0) {
			Toast.makeText(this, "Please enter a temperature to convert", Toast.LENGTH_LONG).show();
		} else {
			convertValueAndToggleRadios();
		}
	}

	private void convertValueAndToggleRadios() {
		RadioButton celsiusButton = (RadioButton) findViewById(R.id.celciusRadioButton);
		RadioButton farenheitButton = (RadioButton) findViewById(R.id.farenheitRadioButton);
		float convertedValue = getConvertedInputValue(celsiusButton);
		textField.setText(String.valueOf(convertedValue));
		toggleRadioButtons(celsiusButton, farenheitButton);
	}

	private float getConvertedInputValue(RadioButton celsiusButton) {
		float inputValue = Float.parseFloat(textField.getText().toString());
		if (celsiusButton.isChecked()) {
			return convertFarenheitToCelsius(inputValue);
		} else {
			return convertCelsiusToFarenheit(inputValue);
		}
	}
	
	private void toggleRadioButtons(RadioButton radioOne, RadioButton radioTwo) {
		radioOne.setChecked(!radioOne.isChecked());
		radioTwo.setChecked(!radioTwo.isChecked());
	}
	 
	private float convertFarenheitToCelsius(float fahrenheit) {
		return ((fahrenheit - 32) * 5 / 9);
	}
	
	private float convertCelsiusToFarenheit(float celsius) {
		return ((celsius * 9) / 5) + 32;
	}


}
