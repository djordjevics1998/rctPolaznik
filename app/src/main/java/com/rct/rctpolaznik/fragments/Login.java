package com.rct.rctpolaznik.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rct.rctpolaznik.MainActivity;
import com.rct.rctpolaznik.R;
import com.google.android.gms.plus.PlusOneButton;
import com.rct.rctpolaznik.RegisterActivity;
import com.rct.rctpolaznik.interfaces.OnFragmentInteractionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.rct.rctpolaznik.Global.TAG_FRAGMENT_LOGIN;
import static com.rct.rctpolaznik.Global.changeFragment;
import static com.rct.rctpolaznik.Global.displayMessage;
import static com.rct.rctpolaznik.Global.getErrorMessage;

public class Login extends Fragment {
    private Context context;
    private OnFragmentInteractionListener activity;

    public static void add(Context context, boolean pop) {
        Bundle args = new Bundle();
        changeFragment(context, TAG_FRAGMENT_LOGIN, pop, args);
    }

    public Login() { } // Required empty public constructor

    public static Login newInstance(Bundle args) {
        Login fragment = new Login();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) { return inflater.inflate(R.layout.fragment_login, container, false); }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppCompatTextView actvRegister = view.findViewById(R.id.content_main_register);
        actvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RegisterActivity.class);
                startActivity(intent);
            }
        });

        final AppCompatEditText acetMail = view.findViewById(R.id.content_main_email),
                acetPassword = view.findViewById(R.id.content_main_password);

        AppCompatButton acbLogin = view.findViewById(R.id.content_main_submit);
        acbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayMessage(context, "Hello world!");
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(context);
                String url ="https://fanste1998.000webhostapp.com/user/login.php";
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    if(jsonResponse.getInt("status") == 1) Toast.makeText(context, "Успешно сте се пријавили!", Toast.LENGTH_LONG).show();
                                    else Toast.makeText(context, "Погрешили сте при уносу!", Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, getErrorMessage(error), Toast.LENGTH_LONG).show();
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context = context;
        activity = (OnFragmentInteractionListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //activity.getCsManager().getRequestQueue().cancelAll(TAG_FRAGMENT_RECOVER);
        activity = null;
        context = null;
    }
}
