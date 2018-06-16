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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.admin.whereareyou.barcode.CartActivity;
import com.example.admin.whereareyou.barcode.GoodsActivity;
import com.example.admin.whereareyou.barcode.QrcreateActivity;
import com.github.clans.fab.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import pl.polidea.view.ZoomView;

public class MainActivity extends AppCompatActivity {
    String TAG = "tiger";
    //버튼
    //이미지버튼 총 19개
    ImageButton go_fruit, go_carrot, go_nut, go_coffee, go_rice, go_noodle, go_pot, go_ice, go_paper, go_snow, go_tow, go_milk, go_honey, go_snack, go_drink, go_fish, go_cow, go_beer, go_bread;
    FloatingActionButton menu_qrCreate, menu_qrScan, menu_qrCart;
    //길찾는 값보낼라고 캔버스객체생성
    NaviCanvas nc;
    //출발값 도착값(출발값은 나중에 비콘으로 찾을 수 있도록)
    int startX, startY;
    int goalX, goalY;
    int[] startFind, goal1, goal2, goal3, goal4, goal5, goal6, goal7, goal8, goal9, goal10, goal11;

    //--------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //-Floating버튼에 intent연결---------------------------------
        menu_qrCart = findViewById(R.id.menu_cart);
        menu_qrCreate = findViewById(R.id.menu_qrcreate);
        menu_qrScan = findViewById(R.id.menu_qrscan);
        menu_qrCreate.setOnClickListener(event);
        menu_qrScan.setOnClickListener(event);
        menu_qrCart.setOnClickListener(event);
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

        //-------------------map기준이 [y][x]이므로 좀헷갈릴수도있겠으나 배열에 Y X순으로 대입
        //출발값(출발값은 나중에 가장가까운 비콘으로 찾을 수 있도록 해야됨)
        startX = 0; startY = 14;
        startFind = new int[]{startY, startX};

        //도착값(이게 맞는건지 모르겠음 매우 비효율적인데 나는 이것밖에 생각안남 ㅇ//창일아 자면안된다 잠에서 깨라
        //goal1, 과일,견과
        goalX = 3; goalY = 2;
        goal1 = new int[]{goalY, goalX};

        //goal2, 야채
        goalX = 9; goalY = 2;
        goal2 = new int[]{goalY, goalX};

        //goal3, 커피
        goalX = 6; goalY = 2;
        goal3 = new int[]{goalY, goalX};

        //goal4, 베이킹, 주방
        goalX = 3; goalY = 5;
        goal4 = new int[]{goalY, goalX};

        //goal5, 라면, 빙과
        goalX = 7; goalY = 5;
        goal5 = new int[]{goalY, goalX};

        //goal6, 수산, 축산
        goalX = 11; goalY = 5;
        goal6 = new int[]{goalY, goalX};

        //goal7, 생활, 욕실
        goalX = 3; goalY = 8;
        goal7 = new int[]{goalY, goalX};

        //goal8, 유제, 냉동
        goalX = 7; goalY = 8;
        goal8 = new int[]{goalY, goalX};

        //goal9, 빵, 주류
        goalX = 11; goalY = 11;
        goal9 = new int[]{goalY, goalX};

        //goal10, 소스
        goalX = 4; goalY = 11;
        goal10 = new int[]{goalY, goalX};

        //goal11, 과자, 음료
        goalX = 7; goalY = 11;
        goal11 = new int[]{goalY, goalX};

