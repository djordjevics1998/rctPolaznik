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
import static com.rct.rctpolaznik.Global.displayMessage;

public class MainActivity extends AppCompatActivity {
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
                            setTopbarVisibility(VISIBLE);
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

        AppCompatTextView actvRegister = findViewById(R.id.content_main_register);
        actvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        final AppCompatEditText acetMail = findViewById(R.id.content_main_email),
                acetPassword = findViewById(R.id.content_main_password);

        AppCompatButton acbLogin = findViewById(R.id.content_main_submit);
        acbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayMessage(getBaseContext(), "Hello world!");
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url ="https://fanste1998.000webhostapp.com/user/login.php";
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    if(jsonResponse.getInt("status") == 1) Toast.makeText(MainActivity.this, "Успешно сте се пријавили!", Toast.LENGTH_LONG).show();
                                    else Toast.makeText(MainActivity.this, "Погрешили сте при уносу!", Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, getErrorMessage(error), Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("mail", acetMail.getText().toString());
                        params.put("password", acetPassword.getText().toString());
                        return params;
                    }
                };

                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });
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

    private static String getErrorMessage(VolleyError error) {
        if(error != null) {
            String err = "Error: " + error.toString();
            if(error.networkResponse != null) err += " error code:" + error.networkResponse.statusCode;
            return err;
        } else return "Unknown error";
    }
}
