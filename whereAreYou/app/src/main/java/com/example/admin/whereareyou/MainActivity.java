package com.example.admin.whereareyou;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.admin.whereareyou.barcode.CartActivity;
import com.example.admin.whereareyou.barcode.GoodsActivity;
import com.example.admin.whereareyou.barcode.QrcreateActivity;
import com.example.admin.whereareyou.function.BackPressedForFinish;
import com.example.admin.whereareyou.searchlist.SearchList;
import com.github.clans.fab.FloatingActionButton;

import java.util.Locale;
import java.util.Random;

import pl.polidea.view.ZoomView;

import static android.speech.tts.TextToSpeech.ERROR;

public class MainActivity extends AppCompatActivity {

    private String TAG = "tiger";
    //확대 축소하게 해주는
    private View v;
    private ZoomView zoomView;
    private RelativeLayout container;

    //버튼
    //이미지버튼 총 19개
    private ImageButton go_fruit, go_carrot, go_nut, go_coffee, go_rice, go_noodle, go_pot, go_ice, go_paper, go_snow, go_tow, go_milk, go_honey, go_snack, go_drink, go_fish, go_cow, go_beer, go_bread;
    private FloatingActionButton menu_qrCreate, menu_qrScan, menu_qrCart;

    //길찾는 값보낼라고 캔버스객체생성
    private NaviCanvas nc;
    //맵string 선언
    final private String fruit = "과일";
    final private String nut = "견과류";
    final private String ve = "야채";
    final private String coffee = "커피";
    final private String rice = "베이킹";
    final private String pot = "주방도구";
    final private String noodle = "라면";
    final private String ice = "빙과류";
    final private String fish = "수산";
    final private String cow = "축산";
    final private String paper = "생활용품";
    final private String tow = "욕실수납";
    final private String milk = "유제품";
    final private String snow = "냉동가공";
    final private String bread = "빵집";
    final private String honey = "소스";
    final private String alco = "주류";
    final private String snack = "과자";
    final private String drink = "음료";
    //search한 값 넘길라고
    private final static int REQUEST_TEST = 1;
    private int btnDe = 0;

    //다이얼로그
    private String loca;

    //길안내 시작 취소 tts
    private TextToSpeech myTTS;

    //뒤로가기 두번눌렀을때 꺼지도록
    BackPressedForFinish backPressedForFinish;

    //출발값 도착값(출발값은 나중에 비콘으로 찾을 수 있도록)
    private int startX, startY;
    private int goalX, goalY;
    private EditText editText;
    private Button btn;

    //--------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //backPressedForFish사용하여 종료
        backPressedForFinish = new BackPressedForFinish(this);
        //myTTS객체
        myTTS = new TextToSpeech(this, eventVoice);
        //내비캔버스 객체생성
        nc = findViewById(R.id.line);
        //-Floating버튼에 intent연결---------------------------------
        menu_qrCart = findViewById(R.id.menu_cart);
        menu_qrCreate = findViewById(R.id.menu_qrcreate);
        menu_qrScan = findViewById(R.id.menu_qrscan);
        menu_qrCreate.setOnClickListener(eventFabButton);
        menu_qrScan.setOnClickListener(eventFabButton);
        menu_qrCart.setOnClickListener(eventFabButton);
        //-이미지버튼연결과 리스너--------------------------------------
        //과일, 야채, 견과류, 커피/fruit, carrot, nut, coffee/4개
        go_fruit = findViewById(R.id.go_fruit);
        go_carrot = findViewById(R.id.go_carrot);
        go_nut = findViewById(R.id.go_nut);
        go_coffee = findViewById(R.id.go_coffee);
        go_fruit.setOnClickListener(event);
        go_carrot.setOnClickListener(event);
        go_nut.setOnClickListener(event);
        go_coffee.setOnClickListener(event);
        //베이킹, 라면, 주방도구, 빙과류/rice, noodle, pot, ice/4개
        go_rice = findViewById(R.id.go_rice);
        go_noodle = findViewById(R.id.go_noodle);
        go_pot = findViewById(R.id.go_pot);
        go_ice = findViewById(R.id.go_ice);
        go_rice.setOnClickListener(event);
        go_noodle.setOnClickListener(event);
        go_pot.setOnClickListener(event);
        go_ice.setOnClickListener(event);
        //생활용품, 냉동가공, 욕실수납, 유제품, 소스/paper, snow, tow, milk, honey/5개
        go_paper = findViewById(R.id.go_paper);
        go_snow = findViewById(R.id.go_snow);
        go_tow = findViewById(R.id.go_tow);
        go_milk = findViewById(R.id.go_milk);
        go_honey = findViewById(R.id.go_honey);
        go_paper.setOnClickListener(event);
        go_snow.setOnClickListener(event);
        go_tow.setOnClickListener(event);
        go_milk.setOnClickListener(event);
        go_honey.setOnClickListener(event);
        //과자, 음료, 수산, 축산/snack, drink, fish, cow/4개
        go_snack = findViewById(R.id.go_snack);
        go_drink = findViewById(R.id.go_drink);
        go_fish = findViewById(R.id.go_fish);
        go_cow = findViewById(R.id.go_cow);
        go_snack.setOnClickListener(event);
        go_drink.setOnClickListener(event);
        go_fish.setOnClickListener(event);
        go_cow.setOnClickListener(event);
        //주류, 빵
        go_beer = findViewById(R.id.go_beer);
        go_bread = findViewById(R.id.go_bread);
        go_beer.setOnClickListener(event);
        go_bread.setOnClickListener(event);
        //커스텀다이얼로그 사용하기위해서 객체생성

