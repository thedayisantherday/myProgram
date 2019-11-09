/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.zxg.myprogram.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxg.myprogram.R;
import com.example.zxg.myprogram.activities.activitycontrol.BaseActivity;
import com.example.zxg.myprogram.adapter.RecyclerViewAdapter;
import com.example.zxg.myprogram.view.BaseFloorEntity;
import com.example.zxg.myprogram.view.BaseRecyclerviewAdapter;
import com.example.zxg.myprogram.view.BaseVH;

import java.util.ArrayList;

/**
 * Activity for scanning and displaying available Bluetooth LE devices.
 */
public class BluetoothScanActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_scan, tv_stop, tv_refresh;
    private RecyclerView rv_bledevices;
    private LeDeviceListAdapter mLeDeviceListAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning;
    private Handler mHandler;

    private static final int REQUEST_ENABLE_BT = 1;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_bluetooth_scan);

        mHandler = new Handler();

        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE is not supported", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth not supported.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
    }

    private void initViews() {
        tv_scan = findViewById(R.id.tv_scan);
        tv_scan.setOnClickListener(this);
        tv_stop = findViewById(R.id.tv_stop);
        tv_stop.setOnClickListener(this);
        tv_refresh = findViewById(R.id.tv_refresh);
        tv_refresh.setOnClickListener(this);
        rv_bledevices = findViewById(R.id.rv_bledevices);
    }

    @Override
    public void initHead(BaseActivity.ViewHolder viewHolder) {
        super.initHead(viewHolder);
        viewHolder.iv_left.setVisibility(View.VISIBLE);
        viewHolder.iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothScanActivity.this.finish();
            }
        });
        viewHolder.tv_title.setVisibility(View.VISIBLE);
        viewHolder.tv_title.setText("蓝牙扫描");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_scan:
                mLeDeviceListAdapter.clear();
                scanLeDevice(true);
                break;
            case R.id.tv_stop:
                scanLeDevice(false);
                break;
            case R.id.tv_refresh:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }

        // Initializes list view adapter.
        mLeDeviceListAdapter = new LeDeviceListAdapter();
        rv_bledevices.setLayoutManager(new LinearLayoutManager(this));
        rv_bledevices.setAdapter(mLeDeviceListAdapter);
        scanLeDevice(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanLeDevice(false);
        mLeDeviceListAdapter.clear();
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    // Adapter for holding devices found through scanning.
    private class LeDeviceListAdapter extends RecyclerView.Adapter<LeDeviceListAdapter.BleDevicesVH> {
        private ArrayList<BluetoothDevice> mLeDevices = new ArrayList<BluetoothDevice>();
        private LayoutInflater mInflator = BluetoothScanActivity.this.getLayoutInflater();

        public void addDevice(BluetoothDevice device) {
            if(!mLeDevices.contains(device)) {
                mLeDevices.add(device);
            }
        }

        public BluetoothDevice getDevice(int position) {
            return mLeDevices.get(position);
        }

        public void clear() {
            mLeDevices.clear();
        }

        @Override
        public LeDeviceListAdapter.BleDevicesVH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BleDevicesVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_device, parent, false));
        }

        @Override
        public void onBindViewHolder(LeDeviceListAdapter.BleDevicesVH holder, int position) {
            if (holder != null && mLeDevices != null && position < mLeDevices.size()) {
                holder.bindVH(mLeDevices.get(position));
            }
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public int getItemCount() {
            return mLeDevices.size();
        }

        public class BleDevicesVH extends RecyclerView.ViewHolder {

            public TextView tv_deviceName;
            public TextView tv_deviceAddress;

            public BleDevicesVH(View itemView) {
                super(itemView);

                tv_deviceName = itemView.findViewById(R.id.device_name);
                tv_deviceAddress = itemView.findViewById(R.id.device_address);
            }

            public void bindVH(final BluetoothDevice entity) {
                if (entity == null) return;

                final String deviceName = entity.getName();
                if (deviceName != null && deviceName.length() > 0)
                    tv_deviceName.setText(deviceName);
                else {
                    tv_deviceName.setText("unknown_device");
                }
                tv_deviceAddress.setText(entity.getAddress());

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent intent = new Intent(BluetoothScanActivity.this, BluetoothControlActivity.class);
                        intent.putExtra(BluetoothControlActivity.EXTRAS_DEVICE_NAME, entity.getName());
                        intent.putExtra(BluetoothControlActivity.EXTRAS_DEVICE_ADDRESS, entity.getAddress());
                        if (mScanning) {
                            mBluetoothAdapter.stopLeScan(mLeScanCallback);
                            mScanning = false;
                        }
                        startActivity(intent);
                    }
                });
            }
        }
    }

    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLeDeviceListAdapter.addDevice(device);
                    mLeDeviceListAdapter.notifyDataSetChanged();
                }
            });
        }
    };
}