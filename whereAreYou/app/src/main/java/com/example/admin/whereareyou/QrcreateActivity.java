package com.example.admin.whereareyou;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QrcreateActivity extends AppCompatActivity{
    String TAG = "tiger";
    Intent intent;
    String content;
    LinearLayout qrll;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcreate);
        textView = findViewById(R.id.qrtext);
        intent = getIntent();
        content = intent.getStringExtra("data").toString();
        textView.setText("당신의 아이디는\b"+content+"입니다");
        Log.d(TAG, "onCreate: "+content);
       if(content.isEmpty()) {
            Toast.makeText(this, "휴대폰번호가 받아지지 않음", Toast.LENGTH_LONG).show();
        } else {
            generateRQCode(content);
        }
    }
    public void generateRQCode(String contents) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            Bitmap bitmap = toBitmap(qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, 500, 500));
            ((ImageView) findViewById(R.id.qriv)).setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
    public static Bitmap toBitmap(BitMatrix matrix) {
        int height = matrix.getHeight();
        int width = matrix.getWidth();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }
}
