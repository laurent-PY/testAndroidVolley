package com.example.victor;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.victor.models.Users;


public class Subscribe extends AppCompatActivity {

    private EditText nom, prenom, mail, password;
    private Button subscribe;
    String json_url = "http://testing/write.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);

        // je déclare les EditText qui sont présent dans le ficihier XML
        nom = (EditText) findViewById(R.id.nom_ins);
        prenom = (EditText) findViewById(R.id.prenom_ins);
        mail = (EditText) findViewById(R.id.mail_ins);
        password = (EditText) findViewById(R.id.password_ins);

        // je déclare le boutton et j'y ajoute un écouteur d'évenement.
        subscribe = (Button) findViewById(R.id.subscribe_ins);
        subscribe.setOnClickListener(new View.OnClickListener() {
            private Object JsonObjectRequest;

            @Override
            public void onClick(View view) {
                // ici je crée l'objet Users subs qui va contenir les infos
                Users subs = new Users();
                // je set les attributs avec les données que je récupère dans les EditText
                subs.setNom(nom.getText().toString());
                subs.setPrenom(prenom.getText().toString());
                subs.setMail(mail.getText().toString());
                subs.setPassword(password.getText().toString());
                // un petit contrôle
                System.out.println(subs.getNom());
                // création d'un objet JSON que j'appel subsOjectJson
                // pour créer l'objet j'appel les données via l'objet Users subs
                // ensuite je "put" les données à l'intérieur en créant les paires clés/valeur
                JSONObject subsOjectJson = new JSONObject();
                try {
                    subsOjectJson.put("nom", subs.getNom());
                    subsOjectJson.put("prenom", subs.getPrenom());
                    subsOjectJson.put("mail", subs.getMail());
                    subsOjectJson.put("password", subs.getPassword());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(subsOjectJson);

                //Comment envoyer proprement le json au serveur ?
                // Json-encode
                //json.. send
            }
        });
    }
}