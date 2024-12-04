package com.example.campusexpensemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "user_data";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static final String PHONE_KEY = "phone";  // Thêm key cho số điện thoại

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng điền tên đăng nhập và mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    // Kiểm tra đăng nhập với thông tin đã lưu trong SharedPreferences
                    String storedUsername = sharedPreferences.getString(USERNAME_KEY, "");
                    String storedPassword = sharedPreferences.getString(PASSWORD_KEY, "");
                    String storedPhone = sharedPreferences.getString(PHONE_KEY, "");  // Lấy số điện thoại đã lưu

                    if (username.equals(storedUsername) && password.equals(storedPassword)) {
                        // Hiển thị thông tin người dùng sau khi đăng nhập thành công
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        // Có thể sử dụng thông tin số điện thoại ở đây nếu cần thiết
                        Log.d("User Phone", "Phone: " + storedPhone);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
