package yoann.m2i.dptmt;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import yoann.m2i.dptmt.Departement;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    private Departement dept;

    private EditText txtSearch;
    private EditText txtNoDept;
    private EditText txtNoRegion;
    private EditText txtNom;
    private EditText txtNomStd;
    private EditText txtSurface;
    private EditText txtDateCreation;
    private EditText txtChefLieu;
    private EditText txtUrlWiki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSearch = (EditText) findViewById(R.id.txtSearch);
        txtNoDept = (EditText) findViewById(R.id.txtNoDept);
        txtNoRegion = (EditText) findViewById(R.id.txtNoRegion);
        txtNom = (EditText) findViewById(R.id.txtNom);
        txtNomStd = (EditText) findViewById(R.id.txtNomStd);
        txtSurface = (EditText) findViewById(R.id.txtSurface);
        txtDateCreation = (EditText) findViewById(R.id.txtDateCreation);
        txtChefLieu = (EditText) findViewById(R.id.txtChefLieu);
        txtUrlWiki = (EditText) findViewById(R.id.txtUrlWiki);
        dept = new Departement(this);
    }
    public void btnSearch (View v) {
        try {
            dept.select(this, txtSearch.getText().toString());
            fromDept();
            txtNoDept.setEnabled(false);
        }
        catch (Exception ex) {
            showToast(ex.getMessage());
        }
    }
    public void btnDelete (View v) {
        dept.delete();
    }
    public void btnSave (View v) {
        try {
            if (dept.getNoDept() == null) {
                toDept();
                dept.insert();
            } else {
                toDept();
                dept.update();
            }
        }
        catch (DbException ex){
            showToast(ex.getMessage());
        }
        catch(Exception ex) {
            showToast(ex.getMessage());
        }
    }
    public void btnClear(View v) {
        txtNoDept.setEnabled(true);
        txtNoDept.setText("");
        txtNoRegion.setText("");
        txtNom.setText("");
        txtNomStd.setText("");
        txtSurface.setText("");
        txtDateCreation.setText("");
        txtChefLieu.setText("");
        txtUrlWiki.setText("");
    }
    private void showToast (String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    private void fromDept() throws Exception {
        txtNoDept.setText(dept.getNoDept());
        txtNoRegion.setText(dept.getNoRegion().toString());
        txtNom.setText(dept.getNomDept());
        txtNomStd.setText(dept.getNomStdDept());
        txtSurface.setText(dept.getAreaDept().toString());
        txtDateCreation.setText(dept.getDateCreation());
        txtChefLieu.setText(dept.getCapitalDept());
        txtUrlWiki.setText(dept.getUrlDept());
    }
    private void toDept() throws Exception {
        dept.setNoDept(txtNoDept.getText().toString());
        dept.setNoRegion(txtNoRegion.getText().toString());
        dept.setNomDept(txtNom.getText().toString());
        dept.setNomStdDept(txtNomStd.getText().toString());
        dept.setAreaDept(txtSurface.getText().toString());
        dept.setDateCreation(txtDateCreation.getText().toString());
        dept.setCapitalDept(txtChefLieu.getText().toString());
        dept.setUrlDept(txtUrlWiki.getText().toString());
    }
    public void deleteDB(View v) {
        deleteDatabase("geo");
    }
}
