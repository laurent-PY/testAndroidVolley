package com.example.victor;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.victor.models.JSONParser;
import com.example.victor.models.Users;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Inscription extends AppCompatActivity {

    EditText nom, prenom, mail, password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        nom = findViewById(R.id.nom_ins);
        prenom = findViewById(R.id.prenom_ins);
        mail = findViewById(R.id.mail_ins);
        password = findViewById(R.id.password_ins);
        login = findViewById(R.id.subscribe_ins);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ici je crée l'objet Users subs qui va contenir les infos
                Users subs = new Users();
                // je set les attributs avec les données que je récupère dans les EditText
                subs.setNom(nom.getText().toString());
                subs.setPrenom(prenom.getText().toString());
                subs.setMail(mail.getText().toString());
                subs.setPassword(password.getText().toString());
                // un petit contrôle
                System.out.println(subs.getNom());

                // final ?
                final TextView textView = (TextView) findViewById(R.id.textView_ins);

                RequestQueue queue = Volley.newRequestQueue(Inscription.this);
                String url ="http://192.168.43.244/testing/write.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                textView.setText("Response is: "+ response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("That didn't work!");
                    }
                }){
                    protected Map<String, String> getParams(){
                        //je set la string pour y inclure les données de l'objet.
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("nom", subs.getNom());
                        paramV.put("prenom", subs.getPrenom());
                        paramV.put("mail", subs.getMail());
                        paramV.put("password", subs.getPassword());
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }
}