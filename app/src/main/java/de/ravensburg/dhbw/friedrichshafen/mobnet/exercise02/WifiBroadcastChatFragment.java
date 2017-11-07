package de.ravensburg.dhbw.friedrichshafen.mobnet.exercise02;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by fan.noli.morina on 02.11.2017.
 */

public class WifiBroadcastChatFragment extends Fragment {

    private static final String TAG = WifiBroadcastChatFragment.class.getSimpleName();
    public static ArrayList<ChatMessage> chatlist;
    EditText editText;
    ListView listView;
    ChatAdapter messageAdapter;

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

        chatlist = new ArrayList<>();
        messageAdapter = new ChatAdapter(getActivity(), chatlist);
        listView.setAdapter(messageAdapter);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sendButton:
                        //messageAdapter.add(editText.getEditableText().toString());
                        Toast.makeText(getContext(), String.format("Input: [%s]", editText.getEditableText().toString()), Toast.LENGTH_SHORT).show();

                        String message = editText.getEditableText().toString();
                        if (!message.equalsIgnoreCase("")) {
                            final ChatMessage chatMessage = new ChatMessage("", "",
                                    message, "" + new SecureRandom().nextInt(1000), true);
                            chatMessage.setMsgID();
                            chatMessage.body = message;
                            chatMessage.Date = CommonMethods.getCurrentDate();
                            chatMessage.Time = CommonMethods.getCurrentTime();
                            editText.setText("");
                            messageAdapter.add(chatMessage);
                            messageAdapter.notifyDataSetChanged();
                        }
                        break;
                }
            }
        });
        Toast.makeText(this.getContext(), "Done", Toast.LENGTH_SHORT).show();

        return rootView;
    }

    public static class CommonMethods {
        private static DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
        private static DateFormat timeFormat = new SimpleDateFormat("K:mma");

        public static String getCurrentTime() {

            Date today = Calendar.getInstance().getTime();
            return timeFormat.format(today);
        }

        public static String getCurrentDate() {

            Date today = Calendar.getInstance().getTime();
            return dateFormat.format(today);
        }

    }

    public class ChatMessage {

        public String body, sender, receiver, senderName;
        public String Date, Time;
        public String msgid;
        public boolean isMine;// Did I send the message.

        public ChatMessage(String Sender, String Receiver, String messageString,
                           String ID, boolean isMINE) {
            body = messageString;
            isMine = isMINE;
            sender = Sender;
            msgid = ID;
            receiver = Receiver;
            senderName = sender;
        }

        public void setMsgID() {

            msgid += "-" + String.format("%02d", new Random().nextInt(100));
        }
    }

    public class ChatAdapter extends BaseAdapter {

        ArrayList<ChatMessage> chatMessageList;
        private LayoutInflater inflater = null;

        public ChatAdapter(Activity activity, ArrayList<ChatMessage> list) {
            chatMessageList = list;
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return chatMessageList.size();
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
            ChatMessage message = chatMessageList.get(position);
            View vi = convertView;
            if (convertView == null)
                vi = inflater.inflate(R.layout.chatbubble, null);

            TextView msg = vi.findViewById(R.id.message_text);
            msg.setText(message.body);
            LinearLayout layout = vi
                    .findViewById(R.id.bubble_layout);
            LinearLayout parent_layout = vi
                    .findViewById(R.id.bubble_layout_parent);

            // if message is mine then align to right
            if (message.isMine) {
                parent_layout.setGravity(Gravity.RIGHT);
            }
            // If not mine then align to left
            else {
                parent_layout.setGravity(Gravity.LEFT);
            }
            msg.setTextColor(Color.BLACK);
            return vi;
        }

        public void add(ChatMessage object) {
            chatMessageList.add(object);
        }
    }
}
