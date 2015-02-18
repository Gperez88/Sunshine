package com.example.android.sunshine.app.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.android.sunshine.app.services.ForecastService;
import com.example.android.sunshine.app.utils.ForecastUtil;

import org.json.JSONException;

/**
 * Created by GPEREZ on 2/4/2015.
 */
public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {
    private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();
    private final int NUMBER_DAYS = 7;

    private Context context;
    private ArrayAdapter<String> mForecastAdapter;

    public FetchWeatherTask(Context context, ArrayAdapter mForecastAdapter) {
        this.context = context;
        this.mForecastAdapter = mForecastAdapter;
    }

    @Override
    protected String[] doInBackground(String... params) {

        if (params.length == 0)
            return null;

        String forecastJsonStr = ForecastService.getDaily(params[0], NUMBER_DAYS);

        try {
            return ForecastUtil.getWeatherDataFromJson(context, forecastJsonStr, NUMBER_DAYS);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String[] result) {
        if (result != null) {
            mForecastAdapter.clear();
            for (String dayForecastStr : result) {
                mForecastAdapter.add(dayForecastStr);
            }
        }
    }

}
