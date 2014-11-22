package com.example.andrii.audiochat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

public class AudioFileManager {
    private static AudioFileManager audioFileManager;
    private String fileName = null;
    private File file;

    public static AudioFileManager getInstance() {
        if (audioFileManager != null)
            return audioFileManager;
        else return audioFileManager = new AudioFileManager();
    }

    public File getSavedAudio() {
        return new File(fileName);
    }

    public void init(String mFileName) {
        fileName = mFileName;
        file = new File(fileName);
        resetAudio();
    }

    public void setAudio(byte[] readBuf) {
        try {
            FileOutputStream fOut = new FileOutputStream(file, true);
            fOut.write(readBuf);
            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void resetAudio() {
        try {
            new FileOutputStream(file, false).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long getAudioLength() {
        return file.length();
    }

    public String getAudioHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            byte[] buffer = new byte[65536]; //created at start.
            InputStream fis = new FileInputStream(fileName);
            int n = 0;
            while (n != -1) {
                n = fis.read(buffer);
                if (n > 0) {
                    digest.update(buffer, 0, n);
                }
            }
            byte[] digestResult = digest.digest();
            fis.close();
            return RC4.convertToHex(digestResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fail";
    }
}
