package com.example.a10sec.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.a10sec.R;
import com.example.a10sec.databinding.FragmRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends BaseFragment {

    protected FirebaseAuth mAuth;
    View view;
    private FragmRegisterBinding registerBinding;
    FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        registerBinding = DataBindingUtil.inflate(inflater, R.layout.fragm_register,container,false);
        view = registerBinding.getRoot();
        mAuth = FirebaseAuth.getInstance();
        click();
        return view;
    }

    void click(){
        registerBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.createUserWithEmailAndPassword(registerBinding.edtTxtEmail.getText().toString().trim(), registerBinding.edtTxtPassword.getText().toString().trim())
                        .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d("Register", "createUserWithEmail:success");
                                    user = mAuth.getCurrentUser();
                                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new HomePageFragment()).commitAllowingStateLoss();
                                } else {
                                    Log.w("Register", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(activity, "Authentication failed.",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });
    }

}
