package cuip.estructura.datos.google.drive.service;

import java.io.IOException;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

public class CargarArchivosService {

	//instncia para log
	private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(CargarArchivosService.class);

	//Instancia para interactuar con google drive
	private Drive drive;
	
	/**
	 * Constructor de clase
	 * @param drive
	 */
	public CargarArchivosService(Drive drive) {
		super();
		this.drive = drive;
	}

	/**
	 * Aqui va todo el codigo para subir archivos.
	 * 1. Leer archivos desde una carpeta de la computadora
	 * 2. crear las carpetas con la estructura, año, mes, dia
	 * 3. subir los archivos a la carpeta dia creada
	 */
	public void crearArchivos() {
		
		try {
			
			this.crearCarpetas();
			
		}catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		}
		
	}
	
	private void crearCarpetas() throws IOException {
		
		String anio = String.valueOf( Calendar.getInstance().get(Calendar.YEAR) );
		
		//crea carpeta de año
//		com.google.api.services.drive.model.Drive contenido = new com.google.api.services.drive.model.Drive();
//		contenido.setName( anio );
		
//		com.google.api.services.drive.model.Drive carpetaAnio = drive.drives().create("Prueba", contenido).execute();
		
		com.google.api.services.drive.model.File carpetaAnio = new File();
		carpetaAnio.setName(anio);
		carpetaAnio.setMimeType("application/vnd.google-apps.folder");
		
		drive.files().create(carpetaAnio).execute();
		
		log.info("Carpeta creada: {}, Fecha de creacion: {}, ID: {}", carpetaAnio.getName(), carpetaAnio.getCreatedTime(), carpetaAnio.getId());
		
		
	}
	
}
