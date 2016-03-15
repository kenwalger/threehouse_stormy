package duranmla.stormy.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import duranmla.stormy.R;
import duranmla.stormy.weather.DailyWeather;

public class DailyWeatherAdapter extends BaseAdapter {

    private Context mContext;
    private DailyWeather[] mDailyWeathers;

    public DailyWeatherAdapter(Context context, DailyWeather[] dailyWeathers) {
        mContext = context;
        mDailyWeathers = dailyWeathers;
    }

    @Override
    public int getCount() {
        return mDailyWeathers.length;
    }

    @Override
    public Object getItem(int position) {
        return mDailyWeathers[position];
    }

    @Override
    public long getItemId(int position) {
        return 0; // we're not going to use this one (we don't have a sort of tagging for each object)
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.daily_list_item, null);
            holder = new ViewHolder();
            holder.dayLabel = (TextView) convertView.findViewById(R.id.dayNameLabel);
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
            holder.temperatureLabel = (TextView) convertView.findViewById(R.id.temperatureLabel);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DailyWeather day = mDailyWeathers[position];

        holder.dayLabel.setText(day.getDayOfTheWeek());
        holder.iconImageView.setImageResource(day.getIconId());
        holder.temperatureLabel.setText(day.getTemperatureMax() + "");

        return convertView;
    }

    public static class ViewHolder {
        ImageView iconImageView;
        TextView temperatureLabel;
        TextView dayLabel;
    }
}
