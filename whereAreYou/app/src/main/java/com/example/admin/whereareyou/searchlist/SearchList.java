package com.example.admin.whereareyou.searchlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.whereareyou.R;

import java.util.ArrayList;
import java.util.List;

public class SearchList extends AppCompatActivity {
    String TAG = "tiger";

    final private String fruitApple = "사과";
    final private String fruitBanana = "바나나";
    final private String nutWal= "호두";
    final private String nutPea= "볶음땅콩";
    final private String veBean= "콩나물";
    final private String vePotato= "감자";
    final private String coffeeKanu = "카누";
    final private String coffeeMaxim = "맥심";
    final private String riceHo = "찰호떡믹스";
    final private String riceSpread = "빵가루";
    final private String potKnife= "주방용칼";
    final private String potDoma= "도마";
    final private String noodleAn= "안성탕면";
    final private String noodleNuke= "핵붉닭볶음면";
    final private String iceSang= "쌍쌍바";
    final private String iceWorld= "월드콘";
    final private String fishGo= "고등어";
    final private String fishGal= "갈치";
    final private String cowFlower= "꽃살(우육)";
    final private String cowHang= "항정살(돈육)";
    final private String paperPaper= "두루마리휴지";
    final private String paperTi= "곽티슈";
    final private String towTow= "수건";
    final private String towBody= "바디샤워";
    final private String milkMilk= "우유";
    final private String milkYogu= "요구르트";
    final private String snowMan= "냉동만두";
    final private String snowRice= "냉동떡갈비";
    final private String breadBa= "바게트빵";
    final private String breadPi= "피자빵";
    final private String honeyGo= "고추장";
    final private String honeyOil= "참기름";
    final private String honeySalt="소금";
    final private String honeySugar="설탕";
    final private String alcoBeer= "맥주";
    final private String alcoSoju= "소주";
    final private String snackSnack= "과자";
    final private String snackChoco= "초코렛";
    final private String drinkCi= "콜라";
    final private String drinkCoke= "사이다";

