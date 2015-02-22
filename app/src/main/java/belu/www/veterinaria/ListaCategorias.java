package belu.www.veterinaria;
//Librerias a utilizar
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListaCategorias extends BaseAdapter {
	
	//Crear un objeto
	static BaseDatos consulta;
	
	//Declaracion de variables
	private Context context;
    private LayoutInflater inflater;
    private int filas;
	private Cursor Consulta;
	
    //Constructor
	public ListaCategorias(Context context){
		this.context = context;
		consulta = new BaseDatos(context, "Mascota.db", null, 1);
		this.filas = consulta.Filas()+1;
	}

	//Metodos de heredados del BaseAdapter
	@Override
	public int getCount() {
		return filas;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	//Regresa la vista al XML en ListView
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
        
		// Declaracion de Variables
        TextView txtTitle;
        ImageView Img;
        
        //Se personaliza la vista de cada columna del listView de forma general
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Obtengo la vista del XML
        View vista = inflater.inflate(R.layout.lista_categoria, parent, false);
        
        //Enlace de XML con Java
        txtTitle = (TextView)vista.findViewById(R.id.txtTitle);
        Img      = (ImageView)vista.findViewById(R.id.Img);
        
        //Modificar valores
        txtTitle.setText(Titulo(position));
        Img.setImageResource(Imagen(position));
        
        return vista;
        }	
	
	public static String Titulo(int position){
		String dato;
		SQLiteDatabase db = consulta.getReadableDatabase();
		Cursor paquete = db.rawQuery("Select Nombre from Categoria", null);
		if(paquete.moveToPosition(position)){
			dato = paquete.getString(0);
		}else{
			dato = "Agregar";
		}
		db.close();
		return dato;
	}

	private int Imagen(int position){
		int dato=0;
		SQLiteDatabase db = consulta.getReadableDatabase();
		Cursor paquete = db.rawQuery("Select Imagen from Categoria", null);
		if(paquete.moveToPosition(position)){
			dato=paquete.getInt(0);
		}else{
			dato = android.R.drawable.ic_input_add;
		}
		db.close();
		return dato;
	}
}
