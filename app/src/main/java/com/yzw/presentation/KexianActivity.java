package com.yzw.presentation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.Handler;
import android.os.Message;

import android_serialport_api.SerialPort;

public class KexianActivity extends Activity {
	private Spinner portSpinner, bautSpinner;
	private Button danjiaBtn, zongjiBtn, shoukuanBtn, zhaolingBtn, qingkongBtn, autotestBtn, backBtn;
	
	private ArrayAdapter<String> adapter;
	private static final String[] port_list={"COM0","COM1","COM2","COM3","COM4","COM5","COM6","COM7"};
	private static final String[] ttys_list={"/dev/ttyS0","/dev/ttyS1","/dev/ttyS2","/dev/ttyS3","/dev/ttyS4","/dev/ttyS5","/dev/ttyS6","/dev/ttyS7"};
	private static final String[] baut_list={"2400","4800","9600"};
	private int portIndex = 4;
	private int bautIndex = 0;
	private SerialPort mSerialPort;
	private InputStream mInputStream;
	private OutputStream mOutputStream;
	private MyThread test;
	private boolean auto = false;
	protected byte[] mUartBuffer = new byte[1024];

	private Handler handler = new Handler(){

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				//showMsg("test over");
				break;
			case 1:
				//showMsg("test Warning");
				break;
			}
		};

	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kexian);
		
		initUart();
		
		portSpinner = (Spinner) findViewById(R.id.portSpinner);
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,port_list);
         
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         
        //将adapter 添加到spinner中
        portSpinner.setAdapter(adapter);
         
        //添加事件Spinner事件监听  
        portSpinner.setOnItemSelectedListener(new PortSpinnerSelectedListener());
         
        //设置默认值
		portSpinner.setSelection(4, true);

        portSpinner.setVisibility(View.VISIBLE);
        
        bautSpinner = (Spinner) findViewById(R.id.bautSpinner);
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,baut_list);
         
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         
        //将adapter 添加到spinner中
        bautSpinner.setAdapter(adapter);
         
        //添加事件Spinner事件监听  
        bautSpinner.setOnItemSelectedListener(new BautSpinnerSelectedListener());
         
        //设置默认值
        bautSpinner.setVisibility(View.VISIBLE);
        
        danjiaBtn = (Button) findViewById(R.id.danjiaBtn);
		danjiaBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mUartBuffer[0] = 0x1B;
				mUartBuffer[1] = 0x73;
				mUartBuffer[2] = 0x31;
				mUartBuffer[3] = 0x1B;
				mUartBuffer[4] = 0x51;
				mUartBuffer[5] = 0x41;
				mUartBuffer[6] = 0x31;
				mUartBuffer[7] = 0x32;
				mUartBuffer[8] = 0x2E;
				mUartBuffer[9] = 0x31;
				mUartBuffer[10] = 0x0D;
				try {
					mOutputStream.write(mUartBuffer,0,11);
					mOutputStream.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

			}
			
		});

		zongjiBtn = (Button) findViewById(R.id.zongjiBtn);
        zongjiBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mUartBuffer[0] = 0x1B;
                mUartBuffer[1] = 0x73;
                mUartBuffer[2] = 0x32;
                mUartBuffer[3] = 0x1B;
                mUartBuffer[4] = 0x51;
                mUartBuffer[5] = 0x41;
                mUartBuffer[6] = 0x32;
                mUartBuffer[7] = 0x33;
                mUartBuffer[8] = 0x2E;
                mUartBuffer[9] = 0x36;
                mUartBuffer[10] = 0x0D;
                try {
                    mOutputStream.write(mUartBuffer,0,11);
                    mOutputStream.flush();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }

        });
		
		shoukuanBtn = (Button) findViewById(R.id.shoukuanBtn);
        shoukuanBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mUartBuffer[0] = 0x1B;
                mUartBuffer[1] = 0x73;
                mUartBuffer[2] = 0x33;
                mUartBuffer[3] = 0x1B;
                mUartBuffer[4] = 0x51;
                mUartBuffer[5] = 0x41;
                mUartBuffer[6] = 0x35;
                mUartBuffer[7] = 0x30;
                mUartBuffer[8] = 0x2E;
                mUartBuffer[9] = 0x30;
                mUartBuffer[10] = 0x0D;
                try {
                    mOutputStream.write(mUartBuffer,0,11);
                    mOutputStream.flush();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }

        });

		zhaolingBtn = (Button) findViewById(R.id.zhaolingBtn);
        zhaolingBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mUartBuffer[0] = 0x1B;
                mUartBuffer[1] = 0x73;
                mUartBuffer[2] = 0x34;
                mUartBuffer[3] = 0x1B;
                mUartBuffer[4] = 0x51;
                mUartBuffer[5] = 0x41;
                mUartBuffer[6] = 0x32;
                mUartBuffer[7] = 0x36;
                mUartBuffer[8] = 0x2E;
                mUartBuffer[9] = 0x34;
                mUartBuffer[10] = 0x0D;
                try {
                    mOutputStream.write(mUartBuffer,0,11);
                    mOutputStream.flush();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        });

		qingkongBtn = (Button) findViewById(R.id.qingkongBtn);
        qingkongBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mUartBuffer[0] = 0x0C;
                try {
                    mOutputStream.write(mUartBuffer,0,1);
                    mOutputStream.flush();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }

        });

		autotestBtn = (Button) findViewById(R.id.autotestBtn);
        autotestBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
				if(auto == false) {
					auto = true;
					test = new MyThread();

					test.start();
					autotestBtn.setText(getResources().getString(R.string.stop_autotest));
				} else {
					auto = false;
					test.interrupt();
					autotestBtn.setText(getResources().getString(R.string.autotest));
				}
            }

        });

		backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
				finish();
            }

        });
		
	}
	
	//使用数组形式操作
    class PortSpinnerSelectedListener implements OnItemSelectedListener{
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			portIndex = arg2;
			closeUart();
			initUart();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
    }
    
  //使用数组形式操作
    class BautSpinnerSelectedListener implements OnItemSelectedListener{
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			bautIndex = arg2;
			closeUart();
			initUart();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	
    }
    
    private void initUart() {
    	//baud,N,8,1
    	try {
			mSerialPort = new SerialPort(new File(ttys_list[portIndex]),
					Integer.parseInt(baut_list[bautIndex]));
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mInputStream = mSerialPort.getInputStream();
		mOutputStream = mSerialPort.getOutputStream();

    } 
    
    private void closeUart() {
    	try {
			mInputStream.close();
			mOutputStream.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		mSerialPort.close();
    }
    
   	@Override
	protected void onDestroy() {
		super.onDestroy();

		if(auto == true)
			test.interrupt();
		closeUart();
	} 

	private class MyThread extends Thread{

		private final Object lock_wait=new Object();

		@Override

		public void run() {

			while(auto){

				try {

					String i = "8.";

					mOutputStream.write(new byte[]{0x0c});

					Thread.sleep(1000);

					for (int j = 0; j < 8; j++) {

						mOutputStream.write(new byte[]{0x1b,0x51,0x41});

						mOutputStream.write(i.getBytes());

						mOutputStream.write(new byte[]{0x0d});

						i+="8";

						Thread.sleep(1000);

					}

					for (int j = 0; j < 4; j++) {

						mOutputStream.write(new byte[]{0x1b,0x73,(byte)(0x31+j)});

						Thread.sleep(1000);

					}

					mOutputStream.write(new byte[]{0x1b,0x40});

					Thread.sleep(3000);

				} catch (InterruptedException e) {

					handler.sendEmptyMessage(0);

					break;

				} catch (Exception e) {

					e.printStackTrace();

					handler.sendEmptyMessage(1);

					break;

				}

			}

		}

	}
}
