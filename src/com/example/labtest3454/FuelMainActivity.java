package com.example.labtest3454;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class FuelMainActivity extends AppCompatActivity {

	EditText edtCurrPrice, edtPurchasePrice,edtLiter;
	DbFuel dbfuel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fuel_main);
		
		edtCurrPrice = (EditText)findViewById(R.id.edtCurrPrice);
		edtPurchasePrice = (EditText) findViewById(R.id.edtPurchase);
		edtLiter = (EditText) findViewById(R.id.edtLiter);
		
		dbfuel = new DbFuel(getApplicationContext());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fuel_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void fnCalc(View vw)
	{
		float fltCurrPrice = Float.parseFloat(edtCurrPrice.getText().toString());
		float fltPurchase = Float.parseFloat(edtPurchasePrice.getText().toString());
		float fltLiter = fltPurchase/fltCurrPrice;
		
		edtLiter.setText(String.valueOf(fltLiter));
	}
	
	public void fnSave(View vw)
	{
		String strPurchase = edtPurchasePrice.getText().toString();
		String strCurrent = edtCurrPrice.getText().toString();
		String strStatus = edtLiter.getText().toString();
		String strQry = "Insert into"+ DbFuel.tblName + "values ("+ strCurrent +", " + strPurchase  +","+ strStatus  + ")" ;
		
		dbfuel.getReadableDatabase().rawQuery(strQry,null);
			
	}
}
