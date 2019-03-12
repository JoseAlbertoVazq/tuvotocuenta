package dam.javazquez.tuvotocuenta.ui.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import dam.javazquez.tuvotocuenta.R;
import dam.javazquez.tuvotocuenta.responses.PropuestaResponse;
import dam.javazquez.tuvotocuenta.util.UtilToken;

public class DetailsActivity extends AppCompatActivity {

    private TextView titulo, contenido, materia;
    private ImageView picture;
    private String jwt, idUser;
    private PropuestaResponse propuesta;
    private Context ctx;

    public DetailsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        jwt = UtilToken.getToken(DetailsActivity.this);
        idUser = UtilToken.getToken(getApplicationContext());
        Intent i = getIntent();
        propuesta = (PropuestaResponse) i.getSerializableExtra("propuesta");
        loadItems();
        setItems();

    }

    public void loadItems() {
        ctx = this;

        titulo = findViewById(R.id.details_titulo);
        contenido = findViewById(R.id.details_contenido);
        materia = findViewById(R.id.details_materia);
        picture = findViewById(R.id.details_photo);
        System.out.println(propuesta);
        Glide.with(this).load(propuesta
                .getPartido()
                .getPicture())
                .apply(new RequestOptions()
                        .override(409, 156))
                .centerInside()
                .into(picture);
    }

    public void setItems(){
        titulo.setText(propuesta.getTitulo());
        contenido.setText(propuesta.getContenido());
        materia.setText(propuesta.getMateria().getNombre());

    }

}
