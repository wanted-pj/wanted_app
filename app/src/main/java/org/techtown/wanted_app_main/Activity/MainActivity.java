package org.techtown.wanted_app_main.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.techtown.wanted_app_main.Activity.Login.LoginActivity;
import org.techtown.wanted_app_main.Fragment.MainFragment;
import org.techtown.wanted_app_main.R;
import org.techtown.wanted_app_main.database.Personal;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static NavController navController;
    private long backPressedTime = 0;
    private long FINISH_INTERVAL_TIME = 2000;

    public static Personal me;
    public static MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 본인
        mainActivity = this;

        // Activity navController 찾기
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        // Activity 바텀 NavigationView 설정
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        // 회원 받기
        me = getIntent().getParcelableExtra("me");

//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();

//        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, navHostFragment).commit();

//        Bundle bundle = new Bundle();
//        bundle.putParcelable("me", me);
//        MainFragment mainFragment = new MainFragment();

//        mainFragment.setArguments(bundle);
    }

    // <----바텀 네비게이션--->
    // 바텀네비게이션 누르면 fragment 이동
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                navController.navigate(R.id.action_global_mainFragment);
                break;
            case R.id.menu_community:
                navController.navigate(R.id.action_global_postingListFragment);
                break;
            case R.id.menu_chat:
                navController.navigate(R.id.action_global_chatListFragment);
                break;
            case R.id.menu_profile:
                navController.navigate(R.id.action_global_profileFragment);
                break;
            default:
                break;
        }
        return true;
    }

    // 뒤로가기 버튼 눌렀을 때, 홈화면 일때와 다른 화면 일때의 구현
    @Override
    public void onBackPressed() {
        if (!navController.popBackStack()) { // currentBackStackEntry가 비어있으면 false 리턴. 따라서, 비어있는 경우
            long currentTime = System.currentTimeMillis();
            long intervalTime = currentTime - backPressedTime;
            if (!(0 > intervalTime || FINISH_INTERVAL_TIME < intervalTime)) {
                finishAffinity();
                System.runFinalization();
                System.exit(0);
            } else {
                backPressedTime = currentTime;
                Toast.makeText(this, "'뒤로' 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        // 홈화면으로 온 경우
        NavDestination currentDestination = navController.getCurrentDestination();
        NavDestination tag1 = navController.getGraph().findNode(R.id.mainFragment);
        if (currentDestination == tag1) {
            BottomNavigationView bnv = findViewById(R.id.bottomNavigation);
            updateBottomMenu(bnv);
        }
    }

    private void updateBottomMenu(BottomNavigationView navigation) {
        navigation.getMenu().findItem(R.id.menu_home).setChecked(true);
    }
    // </----바텀 네비게이션--->
}