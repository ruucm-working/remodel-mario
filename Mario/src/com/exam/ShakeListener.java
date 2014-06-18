package com.exam;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class ShakeListener implements SensorEventListener
{
	private static Context context;
	//맴버변수 (마지막과 현재값을 비교하여 변위를 계산하는 방식)
	private long	m_lLastTime;
	private float	m_fSpeed;
	private float	m_fCurX,  m_fCurY,  m_fCurZ;
	private float	m_fLastX,  m_fLastY,  m_fLastZ;

	// 임계값 설정
	private static final int  SHAKE_THRESHOLD = 800;

	// 매니저 객체
	private SensorManager	m_senMng;
	private Sensor			m_senAccelerometer;

	public ShakeListener(SensorManager sm, Context context)
	{
		this.context = context;
		// 시스템 서비스에서 센서메니져 획득
		// m_senMng = (SensorManager)getSystemService(SENSOR_SERVICE);
		m_senMng = sm;

		// TYPE_ACCELEROMETER의 기본 센서객체를 획득
		m_senAccelerometer = m_senMng.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	}

	// 흔들기가 시작되면 호출되는 함수
	public void onStart()
	{
		Log.i("SHAKE", "onStart()");
	//	super.onStart();
		if(m_senAccelerometer != null)
			m_senMng.registerListener(this, m_senAccelerometer, SensorManager.SENSOR_DELAY_GAME);
	}

	// 흔들기가 끝나면 호출되는 함수
	public void onStop()
	{
		Log.i("kmsTest", "onStop()");
	//	super.onStop();
		if(m_senMng != null)
			m_senMng.unregisterListener(this);
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {}

	// 센서에 변화를 감지하기 위해 계속 호출되고 있는 함수
	public void onSensorChanged(SensorEvent event)
	{
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
		{
			long lCurTime  = System.currentTimeMillis();
			long lGabOfTime  = lCurTime - m_lLastTime;

			// 0.1초보다 오래되면 다음을 수행 (100ms)
			if(lGabOfTime > 150)
			{
				m_lLastTime = lCurTime;

				m_fCurX = event.values[SensorManager.DATA_X];
				m_fCurY = event.values[SensorManager.DATA_Y];
				m_fCurZ = event.values[SensorManager.DATA_Z];

				// 변위의 절대값에  / lGabOfTime * 10000 하여 스피드 계산
				m_fSpeed = Math.abs(m_fCurX + m_fCurY + m_fCurZ - m_fLastX - m_fLastY - m_fLastZ) / lGabOfTime * 10000;

				// 임계값보다 크게 움직였을 경우 다음을 수행
				if(m_fSpeed > SHAKE_THRESHOLD)
				{
					Log.i("SHAKE", "이 개SHAKE IT");
					context.sendBroadcast(new Intent("com.exam.view.SHAKE_DETECTED"));
				}

				// 마지막 위치 저장
				// m_fLastX = event.values[0]; 그냥 배열의 0번 인덱스가 X값
				m_fLastX = event.values[SensorManager.DATA_X];
				m_fLastY = event.values[SensorManager.DATA_Y];
				m_fLastZ = event.values[SensorManager.DATA_Z];
			}
		}
	}
}