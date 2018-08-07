package com.example.zxg.myprogram.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class DoubleSurfaceViewFlicker extends SurfaceView implements
        SurfaceHolder.Callback {

    private final static String TAG = "DoubleSurfaceViewFlicker";
    private SurfaceHolder shd;

    public DoubleSurfaceViewFlicker(Context context) {
        super(context);
        init();
    }

    public DoubleSurfaceViewFlicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DoubleSurfaceViewFlicker(Context context, AttributeSet attrs,
                                    int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    void init() {
        shd = this.getHolder();
        shd.addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        width = this.getWidth();
        height = this.getHeight();

        board = Bitmap.createBitmap(this.getWidth(), this.getHeight(),
                Bitmap.Config.ARGB_8888);
        boardCanvas = new Canvas(board);

        paint = new Paint();
        paint.setStyle(Style.FILL);
        paint.setAntiAlias(true);



//        Random random = new Random(System.currentTimeMillis());
//        Canvas canvas = null;
//        paint.setColor(Color.rgb(random.nextInt(255),
//                random.nextInt(255), random.nextInt(255)));
//        paint.setStrokeWidth(4);
//        paint.setTextSize(80);
//
//
//        paint.setColor(Color.rgb(random.nextInt(255),
//                random.nextInt(255), random.nextInt(255)));
//        paint.setStrokeWidth(4);
//        paint.setTextSize(80);
//
//        canvas = shd.lockCanvas();
//        canvas.drawRect(300, 20, 340, 60, paint);
//        shd.unlockCanvasAndPost(canvas);
//        canvas = shd.lockCanvas();
//        canvas.drawRect(350, 0, 490, 60, paint);
//        shd.unlockCanvasAndPost(canvas);
//
//        int temp = 0;
//        while(temp <= 10) {
//            temp++;
////            canvas = shd.lockCanvas(new Rect(0, 0, 300, 300));
//            canvas = shd.lockCanvas();
//            canvas.drawRect(temp*20, temp*20, temp*20+40, temp*20+40, paint);
//            canvas.drawText(temp+"", temp * 20, temp * 20, paint);
//            shd.unlockCanvasAndPost(canvas);
//            LogUtils.i(TAG, temp + "");
//        }


        drawThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    private int width;
    private int height;
    private Boolean isRunning = true;
    public void setIsRunning(Boolean isRunning) {
        this.isRunning = isRunning;
    }

    private Paint paint;
    private int i = 0;

    Bitmap board = null;
    Canvas boardCanvas = null;

    Thread drawThread = new Thread() {

        public void run() {
            while (isRunning) {
                long startTime = System.currentTimeMillis();
                Random random = new Random(System.currentTimeMillis());
                Canvas canvas = null;

                try {
                    paint.setColor(Color.RED);
                    paint.setStrokeWidth(4);
                    paint.setTextSize(80);

                    i++;
                    if (i>10) {
                        isRunning = false;
                    }

                    canvas = shd.lockCanvas();
                    if (canvas != null && board != null ) {
                        canvas.drawText(i+"", 50+i*20, 50+i*20, paint);
                    }

                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (shd != null && canvas != null) {
                        shd.unlockCanvasAndPost(canvas);
                    }
                    long endTime = System.currentTimeMillis();
                }
            }
        };
    };

}
