package dam.javazquez.tuvotocuenta.ui.dashboard;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import dam.javazquez.tuvotocuenta.R;
import dam.javazquez.tuvotocuenta.ui.login.LoginFragment;
import dam.javazquez.tuvotocuenta.ui.login.SignUpFragment;

public class DashboardActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener, SignUpFragment.OnFragmentInteractionListener {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_favoritos:
                    mTextMessage.setText(R.string.title_favoritos);
                    return true;
                case R.id.navigation_mis_propuestas:
                    mTextMessage.setText(R.string.title_mis_propuestas);
                    return true;
                case R.id.navigation_mi_perfil:
                    mTextMessage.setText(R.string.title_mi_perfil);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
