package com.example.zayn.sampleapp;

import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("Title","description1"));
        items.add(new Item("Another title","description2"));

        final ItemAdapter itemAdapter = new ItemAdapter(this, items, 0);

        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(itemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Item item = (Item) itemAdapter.getItem(i);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
                startActivity(intent);
            }
        });

    }

    private void logout(){

        SharedPref.writeSharedPref(HomeActivity.this,"loginPref","false");
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void showData(){

        databaseHelper = new DatabaseHelper(HomeActivity.this);
        Cursor cursor = databaseHelper.getData();

        if (cursor.getCount() == 0){
            Toast.makeText(HomeActivity.this,"No data",Toast.LENGTH_SHORT).show();
        }

        StringBuffer buffer = new StringBuffer();

        while (cursor.moveToNext()){
            buffer.append("ID: " + cursor.getString(0)+"\n");
            buffer.append("Name: " + cursor.getString(1)+"\n");
            buffer.append("Password: " + cursor.getString(2)+"\n");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Data")
                .setMessage(buffer.toString())
                .setCancelable(true)
                .create()
                .show();

    }

    private void notification(){

        NotificationCompat.Builder notification = new NotificationCompat.Builder(HomeActivity.this);
        notification.setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Notification")
                    .setContentText("You have pressed notify menu item");

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification.build());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout_menu_item:
                logout();
                return true;
            case R.id.show_data_menu_item:
                showData();
                return true;
            case R.id.notify_menu_item:
                notification();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }

    }
}
