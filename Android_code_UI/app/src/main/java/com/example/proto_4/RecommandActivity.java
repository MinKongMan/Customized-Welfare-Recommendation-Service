package com.example.proto_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

public class RecommandActivity extends AppCompatActivity {

    private TextView txt_example1;

    private ArrayList<String> mNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommand);

        int size = 130;
        txt_example1 = (TextView) findViewById(R.id.textView1);
        Spannable span = (Spannable) txt_example1.getText();
        span.setSpan(new AbsoluteSizeSpan(size), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(Color.BLUE), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        init();

    }

    private void init() {
        mNames.add("가정양육수당 지원");

        mNames.add("경계선지능아동 자립지원");

        mNames.add("고용보험 미적용자 출산급여 지원");

        mNames.add("고위험 임산부 의료비 지원");

        mNames.add("공공주택 공급(공공분양 및 공공임대)");

        mNames.add("공동육아나눔터 운영");

        mNames.add("국민연금 출산크레딧");

        mNames.add("국민임대주택공급");

        mNames.add("근로·자녀장려금");

        mNames.add("기존주택 매입임대주택 지원사업");

        initRecyclerView();
    }

    private void initRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.recomendrecycler);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .drawable(R.drawable.blue)
                .size(15)
                .build());
        DetailRecyclerViewAdapter adapter = new DetailRecyclerViewAdapter(this, mNames);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}