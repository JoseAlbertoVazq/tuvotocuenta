package dam.javazquez.tuvotocuenta.ui.propuestas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import dam.javazquez.tuvotocuenta.R;
import dam.javazquez.tuvotocuenta.responses.PropuestaResponse;
import dam.javazquez.tuvotocuenta.responses.UserResponse;
import dam.javazquez.tuvotocuenta.retrofit.generator.AuthType;
import dam.javazquez.tuvotocuenta.retrofit.generator.ServiceGenerator;
import dam.javazquez.tuvotocuenta.retrofit.services.PropuestaService;
import dam.javazquez.tuvotocuenta.ui.details.DetailsActivity;
import dam.javazquez.tuvotocuenta.ui.propuestas.PropuestaFragment.OnListFragmentInteractionListener;
import dam.javazquez.tuvotocuenta.util.UtilToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import java.io.Serializable;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a  and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PropuestaAdapter extends RecyclerView.Adapter<PropuestaAdapter.ViewHolder> {

    private final List<PropuestaResponse> mValues;
    private final OnListFragmentInteractionListener mListener;
    Context contexto;
    String jwt;
    PropuestaService service;
    private int favCode = 0;
    public PropuestaAdapter(int fav, Context ctx,List<PropuestaResponse> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        contexto = ctx;
        favCode = fav;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_propuesta, parent, false);
        contexto = view.getContext();
        jwt = UtilToken.getToken(contexto);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.titulo.setText(mValues.get(position).getTitulo());
        holder.materia.setText(mValues.get(position).getMateria().getNombre());
        if (favCode == 0) {
            holder.fav.setImageResource(R.drawable.ic_no_fav);
        } else if (favCode == 1) {
            holder.fav.setImageResource(R.drawable.ic_fav);
        } else {
            holder.fav.setImageResource(R.drawable.ic_delete);
        }
        Glide.with(holder.mView)
                .load(holder.mItem.getPartido()
                        .getPicture())
                .apply(new RequestOptions()
                        .override(409, 156))
                .centerInside()
                .into(holder.picture);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
        holder.fav.setOnClickListener(v -> {
            if (favCode == 0) {
                service = ServiceGenerator.createService(PropuestaService.class, jwt, AuthType.JWT);
                Call<UserResponse> call = service.addFav(holder.mItem.getId());
                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(contexto, "Añadido a favoritos", Toast.LENGTH_SHORT).show();
                            holder.fav.setImageResource(R.drawable.ic_fav);
                        } else {
                            Toast.makeText(contexto, "Error in request", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Toast.makeText(contexto, "Failure", Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (favCode == 1) {
                service = ServiceGenerator.createService(PropuestaService.class, jwt, AuthType.JWT);
                Call<UserResponse> call = service.deleteFav(holder.mItem.getId());
                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(contexto, "Eliminado de favoritos", Toast.LENGTH_LONG).show();
                            holder.fav.setImageResource(R.drawable.ic_no_fav);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Toast.makeText(contexto, "Failure", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {

                AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                builder.setTitle(R.string.title_borrar).setMessage(R.string.message_delete);
                builder.setPositiveButton("OK", (dialog, which) ->
                        deletePropuesta(holder));
                builder.setNegativeButton(R.string.cancel, (dialog, id) -> {
                    Log.d("Back", "Going back");
                });
                AlertDialog dialog = builder.create();

                dialog.show();
            }
        });
        holder.constraintLayout.setOnClickListener(v -> {
            service = ServiceGenerator.createService(PropuestaService.class, jwt, AuthType.JWT);
            Call<PropuestaResponse> callDetails = service.getOnePropuesta(holder.mItem.getId());
            callDetails.enqueue(new Callback<PropuestaResponse>() {
                @Override
                public void onResponse(Call<PropuestaResponse> call, Response<PropuestaResponse> response) {
                    PropuestaResponse resp = response.body();
                    Intent detailsActivity = new Intent(contexto, DetailsActivity.class);
                    detailsActivity.putExtra("propuesta", resp);
                    contexto.startActivity(detailsActivity);
                }

                @Override
                public void onFailure(Call<PropuestaResponse> call, Throwable t) {

                }
            });
        });

    }

    public void deletePropuesta(final ViewHolder holder) {
        String id = holder.mItem.getId();
        service = ServiceGenerator.createService(PropuestaService.class, jwt, AuthType.JWT);
        Call<PropuestaResponse> call = service.deletePropuesta(id);
        call.enqueue(new Callback<PropuestaResponse>() {
            @Override
            public void onResponse(Call<PropuestaResponse> call, Response<PropuestaResponse> response) {
                if (response.isSuccessful())
                Toast.makeText(contexto, "Propuesta eliminada", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(contexto, "Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PropuestaResponse> call, Throwable t) {
                Toast.makeText(contexto, "Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView titulo;
        public final TextView materia;
        public final ImageView picture;
        public final ImageView fav;
        public PropuestaResponse mItem;
        public final ImageButton delete;
        public ConstraintLayout constraintLayout;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            titulo = view.findViewById(R.id.titulo_propuesta);
            materia = view.findViewById(R.id.materia_propuesta);
            picture = view.findViewById(R.id.photo);
            constraintLayout = view.findViewById(R.id.constraint);
            fav = view.findViewById(R.id.favPrincipal);
            delete = view.findViewById(R.id.delete_propuesta);
        }

    }
}
