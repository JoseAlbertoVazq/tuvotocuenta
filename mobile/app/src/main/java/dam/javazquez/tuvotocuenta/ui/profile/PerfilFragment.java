package dam.javazquez.tuvotocuenta.ui.profile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import dam.javazquez.tuvotocuenta.R;
import dam.javazquez.tuvotocuenta.dto.UserEditedDto;
import dam.javazquez.tuvotocuenta.responses.AfinResponse;
import dam.javazquez.tuvotocuenta.responses.MateriaResponse;
import dam.javazquez.tuvotocuenta.responses.PartidoResponse;
import dam.javazquez.tuvotocuenta.responses.ResponseContainer;
import dam.javazquez.tuvotocuenta.responses.UserResponse;
import dam.javazquez.tuvotocuenta.retrofit.generator.AuthType;
import dam.javazquez.tuvotocuenta.retrofit.generator.ServiceGenerator;
import dam.javazquez.tuvotocuenta.retrofit.services.MateriaService;
import dam.javazquez.tuvotocuenta.retrofit.services.PartidoService;
import dam.javazquez.tuvotocuenta.retrofit.services.PropuestaService;
import dam.javazquez.tuvotocuenta.retrofit.services.UsuarioService;
import dam.javazquez.tuvotocuenta.ui.login.LoginActivity;
import dam.javazquez.tuvotocuenta.util.Geocode;
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
    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";
    private static final String CARPETA_IMAGEN = "imagenes";
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;
    private final int PICK_IMAGE_REQUEST = 71;
    private final int PICK_FROM_CAMERA = 72;
    private final int COD_FOTO = 20;
    private final int COD_SELECCIONADA = 10;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
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
    private PropuestaService serviceP;
    private PartidoService partidoS;
    private String path;
    private String mCurrentPhotoPath;
    private Uri filePath;
    String result;
    private File fileImage;

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
        getAfin(userResponse);
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
                Call<UserResponse> callEdit = service.editUser(userResponse.get_id(), edited);
                callEdit.enqueue(new Callback<UserResponse>() {

                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ctx, "Usuario editado", Toast.LENGTH_SHORT).show();
                            refresh();
                        } else {
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

        picture.setOnClickListener(v -> {

            final CharSequence[] options = {getString(R.string.from_gallery), getString(R.string.from_camera), getString(R.string.cancel)};
            final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(getString(R.string.choose_option));
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int selectedOption) {
                    //dialog's options

                    //open camera
                    if (options[selectedOption] == getString(R.string.from_camera)) {
                        openCamera();
                        //open gallery
                    } else if (options[selectedOption] == getString(R.string.from_gallery)) {
                        performFileSearch();
                        //close dialog
                    } else if (options[selectedOption] == getString(R.string.cancel)) {
                        dialog.dismiss();
                    }
                }
            });//show dialog
            builder.show();


        });
    }

    public void getAfin(UserResponse user){
        serviceP = ServiceGenerator.createService(PropuestaService.class, jwt, AuthType.JWT);
        Call<ResponseContainer<AfinResponse>> callP = serviceP.afin();
        callP.enqueue(new Callback<ResponseContainer<AfinResponse>>() {
            @Override
            public void onResponse(Call<ResponseContainer<AfinResponse>> call, Response<ResponseContainer<AfinResponse>> response) {
                if(response.isSuccessful()){
                    partidoS = ServiceGenerator.createService(PartidoService.class, jwt, AuthType.JWT);
                    Call<PartidoResponse> callPartido = partidoS.getOne(response.body().getRows().get(0).getId());
                    callPartido.enqueue(new Callback<PartidoResponse>() {
                        @Override
                        public void onResponse(Call<PartidoResponse> call, Response<PartidoResponse> response) {
                            if(response.isSuccessful()){
                                user.setPartido(response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call<PartidoResponse> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<AfinResponse>> call, Throwable t) {

            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void refresh() {
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

    //UPDLOAD PROFILE IMAGE METHODS
    public void performFileSearch() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        //upload image with gallery
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {

            Uri uri = null;
            if (resultData != null) {
                filePath = resultData.getData();
                Log.i("Filechooser URI", "Uri: " + filePath.toString());
                Glide
                        .with(this)
                        .load(filePath)
                        .into(picture);
                uploadImage();
            }//upload image with camera
        } else if (requestCode == PICK_FROM_CAMERA && resultCode == Activity.RESULT_OK) {
            MediaScannerConnection.scanFile(getContext(), new String[]{path},
                    null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("Path", "" + path);
                            Glide
                                    .with(ctx)
                                    .load(filePath)
                                    .into(picture);
                        }
                    });

            if (resultData != null) {
                filePath = resultData.getData();
                Log.i("Filechooser URI", "Uri: " + filePath.toString());
                Glide
                        .with(this)
                        .load(filePath)
                        .into(picture);
                uploadImage();
            }
        }
    }

    //upload image to firebase
    private void uploadImage() {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(ctx);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            //where the picture will be saved
            StorageReference ref = storageReference.child("image/" + UUID.randomUUID().toString());

            //uploading picture to the server
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //obtain url
                              obtainDownloadUrl(ref);


                            progressDialog.dismiss();
                            Toast.makeText(ctx, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ctx, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })//progress item
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }

    //TODO open camera and rescue the url in order to upload to the server
    public void openCamera() {

        File myFile = new File(Environment.getExternalStorageDirectory(), DIRECTORIO_IMAGEN);
        boolean isCreated = myFile.exists();

        if (isCreated == false) {
            myFile.mkdirs();
            if (myFile.exists() == true) {
                isCreated = true;
            }

        }
        if (isCreated == true) {
            Long consecutive = System.currentTimeMillis() / 1000;
            String nombre = consecutive.toString() + ".jpg";
            //set storage route
            path = Environment.getExternalStorageState() + File.separator + DIRECTORIO_IMAGEN
                    + File.separator + nombre;
            fileImage = new File(path);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, PICK_FROM_CAMERA);
        }


    }

    //optain picture url from firebase
    public void obtainDownloadUrl(StorageReference ref) {

        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {//taking the url and uptading user
              String urlUploadedPicture=uri.toString();
                UserEditedDto u= new UserEditedDto();
                u.setName(userResponse.getName());
                u.setPicture(urlUploadedPicture);
                u.setEmail(userResponse.getEmail());
                u.setCiudad(userResponse.getCiudad());

                UsuarioService service = ServiceGenerator.createService(UsuarioService.class, jwt, AuthType.JWT);
                Call<UserResponse> edit = service.editUser(userResponse.get_id(), u);
                edit.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if(response.isSuccessful()){
                            refresh();
                        } else {
                            Toast.makeText(ctx, "no suscessful", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Toast.makeText(ctx, "Failure", Toast.LENGTH_SHORT).show();
                    }
                });


            }

        });
        ref.getDownloadUrl().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ctx, "Error, not uploaded", Toast.LENGTH_SHORT).show();
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
