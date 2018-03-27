package com.androidwidgetpoc;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.facebook.react.HeadlessJsTaskService;

public class WidgetProvider extends AppWidgetProvider {
    private static final String WIDGET_TASK = "com.androidwidgetpoc.WIDGET_TASK";

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
    public void onReceive(final Context context, final Intent incomingIntent) {
        super.onReceive(context, incomingIntent);
        if (!incomingIntent.getAction().startsWith("com.androidwidgetpoc.CHARM")) return;
        Intent silentStartIntent = new Intent(context, BackgroundTask.class);
        context.startService(silentStartIntent);
        /*
        * Proxy bundle extras towards the service
        * */
        Intent serviceIntent = new Intent(context, BackgroundTask.class);
        serviceIntent.putExtras(incomingIntent);
        context.startService(serviceIntent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) {
        final int N = appWidgetIds.length;
		      /*int[] appWidgetIds holds ids of multiple instance of your widget
		        * meaning you are placing more than one widgets on your homescreen*/
        for (int i = 0; i < N; ++i) {
            RemoteViews remoteViews = updateWidgetListView(context,appWidgetIds[i]);
            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews updateWidgetListView(Context context, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.appwidget);
        Intent svcIntent = new Intent(context, WidgetService.class);
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        remoteViews.setRemoteAdapter(appWidgetId, R.id.listViewWidget, svcIntent);
        remoteViews.setEmptyView(R.id.listViewWidget, R.id.empty_view);
        return remoteViews;
    }
}
