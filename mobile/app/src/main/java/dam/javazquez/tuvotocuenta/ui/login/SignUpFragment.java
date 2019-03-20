package dam.javazquez.tuvotocuenta.ui.login;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import java.util.regex.Pattern;

import dam.javazquez.tuvotocuenta.R;
import dam.javazquez.tuvotocuenta.responses.LoginResponse;
import dam.javazquez.tuvotocuenta.responses.UserResponse;
import dam.javazquez.tuvotocuenta.retrofit.generator.ServiceGenerator;
import dam.javazquez.tuvotocuenta.retrofit.services.LoginService;
import dam.javazquez.tuvotocuenta.ui.dashboard.DashboardActivity;
import dam.javazquez.tuvotocuenta.util.UtilToken;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignUpFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Context ctx = this.getContext();
    EditText email, password, nombre;
    Spinner ciudades;
    Button btn_signup;
    ImageView logo;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ctx = view.getContext();

        email = view.findViewById(R.id.email_signup2);
        password = view.findViewById(R.id.password_signup);
        nombre = view.findViewById(R.id.name_signup);
        btn_signup = view.findViewById(R.id.btn_do_signup);
        logo = view.findViewById(R.id.imageView);
        ciudades = view.findViewById(R.id.ciudades_signup);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                ctx,
                R.array.provincias,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ciudades.setAdapter(adapter);
        btn_signup.setOnClickListener(v -> doSignUp());
        logo.setOnClickListener(v -> {
            LoginFragment f = new LoginFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contenedor_login, f)
                    .commit();
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void doSignUp() {
        // Recoger los datos del formulario
        String email_txt = email.getText().toString();
        String password_txt = password.getText().toString();
        String nombre_txt = nombre.getText().toString();
        String ciudad_txt = ciudades.getSelectedItem().toString();
        final Pattern EMAIL_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);

        if (email_txt.equals("") || password_txt.equals("")) {
            Toast.makeText(ctx, "Los campos no pueden estar vacíos", Toast.LENGTH_SHORT).show();
        } else if (!EMAIL_REGEX.matcher(email_txt).matches()) {
            Toast.makeText(ctx, "Tienes que usar un email válido", Toast.LENGTH_LONG).show();
        } else if (password_txt.length() < 6) {
            Toast.makeText(ctx, "La contraseña tiene que tener al menos 6 caracteres", Toast.LENGTH_LONG).show();
        } else {


            UserResponse register = new UserResponse(nombre_txt, email_txt, password_txt, ciudad_txt);
            LoginService service = ServiceGenerator.createService(LoginService.class);
            Call<LoginResponse> loginReponseCall = service.doSignUp(register);

            loginReponseCall.enqueue(new retrofit2.Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.code() == 201) {
                        // éxito
                        System.out.println(response.body());
                        UtilToken.setToken(ctx, response.body().getToken());
                        startActivity(new Intent(ctx, DashboardActivity.class));
                    } else {
                        // error
                        Toast.makeText(ctx, "Error al registrar", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.e("NetworkFailure", t.getMessage());
                    Toast.makeText(ctx, "Network Connection Failure", Toast.LENGTH_SHORT).show();

                }
            });
        }

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