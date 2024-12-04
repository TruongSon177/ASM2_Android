package com.example.campusexpensemanager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private SQLiteHelper dbHelper;
    private double totalExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new SQLiteHelper(this);
        totalExpense = dbHelper.getTotalExpense();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

        if (savedInstanceState == null) {
            openFragment(HomeFragment.newInstance(totalExpense), HomeFragment.class.getSimpleName());
        }
    }

    private boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment;
        String tag;

        int itemId = item.getItemId(); // Lấy ID của menu được chọn
        if (itemId == R.id.nav_home) {
            selectedFragment = HomeFragment.newInstance(totalExpense);
            tag = HomeFragment.class.getSimpleName();
        } else if (itemId == R.id.nav_add) {
            selectedFragment = new AddExpenseFragment();
            tag = AddExpenseFragment.class.getSimpleName();
        } else if (itemId == R.id.nav_calendar) {
            selectedFragment = new CalendarFragment();
            tag = CalendarFragment.class.getSimpleName();
        } else if (itemId == R.id.nav_profile) {
            selectedFragment = new ProfileFragment();
            tag = ProfileFragment.class.getSimpleName();
        } else {
            Toast.makeText(this, "Unknown navigation item", Toast.LENGTH_SHORT).show();
            return false;
        }

        openFragment(selectedFragment, tag);
        return true;
    }

    private void openFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, tag)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        totalExpense = dbHelper.getTotalExpense();

        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager()
                .findFragmentByTag(HomeFragment.class.getSimpleName());
        if (homeFragment != null) {
            homeFragment.updateTotalExpense(totalExpense);
        }
    }

    public void updateTotalExpense(double newTotalExpense) {
        totalExpense = newTotalExpense;
        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager()
                .findFragmentByTag(HomeFragment.class.getSimpleName());
        if (homeFragment != null) {
            homeFragment.updateTotalExpense(newTotalExpense);
        }
    }
}
