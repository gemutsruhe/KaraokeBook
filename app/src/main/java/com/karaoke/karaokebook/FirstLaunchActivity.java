package com.karaoke.karaokebook;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.karaoke.karaokebook.databinding.ActivityFirstLaunchBinding;
import com.karaoke.karaokebook.databinding.ActivityMainBinding;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FirstLaunchActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    String sharedPrefKey;
    private ActivityFirstLaunchBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestPermission();

        sharedPref = this.getSharedPreferences(getString(R.string.shared_pref), Context.MODE_PRIVATE);
        if (sharedPref.contains(sharedPrefKey)) {
            goToMainActivity();
        }

        binding = ActivityFirstLaunchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.useWithoutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        assignUserId();
                    }
                }).start();
            }
        });
    }

    void requestPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Log.e("TEST", "Granted");
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Log.e("TEST", "Denied");
            }
        };

        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.INTERNET)
                .check();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void assignUserId() {
        Retrofit retrofit = NetworkClient.getClient(getApplicationContext());
        LibraryAPI api = retrofit.create(LibraryAPI.class);
        //User user = User.getInstance();
        User user = new User();

        Call<User> call = api.assignUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                User userData = response.body();

                if (userData != null) {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt(sharedPrefKey, userData.getId());
                    editor.apply();
                    goToMainActivity();
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable throwable) {
                Log.e("TEST", "failure");
            }
        });
    }


}
