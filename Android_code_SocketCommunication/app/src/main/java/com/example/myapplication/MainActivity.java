package com.example.myapplication;

import android.content.Intent;
import android.os.Handler;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button connect_btn;                 // ip 받아오는 버튼

    EditText ip_edit;               // ip 에디트
    TextView show_text;             // 서버에서온거 보여주는 에디트
    // 소켓통신에 필요한것
    private String html = "";
    private Handler mHandler;

    private Socket socket;

    private DataOutputStream dos;
    private DataInputStream dis;

    private String ip = "172.30.1.20";            // 내 IP 번호
    private int port = 9999;                          // port 번호

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connect_btn = (Button)findViewById(R.id.connect_btn);
        connect_btn.setOnClickListener(this);

        ip_edit = (EditText)findViewById(R.id.ip_edit);
        show_text = (TextView)findViewById(R.id.show_text);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.connect_btn:     // ip 받아오는 버튼
                connect();
        }
    }

    // 로그인 정보 db에 넣어주고 연결시켜야 함.
    void connect(){
        mHandler = new Handler();
        Log.w("connect","연결 하는중");
        // 받아오는거
        Thread checkUpdate = new Thread() {
            public void run() {
                // ip받기
                //String newip = String.valueOf(ip_edit.getText()); 여기는 text부분으로 ip주소 입력하는 곳
                String newip = "172.30.1.50"; //여기에 파이썬 ip주소를 적으면 입력안해도 자동으로 소켓통신

                // 서버 접속
                try {
                    socket = new Socket(newip, port);
                    Log.w("서버 접속됨", "서버 접속됨");
                } catch (IOException e1) {
                    Log.w("서버접속못함", "서버접속못함");
                    e1.printStackTrace();
                }

                Log.w("edit 넘어가야 할 값 :  ","안드로이드에서 서버로 연결요청" );

                try {
                    dos = new DataOutputStream(socket.getOutputStream());   // output에 보낼꺼 넣음
                    dis = new DataInputStream(socket.getInputStream());     // input에 받을꺼 넣어짐
                    dos.writeUTF("<1932,female,Yes,Yes,Yes>"); // 안드로이드에서 보낼 때임 이게!!!!!

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("버퍼", "버퍼생성 잘못됨");
                }
                Log.w("버퍼","버퍼생성 잘됨");

                //System.out.println("try 전단계");
                // 서버에서 계속 받아옴 - 한번은 문자, 한번은 숫자를 읽음. 순서 맞춰줘야 함.
                try {
                    String line = "";
                    int line2;
                    while(true) {
                        ArrayList<Integer> values = new ArrayList<Integer>();
                        for(int i=0;i<20;i++) {

                            //System.out.println("읽기 전에 나오는 문구");
                            line = (String) dis.readUTF();
                            //System.out.println("읽기 성공하면 나오는 문구");
                            line2 = (int) dis.read(); // 이게 안드로이드에서 받을 때임!!!!!
                            //Integer.toString(line2); // 숫자를 문자로?
                            //System.out.println("읽기 성공하면 나오는 문구2");
                            if(line2 != 0) {
                                values.add(line2);
                            }
                        }
                            System.out.println(values);

                        /*if(line2 > 0) {
                            Log.w("------서버에서 받아온 값 ", "" + line2);
                            dos.writeUTF("하나 받았습니다. : " + line2);
                            dos.flush();
                        }
                        if(line2 == 1000) {
                            Log.w("------서버에서 받아온 값 ", "" + line2);
                            socket.close();
                            break;
                        }*/

//                        Log.w("서버에서 받아온 값 ",""+line);
//                        System.out.println(line);
//                        System.out.println(line2);
//                        Log.w("서버에서 받아온 값 ",""+line2);
                    }
                }catch (Exception e){

                }
            }
        };
        // 소켓 접속 시도, 버퍼생성
        checkUpdate.start();

    }
}