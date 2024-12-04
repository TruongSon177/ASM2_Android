package com.example.campusexpensemanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private TextView nameTextView, emailTextView, phoneTextView;
    private ImageView profileImageView;
    private Button editProfileButton, logOutButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Ánh xạ các View
        nameTextView = view.findViewById(R.id.profile_name);
        emailTextView = view.findViewById(R.id.profile_email);
        phoneTextView = view.findViewById(R.id.profile_phone);
        profileImageView = view.findViewById(R.id.profile_image);
        editProfileButton = view.findViewById(R.id.button_edit_profile);
        logOutButton = view.findViewById(R.id.button_log_out);

        // Lấy thông tin từ SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("username", "Unknown User");
        String userEmail = sharedPreferences.getString("email", "example@example.com");
        String userPhone = sharedPreferences.getString("phone", "No Phone Number");

        // Hiển thị thông tin lên giao diện
        nameTextView.setText("Username: " + userName);
        emailTextView.setText("Email: " + userEmail);
        phoneTextView.setText("Phone: " + userPhone);
        profileImageView.setImageResource(R.drawable.default_profile_image);

        // Xử lý sự kiện khi bấm nút "Edit Profile"
        editProfileButton.setOnClickListener(v -> {
            // Mở màn hình chỉnh sửa profile và nhận kết quả trả về
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            startActivityForResult(intent, 1);  // Yêu cầu nhận kết quả từ EditProfileActivity
        });

        // Xử lý sự kiện khi bấm nút "Log Out"
        logOutButton.setOnClickListener(v -> {
            // Chỉ điều hướng đến màn hình đăng nhập mà không xóa dữ liệu
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish(); // Đóng màn hình hiện tại
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Kiểm tra nếu có kết quả trả về từ EditProfileActivity
        if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {
            // Kiểm tra nếu có thông tin mới được cập nhật
            boolean updated = data.getBooleanExtra("updated", false);
            if (updated) {
                // Cập nhật lại giao diện
                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
                String userName = sharedPreferences.getString("username", "Unknown User");
                String userEmail = sharedPreferences.getString("email", "example@example.com");
                String userPhone = sharedPreferences.getString("phone", "No Phone Number");

                nameTextView.setText("Username: " + userName);
                emailTextView.setText("Email: " + userEmail);
                phoneTextView.setText("Phone: " + userPhone);
            }
        }
    }
}
