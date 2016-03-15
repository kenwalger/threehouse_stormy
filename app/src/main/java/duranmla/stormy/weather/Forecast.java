package duranmla.stormy.weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import duranmla.stormy.R;

public class Forecast {
    private CurrentlyWeather mCurrentlyWeather;
    private HourlyWeather[] mHourlyForecast;
    private DailyWeather[] mDailyForecast;

    public Forecast(JSONObject data) throws JSONException {
        setCurrentlyWeather(data);
        setHourlyForecast(data);
        setDailyForecast(data);
    }

    public static int getIconId(String iconString) {
        int iconId = R.drawable.clear_day;

        if (iconString.equals("clear-day")) {
            iconId = R.drawable.clear_day;
        }
        else if (iconString.equals("clear-night")) {
            iconId = R.drawable.clear_night;
        }
        else if (iconString.equals("rain")) {
            iconId = R.drawable.rain;
        }
        else if (iconString.equals("snow")) {
            iconId = R.drawable.snow;
        }
        else if (iconString.equals("sleet")) {
            iconId = R.drawable.sleet;
        }
        else if (iconString.equals("wind")) {
            iconId = R.drawable.wind;
        }
        else if (iconString.equals("fog")) {
            iconId = R.drawable.fog;
        }
        else if (iconString.equals("cloudy")) {
            iconId = R.drawable.cloudy;
        }
        else if (iconString.equals("partly-cloudy-day")) {
            iconId = R.drawable.partly_cloudy;
        }
        else if (iconString.equals("partly-cloudy-night")) {
            iconId = R.drawable.cloudy_night;
        }

        return iconId;
    }

    public CurrentlyWeather getCurrentlyWeather() {
        return mCurrentlyWeather;
    }

    public void setCurrentlyWeather(JSONObject data) throws JSONException {
        mCurrentlyWeather = new CurrentlyWeather(data);
    }

    public HourlyWeather[] getHourlyForecast() {
        return mHourlyForecast;
    }

    public void setHourlyForecast(JSONObject forecast) throws JSONException {
        String timezone = forecast.getString("timezone");
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        mHourlyForecast = new HourlyWeather[data.length()];

        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonData = data.getJSONObject(i);
            mHourlyForecast[i] = new HourlyWeather(timezone, jsonData);;
        }
    }

    public DailyWeather[] getDailyForecast() {
        return mDailyForecast;
    }

    public void setDailyForecast(JSONObject forecast) throws JSONException {
        String timezone = forecast.getString("timezone");
        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");

        mDailyForecast = new DailyWeather[data.length()];

        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonData = data.getJSONObject(i);
            mDailyForecast[i] = new DailyWeather(timezone, jsonData);
        }
    }
}
