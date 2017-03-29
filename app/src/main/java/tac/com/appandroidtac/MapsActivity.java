package tac.com.appandroidtac;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.NumberFormat;
import java.util.List;

import tac.com.appandroidtac.dao.ConexaoSQLite;
import tac.com.appandroidtac.model.Posto;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ConexaoSQLite conexao;

    private List<Posto> postos;
    private static final int ADD_ACTION_CODE = 1;
    private static final NumberFormat FORMAT_CURRENCY = NumberFormat.getCurrencyInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        conexao = new ConexaoSQLite(this);
        postos = conexao.getPostos();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        if (!postos.isEmpty()) {
            for (Posto p : postos) {
                StringBuilder sb = new StringBuilder();
                sb.append("G: ").append(FORMAT_CURRENCY.format(p.getPrecoGasolina())).append(" - ");
                sb.append("GA: ").append(FORMAT_CURRENCY.format(p.getPrecoGasolinaAditivada())).append(" - ");
                sb.append("ET: ").append(FORMAT_CURRENCY.format(p.getPrecoEtanol())).append(" - ");
                sb.append("DI: ").append(FORMAT_CURRENCY.format(p.getPrecoDiesel()));
                LatLng latLng = new LatLng(p.getLatitude(), p.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng).title(p.getNome()).snippet(sb.toString()));
            }
        }
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        Location lastlocation = locationManager.getLastKnownLocation(locationProvider);

        String lat = String.valueOf(lastlocation.getLatitude());
        String lng = String.valueOf(lastlocation.getLongitude());

        LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        mMap.addMarker(new MarkerOptions().position(latLng).title("Sua localização")
                .snippet("Encontre a economia em combustível próximo a você!")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                try {
                    Intent i = new Intent(MapsActivity.this, PrecoCombustivel.class);
                    LatLng latLng = marker.getPosition();
                    double latPosto = latLng.latitude;
                    double lngPosto = latLng.longitude;
                    Posto posto = null;
                    posto = conexao.consultaPorLatLng(latPosto, lngPosto);
                    Bundle bundle = new Bundle();
                    bundle.putDouble("lat", latPosto);
                    bundle.putDouble("lng", lngPosto);
                    bundle.putString("nome", posto.getNome());
                    bundle.putDouble("G", posto.getPrecoGasolina());
                    bundle.putDouble("GA", posto.getPrecoGasolinaAditivada());
                    bundle.putDouble("E", posto.getPrecoEtanol());
                    bundle.putDouble("D", posto.getPrecoDiesel());

                    i.putExtras(bundle);

                    startActivity(i);
                } catch (Exception e) {
                    Log.e("Erro: ", e.getMessage());
                }
            }
        });
    }
}
