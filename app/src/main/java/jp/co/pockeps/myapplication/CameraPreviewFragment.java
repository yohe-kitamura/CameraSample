package jp.co.pockeps.myapplication;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressWarnings("deprecation")
public class CameraPreviewFragment extends Fragment  {

    public static final String TAG = CameraPreviewFragment.class.getCanonicalName();
    private Preview mPreview;
    private Camera mCamera;
    private int defaultCameraId;

    public CameraPreviewFragment() {
    }

    public static CameraPreviewFragment newInstance() {
        return new CameraPreviewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mPreview = new Preview(getContext());

        return mPreview;
    }

    private int getDefaultCameraId() {
        int defaultCameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                defaultCameraId = i;
            }
        }
        return defaultCameraId;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getDefaultCameraId() > 0) {
            mCamera = Camera.open(getDefaultCameraId());
            mPreview.setCamera(mCamera);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mCamera != null) {
            mPreview.setCamera(null);
            mCamera.release();
            mCamera = null;
        }
    }

}
