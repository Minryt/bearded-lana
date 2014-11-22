package com.example.andrii.audiochat;

import android.os.Handler;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Handles reading and writing of messages with socket buffers. Uses a Handler
 * to post messages to UI thread for UI updates.
 */
public class ChatManager implements Runnable {
    private Socket socket = null;
    private Handler handler;
    private static WiFiChatFragment.ChatMessageAdapter chatMessageAdapter;
    private static RC4 rc4 = null;
    public static String strKey = null;

    public ChatManager(Socket socket, Handler handler) {
        this.socket = socket;
        this.handler = handler;
    }

    private OutputStream oStream;
    private static final String TAG = "ChatHandler";

    @Override
    public void run() {
        try {
            InputStream iStream = socket.getInputStream();
            oStream = socket.getOutputStream();

            byte[] buffer = new byte[1024];
            int bytes;
            handler.obtainMessage(WiFiServiceDiscoveryActivity.MY_HANDLE, this)
                    .sendToTarget();
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = iStream.read(buffer);
                    if (bytes == -1) {
                        break;
                    }

                    if (bytes > 0) {
                        buffer = cutByteBuffer(buffer, bytes);
                        if (rc4 != null) {
                            buffer = rc4.decrypt(buffer);
                        }
                        AudioFileManager.getInstance().setAudio(buffer);
                    }

                    // Send the obtained bytes to the UI Activity
                    Log.e(TAG, "Rec:" + RC4.convertToHex(buffer));
                    handler.obtainMessage(WiFiServiceDiscoveryActivity.MESSAGE_READ,
                            bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
                    Log.e(TAG, "disconnected", e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void write(File buffer, WiFiChatFragment.ChatMessageAdapter chatMessageAdapter) {
        ChatManager.chatMessageAdapter = chatMessageAdapter;
        try {
            copyFile(oStream, new FileInputStream(buffer));
        } catch (IOException e) {
            Log.e(TAG, "Exception during write", e);
        }
    }

    public static boolean copyFile(OutputStream outputStream, FileInputStream in) {
        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = in.read(buf)) != -1) {
                if (rc4 != null) {
                    buf = rc4.encrypt(buf);
                }
                Log.e(TAG, "Sen:" + RC4.convertToHex(buf));
                outputStream.write(buf, 0, len);
                outputStream.flush();
            }
            in.close();
            chatMessageAdapter.add("Message was sent");
            chatMessageAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            Log.d(WiFiServiceDiscoveryActivity.TAG, e.toString());
            return false;
        }
        return true;
    }

    private byte[] cutByteBuffer(byte[] readBuf, int arg1) {
        byte[] newByte = new byte[arg1];
        System.arraycopy(readBuf, 0, newByte, 0, arg1);
        return newByte;
    }

    private byte[] getXOROfBytes(byte[] bytes, byte[] bytes2) {
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (bytes[i] ^ bytes2[i]);
        }
        return bytes;
    }

    public void generateKey() {
        //byte[] preKey = getXOROfBytes(socket.getRemoteSocketAddress().toString().getBytes(), socket.getLocalSocketAddress().toString().getBytes());
        byte[] preKey = "TeSSeRaCt".getBytes();
        rc4 = new RC4(preKey);
        strKey = RC4.convertToHex(preKey);
    }
}
