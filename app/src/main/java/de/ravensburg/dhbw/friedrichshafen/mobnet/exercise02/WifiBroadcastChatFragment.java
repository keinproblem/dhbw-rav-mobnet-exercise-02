package de.ravensburg.dhbw.friedrichshafen.mobnet.exercise02;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by fan.noli.morina on 02.11.2017.
 */

public class WifiBroadcastChatFragment extends Fragment {


    EditText editText;
    ListView listView;
    MessageAdapter messageAdapter;

    public static WifiBroadcastChatFragment newInstance() {
        final WifiBroadcastChatFragment wifiBroadcastChatFragment = new WifiBroadcastChatFragment();
        return wifiBroadcastChatFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wifi_chat_fragment_main, container, false);
        Toast.makeText(this.getContext(), "Loading", Toast.LENGTH_SHORT).show();
        Button sendButton = rootView.findViewById(R.id.sendButton);
        editText = rootView.findViewById(R.id.editText2);
        listView = rootView.findViewById(R.id.listView);
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setStackFromBottom(true);
        messageAdapter = new MessageAdapter<String>();
        listView.setAdapter(messageAdapter);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sendButton:
                        messageAdapter.add(editText.getEditableText().toString());
                        editText.setText("");
                        break;
                }
            }
        });
        Toast.makeText(this.getContext(), "Done", Toast.LENGTH_SHORT).show();

        return rootView;
    }

    private class MessageAdapter<T> extends BaseAdapter {
        private LayoutInflater inflater = null;
        private ArrayList<T> messageList = new ArrayList<>();

        @Override
        public int getCount() {
            return messageList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }

        public void add(T message) {
            messageList.add(message);
        }
    }
}
