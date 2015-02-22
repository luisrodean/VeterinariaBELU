package belu.www.veterinaria;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatos extends SQLiteOpenHelper{

	private Context contexto;
	private String NombreBD;
	
	public BaseDatos(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);
		this.contexto = context;
		this.NombreBD = name;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE  TABLE  IF NOT EXISTS Categoria (Id INTEGER PRIMARY KEY NOT NULL, Nombre TEXT NOT NULL, Imagen TEXT NOT NULL)");		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public void createDataBase() throws IOException {
        File pathFile = contexto.getDatabasePath(NombreBD);
        boolean dbExist = checkDataBase(pathFile.getAbsolutePath()); 
        if(!dbExist) {
            this.getReadableDatabase(); 
            try {
                copyDataBase(pathFile); 
            } catch (IOException e) { 
                // Error copying database 
            }
        } 
    }
	
	//Evalua existencia de una base de datos
    private boolean checkDataBase(String path){
        SQLiteDatabase checkDB = null; 
        try {            
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY); 
        } catch(Exception e){
             // Database doesn't exist 
        } 
        if(checkDB != null) { 
            checkDB.close(); 
        }

        return checkDB != null;
    }
    
    //Reemplazar base de datos existente por la que contiene la apicacion
    private void copyDataBase(File pathFile) throws IOException { 
        InputStream myInput = contexto.getAssets().open(NombreBD);
        OutputStream myOutput = new FileOutputStream(pathFile);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        } 
        myOutput.flush();
        myOutput.close();
        myInput.close(); 
    }
    
    public int Filas(){
    	return getReadableDatabase().rawQuery("Select Id, Nombre, Imagen from Categoria", null).getCount();
    }

    public String Recuperar(int tipo, int posicion){
        SQLiteDatabase db = getReadableDatabase();
        Cursor C_consulta = db.rawQuery("Select Nombre, Imagen from Categoria" , null);
        C_consulta.moveToPosition(posicion);
        String recuperar = C_consulta.getString(tipo);
        db.close();
        return recuperar;
    }

    public void SaberImagen(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Categoria");
        db.execSQL("Insert into Categoria (Id, Nombre, Imagen) values (1, 'Perro', "+R.drawable.perro +")");
        db.execSQL("Insert into Categoria (Id, Nombre, Imagen) values (2, 'Gato', "+R.drawable.gato +")");
        db.close();
    }

    public void AgregarCatedoria (String nomcategoria){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("Insert into Categoria (Id, Nombre, Imagen) values ("+(Filas()+1)+" , '"+ nomcategoria +"', '')");
        db.close();
    }


}
