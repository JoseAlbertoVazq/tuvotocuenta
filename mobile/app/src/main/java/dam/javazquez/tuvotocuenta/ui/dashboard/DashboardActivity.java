package dam.javazquez.tuvotocuenta.ui.dashboard;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dam.javazquez.tuvotocuenta.R;
import dam.javazquez.tuvotocuenta.dto.PropuestaCreateDto;
import dam.javazquez.tuvotocuenta.responses.MateriaResponse;
import dam.javazquez.tuvotocuenta.responses.PartidoResponse;
import dam.javazquez.tuvotocuenta.responses.PropuestaResponse;
import dam.javazquez.tuvotocuenta.responses.ResponseContainer;
import dam.javazquez.tuvotocuenta.retrofit.generator.AuthType;
import dam.javazquez.tuvotocuenta.retrofit.generator.ServiceGenerator;
import dam.javazquez.tuvotocuenta.retrofit.services.MateriaService;
import dam.javazquez.tuvotocuenta.retrofit.services.PartidoService;
import dam.javazquez.tuvotocuenta.retrofit.services.PropuestaService;
import dam.javazquez.tuvotocuenta.ui.favs.PropuestaFavFragment;
import dam.javazquez.tuvotocuenta.ui.login.LoginFragment;
import dam.javazquez.tuvotocuenta.ui.login.SignUpFragment;
import dam.javazquez.tuvotocuenta.ui.profile.PerfilFragment;
import dam.javazquez.tuvotocuenta.ui.propias.PropiasFragment;
import dam.javazquez.tuvotocuenta.ui.propuestas.PropuestaFragment;
import dam.javazquez.tuvotocuenta.util.Geocode;
import dam.javazquez.tuvotocuenta.util.MapsActivity;
import dam.javazquez.tuvotocuenta.util.UtilToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity implements PropiasFragment.OnListFragmentInteractionListener, PropuestaFavFragment.OnListFragmentInteractionListener, PerfilFragment.OnFragmentInteractionListener, LoginFragment.OnFragmentInteractionListener, SignUpFragment.OnFragmentInteractionListener, PropuestaFragment.OnListFragmentInteractionListener {

    FragmentTransaction fragmentChanger;
    FloatingActionButton addPropuesta, filtro, mapa;
    String jwt;
    private EditText titulo, contenido;
    private List<PartidoResponse> partidoResponses = new ArrayList<>();
    private List<MateriaResponse> materiaResponses = new ArrayList<>();
    private Map<String, String> options = new HashMap<>();
    private Spinner add_partidos, add_materias;
    private Fragment propuestas, perfil, favoritos, propias;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @SuppressLint("RestrictedApi")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentChanger = getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_dashboard, propuestas);
                    fragmentChanger.commit();
                    addPropuesta.setVisibility(View.GONE);
                    filtro.setVisibility(View.VISIBLE);
                    mapa.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_favoritos:
                    fragmentChanger = getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_dashboard, favoritos);
                    fragmentChanger.commit();
                    addPropuesta.setVisibility(View.GONE);
                    filtro.setVisibility(View.GONE);
                    mapa.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_mis_propuestas:
                    fragmentChanger = getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_dashboard, propias);
                    fragmentChanger.commit();
                    addPropuesta.setVisibility(View.VISIBLE);
                    filtro.setVisibility(View.GONE);
                    mapa.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_mi_perfil:
                    fragmentChanger = getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_dashboard, perfil);
                    fragmentChanger.commit();
                    addPropuesta.setVisibility(View.GONE);
                    filtro.setVisibility(View.GONE);
                    mapa.setVisibility(View.GONE);;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        jwt = UtilToken.getToken(this);
        propuestas = new PropuestaFragment();
        perfil = new PerfilFragment();
        propias = new PropiasFragment();
        favoritos = new PropuestaFavFragment();
        addPropuesta = findViewById(R.id.addPropuesta);
        mapa = findViewById(R.id.mapa);
        filtro = findViewById(R.id.fab_filtro);

        filtro.setOnClickListener(v -> {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ResourceType")
            View dialogLayout = inflater.inflate(R.layout.activity_filtro, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogLayout);
            add_materias = dialogLayout.findViewById(R.id.add_materias);
            add_partidos = dialogLayout.findViewById(R.id.add_partidos);
            cargarMaterias();
            cargarPartidos();

            builder.setPositiveButton("OK", (dialog, which) -> {
                MateriaResponse materiaElegida = (MateriaResponse) add_materias.getSelectedItem();
                PartidoResponse partidoElegido = (PartidoResponse) add_partidos.getSelectedItem();
                if (materiaElegida.getId().equals("5c8b65fa68a6370017f1088c")) {
                    Log.d("sin materia", "sin materia");
                } else {
                    options.put("materia", materiaElegida.getId());
                }

                if (partidoElegido.getId().equals("5c8b661668a6370017f1088d")) {
                    Log.d("sin partido", "sin partido");
                } else {
                    options.put("partido", partidoElegido.getId());
                }
                propuestas = new PropuestaFragment(options);
                fragmentChanger = getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_dashboard, propuestas);
                fragmentChanger.commit();
            });

            builder.setNegativeButton(R.string.cancel, (dialog, id) -> {
                Log.d("Back", "Going back");
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        addPropuesta.setOnClickListener(v -> {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ResourceType")
            View dialogLayout = inflater.inflate(R.layout.activity_add_propuesta, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogLayout);
            titulo = dialogLayout.findViewById(R.id.add_titulo);
            contenido = dialogLayout.findViewById(R.id.add_contenido_multi);
            add_materias = dialogLayout.findViewById(R.id.add_materias);
            add_partidos = dialogLayout.findViewById(R.id.add_partidos);
            cargarMaterias();
            cargarPartidos();

            PropuestaCreateDto propuestaDto = new PropuestaCreateDto();
            builder.setPositiveButton("OK", (dialog, which) -> {
                MateriaResponse materiaElegida = (MateriaResponse) add_materias.getSelectedItem();
                PartidoResponse partidoElegido = (PartidoResponse) add_partidos.getSelectedItem();
                propuestaDto.setTitulo(titulo.getText().toString());
                propuestaDto.setContenido(contenido.getText().toString());
                propuestaDto.setMateria(materiaElegida.getId());
                propuestaDto.setPartido(partidoElegido.getId());

                PropuestaService serviceProp = ServiceGenerator.createService(PropuestaService.class, jwt, AuthType.JWT);
                Call<PropuestaCreateDto> addCall = serviceProp.createPropuesta(propuestaDto);
                addCall.enqueue(new Callback<PropuestaCreateDto>() {
                    @Override
                    public void onResponse(Call<PropuestaCreateDto> call, Response<PropuestaCreateDto> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(DashboardActivity.this, "Propuesta añadida", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(getIntent());
                            fragmentChanger = getSupportFragmentManager().beginTransaction().replace(R.id.contenedor_dashboard, propias);
                            fragmentChanger.commit();

                        } else
                            Toast.makeText(DashboardActivity.this, "Error añadiendo propuesta", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<PropuestaCreateDto> call, Throwable t) {
                        Toast.makeText(DashboardActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                    }
                });
            });
            builder.setNegativeButton(R.string.cancel, (dialog, id) -> {
                Log.d("Back", "Going back");
            });
            AlertDialog dialog = builder.create();
            dialog.show();


        });

        mapa.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, MapsActivity.class)));
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }
    public void cargarPartidos() {
        PartidoService serviceP = ServiceGenerator.createService(PartidoService.class, jwt, AuthType.JWT);
        Call<ResponseContainer<PartidoResponse>> callP = serviceP.listarPartidos();
        callP.enqueue(new Callback<ResponseContainer<PartidoResponse>>() {
            @Override
            public void onResponse(Call<ResponseContainer<PartidoResponse>> call, Response<ResponseContainer<PartidoResponse>> response) {
                if (response.isSuccessful()) {
                    int spinnerPosition = 1;
                    partidoResponses = response.body().getRows();
                    ArrayAdapter<PartidoResponse> adapter = new ArrayAdapter<>(DashboardActivity.this, android.R.layout.simple_spinner_dropdown_item, partidoResponses);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    add_partidos.setAdapter(adapter);
                    add_partidos.setSelection(getIndex(add_partidos, "Sin Partido"));

                } else {
                    Toast.makeText(DashboardActivity.this, "Error in response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<PartidoResponse>> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cargarMaterias() {
        MateriaService serviceM = ServiceGenerator.createService(MateriaService.class);
        Call<ResponseContainer<MateriaResponse>> callM = serviceM.listarMaterias();
        callM.enqueue(new Callback<ResponseContainer<MateriaResponse>>() {
            @Override
            public void onResponse(Call<ResponseContainer<MateriaResponse>> call, Response<ResponseContainer<MateriaResponse>> response) {
                if (response.isSuccessful()) {
                    int spinnerPosition = 1;
                    materiaResponses = response.body().getRows();
                    ArrayAdapter<MateriaResponse> adapter = new ArrayAdapter<>(DashboardActivity.this, android.R.layout.simple_spinner_dropdown_item, materiaResponses);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    add_materias.setAdapter(adapter);
                    add_materias.setSelection(getIndex(add_materias, "Sin Materia"));
                } else {
                    Toast.makeText(DashboardActivity.this, "Error in response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<MateriaResponse>> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(PropuestaResponse item) {

    }
}
