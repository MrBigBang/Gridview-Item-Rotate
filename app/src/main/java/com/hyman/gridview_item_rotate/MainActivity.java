package com.hyman.gridview_item_rotate;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyman.gridview_item_rotate.adapter.GridAdapter;
import com.hyman.gridview_item_rotate.anim.RotateAnimation;
import com.hyman.gridview_item_rotate.util.Constants;
import com.hyman.gridview_item_rotate.util.DisplayUtil;
import com.hyman.gridview_item_rotate.util.ItemCenterMeasure;
import com.hyman.gridview_item_rotate.widget.MyGridView;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by hyman on 2015/8/8.
 */
public class MainActivity extends Activity implements RotateAnimation.InterpolatedTimeListener, SensorEventListener {

    private Context context;

    private MyGridView mGridView;

    private GridAdapter mAdapter;

    private boolean isClicked = false;

    private boolean enableRefreshFirst;

    private boolean enableRefreshSecond;

    private View mView;

    private int gridWidthPix;

    private int gridHeightPix;

    private TextView tvBm;

    private Handler handler;

    private int currentPos;

    private SoundPool soundPool;

    private int music;//define a integer by use function "load()"  to set suondID

    private Typeface tf;//define font style

    private SensorManager sensorManager;

    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        initView();
    }

    private void initView() {

        handler = new Handler();

        DisplayUtil.init(context);

        createCRTextView();

        ItemCenterMeasure.measureCenter(gridWidthPix, gridHeightPix);

        //initialize click sound effect
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        music = soundPool.load(context, R.raw.open, 1);
        //initialize font style
        tf = Typeface.createFromAsset(getAssets(), "fonts/MyriadPro-Cond.otf");

        mGridView = (MyGridView) findViewById(R.id.gridview);

        mAdapter = new GridAdapter(context, Constants.getUrls(1));

        mAdapter.setOnItemTouchedListener(new GridAdapter.OnItemTouchedListener() {

            @Override
            public boolean onItemTouch(View v, MotionEvent event, int position) {
                mView = v;
                currentPos = position;
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if (isClicked) {
                        Toast toast = Toast.makeText(context, getString(R.string.warning),
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return false;
                    } else{
                        //play sound
                        soundPool.play(music, 1, 1, 0, 0, 1);
                        //trigger zoom animation on items
                        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        scaleAnimation.setDuration(500);
                        AnimationSet animationSet = new AnimationSet(true);
                        animationSet.addAnimation(scaleAnimation);
                        animationSet.setFillAfter(true);
                        animationSet.setAnimationListener(new Animation.AnimationListener() {

                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                enableRefreshFirst = true;
                                RotateAnimation rotateAnim = null;

                                float cX = mView.getWidth() / 2.0f;
                                float cY = mView.getHeight() / 2.0f;
                                rotateAnim = new RotateAnimation(cX, cY);

                                if (rotateAnim != null) {
                                    rotateAnim.setInterpolatedTimeListener(MainActivity.this, mView);
                                    rotateAnim.setFillAfter(true);
                                    rotateAnim.setAnimationListener(new Animation.AnimationListener() {

                                        @Override
                                        public void onAnimationStart(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            handler.postDelayed(new Runnable() {

                                                @Override
                                                public void run() {
                                                    otherItemRotate();
                                                }
                                            }, 500);
                                        }
                                    });
                                    mView.startAnimation(rotateAnim);

                                }

                                isClicked = true;
                            }
                        });
                        v.startAnimation(animationSet);
                    }
                }

                return false;
            }
        });

        mGridView.setAdapter(mAdapter);

        //initialize sensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);

    }

    private void otherItemRotate() {
        int num = mAdapter.getCount();
        for (int i = 0; i < num; i++) {
            if (i  != currentPos) {
                View itemView = mGridView.getChildAt(i);
                enableRefreshSecond = true;
                RotateAnimation rotateAnim = null;

                float cX = itemView.getWidth() / 2.0f;
                float cY = itemView.getHeight() / 2.0f;
                rotateAnim = new RotateAnimation(cX, cY);

                if (rotateAnim != null) {
                    RotateAnimation.InterpolatedTimeListener timeListener = new RotateAnimation.InterpolatedTimeListener() {

                        @Override
                        public void interpolatedTime(float interpolatedTime,
                                                     View view) {
                            if (enableRefreshSecond && interpolatedTime > 0.5f) {
                                ImageView imageView = (ImageView) view.findViewById(R.id.grid_image);
                                imageView.setVisibility(View.GONE);
                                TextView tvPoint = (TextView) view.findViewById(R.id.point_tv);
                                tvPoint.setText(getString(R.string.points, 200));
                                tvPoint.setTextColor(getResources().getColor(R.color.green));
                                tvPoint.setTypeface(tf);
                                tvPoint.setVisibility(View.VISIBLE);
                            }


                            // change the alpha
                            if (interpolatedTime > 0.5f) {
                                ViewHelper.setAlpha(view, (interpolatedTime - 0.5f) * 2);
                            } else {
                                ViewHelper.setAlpha(view, 1 - interpolatedTime * 2);
                            }
                        }

                    };
                    rotateAnim.setInterpolatedTimeListener(timeListener, itemView);
                    rotateAnim.setFillAfter(true);
                    itemView.startAnimation(rotateAnim);
                }
            }
        }
    }

    private void createCRTextView() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.logo_ly);
        tvBm = new TextView(context);
        tvBm.setText(getResources().getString(R.string.logo));
        tvBm.setTextAppearance(context, R.style.logoTextView);
        LinearLayout.LayoutParams crParams = new LinearLayout.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);
        crParams.topMargin = 5;
        crParams.bottomMargin = 10;
        crParams.gravity = Gravity.CENTER;
        tvBm.setLayoutParams(crParams);

        tvBm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ScaleAnimation  scaleAnim = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnim.setDuration(500);
                scaleAnim.setFillAfter(false);
                tvBm.startAnimation(scaleAnim);
            }
        });

        ll.addView(tvBm);
        int tvBmHeight = crParams.height + 5 + 10;


        gridWidthPix = DisplayUtil.SCREEN_WIDTH_PIXELS - DisplayUtil.dp2px(16) * 2;
        //I am not sure why the right of next equality should minus 80px to display all items in the gridview without scrollbar in my devices (1920¡Á1080),
        //this may make a bad adaption for various devices with different resolution ratio
        gridHeightPix = DisplayUtil.SCREEN_HEIGHT_PIXELS - DisplayUtil.dp2px(16) * 2 - tvBmHeight - 80;


    }

    @Override
    public void interpolatedTime(float interpolatedTime, View view) {

        if (enableRefreshFirst && interpolatedTime > 0.5f) {
            ImageView imageView = (ImageView) view.findViewById(R.id.grid_image);
            imageView.setVisibility(View.GONE);
            TextView tvPoint = (TextView) view.findViewById(R.id.point_tv);
            tvPoint.setText(getString(R.string.points, 200));
            tvPoint.setTextColor(getResources().getColor(R.color.hotpink));
            tvPoint.setTypeface(tf);
            tvPoint.setVisibility(View.VISIBLE);
            enableRefreshFirst = false;
        }

        // change the alpha
        if (interpolatedTime > 0.5f) {
            ViewHelper.setAlpha(view, (interpolatedTime - 0.5f) * 2);
        } else {
            ViewHelper.setAlpha(view, 1 - interpolatedTime * 2);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float[] values = event.values;
        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            //values[0] X axis acceleration£¬values[1] Y axis acceleration£¬values[2] Z axis acceleration
            if (Math.abs(values[0]) > 17 || Math.abs(values[1]) > 17 || Math.abs(values[2]) > 17) {

                if (!isClicked) {
                    vibrator.vibrate(500);
                    mAdapter.setUrls(Constants.getUrls(2));
                    mGridView.invalidate();
                }
            }
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
