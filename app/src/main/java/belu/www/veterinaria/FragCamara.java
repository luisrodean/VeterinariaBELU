package belu.www.veterinaria;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class FragCamara extends Fragment {

	private Button btnCamara;
	private Button btnGalleria;
	private EditText txtNomCat;
	private ImageView iv;
	public static int camara = 1;
	public static int galeria = 2;
	private String name;
	private Context contexto;
   
	
	public FragCamara(Context contexto){
		this.contexto = contexto;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View vista = inflater.inflate(R.layout.fragment_agregar_categoria, container, false);
		if(vista != null){
			btnCamara   = (Button)vista.findViewById(R.id.btnCamara);
			btnGalleria = (Button)vista.findViewById(R.id.btnGaleria);
			txtNomCat   = (EditText)vista.findViewById(R.id.txtNomCat);
			iv = (ImageView)vista.findViewById(R.id.imgView);
		}

		//Modifica el titulo y logo del action bar
		getActivity().getActionBar().setTitle("Nueva Categoria");
		getActivity().getActionBar().setIcon(android.R.drawable.ic_input_add);
		//Activa el menu propio del fragment 
		setHasOptionsMenu(true);
		
		return vista;	
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
        //accion para el boton galeria
	        btnGalleria.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
                    AccionBoton(galeria);
                }
			});

        //accion para el boton camara
	        btnCamara.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	               AccionBoton(camara);
	            }
	        });
	}

    public void AccionBoton(int presionado){
        Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        int code = presionado;

        if (presionado==camara) {
            //Comprobamos el estado de la memoria externa (tarjeta SD)
            String estado = Environment.getExternalStorageState();
            if (estado.equals(Environment.MEDIA_MOUNTED)) {
                // Creamos una carpeta "MisImagenes" dentro del directorio "Pictures"
                // Con el motodo "mkdirs()" creamos el directorio si es necesario
                File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "BAMOIT");
                if (!path.exists()) {
                    path.mkdirs();
                }
                name = path + "/" + getCode();
            }else{
                name = Environment.getExternalStorageDirectory() + getCode();
            }
                Uri output = Uri.fromFile(new File(name));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
        } else if (presionado==galeria){
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        }

        startActivityForResult(intent, code);
    }

    public static Drawable Escala(Bitmap imgoriginal, int newWidth, int newHeight) {
        int width = imgoriginal.getWidth();
        int height = imgoriginal.getHeight();

        // calculamos el escalado de la imagen destino
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // para poder manipular la imagen
        // debemos crear una matriz

        Matrix matrix = new Matrix();
        // resize the Bitmap
        matrix.postScale(scaleWidth, scaleHeight);
        //se gira la imagen 90°
      //  matrix.postRotate(90.0f);

        // volvemos a crear la imagen con los nuevos valores
        Bitmap resizedBitmap = Bitmap.createBitmap(imgoriginal, 0, 0,
                width, height, matrix, true);

        // si queremos poder mostrar nuestra imagen tenemos que crear un
        // objeto drawable y así asignarlo a un botón, imageview...
        return new BitmapDrawable(resizedBitmap);
    }
	
	//Titulo a la imagen nueva
	@SuppressLint("SimpleDateFormat")
    private String getCode(){
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new java.util.Date());
        return "categoria_" + date + ".png";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == camara) {
            Bitmap bmImg = BitmapFactory.decodeFile(name);
            iv.setImageDrawable(Escala(bmImg, 200, 200));

            new MediaScannerConnection.MediaScannerConnectionClient() {
                private MediaScannerConnection msc = null;

                {
                    msc = new MediaScannerConnection(contexto, this);
                    msc.connect();
                }

                public void onMediaScannerConnected() {
                    msc.scanFile(name, null);
                }

                public void onScanCompleted(String path, Uri uri) {
                    msc.disconnect();
                }
            };

        } else if (requestCode == galeria) {
            if (data == null) {
                iv.setImageResource(R.drawable.imgcategoria);
            } else {
                Uri selectedImage = data.getData();
                InputStream is;
                try {
                    is = getActivity().getContentResolver().openInputStream(selectedImage);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    Bitmap bitmap = BitmapFactory.decodeStream(bis);
                    iv.setImageDrawable(Escala(bitmap, 200, 200));
                } catch (FileNotFoundException e) {
                }
            }
        }
    }

	   @Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.agregar_categoria, menu);
	}

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        int id = item.getItemId();
	        if (id == R.id.Opc_btnGuardar_Categoria) {
                String NomCat = txtNomCat.getText().toString();
                if(NomCat.equals("")){
                    Toast.makeText(contexto,"Ecriba el nombre de una categoria",Toast.LENGTH_SHORT).show();
                }else{
                    BaseDatos base = new BaseDatos(contexto,"Mascota.db", null, 1);
                    SQLiteDatabase db = base.getWritableDatabase();
                    db.execSQL("CREATE  TABLE  IF NOT EXISTS Perro (Num_Folio INTEGER PRIMARY KEY , Nombre VARCHAR, Imagen VARCHAR NOT NULL , Raza TEXT, Fecha_Nac VARCHAR NOT NULL , Sexo TEXT, Propietario VARCHAR, Direccion VARCHAR, Telefono INTEGER NOT NULL , Celular INTEGER NOT NULL , Desparacitacion VARCHAR NOT NULL , Vacunacion VARCHAR NOT NULL , Tratamiento VARCHAR NOT NULL)");

                }
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	    } 


}
