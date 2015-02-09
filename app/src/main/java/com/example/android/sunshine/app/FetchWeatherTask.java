package com.example.android.sunshine.app;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONException;

/**
 * Created by GPEREZ on 2/4/2015.
 */
public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {
    private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

    private ArrayAdapter<String> mForecastAdapter;

    public FetchWeatherTask(ArrayAdapter mForecastAdapter) {
        this.mForecastAdapter = mForecastAdapter;
    }

    @Override
    protected String[] doInBackground(String... params) {

        if (params.length == 0)
            return null;

        int numDays = 7;
        String forecastJsonStr = ForecastService.getDaily(params[0], numDays);

        try {
            return ForecastUtil.getWeatherDataFromJson(forecastJsonStr, numDays);
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
