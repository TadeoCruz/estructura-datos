package cuip.estructura.datos.google.drive.service;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;

import com.google.api.services.drive.Drive;

import cuip.estructura.datos.google.drive.auth.Autorizacion;

/**
 * Clase de servicio para administrar la carga y descarga de archivos a la cuenta de google drive
 * @author tcruz
 *
 */
public class PracticaDriveService {
	
	private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(PracticaDriveService.class);

	
	/**
	 * instancia de clase para interactuar con google drive
	 */
	private Drive drive;
	
	/**
	 * Constructor de la clase
	 */
	public PracticaDriveService() {
		/**
		 * Instancia de clase para autenticarse a google drive
		 */
		Autorizacion autorizacion = new Autorizacion();
		drive = autorizacion.login();
		
		log.info(drive.getBaseUrl());
	}
	
	
	/**
	 * Metodo para listar archivos existentes
	 * @throws IOException 
	 */
	public void listarArchivos() throws IOException {
		
		/**
		 * Instancia de clase para listar los archivos existentes en google drive
		 */
		ListarArchivosService listarArchivos = new ListarArchivosService(drive);
		listarArchivos.listarArchivos();
		
	}

	/**
	 * Metodo para crear nuevos archivos en google drive
	 */
	public void cargarArchivos() {
		
		CargarArchivosService cargarArchivosService = new CargarArchivosService(drive);
		cargarArchivosService.crearArchivos();
		
	}
	
	public void descargarArchivos() {
		
	}
}
