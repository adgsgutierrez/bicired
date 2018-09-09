package co.com.konrad.bicired.view;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

import co.com.konrad.bicired.R;

public class CreateEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    Button btn_fecha;
    TextView textfecha;
    int day,month,year,hour,minutes;
    int dayFinal,monthFinal,yearFinal,hourFinal,minutesFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event2);

        btn_fecha = (Button) findViewById(R.id.btnfecha);
        textfecha =(TextView) findViewById(R.id.textfecha);

        btn_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateEventActivity.this,CreateEventActivity.this,year,month,day);
                datePickerDialog.show();

            }
        });

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
yearFinal = i;
monthFinal = i1+1;
dayFinal = i2;

Calendar c = Calendar.getInstance();
hour = c.get(Calendar.HOUR_OF_DAY);
minutes = c.get(Calendar.MINUTE);

TimePickerDialog timePickerDialog = new TimePickerDialog(CreateEventActivity.this,CreateEventActivity.this,hour,minutes,true);
timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
    hourFinal = i;
    minutes = i1;

        textfecha.setText(dayFinal+"/"+monthFinal+"/"+yearFinal+" "+hourFinal+":"+minutes);
    }
}
