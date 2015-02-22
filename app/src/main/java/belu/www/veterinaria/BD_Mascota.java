package belu.www.veterinaria;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class BD_Mascota extends SQLiteOpenHelper{

	String sqlCreate = "CREATE TABLE Categorias (FOLIO INTEGER primary key NOT NULL, NOMBRE_CATEGORIA TEXT NOT NULL, IMAGEN TEXT NULL)";
	
	private String bdmascota;
	private String msg="No creada";
	private Context contexto;
	
	
	public String getNombd()
	{ 
		return this.bdmascota; 
	}
	
	public BD_Mascota(Context contexto, String nombreDB, CursorFactory factory, int version)
	{
	    super(contexto, nombreDB, factory, version);
	    this.bdmascota = nombreDB;
	    this.contexto = contexto;
 	}
	
	public void createDataBase() throws IOException {
        File pathFile = contexto.getDatabasePath(bdmascota);
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
	//Crea base de datos cuando se manda llamar en carga
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
       
	}
	
	//Evalua existencia de una base de datos
    private boolean checkDataBase(String path) { 
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
        InputStream myInput = contexto.getAssets().open("Categorias.db");
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

  //Actualiza base de datos
  	@Override
  	public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva)
  	{
  	}
  	
  	
  	public String getmsg(){
  		return msg;
  	}
  	
  	
  	//	insertar registros a una tabla
  	public void insert(SQLiteDatabase db, String tabla, String[] campos, String[] valores)
  	{
  		String query = "INSERT INTO @tabla (@campos) VALUES (@valores)";

  		String fields = "";
  		for (int i = 0; i < campos.length; i++)
  			fields+=campos[i] + ",";
  		fields = fields.substring(0, fields.length() - 1);	// fields = id , user , pasword 

  		String values = "";
  		for (int i = 0; i < valores.length; i++)
  			values += ("'" + valores[i] + "',");
  		values = values.substring(0, values.length() - 1);

  		query = query.replace("@campos", fields).replace("@valores", values).replace("@tabla", tabla);

  		try
  		{
  			db.execSQL(query);
  			Toast.makeText(contexto, "Categoria ingresada", Toast.LENGTH_SHORT).show();
  		}
  		catch(SQLException ex)
  		{
  			Toast.makeText(contexto, "Error al ingresar categoria", Toast.LENGTH_SHORT).show();			
  		}
  	}
  	
  	//	modificar registros de una tabla
  	public void update(SQLiteDatabase db, String tabla, String[] campos, String[] valores, String id)
  	{
  		String query = "UPDATE @tabla SET @datos WHERE Id ="+id;

  		String fields = "";
  		for (int i = 0; i < campos.length; i++)
  			fields+=campos[i] + "=" + "'" + valores[i] + "'" + ","; // Spanish='correr',PresentSimple='run',
  		fields = fields.substring(0, fields.length() - 1);	// Spanish='correr',PresentSimple='run'

  		query = query.replace("@datos", fields).replace("@tabla", tabla);

  		try
  		{
  			db.execSQL(query);
  			Toast.makeText(contexto, "Categoria Modificado", Toast.LENGTH_SHORT).show();
  		}
  		catch(SQLException ex)
  		{
  			Toast.makeText(contexto, "Error al modificado Categoria", Toast.LENGTH_SHORT).show();			
  		}
  	}

  	
  	//	eliminar registros de una tabla
  	public void delete(SQLiteDatabase db, String tabla, String id)
  	{
  		String query = "DELETE FROM "+ tabla +" WHERE Id = "+id;

  		try
  		{
  			db.execSQL(query);
  			Toast.makeText(contexto, "Categoria Eliminado", Toast.LENGTH_SHORT).show();
  		}
  		catch(SQLException ex)
  		{
  			Toast.makeText(contexto, "Error al Eliminado Categoria", Toast.LENGTH_SHORT).show();			
  		}
  	}
  	
  	
  	//Carga registros de la base de datos
	public Cursor CargarCursor(SQLiteDatabase db){
		db = getReadableDatabase();
		return db.rawQuery("Select Id, Nombre, Imagen from Categoria", null);
	}
	
  	
  	
  }
