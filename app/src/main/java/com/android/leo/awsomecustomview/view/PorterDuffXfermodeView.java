package com.android.leo.awsomecustomview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.RectF;


public class PorterDuffXfermodeView extends View {
    private int bitmapSize;
    private int offset;
    private Paint xfermodePaint;
    private Paint textPaint;
    private static final PorterDuff.Mode MODE = PorterDuff.Mode.CLEAR;
    private Bitmap srcBitmap, dstBitmap;
    private Bitmap xfermodeBitmap;

    private PorterDuffXfermode porterDuffXfermode;

    public PorterDuffXfermodeView(Context context) {
        this(context, null);
    }

    public PorterDuffXfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        setXfermode(MODE);
    }

    private void initPaint() {
        xfermodePaint = new Paint();

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(50);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmapSize = w / 2;
        //offset is 1/3
        offset = bitmapSize / 3;
        srcBitmap = makeSrc(bitmapSize, bitmapSize);
        dstBitmap = makeDst(bitmapSize, bitmapSize);
        xfermodeBitmap = createXfermodeBitmap();
    }


    private Bitmap makeDst(int width, int height) {
        Bitmap bm = createBitamp(width, height);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(0xFFFFCC44);
        //the oval's width and height is both 2/3
        c.drawOval(new RectF(0, 0, width / 3 * 2, height / 3 * 2), p);
        return bm;
    }

    private Bitmap makeSrc(int width, int height) {
        Bitmap bm = createBitamp(width, height);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(0xFF66AAFF);
        //move x,y to 1/3 position, the rect's width and height is both 2/3,
        c.drawRect(width / 3, height / 3, width, height, p);
        return bm;
    }

    private Bitmap createBitamp(int w, int h) {
        return Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.drawColor(Color.LTGRAY);
        canvas.translate(-offset / 2, 0);

        canvas.drawBitmap(srcBitmap, 0, -offset, null);
        canvas.drawText("src", offset+bitmapSize/4, offset, textPaint);

        canvas.drawBitmap(dstBitmap, 4 * offset, 0, null);
        canvas.drawText("dst", 4 * offset+bitmapSize/4, offset, textPaint);

        canvas.restore();

        canvas.drawBitmap(xfermodeBitmap, 0, bitmapSize, null);
    }


    private Bitmap createXfermodeBitmap() {
        if (bitmapSize <= 0) {
            return null;
        }
        Bitmap bitmap = createBitamp(bitmapSize, bitmapSize);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawBitmap(dstBitmap, 0, 0, null);
        canvas.drawBitmap(srcBitmap, 0, 0, xfermodePaint);
        return bitmap;
    }

    public void setXfermode(PorterDuff.Mode mode) {
        if (mode == null) {
            return;
        }
        porterDuffXfermode = new PorterDuffXfermode(mode);
        xfermodePaint.setXfermode(porterDuffXfermode);
        xfermodeBitmap = createXfermodeBitmap();
    }
}
