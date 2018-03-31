package com.androidwidgetpoc;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.RemoteViews;
import android.content.Intent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;

import android.widget.*;

public class BackgroundTaskBridge extends ReactContextBaseJavaModule {

    public BackgroundTaskBridge(final ReactApplicationContext reactContext) { super(reactContext); }

    //ArrayList<ListItem> lista = new ArrayList<ListItem>();
    //ArrayAdapter<ListItem> adaptor;

    @Override
    public String getName() { return "BackgroundTaskBridge"; }

    @ReactMethod
    public void initializeWidgetBridge(ReadableArray starredCharms) {
        RemoteViews widgetView = new RemoteViews(this.getReactApplicationContext().getPackageName(), R.layout.appwidget);
        widgetView.removeAllViews(R.id.charms_layout);
        for (int i = 0; i < starredCharms.size(); i++) {
          ReadableMap charm = starredCharms.getMap(i);
          updateView(widgetView, charm, R.layout.list_row);
        }
        AppWidgetManager.getInstance(this.getReactApplicationContext()).updateAppWidget(new ComponentName(this.getReactApplicationContext(), WidgetProvider.class), widgetView);
    }

    private void updateView(RemoteViews widgetView, ReadableMap charm, Integer layout) {
        String bio = charm.getString("bio").replace("\r","").replace("\n","").replace("\t","");
        RemoteViews charm_view = new RemoteViews(this.getReactApplicationContext().getPackageName(), layout);
        URL url = null;
        try {
            url = new URL(charm.getString("imageurl"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        charm_view.setImageViewBitmap(R.id.ch, bmp);
        charm_view.setTextViewText(R.id.heading, charm.getString("name"));
        charm_view.setTextViewText(R.id.content,bio);
        //lista.add(new Contact(nume, prenume));
        //adaptor.notifyDataSetChanged();
        widgetView.addView(R.id.charms_layout, charm_view);
        //views.setRemoteAdapter(R.id.listViewWidget, intent);
    }

}
