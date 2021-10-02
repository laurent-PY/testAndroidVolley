package com.example.victor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
//block d'import pour la librairy Volley
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

//block d'import pour la class Users et la class CheckFields
import com.example.victor.models.Users;
import com.example.victor.utils.ChekFields;
//block d'import pour manipuler et créer le Json
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
//ce block d'import me sert à falcifier le certificat SSL
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class PostVolley extends AppCompatActivity {

    //déclaration des 'EditText' qui vont servir de passerelle pour le bindage.
    private EditText nom_volley, prenom_volley, mail_volley, password_volley, confirm_volley;
    private Button button_volley;
    private TextView error_nom_volley, error_prenom_volley, error_mail_volley, error_password_volley, error_confirm_volley;

    // TODO test progres bar
    ProgressBar pb;
    int counter = 0;
    // TODO test progres bar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_volley);
        //appelle la méthode de falcification de certificat

        handleSSLHandshake();

        // Bindage des champs dans la vue xml avec les 'EditText' déclarés précedement.
        nom_volley = (EditText) findViewById(R.id.nom_volley);
        prenom_volley = (EditText) findViewById(R.id.prenom_volley);
        mail_volley = (EditText) findViewById(R.id.mail_volley);
        password_volley = (EditText) findViewById(R.id.password_volley);
        confirm_volley = (EditText) findViewById(R.id.confirm_volley);
        error_nom_volley = (TextView) findViewById(R.id.error_nom_volley);
        error_prenom_volley = (TextView) findViewById(R.id.error_prenom_volley);
        error_mail_volley = (TextView) findViewById(R.id.error_mail_volley);
        error_password_volley = (TextView) findViewById(R.id.error_password_volley);
        error_confirm_volley = (TextView) findViewById(R.id.error_confirm_volley);
        button_volley = (Button) findViewById(R.id.button_volley);


        // placement d'un éctouteur d'événement sur le button
        button_volley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lorsque le bouton est cliqué, création d'un objet.
                Users subs = new Users();
                // affectation des valeurs aux attributs de l'objet.
                // l'attribut action est nécessaire lors de l'appel dans le backend. Il me permet de faire du routage.
                subs.setAction("register");
                subs.setNom(nom_volley.getText().toString());
                subs.setPrenom(prenom_volley.getText().toString());
                subs.setMail(mail_volley.getText().toString());
                subs.setPassword(password_volley.getText().toString());
                subs.setConfirm((confirm_volley).getText().toString());
                // Todo terminer le contrôle de champs saisis
                // contrôle de la présence de saisi, et l'ajout de regex qui limite à a-A z-Z et trait d'union '-'
                String state_nom = ChekFields.nom_volley_check(subs);
                // je set le label text avec le msg de retour de la méthode
                error_nom_volley.setText(state_nom);
                // contrôle de la présence de saisi, et l'ajout de regex qui limite à a-A z-Z et trait d'union '-'
                String state_prenom = ChekFields.prenom_volley_check(subs);
                // je set le label text avec le msg de retour de la méthode
                error_prenom_volley.setText(state_prenom);
                // contrôle sur l'adresse, présence de saisi et mail via REGEX EMAIL_ADDRESS de Java
                String state_mail = ChekFields.mail_volley_check(subs);
                // je set le label text avec le msg de retour de la méthode
                error_mail_volley.setText(state_mail);
                // contrôle sur me mdp présence et complexité de 8 caractères 1 lettre et un chiffre
                String state_password = ChekFields.password_volley_check(subs);
                // je set le label text avec le msg de retour de la méthode
                error_password_volley.setText(state_password);
                // contrôle de la confirmation du champ confirm
                String state_confirm = ChekFields.confirm_volley_check(subs);
                // je set le label text avec le msg de retour de la méthode
                error_confirm_volley.setText(state_confirm);

                // Todo terminer le contrôle de champs saisis

                if(state_nom.equals("") && state_prenom.equals("") && state_mail.equals("") && state_password.equals("") && state_confirm.equals("")){
//                    nom_volley.setText("");
//                    prenom_volley.setText("");
//                    mail_volley.setText("");
//                    password_volley.setText("");
//                    confirm_volley.setText("");
//                    Toast.makeText(PostVolley.this, "Envoi des données OK !", Toast.LENGTH_SHORT).show();
                    // j'appelle la méthode volley qui se charge de récupérer les données pour envoyer un json au backend
                    volleyPost(subs);

                }
            }
            // méthode qui se charge de créer la string qui servira à la création du Json à envoyer au serveur backend PHP
            // elle a besoin en paramètre d'un objet, l'objet utilisateur(Users).
            public void volleyPost(Users subs) {

                // postUrl qui reçois l'adresse du serveur et le nom du script à appeler.
                String postUrl = "https://192.168.0.19/testing/write.php";
                RequestQueue requestQueue = Volley.newRequestQueue(PostVolley.this);
                // créer les paires clé valeur à stocker dans l'objet json.
                // attention, j'ai créé une paire "action : "register" qui servira au script PHP pour router la demande, par exemple register->insert,
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
                        //test pour afficher le retour
                        System.out.println(response);
                        // try catch qui me permet de récuperer la réponse du back et de la stocker dans une variable que je vais passer sous condition
                        try {
                            boolean rep = response.getBoolean("success");
                            // si "success" et égal à true, l'email n'existe pas en Base de données, j'ai déjà inscrit l'utilisateur dans la base, je le redirige vers login
                            if(rep){
                                Intent login = new Intent(PostVolley.this, Login.class);
                                startActivity(login);
                                // sinon le back m'as répondu que l'utilisateur veut utiliser un mail déjà existant, et donc un false sans insert et j'affiche un Toast.
                            }else{
                                Toast.makeText(PostVolley.this, "Adresse mail déjà utilisée.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                requestQueue.add(jsonObjectRequest);
                // System.out.println("ici -------------------->"+ jsonObjectRequest);
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