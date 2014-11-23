package com.example.andrii.audiochat;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * This fragment handles chat related UI which includes a list view for messages
 * and a message entry field with send button.
 */
public class WiFiChatFragment extends Fragment {
    private ChatManager chatManager;
    ChatMessageAdapter adapter = null;
    private List<String> items = new ArrayList<String>();

    private Button mRecordButton;
    private Button mPlayButton;
    private AudioManager audioManager = AudioManager.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ListView listView = (ListView) view.findViewById(android.R.id.list);
        adapter = new ChatMessageAdapter(getActivity(), android.R.id.text1,
                items);
        listView.setAdapter(adapter);
        mRecordButton = (Button) view.findViewById(R.id.rec);
        mPlayButton = (Button) view.findViewById(R.id.play);

        mRecordButton.setText("Start recording");
        mRecordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean mStartRecording = audioManager.isStartRecording();
                audioManager.onRecord(mStartRecording);
                if (mStartRecording) {
                    mRecordButton.setText("Stop recording");
                } else {
                    mRecordButton.setText("Start recording");
                }
                audioManager.setIsStartRecording(!mStartRecording);
            }
        });

        mPlayButton.setText("Start playing");
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean mStartPlaying = audioManager.isStartPlaying();
                audioManager.onPlay(mStartPlaying);
                if (mStartPlaying) {
                    mPlayButton.setText("Stop playing");
                } else {
                    mPlayButton.setText("Start playing");
                }
                audioManager.setIsStartPlaying(!mStartPlaying);
            }
        });

        view.findViewById(R.id.send).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        if (chatManager != null) {
                            chatManager.write(AudioFileManager.getInstance().getSavedAudio(), adapter);
                            AudioFileManager.getInstance().resetAudio();
                        }
                    }
                });

        view.findViewById(R.id.key).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        if (chatManager != null) {
                            chatManager.generateKey();
                            pushMessage("Key: " + ChatManager.strKey);
                        }
                    }
                });

        return view;
    }

    public interface MessageTarget {
        public Handler getHandler();
    }

    public void setChatManager(ChatManager obj) {
        chatManager = obj;
    }

    public void pushMessage(String readMessage) {
        adapter.add(readMessage);
        adapter.notifyDataSetChanged();
    }

    /**
     * ArrayAdapter to manage chat messages.
     */
    public class ChatMessageAdapter extends ArrayAdapter<String> {
        public ChatMessageAdapter(Context context, int textViewResourceId,
                                  List<String> items) {
            super(context, textViewResourceId, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(android.R.layout.simple_list_item_1, null);
            }
            String message = items.get(position);
            if (message != null && !message.isEmpty()) {
                TextView nameText = (TextView) v
                        .findViewById(android.R.id.text1);
                if (nameText != null) {
                    nameText.setText(message);
                    if (message.startsWith("Me: ")) {
                        nameText.setTextAppearance(getActivity(),
                                R.style.normalText);
                    } else {
                        nameText.setTextAppearance(getActivity(),
                                R.style.boldText);
                    }
                }
            }
            return v;
        }
    }
}