package com.yyh.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yyh.daemon.R;
import com.yyh.fork.NativeRuntime;
import com.yyh.utils.FileUtils;

@SuppressWarnings("unchecked")
public class MainActivity extends Activity {

	Button btnstart, btnend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initService();
	}

	private void initService() {
		btnstart = (Button) findViewById(R.id.btn_start);
		btnend = (Button) findViewById(R.id.btn_end);

		Toast.makeText(this, NativeRuntime.getInstance().stringFromJNI(), Toast.LENGTH_LONG).show();
		String executable = "libhelper.so";
		String aliasfile = "helper";
		String parafind = "/data/data/" + getPackageName() + "/" + aliasfile;
		String retx = "false";
		NativeRuntime.getInstance().RunExecutable(getPackageName(), executable, aliasfile, getPackageName() + "/com.yyh.service.HostMonitor");

		btnstart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				(new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							NativeRuntime.getInstance().startService(getPackageName() + "/com.yyh.service.HostMonitor", FileUtils.createRootPath());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				})).start();
			}
		});

		btnend.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					NativeRuntime.getInstance().stopService();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
	}
}