        //확대축소 가능하게해주는 ZoomView사용//만약 focus로 가는곳을 지정하지 못한다면 SurfaceView로 제어할수도 있는거 같음
        v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.zoom_item, null, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        zoomView = new ZoomView(this);
        zoomView.addView(v);
        zoomView.setLayoutParams(layoutParams);
        //zoomView.setMiniMapEnabled(true); // 좌측 상단 검은색 미니맵 설정
        //zoomView.setMiniMapCaption("확인창"); //미니 맵 내용
        //zoomView.setMiniMapCaptionSize(20); // 미니 맵 내용 글씨 크기 설정
        zoomView.setMaxZoom(2f); // 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다.
        container = findViewById(R.id.container);
        container.addView(zoomView);
        //-------------------------------------------------------------확대축소끝
        //-------------코드시작-------------------------------
        //-도착지 xy값
        //-------------------map기준이 [y][x]이므로 좀헷갈릴수도있겠으나 배열에 Y X순으로 대입
        //출발값(출발값은 나중에 가장가까운 비콘으로 찾을 수 있도록 해야됨)
        startX = 0;
        startY = 14;
        //
        nc = findViewById(R.id.line);
        editText = findViewById(R.id.edittext);
        btn = findViewById(R.id.btnsearch);
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn.setBackgroundColor(Color.argb(75, 255, 0, 0));
                        break;
                    case MotionEvent.ACTION_UP:
                        btn.setBackgroundColor(Color.argb(0, 255, 0, 0));
                        Intent intent = new Intent(MainActivity.this, SearchList.class);
                        startActivityForResult(intent, REQUEST_TEST);
                        break;
                }
                return false;
            }
        });
    }

    //search결과 들고와서 제어한다 길그린다 아아아아아
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TEST) {
            if (resultCode == RESULT_OK) {
                btnDe = 1;
                String loca = data.getStringExtra("sv");
                int[] goalPoint = data.getIntArrayExtra("gp");
                goalX = goalPoint[0];
                goalY = goalPoint[1];
                Log.d(TAG, "onClick: " + goalX + "메인 값받는곳" + goalY);
                show(loca, goalX, goalY);
            } else {   // RESULT_CANCEL
            }
        }
    }

    //이미지버튼 제어하는곳-------------------------------------------------------------------------------
    View.OnClickListener event = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //map[y][x]값 기준 도착지
                //map[2][3]
                //과일/1/goal1
                case R.id.go_fruit:
                    loca = fruit;
                    goalX = 3;
                    goalY = 2;
                    break;
                //견과/2/goal1
                case R.id.go_nut:
                    loca = nut;
                    goalX = 3;
                    goalY = 2;
                    break;
                //map[2][9]
                //야채/3/goal2
                case R.id.go_carrot:
                    loca = ve;
                    goalX = 9;
                    goalY = 2;
                    break;
                //map[2][6]
                //커피/4/goal3
                case R.id.go_coffee:
                    loca = coffee;
                    goalX = 6;
                    goalY = 2;
                    break;
                //map[5][3]
                //베이킹/5/goal4
                case R.id.go_rice:
                    loca = rice;
                    goalX = 3;
                    goalY = 5;
                    break;
                //주방/6/goal4
                case R.id.go_pot:
                    loca = pot;
                    goalX = 3;
                    goalY = 5;
                    break;
                //map[5][7]
                //라면/7/goal5
                case R.id.go_noodle:
                    loca = noodle;
                    goalX = 7;
                    goalY = 5;
                    break;
                //빙과/8/goal5
                case R.id.go_ice:
                    loca = ice;
                    goalX = 7;
                    goalY = 5;
                    break;
                //map[5][11]
                //수산/9/goal6
                case R.id.go_fish:
                    loca = fish;
                    goalX = 11;
                    goalY = 5;
                    break;
                //축산/10/goal6
                case R.id.go_cow:
                    loca = cow;
                    goalX = 11;
                    goalY = 5;
                    break;
                //map[8][3]
                //생활/11/goal7
                case R.id.go_paper:
                    loca = paper;
                    goalX = 3;
                    goalY = 8;
                    break;
                //욕실/12/goal7
                case R.id.go_tow:
                    loca = tow;
                    goalX = 3;
                    goalY = 8;
                    break;
                //map[8][7]
                //유제/13/goal8
                case R.id.go_milk:
                    loca = milk;
                    goalX = 7;
                    goalY = 8;
                    break;
                //냉동/14/goal8
                case R.id.go_snow:
                    loca = snow;
                    goalX = 7;
                    goalY = 8;
                    break;
                //map[11][11]
                //빵/15/goal9
                case R.id.go_bread:
                    loca = bread;
                    goalX = 11;
                    goalY = 11;
                    break;
                //주류/16/goal9
                case R.id.go_beer:
                    loca = alco;
                    goalX = 11;
                    goalY = 11;
                    break;
                //map[11][4]
                //소스/17/goal10
                case R.id.go_honey:
                    loca = honey;
                    goalX = 4;
                    goalY = 11;
                    break;
                //map[11][7]
                //과자/18/goal11
                case R.id.go_snack:
                    loca = snack;
                    goalX = 7;
                    goalY = 11;
                    break;
                //음료/19/goal11
                case R.id.go_drink:
                    loca = drink;
                    goalX = 7;
                    goalY = 11;
                    break;
            }
            btnDe = 0;
            show(loca, goalX, goalY);
            nc.invalidate();
        }
    };

    //Fab버튼 제어
    View.OnClickListener eventFabButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //floating버튼 제어하는곳---------------------------------------------------------------------------------------------------
                //-----------------권한승인
                case R.id.menu_qrcreate:
                    TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    int permissionChk = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE);
                    if (permissionChk == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(MainActivity.this, "권한을 거부할 시 마일리지 적립이 되지 않습니다.", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                    } else {
                        String tellNum = "";
                        try {
                            tellNum = tel.getLine1Number().toString();
                            Intent intent = new Intent(MainActivity.this, QrcreateActivity.class);
                            intent.putExtra("data", tellNum);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                //QR스캔---------------------------------------------------------------------------------
                case R.id.menu_qrscan:
                    //나중에 다른 Activity로 넘어가게 만들어서 상품정보띄울 수 있어야됨
                    Log.d(TAG, "onClick: 스캔누름");
                    Intent intent1 = new Intent(MainActivity.this, GoodsActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.menu_cart:
                    Log.d(TAG, "onClick: 장바구니누름");
                    Intent intent2 = new Intent(MainActivity.this, CartActivity.class);
                    startActivity(intent2);
                    break;
            }
        }
    };

    //다이얼로그 실행
    final void show(String loca, int goalX, int goalY) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (btnDe == 0) {
            builder.setTitle("길안내");
            loca = loca + "코너로 길안내를 시작하겠습니다.";
        } else {
            builder.setTitle("상품 길안내");
            loca = "상품(" + loca + ")으로 길안내를 시작하겠습니다";
        }
        builder.setMessage(loca);
        builder.setPositiveButton("네", yesDialog);
        builder.setNegativeButton("아니요", noDialog);
        builder.show();
        //말하자
        myTTS.setPitch(1.0f);
        myTTS.setSpeechRate(1.0f);
        myTTS.speak(loca, TextToSpeech.QUEUE_FLUSH, null);
    }

    //다이얼로그 확인 눌렀을때 작동할수있는 함수
    DialogInterface.OnClickListener yesDialog = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            nc.setsY(startY);
            nc.setsX(startX);
            nc.setgY(goalY);
            nc.setgX(goalX);
            nc.invalidate();
            dialog.dismiss();
        }
    };
    //다이얼로그 취소 눌렀을때 작동함
    DialogInterface.OnClickListener noDialog = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            loca = "길안내를 취소하였습니다";
            Toast.makeText(getApplicationContext(), loca, Toast.LENGTH_SHORT).show();
            //말해라
            myTTS.setPitch(1.0f);
            myTTS.setSpeechRate(1.0f);
            myTTS.speak(loca, TextToSpeech.QUEUE_FLUSH, null);
            dialog.dismiss();
        }
    };

    //음성출력되게해주는 함수
    TextToSpeech.OnInitListener eventVoice = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            if (status != ERROR) {
                myTTS.setLanguage(Locale.KOREAN);
            }
        }
    };

    //음성출력 앱 종료시 날려줌
    @Override
    protected void onDestroy() {
        super.onDestroy();
        myTTS.stop();
        myTTS.shutdown();
    }

    //뒤로가기 두번터치시 꺼지게

    @Override
    public void onBackPressed() {
        backPressedForFinish.onBackPressed();
    }
}
