package com.hlub.dev.demomapclass;

import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DatabaseManager databaseManager;
    MapDAO mapDAO;
    List<Vitri> vitriList;

    private EditText edtKinhDo;
    private EditText edtViDo;
    private EditText edtTen;

    private EditText edtUpdateKinDo;
    private EditText edtUpdateViDo;
    private EditText edtUpdateTen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        anhxa();
        databaseManager = new DatabaseManager(this);
        mapDAO = new MapDAO(databaseManager);
        vitriList = new ArrayList<>();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        vitriList = mapDAO.getAllViTri();
        for (Vitri vitri : vitriList) {
            LatLng sydney = new LatLng(vitri.getKinhdo(), vitri.getVido());
            mMap.addMarker(new MarkerOptions().position(sydney).title(vitri.getTitle()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //tim vi tri theo kinh do, vi do
                LatLng lg = marker.getPosition();
                Vitri vitri = mapDAO.getVitri(lg.longitude, lg.latitude);
                if (vitri != null) {
                    diaLogSuaXoa(vitri, marker);
                    Toast.makeText(MapsActivity.this, "" + vitri.getId() + "/ " + vitri.getTitle() + " / " + vitri.getKinhdo() + "/ " + vitri.getVido(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

    }

    //nhan vao marker -> dialog sua/xoa
    private void diaLogSuaXoa(final Vitri vitri, final Marker marker) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tùy chọn");


        builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mapDAO.delete(vitri) > 0) {
                    marker.remove();
                    Toast.makeText(MapsActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    onMapReady(mMap);

                }
            }
        });
        builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogSua(vitri, marker);
            }
        });
        builder.show();
    }

    //dialog sua marker
    private void dialogSua(final Vitri vitri, final Marker marker) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sửa vị trí");

        //view
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.update_map, null);
        edtUpdateKinDo = (EditText) view.findViewById(R.id.edtUpdateKinDo);
        edtUpdateViDo = (EditText) view.findViewById(R.id.edtUpdateViDo);
        edtUpdateTen = (EditText) view.findViewById(R.id.edtUpdateTen);
        builder.setView(view);
        edtUpdateKinDo.setText(String.valueOf(vitri.getKinhdo()));
        edtUpdateViDo.setText(String.valueOf(vitri.getVido()));
        edtUpdateTen.setText(String.valueOf(vitri.getTitle()));

        builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                vitri.setKinhdo(Long.parseLong(edtUpdateKinDo.getText().toString()));
                vitri.setVido(Long.parseLong(edtUpdateKinDo.getText().toString()));
                vitri.setTitle(edtUpdateTen.getText().toString());
                mapDAO.updateMap(vitri);
                LatLng lg = new LatLng(Long.parseLong(edtUpdateKinDo.getText().toString()),
                        Long.parseLong(edtUpdateKinDo.getText().toString()));
                marker.setPosition(lg);
                Toast.makeText(MapsActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                onMapReady(mMap);


            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    //add marker vao sqlite
    public void add(View view) {
        String ten = edtTen.getText().toString();
        String king = edtKinhDo.getText().toString();
        String vi = edtViDo.getText().toString();
        if (king.length() == 0) {
            edtKinhDo.setError("Không để trống kinh độ");
        } else if (vi.length() == 0) {
            edtViDo.setError("Không để trống vĩ độ");
        } else {
            mapDAO.insertMap(new Vitri(0, ten, Long.parseLong(king), Long.parseLong(vi)));
            onMapReady(mMap);
            setText();
        }
    }

    void anhxa() {
        edtKinhDo = (EditText) findViewById(R.id.edtKinhDo);
        edtViDo = (EditText) findViewById(R.id.edtViDo);
        edtTen = (EditText) findViewById(R.id.edtTen);
    }

    void setText() {
        edtTen.setText("");
        edtKinhDo.setText("");
        edtViDo.setText("");
    }
}
