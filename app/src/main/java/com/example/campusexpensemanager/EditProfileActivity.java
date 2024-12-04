package com.example.campusexpensemanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private EditText editName, editEmail, editPhone;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editName = findViewById(R.id.edit_name);
        editEmail = findViewById(R.id.edit_email);
        editPhone = findViewById(R.id.edit_phone);
        saveButton = findViewById(R.id.button_save);

        // Lấy thông tin người dùng từ SharedPreferences và hiển thị lên các EditText
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        editName.setText(sharedPreferences.getString("username", ""));
        editEmail.setText(sharedPreferences.getString("email", ""));
        editPhone.setText(sharedPreferences.getString("phone", ""));

        // Lưu thông tin khi bấm nút Save
        saveButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", editName.getText().toString());
            editor.putString("email", editEmail.getText().toString());
            editor.putString("phone", editPhone.getText().toString());
            editor.apply();

            // Gửi kết quả trở lại ProfileFragment
            Intent resultIntent = new Intent();
            resultIntent.putExtra("updated", true);  // Thêm thông tin cập nhật
            setResult(RESULT_OK, resultIntent);
            finish();  // Đóng màn hình chỉnh sửa
        });
    }
}
