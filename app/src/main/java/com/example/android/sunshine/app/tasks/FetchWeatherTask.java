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

    private ArrayAdapter<String> mForecastAdapter;
    private final Context mContext;

    public FetchWeatherTask(Context context, ArrayAdapter<String> forecastAdapter) {
        mContext = context;
        mForecastAdapter = forecastAdapter;
    }

    @Override
    protected String[] doInBackground(String... params) {

        // If there's no zip code, there's nothing to look up.  Verify size of params.
        if (params.length == 0) {
            return null;
        }

        String locationQuery = params[0];
        int numDays = 14;

        try {
            String forecastJsonStr = ForecastService.getDaily(locationQuery, numDays);
            return ForecastUtil.getWeatherDataFromJson(mContext, forecastJsonStr, locationQuery);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(String[] result) {
        if (result != null && mForecastAdapter != null) {
            mForecastAdapter.clear();

            for(String dayForecastStr : result) {
                mForecastAdapter.add(dayForecastStr);
            }
            // New data is back from the server.  Hooray!
        }
    }
}