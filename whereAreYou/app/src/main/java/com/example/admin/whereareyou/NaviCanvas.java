package com.example.admin.whereareyou;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.admin.whereareyou.astar.Grid2d;

import java.util.List;

class NaviCanvas extends View {
    int sX, sY, gX, gY;

    public int getsX() {
        return sX;
    }

    public void setsX(int sX) {
        this.sX = sX;
    }

    public int getsY() {
        return sY;
    }

    public void setsY(int sY) {
        this.sY = sY;
    }

    public int getgX() {
        return gX;
    }

    public void setgX(int gX) {
        this.gX = gX;
    }

    public int getgY() {
        return gY;
    }

    public void setgY(int gY) {
        this.gY = gY;
    }

    public NaviCanvas(Context context) {
        super(context);
    }

    public NaviCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NaviCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        String TAG = "tiger";
        //테스트용 paint 정의
        Paint paintLine = new Paint();//블럭나눔
        Paint paintMarker = new Paint();//마커
        Paint paintWall = new Paint();//벽
        Paint paintRoad = new Paint();//길
        //블럭나누는 설정
        paintLine.setStrokeWidth(0);
        paintLine.setColor(Color.BLACK);
        //전체길 설정
        paintMarker.setStrokeWidth(20);
        paintMarker.setColor(Color.BLUE);
        //벽 설정
        paintWall.setStrokeWidth(Color.BLACK);
        paintWall.setStrokeWidth(5);
        //길그리는 설정
        paintRoad.setStrokeWidth(40);
        paintRoad.setStyle(Paint.Style.STROKE);
        paintRoad.setColor(Color.RED);
        paintRoad.setStrokeJoin(Paint.Join.ROUND);
        paintRoad.setStrokeCap(Paint.Cap.ROUND);
        paintRoad.setStrokeMiter(45.0f);
        paintRoad.setAntiAlias(true);
        paintRoad.setStyle(Paint.Style.STROKE);
        //x,y좌표값 기본설정
        int xmin = 0;
        int ymin = 0;
        int xmax = getWidth();
        int ymax = getHeight();
        //시험용 네변 줄끗기
       /* canvas.drawLine(xmin, ymin, xmin, ymax, paintLine);//좌변
        canvas.drawLine(xmax, ymin, xmax, ymax, paintLine);//우변
        canvas.drawLine(xmin, ymin, xmax, ymin, paintLine);//상변
        canvas.drawLine(xmin, ymax, xmax, ymax, paintLine);//하변*/
        //각 구역나누기위한 값
        int xlength = 13;
        int ylength = 15;
        //시험용 줄긋기 상하
       /* for (int i = 0; i < xlength; i++) {
            canvas.drawLine(xmax * i / xlength, ymin, xmax * i / xlength, ymax, paintLine);
        }
        //시험용 줄긋기 좌우
        for (int i = 0; i < ylength; i++) {
            canvas.drawLine(xmin, ymax * i / ylength, xmax, ymax * i / ylength, paintLine);
        }*/
        //Astar사용하기 위한 배열
        double[][] map = {
                ////////0  1  2  3  4  5  6  7  8  9 10 11 12
                /*0*/ { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },//0
                /*1*/ { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },//1
                /*2*/ { 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },//2
                /*3*/ { 3, 0, 3, 3, 3, 3, 3, 3, 3, 0, 3, 3, 3 },//3
                /*4*/ { 3, 0, 3, 3, 3, 3, 3, 3, 3, 0, 3, 3, 3 },//4
                /*5*/ { 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },//5
                /*6*/ { 3, 0, 3, 3, 3, 3, 3, 3, 3, 0, 3, 3, 3 },//6
                /*7*/ { 3, 0, 3, 3, 3, 3, 3, 3, 3, 0, 3, 3, 3 },//7
                /*8*/ { 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },//8
                /*9*/ { 3, 0, 3, 3, 3, 3, 3, 3, 3, 0, 3, 3, 3 },//9
                /*10*/{ 3, 0, 3, 3, 3, 3, 3, 3, 3, 0, 3, 3, 3 },//10
                /*11*/{ 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },//11
                /*12*/{ 3, 0, 3, 3, 0, 3, 3, 3, 3, 0, 3, 3, 3 },//12
                /*13*/{ 3, 0, 3, 3, 0, 3, 3, 3, 3, 0, 3, 3, 3 },//13
                /*14*/{ 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0 }///14
                ////////0  1  2  3  4  5  6  7  8  9 10 11 12
        };
        Grid2d map2d = new Grid2d(map, false);
        //최소거리 뽑는거 logcat으로 확인
        //Log.d(TAG, "onDraw: " + map2d.findPath(1, 2, 10, 11));
        //findPath(출발지x,출발지y, 도착지x,도착지y);
        List<Grid2d.MapNode> ar = map2d.findPath(getsX(), getsY(), getgX(), getgY());
        Log.d(TAG, "onDraw: "+getsX()+" "+getsY()+" "+getgX()+" "+getgY());

