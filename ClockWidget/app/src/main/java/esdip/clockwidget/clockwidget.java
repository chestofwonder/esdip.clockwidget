package esdip.clockwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Calendar;


/**
 * Implementation of App Widget functionality.
 */
public class clockwidget extends AppWidgetProvider {

    private static BroadcastReceiver updateReceiver;
    private static Calendar instanceCalendar;
    private final String DEBUG_TAG = "DEBUG";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            Log.i(DEBUG_TAG, "onUpdate Widget3");

            CharSequence widgetText = context.getString(R.string.appwidget_text);

            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.clockwidget);
            views.setTextViewText(R.id.appwidget_text, widgetText);

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onEnabled(Context context) {

        // Enter relevant functionality for when the first widget is created
        updateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                instanceCalendar = Calendar.getInstance();

                Log.i(DEBUG_TAG, String.valueOf(instanceCalendar.get(Calendar.HOUR_OF_DAY))); // Reloj de 24 horas
                //  Log.i(DEBUG_TAG, String.valueOf(instanceCalendar.get(Calendar.HOUR))); // Reloj de 12 horas
                // Log.i(DEBUG_TAG, String.valueOf(instanceCalendar.get(Calendar.AM_PM))); // AM = 0 PM = 1
                // Log.i(DEBUG_TAG, String.valueOf(instanceCalendar.get(Calendar.MINUTE)));
                //  if(intent.getAction().compareTo(Intent.ACTION_TIME_TICK)==0)
                //{
                //  Toast.makeText(context, intent.getAction(), Toast.LENGTH_SHORT).show();
                //hourText.setText(String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)));
                //minuteText.setText(String.valueOf(Calendar.getInstance().get(Calendar.MINUTE)));
                //}

            }
        };

        context.getApplicationContext().registerReceiver(updateReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        context.getApplicationContext().unregisterReceiver(updateReceiver);
    }

}

