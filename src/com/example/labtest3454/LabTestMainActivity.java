package com.example.labtest3454;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LabTestMainActivity extends AppCompatActivity {

	EditText edtName, edtWeight, edtHeight, edtStat;
	Button btnSave,btnCal;
	DBbmi mybmiDb ;
	float fltBmi;
	String strDisplayStatus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_lab_test_main);
		edtName = (EditText) findViewById(R.id.edtName);
		edtWeight = (EditText) findViewById(R.id.edtWeight);
		edtHeight = (EditText) findViewById(R.id.edtHeight);
		edtStat = (EditText) findViewById(R.id.edtBMIStat);
		edtStat.setFocusable(false);
		edtStat.setClickable(false);
		mybmiDb = new DBbmi(getApplicationContext());
		mybmiDb.onCreate(mybmiDb.getReadableDatabase());
		
        Runnable run = new Runnable() {
		String strName,strWeight,strHeight,strStat = "";	
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(mybmiDb.fnTotalRow() != 0)
				{
					Cursor cur = mybmiDb.getReadableDatabase().rawQuery("Select * from "+ DBbmi.tblName , null);
					Log.d("Reading sqlite table tbPersonaInfo", "sqlData:");
					 if(cur.moveToFirst())
				    {
					  strName  = cur.getString(cur.getColumnIndex(DBbmi.colName));
				      strWeight = cur.getString(cur.getColumnIndex(DBbmi.colWeight));
				      strHeight = cur.getString(cur.getColumnIndex(DBbmi.colHeight));
				      strStat = cur.getString(cur.getColumnIndex(DBbmi.colStatus));
				    }
					 runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							edtName.setText(strName);
							edtHeight.setText(strHeight);
							edtWeight.setText(strWeight);
							edtStat.setText(strStat);
						}
					});
					 
				}
			}
		};	
		
		 new Thread(run).start();
	  
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lab_test_main, menu);
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
	
	public void fnCalcBMI(View vw)
	{
		float fltWeight = Float.parseFloat(edtWeight.getText().toString());
		float fltHeight = Float.parseFloat(edtHeight.getText().toString());
	    fltBmi = (fltWeight/((fltHeight/100)*(fltHeight/100)));
	    
	    String strStat = "";
	    if(fltBmi < 18.5)
	    	strStat = "Underweight";
	    else if(fltBmi <25)
	    	strStat = "Normal Weight";
	    else if(fltBmi <30)
	    	strStat = "OverWeight";
	    else 
	    	strStat = "Obese!!!";
	    	
	    strDisplayStatus = "Your BMI is: "+ String.valueOf(fltBmi) + " \n Status:"+strStat;
	    edtStat.setText(strDisplayStatus);
	}
	
	public void fnSave(View vw)
	{
		Runnable run = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String strName,strWeight,strHeight;
				strName = edtName.getText().toString();
				strHeight = edtHeight.getText().toString();
				strWeight = edtWeight.getText().toString();
				String strStatToSave = (edtStat.getText().toString()!=""? edtStat.getText().toString() : "Please recalculate"  );
				String strInsertSql = "Insert into "+ DBbmi.tblName +" ("+ DBbmi.colName + ", " + DBbmi.colHeight + ", "+ DBbmi.colWeight +", "+ DBbmi.colStatus +")  values ( '"
				+ strName +"', '"+ strHeight +"', '"+ strWeight +"', '"+ strStatToSave + "' );";
				Log.d(strInsertSql, "sqlData:");
				mybmiDb.fnClearData(); 
				mybmiDb.fnExecuteSql(strInsertSql, getApplicationContext());
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "Your Details Successfully Save!", Toast.LENGTH_SHORT).show();
					}
				});
			}
		};
		
		 new Thread(run).start();
	}
}
