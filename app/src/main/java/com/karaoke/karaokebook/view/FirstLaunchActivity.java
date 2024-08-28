package com.karaoke.karaokebook.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.karaoke.karaokebook.R;
import com.karaoke.karaokebook.data.local.AppDatabase;
import com.karaoke.karaokebook.data.model.User;
import com.karaoke.karaokebook.data.remote.LibraryAPI;
import com.karaoke.karaokebook.data.remote.NetworkClient;
import com.karaoke.karaokebook.databinding.ActivityFirstLaunchBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FirstLaunchActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    String sharedPrefKey;

    Retrofit retrofit;
    LibraryAPI api;
    private ActivityFirstLaunchBinding binding;
    ActivityResultLauncher<Intent> registerForActivityResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestPermission();

        sharedPref = this.getSharedPreferences(getString(R.string.shared_pref), Context.MODE_PRIVATE);
        //if (sharedPref.contains(sharedPrefKey)) {
        AppDatabase.getInstance(getApplicationContext());
        goToMainActivity();
        //}

        binding = ActivityFirstLaunchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        retrofit = NetworkClient.getClient(getApplicationContext());
        api = retrofit.create(LibraryAPI.class);

        binding.useWithoutLogin.setOnClickListener(view -> new Thread(this::assignUser).start());
        binding.googleLoginBtn.setOnClickListener(view -> {

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);
            Intent signInIntent = googleSignInClient.getSignInIntent();

            registerForActivityResult.launch(signInIntent);

        });

        GoogleSignInAccount gsa = GoogleSignIn.getLastSignedInAccount(FirstLaunchActivity.this);
        if (gsa != null) {

        }

        registerForActivityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                new Thread(() -> {

                    String email = getLoginEmail(result);

                    Call<User> call = api.findAssignedEmail(email);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            User user = response.body();
                            int id = user.getId();
                            if (id == -1) {

                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable throwable) {

                        }
                    });
                }).start();


            }
        });
    }

    private String getLoginEmail(ActivityResult result) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);

            return account.getEmail();
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
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

    private void assignUser() {
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

    private void addAccount() {

    }
}
