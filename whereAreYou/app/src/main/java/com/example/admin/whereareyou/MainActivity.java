package com.example.admin.whereareyou;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import pl.polidea.view.ZoomView;

public class MainActivity extends AppCompatActivity {
    String TAG = "tiger";
    //테스트용 전역함수 추후 삭제
    EditText startX;
    EditText startY;
    EditText goalX;
    EditText goalY;
    LinearLayout testll;
    NaviCanvas nc;

    //-----------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView indexGIF = findViewById(R.id.indexGIF);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(indexGIF);
        Glide.with(this).load(R.raw.index).into(imageViewTarget);

        //확대축소 가능하게해주는 ZoomView사용
        View v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.zoom_item, null, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ZoomView zoomView = new ZoomView(this);
        zoomView.addView(v);
        zoomView.setLayoutParams(layoutParams);
        zoomView.setMiniMapEnabled(true); // 좌측 상단 검은색 미니맵 설정
        zoomView.setMaxZoom(2f); // 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다.
        zoomView.setMiniMapCaption("확인창"); //미니 맵 내용
        zoomView.setMiniMapCaptionSize(20); // 미니 맵 내용 글씨 크기 설정

        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        container.addView(zoomView);

        //-------------코드시작-------------------------------
        //테스트용 코드--------------------------------------
        startX = findViewById(R.id.startTextX);
        startY = findViewById(R.id.startTextY);
        goalX = findViewById(R.id.goalTextX);
        goalY = findViewById(R.id.goalTextY);
        testll = findViewById(R.id.testll);
        nc = findViewById(R.id.line);
        //---------------------------핸드폰정보읽어오자
    }

    public void test1(View view) {
        Log.d(TAG, "onTouch: DOWN");
        int sX = Integer.parseInt(startX.getText().toString());
        int sY = Integer.parseInt(startY.getText().toString());
        int gX = Integer.parseInt(goalX.getText().toString());
        int gY = Integer.parseInt(goalY.getText().toString());
        //NaviCanvas로 보내기위한
        nc.setsX(sX);
        nc.setsY(sY);
        nc.setgX(gX);
        nc.setgY(gY);
        nc.invalidate();
    }    //--------테스트코드끝-------------------------------

    //QR스캔
    public void qrScan(View view) {
        //나중에 다른 Activity로 넘어가게 만들어서 상품정보띄울 수 있어야됨
        new IntentIntegrator(this).initiateScan(); // `this` is the current Activity
    }

    //QR스캔 후 과정처리하는 함수 여기 손보면 됨
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //  com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE
        //  = 0x0000c0de; // Only use bottom 16 bits
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result == null) {
                // 취소됨
                Toast.makeText(this, "상품정보 보기를 취소하셨습니다.", Toast.LENGTH_LONG).show();
            } else {
                // 스캔된 QRCode --> result.getContents()
                Toast.makeText(this, "상품정보: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //QR코드 생성하면서 핸드폰 정보 얻어와서 그걸 위주로 QR생성해주자
    public void qrCreate(View view) {
//-----------------권한승인
        TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        int permissionChk = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if(permissionChk == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "권한을 거부할 시 마일리지 적립이 되지 않습니다.", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        } else {
            String tellNum = "";
            try {
                tellNum = tel.getLine1Number().toString();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }



        //----------권한끝
    }
}
