package com.example.kkryveshko.chat;

import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.view.View;
import android.widget.Button;

/**
 * Created by Konstantin on 13.11.2014.
 */
public class Controller {

    private static Controller controller;
    private AppView appView;
    private Model model;
    private Button buttonServer;
    private Button buttonClient;

    private WifiP2pManager mWiFiManager;
    private WifiP2pManager.Channel mWiFiChanell;
    private WifiP2pDeviceList deviceList;
    private boolean isConnected;

    public static Controller getInstance() {
        if (controller == null) {
            return controller = new Controller();
        }
        return controller;
    }

    public void init() {
        appView = AppView.getInstance();
        model = Model.getInstance();
    }

    public void createServerButton(Button buttonServer) {
        this.buttonServer = buttonServer;
        this.buttonServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (model.getState()) {
                    case START:
                        model.createServer();
                        appView.createServer();
                        break;
//                    case WAIT_CLIENT:
//                        break;
                    case FIND_SERVER:
                        model.resetState();
                        appView.resetButtons();
                        break;
                }
            }
        });
    }


    public void createClientButton(Button buttonClient) {
        this.buttonClient = buttonClient;
        this.buttonClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (model.getState()) {
                    case START:
                        model.createClient();
                        appView.createClient();
                        break;
                    case WAIT_CLIENT:
                        model.resetState();
                        appView.resetButtons();
                        break;
//                    case FIND_SERVER:
//                        break;
                }
            }
        });
    }

    public void initWiFiManager(WifiP2pManager mManager) {
        mWiFiManager = mManager;
    }

    public void initWiFiChannel(WifiP2pManager.Channel mChannel) {
        mWiFiChanell = mChannel;
    }

    public void findPeers() {
        mWiFiManager.discoverPeers(mWiFiChanell, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                appView.print("Founded");
            }

            @Override
            public void onFailure(int reasonCode) {
                appView.print("Devices not found");
            }
        });

        while (deviceList == null) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {

            }
        }
        for (WifiP2pDevice device : deviceList.getDeviceList()) {
            appView.print(device.deviceName);
        }
    }

    public void setWifiP2pDeviceList(WifiP2pDeviceList peers) {
        deviceList = peers;
    }

    public void connectToPeer() {
        if (!isConnected) {
            WifiP2pDevice device = deviceList.getDeviceList().iterator().next();
            WifiP2pConfig config = new WifiP2pConfig();
            config.deviceAddress = device.deviceAddress;
            mWiFiManager.connect(mWiFiChanell, config, new WifiP2pManager.ActionListener() {

                @Override
                public void onSuccess() {
                    appView.print("Connect to device");
                    isConnected = true;
                }

                @Override
                public void onFailure(int reason) {
                    appView.print("Device busy");
                }
            });
        }
    }
}