        //확대축소 가능하게해주는 ZoomView사용//만약 focus로 가는곳을 지정하지 못한다면 SurfaceView로 제어할수도 있는거 같음
        View v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.zoom_item, null, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ZoomView zoomView = new ZoomView(this);
        zoomView.addView(v);
        zoomView.setLayoutParams(layoutParams);
        //zoomView.setMiniMapEnabled(true); // 좌측 상단 검은색 미니맵 설정
        //zoomView.setMiniMapCaption("확인창"); //미니 맵 내용
        //zoomView.setMiniMapCaptionSize(20); // 미니 맵 내용 글씨 크기 설정
        zoomView.setMaxZoom(2f); // 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다.
        RelativeLayout container = findViewById(R.id.container);
        container.addView(zoomView);
        //-------------------------------------------------------------확대축소끝
        //-------------코드시작-------------------------------
        nc = findViewById(R.id.line);//NaviCanvas객체
    }
    //--------테스트코드끝-------------------------------
    View.OnClickListener event = new View.OnClickListener() {
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
            //이미지버튼 제어하는곳-------------------------------------------------------------------------------
                //map[y][x]값 기준 도착지
                    //map[2][3]
                        //과일/1/goal1
                case R.id.go_fruit :
                    Log.d(TAG, "onClick: 과일");
                    nc.setsY(startFind[0]);nc.setsX(startFind[1]);
                    nc.setgY(goal1[0]);nc.setgX(goal1[1]);
                    nc.invalidate();
                    break;
                        //견과/2/goal1
                case R.id.go_nut :
                    Log.d(TAG, "onClick: 견과");
                    nc.setsY(startFind[0]);nc.setsX(startFind[1]);
                    nc.setgY(goal1[0]);nc.setgX(goal1[1]);
                    nc.invalidate();
                    break;
                    //map[2][9]
                        //야채/3/goal2
                case R.id.go_carrot :
                    Log.d(TAG, "onClick: 야채");
                    nc.setsY(startFind[0]);nc.setsX(startFind[1]);
                    nc.setgY(goal2[0]);nc.setgX(goal2[1]);
                    nc.invalidate();
                    break;
                    //map[2][6]
                        //커피/4/goal3
                case R.id.go_coffee :
                    Log.d(TAG, "onClick: 커피");
                    nc.setsY(startFind[0]);nc.setsX(startFind[1]);
                    nc.setgY(goal3[0]);nc.setgX(goal3[1]);
                    nc.invalidate();
                    break;
                    //map[5][3]
                        //베이킹/5/goal4
                case R.id.go_rice :
                    Log.d(TAG, "onClick: 베이킹");
                    nc.setsY(startFind[0]);nc.setsX(startFind[1]);
                    nc.setgY(goal4[0]);nc.setgX(goal4[1]);
                    nc.invalidate();
                    break;
                        //주방/6/goal4
                case R.id.go_pot :
                    Log.d(TAG, "onClick: 주방");
                    nc.setsY(startFind[0]);nc.setsX(startFind[1]);
                    nc.setgY(goal4[0]);nc.setgX(goal4[1]);
                    nc.invalidate();
                    break;
                    //map[5][7]
                        //라면/7/goal5
                case R.id.go_noodle :
                    Log.d(TAG, "onClick: 라면");
                    nc.setsY(startFind[0]);nc.setsX(startFind[1]);
                    nc.setgY(goal5[0]);nc.setgX(goal5[1]);
                    nc.invalidate();
                    break;
                        //빙과/8/goal5
                case R.id.go_ice :
                    Log.d(TAG, "onClick: 빙과");
                    nc.setsY(startFind[0]);nc.setsX(startFind[1]);
                    nc.setgY(goal5[0]);nc.setgX(goal5[1]);
                    nc.invalidate();
                    break;
                   //map[5][11]
                        //수산/9/goal6
                case R.id.go_fish :
                    Log.d(TAG, "onClick: 수산");
                    nc.setsY(startFind[0]);nc.setsX(startFind[1]);
                    nc.setgY(goal6[0]);nc.setgX(goal6[1]);
                    nc.invalidate();
                    break;
                        //축산/10/goal6
                case R.id.go_cow :
                    Log.d(TAG, "onClick: 축산");
                    nc.setsY(startFind[0]);nc.setsX(startFind[1]);
                    nc.setgY(goal6[0]);nc.setgX(goal6[1]);
                    nc.invalidate();
                    break;
                    //map[8][3]
                        //생활/11/goal7
                case R.id.go_paper :
                    Log.d(TAG, "onClick: 생활");
                    nc.setsY(startFind[0]);nc.setsX(startFind[1]);
                    nc.setgY(goal7[0]);nc.setgX(goal7[1]);
                    nc.invalidate();
                    break;
                        //욕실/12/goal7
                case R.id.go_tow :
                    Log.d(TAG, "onClick: 욕실");
                    nc.setsY(startFind[0]);nc.setsX(startFind[1]);
                    nc.setgY(goal7[0]);nc.setgX(goal7[1]);
                    nc.invalidate();
                    break;
                    //map[8][7]
                        //유제/13/goal8
                case R.id.go_milk :
                    Log.d(TAG, "onClick: 유제");
                    nc.setsY(startFind[0]);nc.setsX(startFind[1]);
                    nc.setgY(goal8[0]);nc.setgX(goal8[1]);
                    nc.invalidate();
                    break;
                        //냉동/14/goal8
                case R.id.go_snow :
                    Log.d(TAG, "onClick: 냉동");
                    nc.setsY(startFind[0]);nc.setsX(startFind[1]);
                    nc.setgY(goal8[0]);nc.setgX(goal8[1]);
                    nc.invalidate();
                    break;
                    //map[11][11]
                        //빵/15/goal9
                case R.id.go_bread :
                    Log.d(TAG, "onClick: 빵");
                    nc.setsY(startFind[0]);nc.setsX(startFind[1]);
                    nc.setgY(goal9[0]);nc.setgX(goal9[1]);
                    nc.invalidate();
                    break;
                        //주류/16/goal9
                case R.id.go_beer :
                    Log.d(TAG, "onClick: 주류");
                    nc.setsY(startFind[0]);nc.setsX(startFind[1]);
                    nc.setgY(goal9[0]);nc.setgX(goal9[1]);
                    nc.invalidate();
                    break;
                    //map[11][4]
                        //소스/17/goal10
                case R.id.go_honey :
                    Log.d(TAG, "onClick: 소스");
                    nc.setsY(startFind[0]);nc.setsX(startFind[1]);
                    nc.setgY(goal10[0]);nc.setgX(goal10[1]);
                    nc.invalidate();
                    break;
                    //map[11][7]
                        //과자/18/goal11
                case R.id.go_snack :
                    Log.d(TAG, "onClick: 과자");
                    nc.setsY(startFind[0]);nc.setsX(startFind[1]);
                    nc.setgY(goal11[0]);nc.setgX(goal11[1]);
                    nc.invalidate();
                    break;
                        //음료/19/goal11
                case R.id.go_drink :
                    Log.d(TAG, "onClick: 음료");
                    nc.setsY(startFind[0]);nc.setsX(startFind[1]);
                    nc.setgY(goal11[0]);nc.setgX(goal11[1]);
                    nc.invalidate();
                    break;
            }
        }
    };
}
