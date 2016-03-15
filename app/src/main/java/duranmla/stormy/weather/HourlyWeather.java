package duranmla.stormy.weather;

import org.json.JSONException;
import org.json.JSONObject;

public class HourlyWeather {
    private long mTime;
    private String mSummary;
    private double mTemperature;
    private String mIcon;
    private String mTimezone;

    public HourlyWeather(String timezone, JSONObject data) throws JSONException {
        setSummary(data.getString("summary"));
        setTemperature(data.getDouble("temperature"));
        setIcon(data.getString("icon"));
        setTime(data.getLong("time"));
        setTimezone(timezone);
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public double getTemperature() {
        return mTemperature;
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getTimezone() {
        return mTimezone;
    }

    public void setTimezone(String timezone) {
        mTimezone = timezone;
    }
}
