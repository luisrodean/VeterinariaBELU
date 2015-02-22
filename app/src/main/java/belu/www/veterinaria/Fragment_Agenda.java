package belu.www.veterinaria;



import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Fragment_Agenda extends Fragment {

	//Declaro objetos
	private ListView Lista_Mascota;
    private Context contexto;
    private Lista_Agenda lista_A;


    //Constructor
    public Fragment_Agenda(String nuevo, Context contexto){
        this.contexto = contexto;
        lista_A = new Lista_Agenda(contexto,nuevo);
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View vista = inflater.inflate(R.layout.fragment_agenda, container, false);
		if(vista != null){
			Lista_Mascota = (ListView)vista.findViewById(R.id.Lista_Agenda);
		}
		return vista;
}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

	}
}
