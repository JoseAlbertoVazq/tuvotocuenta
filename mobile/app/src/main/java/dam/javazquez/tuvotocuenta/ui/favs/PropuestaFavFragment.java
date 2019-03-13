package dam.javazquez.tuvotocuenta.ui.favs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import dam.javazquez.tuvotocuenta.R;
import dam.javazquez.tuvotocuenta.responses.PropuestaResponse;
import dam.javazquez.tuvotocuenta.responses.ResponseContainer;
import dam.javazquez.tuvotocuenta.retrofit.generator.AuthType;
import dam.javazquez.tuvotocuenta.retrofit.generator.ServiceGenerator;
import dam.javazquez.tuvotocuenta.retrofit.services.PropuestaService;

import dam.javazquez.tuvotocuenta.ui.login.LoginActivity;
import dam.javazquez.tuvotocuenta.ui.propuestas.PropuestaAdapter;
import dam.javazquez.tuvotocuenta.ui.propuestas.PropuestaFragment;
import dam.javazquez.tuvotocuenta.util.UtilToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PropuestaFavFragment extends Fragment {
    Context ctx = getContext();
    List<PropuestaResponse> propuestas = new ArrayList<>();
    String jwt;
    PropuestaService service;
    PropuestaAdapter adapter;
    private static final int FAV_CODE = 1;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PropuestaFavFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PropuestaFavFragment newInstance(int columnCount) {
        PropuestaFavFragment fragment = new PropuestaFavFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jwt = UtilToken.getToken(getContext());

        if(jwt == null){
            Intent i = new Intent(getActivity(), LoginActivity.class);
            startActivity(i);
        }

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_propuestafav_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            service = ServiceGenerator.createService(PropuestaService.class, jwt, AuthType.JWT);

            Call<ResponseContainer<PropuestaResponse>> call = service.listaFavs();
            call.enqueue(new Callback<ResponseContainer<PropuestaResponse>>() {
                @Override
                public void onResponse(Call<ResponseContainer<PropuestaResponse>> call, Response<ResponseContainer<PropuestaResponse>> response) {
                    if(response.isSuccessful()){
                        propuestas = response.body().getRows();
                        adapter = new PropuestaAdapter(FAV_CODE, context, propuestas, mListener);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(getActivity(), "Error in request", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseContainer<PropuestaResponse>> call, Throwable t) {
                    Log.e("NetworkFailure", t.getMessage());
                    Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener extends PropuestaFragment.OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(PropuestaResponse item);
    }
}
