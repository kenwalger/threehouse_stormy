package duranmla.stormy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import duranmla.stormy.R;
import duranmla.stormy.weather.HourlyWeather;


public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.HourViewHolder> {

    private HourlyWeather[] mHourlyWeathers;

    public HourlyWeatherAdapter(HourlyWeather[] hours) {
        mHourlyWeathers = hours;
    }

    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_list_item, parent, false);
        HourViewHolder viewHolder = new HourViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HourViewHolder holder, int position) {
        holder.bindHour(mHourlyWeathers[position]);
    }

    @Override
    public int getItemCount() {
        return mHourlyWeathers.length;
    }

    public class HourViewHolder extends RecyclerView.ViewHolder {

        public ImageView mIconImageView;
        public TextView  mTemperatureLabel;
        public TextView  mSummaryLabel;
        public TextView  mTimeLabel;

        public HourViewHolder(View itemView) {
            super(itemView);

            mTimeLabel = (TextView) itemView.findViewById(R.id.hourLabel);
            mSummaryLabel = (TextView) itemView.findViewById(R.id.hourSummaryLabel);
            mTemperatureLabel = (TextView) itemView.findViewById(R.id.hourTemperatureLabel);
            mIconImageView = (ImageView) itemView.findViewById(R.id.iconImageView);
        }

        public void bindHour(HourlyWeather hour) {
            mTimeLabel.setText(hour.getHour());
            mSummaryLabel.setText(hour.getSummary());
            mTemperatureLabel.setText(hour.getTemperature() + "");
            mIconImageView.setImageResource(hour.getIconId());
        }
    }
}
