package dam.javazquez.tuvotocuenta.ui.profile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import dam.javazquez.tuvotocuenta.R;
import dam.javazquez.tuvotocuenta.dto.UserEditedDto;
import dam.javazquez.tuvotocuenta.responses.UserResponse;
import dam.javazquez.tuvotocuenta.retrofit.generator.AuthType;
import dam.javazquez.tuvotocuenta.retrofit.generator.ServiceGenerator;
import dam.javazquez.tuvotocuenta.retrofit.services.UsuarioService;
import dam.javazquez.tuvotocuenta.ui.login.LoginActivity;
import dam.javazquez.tuvotocuenta.util.UtilToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PerfilFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int READ_REQUEST_CODE = 42;
    private Uri uriSelected;
    private String jwt;
    private Context ctx;
    private UserResponse userResponse;
    private String userId;
    private TextView nombre, email, ciudad, partido;
    private EditText nombre_edit, email_edit, password_edit;
    private Spinner ciudades_edit;
    private ImageView picture;
    private Button btn_editar, btn_logout;
    private UsuarioService service;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        ctx = getContext();//get main activity, token and user id
        jwt = UtilToken.getToken(ctx);
        userId = UtilToken.getId(ctx);
        if (jwt == null) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ctx = getContext();//get main activity, token and user id
        jwt = UtilToken.getToken(ctx);
        userId = UtilToken.getId(ctx);
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        loadItem(view);
        getUser(view);
        return view;
    }

    public void loadItem(View view) {
        nombre = view.findViewById(R.id.perfil_nombre);
        email = view.findViewById(R.id.perfil_email);
        partido = view.findViewById(R.id.perfil_partido_din);
        ciudad = view.findViewById(R.id.perfil_ciudad_din);
        picture = view.findViewById(R.id.profile_image);
        btn_editar = view.findViewById(R.id.btn_editar_perfil);
        btn_logout = view.findViewById(R.id.btn_logout);
    }

    public void setItems(Response<UserResponse> response, View view) {

        userResponse = response.body();
        if (userResponse.getPartido() == null) {
            partido.setText("Sin partido afín");
        } else {
            partido.setText(userResponse.getPartido().getNombre());
        }
        ciudad.setText(userResponse.getCiudad());
        nombre.setText(userResponse.getName());
        email.setText(userResponse.getEmail());
        Glide.with(view).load(userResponse.getPicture()).into(picture);
        btn_editar.setOnClickListener(v -> {
            //abrir diálogo de edición
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ResourceType")
            View dialogLayout = inflater.inflate(R.layout.activity_edit, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setView(dialogLayout);
            nombre_edit = dialogLayout.findViewById(R.id.name_edit);
            email_edit = dialogLayout.findViewById(R.id.email_edit);
            password_edit = dialogLayout.findViewById(R.id.password_edit);
            ciudades_edit = dialogLayout.findViewById(R.id.ciudades_edit);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    ctx,
                    R.array.provincias,
                    android.R.layout.simple_spinner_item
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ciudades_edit.setAdapter(adapter);
            if (userResponse.getCiudad() != null) {
                int spinnerPosition = adapter.getPosition(userResponse.getCiudad());
                ciudades_edit.setSelection(spinnerPosition);
            }

            setDialogItems(userResponse);
            UserEditedDto edited = new UserEditedDto();



            builder.setPositiveButton("OK", (dialog, which) -> {
                edited.setPicture(userResponse.getPicture());
                edited.setEmail(email_edit.getText().toString());
                edited.setName(nombre_edit.getText().toString());
                edited.setCiudad(ciudades_edit.getSelectedItem().toString());
                service = ServiceGenerator.createService(UsuarioService.class, jwt, AuthType.JWT);
                System.out.println(userResponse.get_id());
                Call<UserResponse> callEdit = service.editUser(userResponse.get_id(),edited);
                callEdit.enqueue(new Callback<UserResponse>() {

                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(ctx, "Usuario editado", Toast.LENGTH_SHORT).show();
                            refresh();
                        }else{
                            Toast.makeText(ctx, "Error al editar el usuario", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Toast.makeText(ctx, "Failure", Toast.LENGTH_SHORT).show();
                    }
                });
            });
            builder.setNegativeButton("Cancelar", (dialog, id) -> {
                Log.d("Back", "Going back");
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        });
        btn_logout.setOnClickListener(v -> {
            UtilToken.clearAll(ctx);
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void refresh(){
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void getUser(View view) {//obtain from the api the user logged
        service = ServiceGenerator.createService(UsuarioService.class,
                jwt, AuthType.JWT);
        Call<UserResponse> getOneUser = service.getMe();
        getOneUser.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                //Resources res = getResources();
                String points = "";
                if (response.isSuccessful()) {
                    Log.d("Success", "user obtain successfully");
                    setItems(response, view);
                } else {
                    Log.d("Fail", "user can't be obtain successfully");
                    Toast.makeText(ctx, "You have to log in!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("Conexion failure", "FALLITO BUENO");
                Toast.makeText(ctx, "Fail in the request!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setDialogItems(UserResponse user) {
        nombre_edit.setText(user.getName());
        email_edit.setText(user.getEmail());


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
