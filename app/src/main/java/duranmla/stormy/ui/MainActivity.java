package duranmla.stormy.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import duranmla.stormy.R;
import duranmla.stormy.weather.CurrentlyWeather;
import duranmla.stormy.weather.Forecast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public static final String HOURLY_FORECAST = "HOURLY_FORECAST";
    private final String TAG = MainActivity.class.getSimpleName();
    public static final String DAILY_FORECAST = "DAILY_FORECAST";

    private Forecast mForecast;

    // this boilerplate code allow us to declare members variables and get its value
    @Bind(R.id.locationLabel) TextView mLocationLabel;
    @Bind(R.id.timeLabel) TextView mTimeLabel;
    @Bind(R.id.temperatureLabel) TextView mTemperatureLabel;
    @Bind(R.id.humidityValue) TextView mHumidityValue;
    @Bind(R.id.precipValue) TextView mPrecipValue;
    @Bind(R.id.summaryLabel) TextView mSummaryLabel;
    @Bind(R.id.iconImageView) ImageView mIconImageView;
    @Bind(R.id.progressBar) ProgressBar mProgressBar;
    @Bind(R.id.refreshImageView) ImageView mRefreshImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final double latitude = 37.8267;
        final double longitude = -122.423;

        // make butter knife work as expected getting all the data
        ButterKnife.bind(this);

        mProgressBar.setVisibility(View.INVISIBLE);
        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast(latitude, longitude);
            }
        });

        getForecast(latitude, longitude);
    }

    private void getForecast(double latitude, double longitude) {
        String APIUrl = "https://api.forecast.io/forecast/%1$s/%2$s,%3$s";
        String APIKey = "23484fbc98c00a8767918b9e89734d28";

        final String targetURL = String.format(APIUrl, APIKey, latitude, longitude);

        if (isNetworkAvailable()) {
            toggleRefresh();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(targetURL).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.v(TAG, "Request fail!");
                    toggleRefresh();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            mForecast = getCurrentForecast(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                        toggleRefresh();
                    } catch (IOException e) {
                        Log.e(TAG, "caught exception: ", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "caught exception: ", e);
                    }
                }
            });
        } else {
            Toast.makeText(MainActivity.this, R.string.network_unavailable_message, Toast.LENGTH_LONG).show();
        }
    }

    private void toggleRefresh() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressBar.getVisibility() == View.INVISIBLE) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mRefreshImageView.setVisibility(View.INVISIBLE);
                } else {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mRefreshImageView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void updateDisplay() {
        CurrentlyWeather currentlyWeather = mForecast.getCurrentlyWeather();
        // we need to add support to GPS to be able to change it
        mLocationLabel.setText("Los Angeles, CA");

        mTemperatureLabel.setText(currentlyWeather.getTemperature() + "");
        mHumidityValue.setText(currentlyWeather.getHumidity() + "");
        mPrecipValue.setText(currentlyWeather.getPrecipitation() + "%");
        mSummaryLabel.setText(currentlyWeather.getSummary());
        mTimeLabel.setText("At " + currentlyWeather.getFormattedTime() + " it will be:");

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), currentlyWeather.getIconId(), null);
        mIconImageView.setImageDrawable(drawable);
    }

    private Forecast getCurrentForecast(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        return new Forecast(jsonObject);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }

    @OnClick (R.id.dailyButton)
    public void startDailyForecastActivity(View view) {
        Intent intent = new Intent(this, DailyForecastActivity.class);
        intent.putExtra(DAILY_FORECAST, mForecast.getDailyForecast());
        startActivity(intent);
    }

    @OnClick (R.id.hourlyButton)
    public void startHourlyForecastActivity(View view) {
        Intent intent = new Intent(this, HourlyForecastActivity.class);
        intent.putExtra(HOURLY_FORECAST, mForecast.getHourlyForecast());
        startActivity(intent);
    }
}
