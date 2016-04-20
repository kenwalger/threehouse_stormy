package duranmla.stormy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import duranmla.stormy.R;
import duranmla.stormy.weather.HourlyWeather;


public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.HourViewHolder> {

    private HourlyWeather[] mHourlyWeathers;
    private Context mContext;

    public HourlyWeatherAdapter(Context context, HourlyWeather[] hours) {
        mContext = context;
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

    public class HourViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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

            itemView.setOnClickListener(this);
        }

        public void bindHour(HourlyWeather hour) {
            mTimeLabel.setText(hour.getHour());
            mSummaryLabel.setText(hour.getSummary());
            mTemperatureLabel.setText(hour.getTemperature() + "");
            mIconImageView.setImageResource(hour.getIconId());
        }

        @Override
        public void onClick(View v) {
            String time = mTimeLabel.getText().toString();
            String temperature = mTemperatureLabel.getText().toString();
            String summary = mSummaryLabel.getText().toString();
            String message = String.format("At %s it will be % and %s", time, temperature, summary);
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        }
    }
}
