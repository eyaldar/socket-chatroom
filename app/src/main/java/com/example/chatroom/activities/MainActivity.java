package com.example.chatroom.activities;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.chatroom.R;
import com.example.chatroom.events.CollectionChangedEventListener;
import com.example.chatroom.events.CollectionChangedType;
import com.example.chatroom.interfaces.IMainActivityViewModel;
import com.google.inject.Inject;
import com.google.inject.Provider;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class MainActivity extends RoboActivity {
    @Inject
    Provider<Context> contextProvider;
    @InjectView(R.id.messagesList)
    private ListView messagesListView;
    @InjectView(R.id.sendButton)
    private Button sendButton;
    @InjectView(R.id.editTextMessage)
    private EditText messageEditText;
    private ArrayAdapter<String> adapter;
    @Inject
    private IMainActivityViewModel viewModel;

    private OnClickListener onSendMessageClicked = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Editable editableText = messageEditText.getText();

            String message = editableText.toString();
            messageEditText.setText("");

            if (!message.isEmpty()) {
                viewModel.sendMessage(message);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ArrayAdapter<>(contextProvider.get(), R.layout.message_row, viewModel.getMessages());
        messagesListView.setAdapter(adapter);

        sendButton.setOnClickListener(onSendMessageClicked);

        viewModel.setOnCollectionChangedListener(new CollectionChangedEventListener<String>() {
            @Override
            public void onCollectionChanged(String item, CollectionChangedType eventType) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
