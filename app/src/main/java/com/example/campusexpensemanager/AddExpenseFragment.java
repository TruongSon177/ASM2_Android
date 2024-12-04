package com.example.campusexpensemanager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.widget.Button;

public class AddExpenseFragment extends Fragment {

    private EditText editTextDescription, editTextAmount, editTextCategory;
    private Button buttonAddExpense;
    private SQLiteHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_expense, container, false);

        // Khởi tạo SQLiteHelper để thao tác với cơ sở dữ liệu
        dbHelper = new SQLiteHelper(getContext());

        // Tìm các EditText và Button trong layout
        editTextDescription = rootView.findViewById(R.id.editTextDescription);
        editTextAmount = rootView.findViewById(R.id.editTextAmount);
        editTextCategory = rootView.findViewById(R.id.editTextCategory);
        buttonAddExpense = rootView.findViewById(R.id.buttonAddExpense);


        buttonAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin chi tiêu từ các EditText
                String description = editTextDescription.getText().toString().trim();
                String amountStr = editTextAmount.getText().toString().trim();
                String category = editTextCategory.getText().toString().trim();

                // Kiểm tra xem các trường có hợp lệ không
                if (TextUtils.isEmpty(description) || TextUtils.isEmpty(amountStr) || TextUtils.isEmpty(category)) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin chi tiêu!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Chuyển đổi amountStr thành double
                double amount = Double.parseDouble(amountStr);

                // Thêm chi tiêu vào cơ sở dữ liệu
                dbHelper.addExpense(description, amount, category);

                // Lấy tổng chi tiêu mới từ cơ sở dữ liệu
                double newTotalExpense = dbHelper.getTotalExpense();

                // Gọi phương thức updateTotalExpense trong MainActivity để cập nhật tổng chi tiêu mới
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.updateTotalExpense(newTotalExpense);  // Cập nhật tổng chi tiêu mới
                }

                // Thông báo và đóng fragment hiện tại
                Toast.makeText(getContext(), "Chi tiêu đã được thêm!", Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStack(); // Quay lại fragment trước đó
            }
        });

        return rootView;
    }
}
