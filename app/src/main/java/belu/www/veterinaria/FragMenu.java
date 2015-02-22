package belu.www.veterinaria;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class FragMenu extends Fragment{
	
	private Context contexto;
    private ListaCategorias LCategorias;
	private ListView LvVista;

    public FragMenu(Context contexto) {
        this.contexto = contexto;
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View vista = inflater.inflate(R.layout.fragment_menu, container, false);
		if (vista != null){
			LvVista = (ListView)vista.findViewById(R.id.lvCategoria);
		}
		getActivity().getActionBar().setTitle("MENU PRINCIPAL");
		getActivity().getActionBar().setIcon(R.drawable.ic_menu_home);

		return vista; 
		}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		LCategorias = new ListaCategorias(contexto);
		LvVista.setAdapter(LCategorias);
		
		LvVista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent Cambio = new Intent(contexto, Secundario.class);
				//Agrega una nueva categoria
				if(position == LCategorias.getCount()-1){
					Cambio.putExtra("tipo", "nuevo");

				//Mensaje de categoria elegida
				}else{
					Toast.makeText(contexto, "Categoria " + LCategorias.Titulo(position), Toast.LENGTH_SHORT).show();
                    Cambio.putExtra("tipo", LCategorias.Titulo(position));
				}
                    startActivity(Cambio);
			}
		});
	}

}
