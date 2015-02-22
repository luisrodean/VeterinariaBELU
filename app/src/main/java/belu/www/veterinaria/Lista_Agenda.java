package belu.www.veterinaria;

import android.R.raw;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Lista_Agenda extends BaseAdapter{

	//Declarar variables
	private Context context;
    private LayoutInflater inflater;
    private String tabla;
    private BaseDatos consulta;

	//Inicializar variables
	public Lista_Agenda(Context context, String nuevo){
		this.context = context;
        this.tabla = nuevo;
        consulta = new BaseDatos(context, "Mascota.db", null, 1);
	}
	
	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View vista = inflater.inflate(R.layout.lista_agenda, parent, false);
		
		//Declarar objetos
		ImageView Img;
		TextView Title;
        
        //Enlace de XML con Java
        Title = (TextView)vista.findViewById(R.id.tvNombre);
        Img   = (ImageView)vista.findViewById(R.id.imgFoto);

        Title.setText(consulta.Recuperar(0, position));
        Img.setImageResource(R.drawable.gato);
                //consulta.Recuperar(1, position));

		return vista;
	}



}
