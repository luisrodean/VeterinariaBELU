package belu.www.veterinaria;

import java.io.IOException;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FragBienvenida extends Fragment{
	
	private ImageView ImgHuella;
	private Context contex;
	private BaseDatos categoria;
	
//Declaro enlace a otra clase y creacion de base de daatos
	public FragBienvenida(Context contexto){
		this.contex = contexto;
		categoria = new BaseDatos(contex, "Mascota.db", null, 1);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View vista = inflater.inflate(R.layout.fragment_principal, container, false);
		if(vista != null){
			ImgHuella = (ImageView)vista.findViewById(R.id.imgCarga); 
		}
		return vista;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
        //Crea base de datos en dispositivo
        try {
			categoria.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}

		new CountDownTimer(5500, 1000) {

	            public void onTick(long millisUntilFinished) {
	                int segundo = (int)millisUntilFinished/1000;
	                switch (segundo){
	                    case 1:
	                        ImgHuella.setImageResource(R.drawable.huella5);
	                    break;
	                    case 2:
	                        ImgHuella.setImageResource(R.drawable.huella4);
	                    break;
	                    case 3:
	                        ImgHuella.setImageResource(R.drawable.huella3);
	                    break;
	                    case 4:
	                        ImgHuella.setImageResource(R.drawable.huella2);
	                    break;
	                }
	            }

	            public void onFinish() {
	            	getFragmentManager().beginTransaction().replace(R.id.container, new FragMenu(contex)).commit();
	            }
	        }.start();

	    }
	

}

