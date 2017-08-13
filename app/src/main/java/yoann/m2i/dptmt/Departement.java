package yoann.m2i.dptmt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DEFARGE on 10/08/2017.
 */

public class Departement {

    private SQLiteDatabase db;
    private Context ctxt;
    private String noDept;
    private Integer noRegion;
    private String nomDept;
    private String nomStdDept;
    private Integer areaDept;
    private Date dateCreation;
    private String capitalDept;
    private String urlDept;


    private Date sqlToDate(String dateCreation) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.parse(dateCreation);
    }
    private String dateToSql (Date dateCreation) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(dateCreation);
    }
    private Date stringToDate (String dateCreation) throws Exception  {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.parse(dateCreation);
    }
    private String dateToString (Date dateCreation) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(dateCreation);
    }

    public String getUrlDept() { return urlDept; }
    public void setUrlDept(String urlDept) { this.urlDept = urlDept;}

    public String getNoDept() { return noDept; }
    public void setNoDept(String noDept) throws Exception {
        String regex = "[0-9][0-9AB][0-9]?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(noDept);
        if (matcher.find()){
            this.noDept = noDept;
        }
        else {
            throw new DbException(ctxt.getString(R.string.errNoDept));
        }
    }

    public Integer getNoRegion() {return noRegion;}
    public void setNoRegion(Integer noRegion) {this.noRegion = noRegion;}
    public void setNoRegion(String noRegion) throws Exception {
        try {
            this.noRegion = Integer.parseInt(noRegion);
        }
        catch (Exception ex) {
            throw new DbException(ctxt.getString(R.string.errNoReg));
        }
    }

    public String getNomDept() { return nomDept; }
    public void setNomDept(String nomDept) { this.nomDept = nomDept;}

    public String getNomStdDept() { return nomStdDept;}
    public void setNomStdDept(String nomStdDept) {this.nomStdDept = nomStdDept;}

    public Integer getAreaDept() {return areaDept;}
    public void setAreaDept(Integer areaDept) {this.areaDept = areaDept;}
    public void setAreaDept (String areaDept) throws Exception{
        try {
            this.areaDept = Integer.parseInt(areaDept);
        }
        catch (Exception ex){
            throw new DbException("La surface doit être un chiffre");
        }
    }

    public String getDateCreation() {return dateToString(dateCreation);}
    public void setDateCreation(String dateCreation) throws Exception{
        try{
            this.dateCreation = stringToDate(dateCreation);
        }
        catch(Exception ex){
            throw new DbException(ctxt.getString(R.string.errDate));
        }
    }

    public String getCapitalDept() {return capitalDept;}
    public void setCapitalDept(String capitalDept) {this.capitalDept = capitalDept;}

    public void showToast (String msg) {
        Toast.makeText(ctxt, msg, Toast.LENGTH_LONG).show();
    }

    public void select(Context ctxt,String no) throws Exception{
        String[] cols = {"no_dept","no_region","nom","nom_std","surface", "date_creation", "chef_lieu","url_wiki"};
        String critere = "UPPER(no_dept) = '"
                + no + "'";
        Cursor curs = db.query("departements", cols, critere, null,null,null,null);

        if(curs.getCount() == 0) {
            showToast("le département n'existe pas");
        }
        else {
            curs.moveToFirst();
            noDept = curs.getString(curs.getColumnIndex("no_dept"));
            noRegion=curs.getInt(curs.getColumnIndex("no_region"));
            nomDept = curs.getString(curs.getColumnIndex("nom"));
            nomStdDept=curs.getString(curs.getColumnIndex("nom_std"));
            areaDept=curs.getInt(curs.getColumnIndex("surface"));
            dateCreation=sqlToDate(curs.getString(curs.getColumnIndex("date_creation")));
            capitalDept=curs.getString(curs.getColumnIndex("chef_lieu"));
            urlDept=curs.getString(curs.getColumnIndex("url_wiki"));

        }
    }

    public Departement(Context c) {
        ctxt = c;
        DbInit dbInit = DbInit.getInstance(ctxt, "france");
        db = dbInit.getWritableDatabase();
    }
    public Departement(Context c, String no) {
        ctxt = c;
        DbInit dbInit = DbInit.getInstance(ctxt, "geo");
        db = dbInit.getWritableDatabase();
    }

    public void delete() {
        String crit = "no_dept = '" + noDept + "'";
        db.delete("departements", crit, null);
        showToast("département effacé");
    }
    public void update(){
        String crit = "no_dept = '" + noDept + "'";
        ContentValues values = new ContentValues();
        values.put("no_dept", noDept);
        values.put("no_region", noRegion);
        values.put("nom", nomDept);
        values.put("nom_std",nomStdDept);
        values.put("surface", areaDept);
        values.put("date_creation", dateToSql(dateCreation));
        values.put("chef_lieu", capitalDept);
        db.update("departements", values, crit, null);
    }
    public void insert() {
        ContentValues values = new ContentValues();
        values.put("no_dept", noDept);
        values.put("no_region", noRegion);
        values.put("nom", nomDept);
        values.put("nom_std",nomStdDept);
        values.put("surface", areaDept);
        values.put("date_creation", dateToSql(dateCreation));
        values.put("chef_lieu", capitalDept);
        db.insert("departements",null, values);
    }
}
