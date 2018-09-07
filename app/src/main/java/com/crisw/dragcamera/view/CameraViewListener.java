package com.crisw.dragcamera.view;

import android.graphics.Bitmap;

public interface CameraViewListener  {
    void resetState(int type);

    void confirmState(int type);

    void showPicture(Bitmap bitmap, boolean isVertical);

    void startPreviewCallback();

    boolean handlerFoucs(float x, float y);
}