    private List<String> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<String> arraylist;
    //보낼 좌표값
    int goalx;
    int goaly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchlist);

        editSearch = findViewById(R.id.editsearch);
        listView = findViewById(R.id.listviewll);

        // 리스트를 생성한다.
        list = new ArrayList<>();

        // 검색에 사용할 데이터을 미리 저장한다.
        settingList();

        // 리스트의 모든 데이터를 arraylist에 복사한다.// list 복사본을 만든다.
        arraylist = new ArrayList<>();
        arraylist.addAll(list);

        // 리스트에 연동될 아답터를 생성한다.
        adapter = new SearchAdapter(list, this);

        // 리스트뷰에 아답터를 연결한다.
        listView.setAdapter(adapter);

        //클릭해서 값제어
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView label = view.findViewById(R.id.label);
                //Log.d(TAG, "onItemClick: "+label.getText());
                if (fruitApple.equals(label.getText().toString()) ||
                        fruitBanana.equals(label.getText().toString()) ||
                        nutPea.equals(label.getText().toString()) ||
                        nutWal.equals(label.getText().toString())) {
                    goalx = 3;
                    goaly = 2;
                } else if (veBean.equals(label.getText().toString()) ||
                        vePotato.equals(label.getText().toString())) {
                    goalx = 9;
                    goaly = 2;
                }  else if (coffeeKanu.equals(label.getText().toString()) ||
                        coffeeMaxim.equals(label.getText().toString())) {
                    goalx = 6;
                    goaly = 2;
                }  else if (riceSpread.equals(label.getText().toString()) ||
                        riceHo.equals(label.getText().toString()) ||
                        potDoma.equals(label.getText().toString()) ||
                        potKnife.equals(label.getText().toString())) {
                    goalx = 3;
                    goaly = 5;
                }  else if (noodleAn.equals(label.getText().toString()) ||
                        noodleNuke.equals(label.getText().toString()) ||
                        iceSang.equals(label.getText().toString()) ||
                        iceWorld.equals(label.getText().toString())) {
                    goalx = 7;
                    goaly = 5;
                }  else if (fishGal.equals(label.getText().toString()) ||
                        fishGo.equals(label.getText().toString()) ||
                        cowFlower.equals(label.getText().toString()) ||
                        cowHang.equals(label.getText().toString())) {
                    goalx = 11;
                    goaly = 5;
                } else if (paperPaper.equals(label.getText().toString()) ||
                        paperTi.equals(label.getText().toString()) ||
                        towBody.equals(label.getText().toString()) ||
                        towTow.equals(label.getText().toString())) {
                    goalx = 3;
                    goaly = 8;
                }  else if (milkMilk.equals(label.getText().toString()) ||
                        milkYogu.equals(label.getText().toString()) ||
                        snowMan.equals(label.getText().toString()) ||
                        snowRice.equals(label.getText().toString())) {
                    goalx = 7;
                    goaly = 8;
                } else if (alcoBeer.equals(label.getText().toString()) ||
                        alcoSoju.equals(label.getText().toString()) ||
                        breadPi.equals(label.getText().toString()) ||
                        breadBa.equals(label.getText().toString())) {
                    goalx = 11;
                    goaly = 11;
                } else if (honeyGo.equals(label.getText().toString()) ||
                        honeyOil.equals(label.getText().toString())||
                        honeySalt.equals(label.getText().toString())||
                        honeySugar.equals(label.getText().toString())) {
                    goalx = 4;
                    goaly = 11;
                } else if (snackSnack.equals(label.getText().toString()) ||
                        snackChoco.equals(label.getText().toString()) ||
                        drinkCoke.equals(label.getText().toString()) ||
                        drinkCi.equals(label.getText().toString())) {
                    goalx = 7;
                    goaly = 11;
                }
                int[] goalPoint = {goalx, goaly};
                Intent intent = new Intent();
                intent.putExtra("sv", label.getText().toString());
                intent.putExtra("gp", goalPoint);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        // input창에 검색어를 입력시 "addTextChangedListener" 이벤트 리스너를 정의한다.
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = editSearch.getText().toString();
                search(text);
            }
        });


    }

    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            list.addAll(arraylist);
        }
        // 문자 입력을 할때..
        else {
            // 리스트의 모든 데이터를 검색한다.
            for (int i = 0; i < arraylist.size(); i++) {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylist.get(i).toLowerCase().contains(charText)) {
                    // 검색된 데이터를 리스트에 추가한다.
                    list.add(arraylist.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }

    // 검색에 사용될 데이터를 리스트에 추가한다.
    private void settingList() {
        //주류 1
        list.add(alcoBeer);//101
        list.add(alcoSoju);//102
        //빵집 2
        list.add(breadPi);//202
        list.add(breadBa);//201
        //유제품 3
        list.add(milkMilk);//301
        list.add(milkYogu);//302
        //수산 4
        list.add(fishGal);//401
        list.add(fishGo);//402
        //과일 5
        list.add(fruitBanana);//501
        list.add(fruitApple);//502
        //축산 6
        list.add(cowFlower);//601
        list.add(cowHang);//602
        //과자 7
        list.add(snackSnack);//702
        list.add(snackChoco);//701
        //소스 8
        list.add(honeySalt);//801
        list.add(honeySugar);//802
        //소스 13//항목잘못입력 그대로 소스로 13번이 존재하는것으로
        list.add(honeyOil);//1301
        list.add(honeyGo);//1302
        //주방도구 9
        list.add(potDoma);//901
        list.add(potKnife);//902
        //야채 10
        list.add(veBean);//1001
        list.add(vePotato);//1002
        //커피 11
        list.add(coffeeKanu);//1101
        list.add(coffeeMaxim);//1102
        //음료 12
        list.add(drinkCoke);//1201
        list.add(drinkCi);//1202
        //빙과류 14
        list.add(iceSang);//1401
        list.add(iceWorld);//1402
        //라면 15
        list.add(noodleAn);//1501
        list.add(noodleNuke);//1502
        //견과류 16
        list.add(nutPea);//1601
        list.add(nutWal);//1602
        //생활용품 17
        list.add(paperPaper);//1701
        list.add(paperTi);//1702
        //베이킹 18
        list.add(riceHo);//1801
        list.add(riceSpread);//1802
        //냉동가공 19
        list.add(snowMan);//1901
        list.add(snowRice);//1902
        //욕실용품 20
        list.add(towBody);//2001
        list.add(towTow);//2002
        //실험용 1만개 넣어도 이상없음
        /*for (int i =0;i<10000;i++) {
            String ab = "" + i;
            list.add(ab);
        }*/
    }
}