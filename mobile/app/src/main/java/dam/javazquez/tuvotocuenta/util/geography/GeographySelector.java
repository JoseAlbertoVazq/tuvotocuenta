package dam.javazquez.tuvotocuenta.util.geography;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.HashMap;

import dam.javazquez.tuvotocuenta.R;
import dam.javazquez.tuvotocuenta.util.data.GeographySpain;

@SuppressLint("ValidFragment")
public class GeographySelector extends DialogFragment
        implements OnItemSelectedListener, OnClickListener {

    private static GeographySpain geography;

    private GeographyListener gListener;

    private Activity mActivity;
    private Context context;
    private Spinner  spRegion;
    private Spinner  spProvincia;
    private Spinner  spMunicipio;
    private Button   btSearch;

    private ArrayAdapter<String> aaMunicipios;
    private ArrayAdapter<String> aaProvincias;
    private ArrayAdapter<String> aaRegiones;

    private String region;
    private String provincia = "";
    private String municipio = "";

    public void setOnGeograpySelectedListener(GeographyListener lis){
        this.gListener = lis;
    }

    public GeographySelector(Context context){
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = getActivity();

        geography = GeographySpain.getInstance(mActivity);

        View view = inflater.inflate(R.layout.fragment_sign_up, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);




        spProvincia = view.findViewById(R.id.ciudades_signup) ;





        aaProvincias= new ArrayAdapter<String>(
                context,
                android.R.layout.simple_spinner_item
        );




        aaProvincias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);





        spProvincia.setAdapter(aaProvincias);



        spProvincia.setOnItemSelectedListener(this);




        btSearch.setOnClickListener(this);
        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position,
                               long id) {
        int viewId = parent.getId();
         if(viewId == R.id.ciudades_signup) {
             provincia = aaProvincias.getItem(position);
             if (provincia != null
                     &&
                     !provincia.equalsIgnoreCase(geography.provinciaLabel)
                     &&
                     !provincia.equalsIgnoreCase(geography.provinciaSinRegionLabel)
                     && !provincia.equalsIgnoreCase("")
             ) {
                 aaMunicipios = geography.putStringArrayIntoAdapter(
                         geography.getMunicipios(
                                 provincia, geography.municipioLabel)
                         , mActivity
                 );
             }
         }
    }


    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onClick(View v) {


    }

}
