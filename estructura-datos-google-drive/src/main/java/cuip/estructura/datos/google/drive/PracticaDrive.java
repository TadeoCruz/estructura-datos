/**
 * 
 */
package cuip.estructura.datos.google.drive;

import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;

import cuip.estructura.datos.google.drive.service.PracticaDriveService;

/**
 * Clase principal para ejecutar la aplicacion
 * @author tcruz
 *
 */
public class PracticaDrive {
	
	/**
	 * Variable para log de informacion
	 */
	private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(PracticaDrive.class);

	/**
	 * @param args
	 * @throws GeneralSecurityException 
	 */
	public static void main(String[] args) {

		try {
			
			PracticaDriveService driveService = new PracticaDriveService();
			
			driveService.listarArchivos();
			
			driveService.cargarArchivos();
			
			
		} catch (Exception e) {
			log.error(e);
		}
	}

}
