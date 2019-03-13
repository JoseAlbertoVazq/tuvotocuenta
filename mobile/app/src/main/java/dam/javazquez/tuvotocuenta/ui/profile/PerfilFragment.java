package dam.javazquez.tuvotocuenta.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import dam.javazquez.tuvotocuenta.R;
import dam.javazquez.tuvotocuenta.responses.UserResponse;
import dam.javazquez.tuvotocuenta.retrofit.generator.AuthType;
import dam.javazquez.tuvotocuenta.retrofit.generator.ServiceGenerator;
import dam.javazquez.tuvotocuenta.retrofit.services.UsuarioService;
import dam.javazquez.tuvotocuenta.ui.login.LoginActivity;
import dam.javazquez.tuvotocuenta.util.UtilToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        View view =  inflater.inflate(R.layout.fragment_perfil, container, false);
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

    public void setItems(Response<UserResponse> response, View view){

        userResponse = response.body();
        if(userResponse.getPartido() == null){
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
    public void getUser(View view){//obtain from the api the user logged
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
