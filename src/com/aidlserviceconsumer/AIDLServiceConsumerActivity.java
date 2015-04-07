package com.aidlserviceconsumer;

import com.aidlservice.ISumService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AIDLServiceConsumerActivity extends Activity implements OnClickListener {

	private String TAG = "AIDLServiceConsumerActivity";
	private Button mBindServiceBtn;
	private Button mUnBindServiceBtn;
	private Button mFibSumBtn;
	private Button mClearBtn;
	private Button mCrachBtn;
	private EditText mNEditText;
	private ISumService mSumService;
	
	private Bundle mThreadArgs;
	private Message  mThreadMsg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mBindServiceBtn = (Button) findViewById(R.id.button1);
		mUnBindServiceBtn = (Button) findViewById(R.id.button2);
		mFibSumBtn = (Button) findViewById(R.id.button3);
		mClearBtn = (Button) findViewById(R.id.button4);
		mCrachBtn = (Button) findViewById(R.id.button5);
		
		mNEditText  = (EditText) findViewById(R.id.editText1);
		
		mBindServiceBtn.setEnabled(true);
		mUnBindServiceBtn.setEnabled(false);
		mFibSumBtn.setEnabled(false);
		mClearBtn.setEnabled(false);
		mCrachBtn.setEnabled(false);
		
		mBindServiceBtn.setOnClickListener(this);
		mUnBindServiceBtn.setOnClickListener(this);
		mFibSumBtn.setOnClickListener(this);
		mClearBtn.setOnClickListener(this);
		mCrachBtn.setOnClickListener(this);
		
		HandlerThread hthr = new HandlerThread("SumThread", Process.THREAD_PRIORITY_BACKGROUND);
		hthr.start();
		
		mServiceLooper = hthr.getLooper();
		mServiceHandler = new ServiceHandler(mServiceLooper);
		mThreadArgs = new Bundle();
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			Intent intent = new Intent(ISumService.class.getName()) ;
			bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE);
			mBindServiceBtn.setEnabled(false);
			mUnBindServiceBtn.setEnabled(true);
			mFibSumBtn.setEnabled(true);
			mClearBtn.setEnabled(true);
			mCrachBtn.setEnabled(true);
			break;
			
		case R.id.button2:
			unbindService(mServiceConn);
			mBindServiceBtn.setEnabled(true);
			mUnBindServiceBtn.setEnabled(false);
			mFibSumBtn.setEnabled(false);
			mClearBtn.setEnabled(false);
			mCrachBtn.setEnabled(false);
			break;
			
		case R.id.button3:
			mThreadMsg = mServiceHandler.obtainMessage();
			mThreadArgs.clear();
			mThreadArgs.putBoolean("fibsum", true);
			Log.v(TAG , "1: "+ Long.parseLong(mNEditText.getText().toString()));
			mThreadArgs.putLong("n", Long.parseLong(mNEditText.getText().toString()));
			Log.v(TAG , "2: "+ Long.parseLong(mNEditText.getText().toString()));
			mThreadMsg.obj = mThreadArgs;
			mServiceHandler.sendMessage(mThreadMsg);
			break;
			
		case R.id.button4:
			mNEditText.setText("");
			break;
	
		case R.id.button5:
			Process.killProcess(Process.myPid());
			break;
	
		default:
			break;
		}
	}
	
	private ServiceConnection mServiceConn = new ServiceConnection() {
		
		@Override
		public void onServiceConnected(ComponentName arg0, IBinder service) {
			Log.v(TAG , "SERVICE CONNECTED");
			mSumService = ISumService.Stub.asInterface(service);
			Toast.makeText(getApplicationContext(), "Service Connected", Toast.LENGTH_LONG).show();
		}
		
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			Log.v(TAG , "SERVICE DISCONNECTED");
			mSumService = null;
			Toast.makeText(getApplicationContext(), "Service disconnected", Toast.LENGTH_LONG).show();
		}
	};
	
	private volatile Looper mServiceLooper;
	private volatile ServiceHandler mServiceHandler;
	
	private final class ServiceHandler extends Handler{
		public ServiceHandler(Looper looper) {
			super(looper);
		}
		
		public void handleMessage(Message msg){
			Bundle args = (Bundle)msg.obj;
			
			if (args.containsKey("fibsum")) {
				Log.v(TAG , "args: "+ args.getLong("n"));
				remoteFibonacciSum(args.getLong("n"));
			} 
		}
	}
	
	private void remoteFibonacciSum(long n) {
		try {
			long v = mSumService.fibonacciSum(n);
			Toast.makeText(getApplicationContext(), "Fibonacci Sum : "+v, Toast.LENGTH_LONG).show();
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}

	
}
