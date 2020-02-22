package com.rct.rctpolaznik;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.rct.rctpolaznik.data.User;
import com.rct.rctpolaznik.fragments.MainMenu;
import com.rct.rctpolaznik.interfaces.OnFragmentInteractionListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.view.View.VISIBLE;
import static com.rct.rctpolaznik.Global.ACTION_CHANGE_FRAGMENT;
import static com.rct.rctpolaznik.Global.ACTION_DISPLAY_MESSAGE;
import static com.rct.rctpolaznik.Global.ARG_MESSAGE;
import static com.rct.rctpolaznik.Global.ARG_POP;
import static com.rct.rctpolaznik.Global.ARG_TAG_FRAGMENT;
import static com.rct.rctpolaznik.Global.TAG_FRAGMENT_LOGIN;
import static com.rct.rctpolaznik.Global.TAG_FRAGMENT_MAIN_MENU;
import static com.rct.rctpolaznik.Global.TAG_FRAGMENT_NOT_LOGGED_IN;
import static com.rct.rctpolaznik.Global.displayMessage;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    private final Handler handler = new Handler();
    private AppCompatTextView actvMessage;
    private final Runnable hideMessage = new Runnable() {
        @Override
        public void run() {
            if(actvMessage != null) actvMessage.setVisibility(View.GONE);
        }
    };
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction() == null) return;

            switch (intent.getAction()) {
                case ACTION_CHANGE_FRAGMENT:
                    String tagFragment = intent.getStringExtra(ARG_TAG_FRAGMENT);
                    /*switch (tagFragment) {
                        case TAG_FRAGMENT_COMPANY_INDEX:
                            setTopbarVisibility(VISIBLE); // komentar
                            break;
                        case TAG_FRAGMENT_VOUCHER_ACTIVATION:
                            Fragment fStory= getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_STORY);
                            if(fStory != null) ((Story)fStory).setScrolling(false);
                        case TAG_FRAGMENT_ADMIN_MENU:
                            setTopbarVisibility(View.GONE);
                            break;
                    }
                    boolean pop = intent.getBooleanExtra(ARG_POP, true);
                    changeFragment(MainActivity.this, tagFragment, pop, intent.getExtras());*/
                    break;
                case ACTION_DISPLAY_MESSAGE:
                    actvMessage.setText(intent.getStringExtra(ARG_MESSAGE));
                    actvMessage.setVisibility(VISIBLE);
                    handler.removeCallbacks(hideMessage);
                    handler.postDelayed(hideMessage, 5000);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initOnResume();

        if(savedInstanceState == null) MainMenu.add(getBaseContext(), "test1", 23, true);
    }

    private void initOnResume() {
        actvMessage = findViewById(R.id.activity_main_message);
        actvMessage.setVisibility(View.GONE);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_CHANGE_FRAGMENT);
        intentFilter.addAction(ACTION_DISPLAY_MESSAGE);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        initOnResume();
    }

    private void initOnPause() {
        try { unregisterReceiver(receiver); } catch (Exception ignored) {}
        handler.removeCallbacks(hideMessage);
    }

    @Override
    protected void onPause() {
        super.onPause();
        initOnPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        initOnPause();
    }

    @Override
    public void onBackPressed() {
        /*if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }*/

        int count = getSupportFragmentManager().getBackStackEntryCount();
        if(count != 0)
        {
            String lastFragmentName = getSupportFragmentManager().getBackStackEntryAt(count - 1).getName();
            if (lastFragmentName != null) {
                switch (lastFragmentName) {
                    case TAG_FRAGMENT_MAIN_MENU:
                        finish();
                        return;
                    case TAG_FRAGMENT_LOGIN:
                        MainMenu.add(getBaseContext(), "param", 2, true);
                        return;
                    case TAG_FRAGMENT_NOT_LOGGED_IN:
                        getSupportFragmentManager().popBackStack();
                        return;
                    default:
                        //super.onBackPressed();
                }
            }
        }
    }

    @Override
    public User getUser() {
        return null;
    }
}
