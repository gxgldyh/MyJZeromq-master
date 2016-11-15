package com.example.testjzeromq;

import org.zeromq.ZMQ;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button) findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myclick();
			}
		});
	}

	/**
	 * 功能描述:按钮事件
	 */
	private void myclick(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				sub();
			}
		}).start();
	}
	
	/**
	 * 功能描述: 接收服务器发来的数据
	 */
	private void sub(){
		ZMQ.Context context = ZMQ.context(1);  
        ZMQ.Socket subscriber = context.socket(ZMQ.SUB);  
        subscriber.connect("tcp://192.168.1.125:5561"); //连接服务器ip地址及端口号 
        subscriber.subscribe("".getBytes()); //描述 
        while (!Thread.currentThread().isInterrupted()) {  
            byte[] message = subscriber.recv();  
            System.out.println("receive : " + new String(message));  
        }  
        subscriber.close();  
        context.term();  
	}

}
