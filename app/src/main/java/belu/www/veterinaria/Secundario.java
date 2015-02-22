package belu.www.veterinaria;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class Secundario extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_secundario);
            String Nuevo = getIntent().getExtras().getString("tipo");
        if(Nuevo.equals("nuevo")) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new FragCamara(this))
                    .commit();
        }else{
            Toast.makeText(this,"Seleccionado "+Nuevo,Toast.LENGTH_SHORT).show();
            getFragmentManager().beginTransaction().replace(R.id.container, new Fragment_Agenda(Nuevo,this)).commit();
        }
    }
	
}