        //출발지에서 도착지로가는 최소의 거리를 구하여 맵위에 표시하기 위하여 숫자만 도출함
        //경유하는 길은 3으로 변경시킴

        //0과 3, 1로 나눠서 제어할떄 필요함
        for (int i = 0; i < ar.size(); i++) {
            String s = ar.get(i).toString();
            String[] split = s.split(",");
            split[1] = split[1].trim();
            //째진거확인 System.out.println(split[0] + split[1]);
            map[Integer.parseInt(split[1].substring(0, split[1].length() - 1))][Integer.parseInt(split[0].substring(1, split[0].length()))] = 1;
        }
        /*자바용 변환 확인 출력코드
         *for (int i = 0; i < 15; i++) {
         *   for (int j = 0; j < 13; j++) {
         *       System.out.print((int) map[i][j] + " ");
         *   }
         *   System.out.println();
        }*/
        //배열 사용하여 길인곳과 아닌곳 제어
        /*for (int i = 0; i < ylength; i++) {
            for (int j = 0; j < xlength; j++) {
                int xmarker = xmax * j / xlength + xmax / xlength / 2;
                int ymarker = ymax * i / ylength + ymax / ylength / 2;
                int xwall = xmax * j / xlength;
                int ywall = ymax * i / ylength;
                int xwall1 = xmax * j / xlength + xmax / xlength;
                int ywall1 = ymax * i / ylength + ymax / ylength;
                if (map[i][j] == 0) {
                    //map을 통해서 길인곳 제어
                    canvas.drawPoint(xmarker, ymarker, paintMarker);
                } else if(map[i][j] == 1) {
                    canvas.drawPoint(xmarker, ymarker, paintRoad);
                } else {
                    //아닌 곳 벽으로 제어
                    canvas.drawLine(xwall, ywall, xwall1, ywall1, paintWall);
                }
            }
        }*/
        //x와 y값을 조정하여 각 네모에 중앙 찾아주는 변수
        int xmarker = xmax * getsX() / xlength + xmax / xlength / 2;
        int ymarker = ymax *getsY() / ylength + ymax / ylength / 2;
        //지나온 x좌표 y좌표를 대입 저장
        int oldX=xmarker;
        int oldY=ymarker;
        //Log.d(TAG,  getsY()+ " " + getsX()  +" "+ oldX + ", " + oldY+"");
        //drawLine으로 경로 그려주는 곳
        for (int i = 0; i < ar.size(); i++) {
            //A*알고리즘 결과값을 대입시키기 위해서 subString으로 제어
            String s = ar.get(i).toString();
            String[] split = s.split(",");
            split[1] = split[1].trim();
            int preX = Integer.parseInt(split[0].substring(1, split[0].length()));
            int preY = Integer.parseInt(split[1].substring(0, split[1].length() - 1));
            //Log.d(TAG, "onDraw: " + preX + ", " + preY);
            //A*알고리즘으로 불러온 값의 x, y좌표를 구함
            xmarker = xmax * preX / xlength + xmax / xlength / 2;
            ymarker = ymax * preY / ylength + ymax / ylength / 2;
            //Log.d(TAG,  oldX + ", " + oldY + "onDraw: " + xmarker + ", " + ymarker);
            //각 지점을 구좌표 앞으로 이동한 값으로 x, y값 이어줌
            canvas.drawLine(oldX,oldY,xmarker,ymarker,paintRoad);
            oldX = xmarker;
            oldY = ymarker;
        }
        //--------------------각초당으로 제어해서 초록색 움직이게해보자
    }
}
