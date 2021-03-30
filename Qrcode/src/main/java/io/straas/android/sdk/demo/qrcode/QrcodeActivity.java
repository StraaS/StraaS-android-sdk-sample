package io.straas.android.sdk.demo.qrcode;

import android.annotation.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.SurfaceHolder.*;

import com.google.android.gms.tasks.*;
import com.google.android.gms.vision.*;
import com.google.android.gms.vision.barcode.*;

import java.io.*;

import androidx.annotation.*;
import androidx.appcompat.app.*;
import androidx.appcompat.widget.*;
import androidx.loader.app.LoaderManager.*;
import androidx.loader.content.Loader;

@SuppressLint("MissingPermission")
public class QrcodeActivity extends AppCompatActivity implements Callback, OnSuccessListener<Barcode> {
    public static final int LOADER_ID = 0;
    public static final String KEY_QR_CODE_VALUE = "key_qr_code";

    public static final String TAG = QrcodeActivity.class.getSimpleName();
    private SurfaceHolder mSurfaceHolder;
    private CameraSource mCameraSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        SurfaceView surfaceView = findViewById(R.id.surfaceview);
        surfaceView.getHolder().addCallback(this);
        getSupportLoaderManager().initLoader(LOADER_ID, null, new LoaderCallbacks<CameraSource>() {
            @Override
            public Loader<CameraSource> onCreateLoader(int id, Bundle args) {
                return new CameraSourceLoader(QrcodeActivity.this);
            }

            @Override
            public void onLoadFinished(Loader<CameraSource> loader, CameraSource data) {
                ((CameraSourceLoader) loader).getBarcodeScanTask()
                        .addOnSuccessListener(QrcodeActivity.this, QrcodeActivity.this);
                mCameraSource = data;
                if (mSurfaceHolder != null) {
                    startCameraSource();
                }
            }

            @Override
            public void onLoaderReset(Loader<CameraSource> loader) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        getSupportLoaderManager().getLoader(LOADER_ID).stopLoading();
        mCameraSource = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Because of this issue: http://stackoverflow.com/a/29464116
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(Barcode barcode) {
        Intent intent = new Intent();
        intent.putExtra(KEY_QR_CODE_VALUE, barcode.displayValue);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void startCameraSource() {
        if (mCameraSource != null) {
            try {
                mCameraSource.start(mSurfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
                Intent intent = new Intent();
                intent.putExtra(TAG, e.getMessage());
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceHolder = holder;
        startCameraSource();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mSurfaceHolder = null;
    }
}
