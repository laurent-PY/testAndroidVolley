package com.example.victor;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.android.volley.toolbox.Volley;
import com.example.victor.models.Users;
import com.example.victor.utils.ChekFields;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class PostVolley extends AppCompatActivity {

    //déclaration des 'EditText' qui vont servir de passerelle pour le bindage.
    private EditText nom_volley, prenom_volley, mail_volley, password_volley;
    private Button button_volley;
    private TextView errormsg_volley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_volley);
        handleSSLHandshake();

        // Bindage des champs dans la vue xml avec les 'EditText' déclarés précedement.
        nom_volley = (EditText) findViewById(R.id.nom_volley);
        prenom_volley = (EditText) findViewById(R.id.prenom_volley);
        mail_volley = (EditText) findViewById(R.id.mail_volley);
        password_volley = (EditText) findViewById(R.id.password_volley);
        errormsg_volley = (TextView) findViewById(R.id.errormsg_volley);
        button_volley = (Button) findViewById(R.id.button_volley);


        // placement d'un éctouteur d'évènement sur le button
        button_volley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lorsque le bouton est cliqué, création d'un objet.
                Users subs = new Users();
                // affectation des valeurs aux attributs de l'objet.
                // l'attribut action est necessaire lors de l'appel dans le backend. Il me permet de faire du routage.
                subs.setAction("register");
                subs.setNom(nom_volley.getText().toString());
                subs.setPrenom(prenom_volley.getText().toString());
                subs.setMail(mail_volley.getText().toString());
                subs.setPassword(password_volley.getText().toString());
                boolean $test = ChekFields.emptyFields(subs);
                if($test == true){
                    errormsg_volley.setText("Tous les champs doivent être saisis");
                }else{
                    // j'appel la méthode volley qi se charge de récupéré les données pour envoyer un json au backend
                    volleyPost(subs);
                    // petit contrôle.
                    System.out.println("Fin de création de l'objet subs : ****************************" + subs);
                }



            }
            // méthode qui se charge de créer la string qui servira à la création du Json à envoyer au serveur backend PHP
            // elle a besoin en parametre d'un objet, l'objet utilisateur(Users).
            public void volleyPost(Users subs) {
                // postUrl qui reçois l'adresse du serveur et le nom du script à appeler.
                String postUrl = "https://192.168.43.244/testing/write.php";
                RequestQueue requestQueue = Volley.newRequestQueue(PostVolley.this);
                // créer les paires clé valeur à stocker dans l'objet json.
                // attention, j'ai créé une paires "action : "register" qui servira au script PHP pour router la demande, par exemple register->insert,
                // delete->del, maj->update par exemple.
                JSONObject postData = new JSONObject();
                try {
                    postData.put("action", subs.getAction());
                    postData.put("nom", subs.getNom());
                    postData.put("prenom", subs.getPrenom());
                    postData.put("mail", subs.getMail());
                    postData.put("password", subs.getPassword());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                requestQueue.add(jsonObjectRequest);
                System.out.println("ici -------------------->     "+ jsonObjectRequest);
            }
        });


    }
    //Methode à ne pas utiliser hors dev local "simule la présence d'un certificat de confiance 'cert' SSL"
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }
}