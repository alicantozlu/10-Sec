package com.example.a10sec.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.a10sec.MainActivity;
import com.example.a10sec.R;
import com.example.a10sec.databinding.FragmRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mindorks.paracamera.Camera;

public class RegisterFragment extends BaseFragment {

    View view;
    private FragmRegisterBinding registerBinding;
    private StorageReference mStorageRef;
    private int MY_CAMERA_PERMISSION_CODE=100;
    Camera camera;
    private String selectedImagePath;
    private Uri selectedImageUri;
    //FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        registerBinding = DataBindingUtil.inflate(inflater, R.layout.fragm_register,container,false);
        view = registerBinding.getRoot();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        click();
        return view;
    }

    void click(){
        registerBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (registerBinding.edtTxtPassword.getText().toString().trim().equals(registerBinding.edtTxtRepassword.getText().toString().trim())){
                    registerProcess();
                }else{
                    Toast.makeText(activity,"Girilen şifreler aynı değil.",Toast.LENGTH_LONG).show();
                }
            }
        });
        registerBinding.imgUserimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Camera",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                openCamera();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Gallery",
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openGallery();
                    }
                });
                alertDialog.show();
            }
        });
    }

    //kullanıcının email ve passworduyle firebase auth kısmına kaydının yapıldığı yer
    private void registerProcess(){
        MainActivity.mAuth.createUserWithEmailAndPassword(registerBinding.edtTxtEmail.getText().toString().trim(), registerBinding.edtTxtPassword.getText().toString().trim())
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Register", "createUserWithEmail:success");
                            //user = mAuth.getCurrentUser();
                            MainActivity.myPreferences.setLoggedIn(true);
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new HomePageFragment()).commitAllowingStateLoss();
                        } else {
                            Log.w("Register", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(activity, "Authentication failed.",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    //camerayı açtığımız kısım
    private void openCamera(){
        Toast.makeText(activity,"openCamera",Toast.LENGTH_SHORT).show();
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            takePhoto();
        }else{
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                Log.i("Camera","Camera permission is need to show the camera preview");
            }
            requestPermissions(new String[]{Manifest.permission.CAMERA},MY_CAMERA_PERMISSION_CODE);
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                takePhoto();
            }
        }
    }

    // galeryi açtığımız kısım
    private  void openGallery(){
        Toast.makeText(activity,"openGallery",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 237);
    }

    //camerayla çekilen fotoyu aldığımız kısım
    public void takePhoto(){
        camera = new Camera.Builder()
                .resetToCorrectOrientation(true)// it will rotate the camera bitmap to the correct orientation from meta data
                .setTakePhotoRequestCode(1)
                .setDirectory("pics")
                .setName("ali_" + System.currentTimeMillis())
                .setImageFormat(Camera.IMAGE_JPEG)
                .setCompression(75)
                .setImageHeight(1000)// it will try to achieve this height as close as possible maintaining the aspect ratio;
                .build(this);
        try {
            camera.takePicture();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // kameradan veya galreiden dönen değere göre işlem yapar
    // request code camreraysa cameradaki resmin bitmapini alır ve glide(resim basma kütüphanesi) ile img i ekrana basar
    // request code 237 gelirse(237 openGalery() kısmında belirlediğimiz request code) gelen datanın urisini (url gibi bişey) stringe çevirip glide ile resmi basar
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Camera.REQUEST_TAKE_PHOTO) {
            Bitmap bitmap = camera.getCameraBitmap();
            if (bitmap != null) {
                Glide.with(this).load(bitmap).into(registerBinding.imgUserimg);

            } else {
                Toast.makeText(activity, "Picture not taken!", Toast.LENGTH_SHORT).show();
            }
        }
        if (resultCode == activity.RESULT_OK) {
            if (requestCode == 237) {
                selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                Glide.with(this)
                        .load(selectedImageUri)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .optionalCenterCrop()
                        .into(registerBinding.imgUserimg);
            }
        }
    }

    //Uriyi alıp stringe çevirir
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    //resmin firebase storageye kaydedilen kısmı
    private void uploadImg(){

    }

}
