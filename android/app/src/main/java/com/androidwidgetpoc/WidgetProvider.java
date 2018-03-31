package com.androidwidgetpoc;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.os.Build;

import com.facebook.react.HeadlessJsTaskService;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class WidgetProvider extends AppWidgetProvider {

    /*
    * When enabled on screen, let the BackgroundTaskBridge
    * manipulate it from javascript
    */

    @Override
    public void onEnabled(Context context) {
        Log.d("WIDGET_PROVIDER", "En onEnabled");
        Intent serviceIntent = new Intent(context, BackgroundTask.class);
        context.startService(serviceIntent);
        HeadlessJsTaskService.acquireWakeLockNow(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) {
		      /*int[] appWidgetIds holds ids of multiple instance of your widget
		        * meaning you are placing more than one widgets on your homescreen*/
        for (int appWidgetId : appWidgetIds){
            //Intent svcIntent = new Intent(context, MyWidgetService.class);
            //svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.appwidget);

            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
              remoteViews.setRemoteAdapter(R.id.listViewWidget, svcIntent);
            else
              remoteViews.setRemoteAdapter(appWidgetId, R.id.listViewWidget, svcIntent);*/

            remoteViews.setEmptyView(R.id.listViewWidget, R.id.empty_view);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

}
