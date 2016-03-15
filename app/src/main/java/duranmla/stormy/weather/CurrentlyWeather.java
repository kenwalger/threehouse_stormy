package duranmla.stormy.weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import duranmla.stormy.R;

public class CurrentlyWeather {
    private String mIcon;
    private long mTime;
    private double mTemperature;
    private double mHumidity;
    private double mPrecipitation;
    private String mSummary;
    private String mTimeZone;

    public CurrentlyWeather(JSONObject data) throws JSONException{
        JSONObject forecast = data.getJSONObject("currently");

        setTimeZone(data.getString("timezone"));

        setIcon(forecast.getString("icon"));
        setTime(forecast.getLong("time"));
        setTemperature(forecast.getDouble("temperature"));
        setHumidity(forecast.getDouble("humidity"));
        setPrecipitation(forecast.getDouble("precipProbability"));
        setSummary(forecast.getString("summary"));
    }

    public CurrentlyWeather(String timeZone, JSONObject data) throws JSONException{
        setTimeZone(timeZone);
        setIcon(data.getString("icon"));
        setTime(data.getLong("time"));
        setTemperature(data.getDouble("temperature"));
        setHumidity(data.getDouble("humidity"));
        setPrecipitation(data.getDouble("precipProbability"));
        setSummary(data.getString("summary"));
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public int getPrecipitation() {
        int prepPorcentage = (int) (mPrecipitation * 100);
        return Math.round(prepPorcentage);
    }

    public void setPrecipitation(double precipitation) {
        mPrecipitation = precipitation;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public int getTemperature() {
        return (int) Math.round(mTemperature);
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public long getTime() {
        return mTime;
    }

    public String getFormattedTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date dateTime = new Date(getTime()*1000);
        String timeString = formatter.format(dateTime);

        return timeString;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public int getIconId() {
        return Forecast.getIconId(mIcon);
    }
}
