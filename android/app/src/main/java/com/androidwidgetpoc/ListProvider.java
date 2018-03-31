package com.androidwidgetpoc;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListProvider implements RemoteViewsFactory {
    private ArrayList<ListItem> listItemList = new ArrayList<ListItem>();
    private Context context = null;
    private int appWidgetId;

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        populateListItem();

        /*String url = "https://www.simplifiedcoding.net/demos/marvel/";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    try {
                        JSONArray Jobject = new JSONArray(myResponse);
                        respJson(Jobject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });*/
    }

    private void respJson(JSONArray Jarray) throws JSONException {
        Log.v("Mess:",""+Jarray);
        Log.v("Lenght:",""+Jarray.length());
        for (int i = 0; i < Jarray.length(); i++) {
            JSONObject object = Jarray.getJSONObject(i);
            ListItem listItem = new ListItem();
            Log.v("User:",object.getString("name"));
            listItem.heading = object.getString("name");
            listItem.content = object.getString("realname");
            listItemList.add(listItem);
        }
    }

    private void populateListItem(){
        for (int i = 0; i < 22; i++) {
            ListItem listItem = new ListItem();
            listItem.heading = "Hi";
            listItem.content = i + " This is the content of the app widget listview.";
            listItemList.add(listItem);
        }
    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.list_row);
        ListItem listItem = listItemList.get(position);
        //remoteView.setImageViewUri(R.id.imageView, listItem.imageView);
        remoteView.setTextViewText(R.id.heading, listItem.heading);
        remoteView.setTextViewText(R.id.content, listItem.content);
        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {}

    @Override
    public void onDataSetChanged() {}

    @Override
    public void onDestroy() {}

}
