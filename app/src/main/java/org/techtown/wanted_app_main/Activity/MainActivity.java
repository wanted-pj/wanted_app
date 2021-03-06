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
    public static BottomNavigationView bottomNavigationView;

    Bundle bundle = new Bundle();

    public static int btnNavIndex; // 0홈 1커뮤니티 2채팅 3프로필

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 회원 받기
        me = getIntent().getParcelableExtra("me");
        setContentView(R.layout.activity_main);

        // 본인
        mainActivity = this;

        // Activity navController 찾기
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        // Activity 바텀 NavigationView 설정
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        setBtnNavIndex(0);


        // MainFragment 에 me 뿌리기
//        MainFragment mainFragment = new MainFragment();
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("me", me);
//        mainFragment.setArguments(bundle);
//        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, mainFragment).commitAllowingStateLoss();
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

    public static void setBtnNavIndex(int btnNavIndex) {
        MainActivity.btnNavIndex = btnNavIndex;
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
        NavDestination main = navController.getGraph().findNode(R.id.mainFragment);
        NavDestination friendMore = navController.getGraph().findNode(R.id.friendMoreFragment);
        NavDestination community = navController.getGraph().findNode(R.id.postingListFragment);

        if (currentDestination == main || currentDestination == friendMore) {
            setBtnNavIndex(0);
            updateBottomMenu();
        } else if (currentDestination == community) {
            setBtnNavIndex(1);
            updateBottomMenu();
        }
    }

    public static void updateBottomMenu() {
        // 0홈 1커뮤니티 2채팅 3프로필
        switch (btnNavIndex) {
            case 0:
                bottomNavigationView.getMenu().findItem(R.id.menu_home).setChecked(true);
                break;
            case 1:
                bottomNavigationView.getMenu().findItem(R.id.menu_community).setChecked(true);
                break;
            case 2:
                bottomNavigationView.getMenu().findItem(R.id.menu_chat).setChecked(true);
                break;
            case 3:
                bottomNavigationView.getMenu().findItem(R.id.menu_profile).setChecked(true);
                break;
        }
    }
    // </----바텀 네비게이션--->
}