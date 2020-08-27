package com.example.proto_4;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class EditAddActivity extends AppCompatActivity {

    static String email,money,child,disabled;
    static Client client = new Client();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_add);
        //  mAuth = FirebaseAuth.getInstance();
        RadioGroup rg_cg = findViewById(R.id.ChildGroup);
        rg_cg.setOnCheckedChangeListener(radioGroupButtonChangeListener_c);
        RadioGroup rg_mg = findViewById(R.id.MoneyGroup);
        rg_mg.setOnCheckedChangeListener(radioGroupButtonChangeListener_m);
        RadioGroup rg_dg = findViewById(R.id.disabledGroup);
        rg_dg.setOnCheckedChangeListener(radioGroupButtonChangeListener_ds);

        findViewById(R.id.finishbutton).setOnClickListener(onClickListener);
        findViewById(R.id.cancelbutton2).setOnClickListener(onClickListener);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String UID = user.getUid();

        List<Client>  a = new ArrayList<>();
        a = MainActivity.db.clientDao().loadClients(UID);

        startToast(a.get(0).toString());
    }
    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.finishbutton:

                    profile_add();
                    startToast("회원정보 수정 완료!");
                    break;
                case R.id.cancelbutton2:
                    startActivity(MainActivity.class);
                    break;

            }

        }
    };
    private void profile_add(){
        FirebaseUser log_user = FirebaseAuth.getInstance().getCurrentUser();
        if(log_user!=null){
            email =  log_user.getEmail();
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String UID = user.getUid();

//        Client client = new Client();
        client.setUID(UID);
        client.setDisabled(disabled);
        client.setChildren(child);
        client.setMoney(money);

        if(child.length()>0 && money.length()>0 && disabled.length()>0){
            MainActivity.db.clientDao().update(client);
            startToast("성공");
            startActivity(MainActivity.class);

////            FirebaseFirestore db = FirebaseFirestore.getInstance();
//            Edit_add_info edit_add_info = new Edit_add_info(disabled, child,money);
////            if(user!=null){
////                db.collection("users").document(email).set(edit_add_info, SetOptions.merge())
////                        .addOnSuccessListener(new OnSuccessListener<Void>() {
////                            @Override
////                            public void onSuccess(Void aVoid) {

//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                startToast("제대로 해라");
//                                //               Log.w(TAG, "Error writing document", e);
//                            }
//                        });
//            }
        }
        else startToast("버튼을 모두 체크 하세요.");
    }

    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener_m = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup rg, @IdRes int i) {
            if(i == R.id.MoneyYesButton){
                money = "Yes";
            }
            else if(i == R.id.MoneyNoButton){
                money = "No";
            }
        }
    };

    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener_ds = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup rg, @IdRes int i) {

            if(i == R.id.disabledYesButton){
                disabled = "Yes";
            }
            else if(i == R.id.disabledNobutton){
                disabled = "No";
            }
        }
    };

    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener_c = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup rg, @IdRes int i) {
            if(i == R.id.JasikYesButton){
                child = "Yes";
            }
            else if(i == R.id.JasikNoButton){
                child = "N";
            }
        }
    };



    private void startActivity(Class c){
        Intent intent = new Intent(this,c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
