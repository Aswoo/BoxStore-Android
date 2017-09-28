package com.b05studio.boxstore.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.b05studio.boxstore.MainActivity;
import com.b05studio.boxstore.R;
import com.b05studio.boxstore.view.fragment.categoryFragment;
import com.b05studio.boxstore.view.fragment.homeFragment;
import com.b05studio.boxstore.view.fragment.mypageFragment;
import com.b05studio.boxstore.view.fragment.notificationFragment;
import com.b05studio.boxstore.view.fragment.sellFragment;

import java.lang.reflect.Field;

/**
 * Created by joyeongje on 2017. 9. 28..
 */

public class BoxstoreMenuActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

      BottomNavigationViewHelper bottomnavigationViewHelper = new BottomNavigationViewHelper();
        bottomnavigationViewHelper.removeShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                selectedFragment = homeFragment.newInstance();
                                break;
                            case R.id.navigation_category:
                                selectedFragment = categoryFragment.newInstance();
                                break;
                            case R.id.navigation_sell:
                                selectedFragment = sellFragment.newInstance();
                                break;
                            case R.id.navigation_notifications:
                                selectedFragment = notificationFragment.newInstance();
                                break;
                            case R.id.navigation_mypage:
                                selectedFragment = mypageFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, homeFragment.newInstance());
        transaction.commit();



        //Used to select an item programmatically

    }
    class BottomNavigationViewHelper {
        public void removeShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
            } catch (IllegalAccessException e) {
                Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
            }
        }
    }
}
