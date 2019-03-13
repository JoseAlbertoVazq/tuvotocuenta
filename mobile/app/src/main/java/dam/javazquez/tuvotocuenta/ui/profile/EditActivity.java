package dam.javazquez.tuvotocuenta.ui.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import dam.javazquez.tuvotocuenta.R;

public class EditActivity extends AppCompatActivity {
    EditText email, password, nombre;
    Spinner ciudades;
    String jwt, userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
    }
}
