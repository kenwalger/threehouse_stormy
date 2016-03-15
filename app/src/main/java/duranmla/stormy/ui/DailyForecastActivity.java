package duranmla.stormy.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.Arrays;

import duranmla.stormy.R;
import duranmla.stormy.adapters.DailyWeatherAdapter;
import duranmla.stormy.weather.DailyWeather;

public class DailyForecastActivity extends ListActivity {

    private DailyWeather[] mDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

//        example of use of adapters without no custom classes
//        String[] weekDays = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, weekDays);
//        setListAdapter(adapter);

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
        mDays = Arrays.copyOf(parcelables, parcelables.length, DailyWeather[].class);
        DailyWeatherAdapter adapter = new DailyWeatherAdapter(this, mDays);
    }
}
