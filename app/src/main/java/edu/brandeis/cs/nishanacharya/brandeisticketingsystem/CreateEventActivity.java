package edu.brandeis.cs.nishanacharya.brandeisticketingsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editEventName;
    private EditText editEventDescription;
    private EditText editEventLocation;
    private DatePicker editDate;
    private String date;
    private String time;
    private TimePicker editTime;
    private Button buttonCreateEvent;
    private EventDataHandler eventDataHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        eventDataHandler = new EventDataHandler(this);
        createEvent();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu item) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.directory,item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.home_Button:
                Intent homeIntent = new Intent(this, HomeActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    private void createEvent() {
        date = "";
        time = "";
        editEventName = (EditText) findViewById(R.id.editEventName);
        editEventDescription = (EditText) findViewById(R.id.editEventDescription);
        editEventLocation = (EditText) findViewById(R.id.editEventLocation);
        editDate = (DatePicker) findViewById(R.id.editEventDate);
        editDate.init(editDate.getYear(), editDate.getMonth(), editDate.getDayOfMonth(),new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker arg0, int year, int monthIn, int dayIn) {
                String day = "" + dayIn;
                String month = "" + monthIn;
                monthIn++;
                if(dayIn < 10){
                    day = "0" + dayIn;
                }
                if(monthIn < 10){
                    month = "0" + monthIn;
                }
                date = year + "/" + month + "/" + day;
            }
        } );
        editTime = findViewById(R.id.editEventTime);
        editTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            public void onTimeChanged(TimePicker view, int hourOfDayIn, int minuteIn) {
                String hourOfDay = "" + hourOfDayIn;
                String minute = "" + minuteIn;
                if(hourOfDayIn < 10){
                    hourOfDay = "0" + hourOfDay;
                }
                if(minuteIn < 10){
                    minute = "0" + minuteIn;
                }
                time = hourOfDay + ":" + minute + ":00";
            }
        });

        buttonCreateEvent = (Button) findViewById(R.id.buttonCreateEvent);
        buttonCreateEvent.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == buttonCreateEvent){
            Log.w("Create Event",editEventName.getText().toString() + " " + editEventDescription.getText().toString() + " " + editEventLocation.getText().toString()  + " " + date + " " + time);

            if(editEventName.getText().toString().equals("") || editEventDescription.getText().toString().equals("")|| editEventLocation.getText().toString().equals("")|| date.equals("")|| time.equals("")){
                Toast.makeText(CreateEventActivity.this, "Please enter all the information", Toast.LENGTH_SHORT).show();
            } else {
                eventDataHandler.insertEvent(editEventName.getText().toString(), editEventDescription.getText().toString(), editEventLocation.getText().toString(),
                        date, time);
                finish();
                startActivity(new Intent(CreateEventActivity.this, HomeActivity.class));
            }
        }
    }
}
