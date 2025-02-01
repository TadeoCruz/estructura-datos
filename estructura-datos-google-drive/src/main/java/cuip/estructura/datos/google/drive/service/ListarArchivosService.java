/**
 * 
 */
package cuip.estructura.datos.google.drive.service;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

/**
 * @author tcruz
 *
 */
public class ListarArchivosService {

	private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(ListarArchivosService.class);

	private Drive drive;

	/**
	 * @param drive
	 */
	public ListarArchivosService(Drive drive) {
		super();
		this.drive = drive;
	}

	public boolean listarArchivos() throws IOException {

		FileList result = drive.files().list().setPageSize(5).setOrderBy("modifiedTime")
				.setFields("nextPageToken, files(id, name, originalFilename, mimeType, createdTime, properties, parents, spaces, labelInfo, owners, driveId)").execute();

		log.info("Fields Drive: {}", String.join(",", result.keySet()));

//				Listar archivos
		List<File> files = result.getFiles();
//		log.info(result.getNextPageToken());

		files.forEach(file -> {

			try {
				
				log.info("Archivo. Nombre:{}, Nombre Original: {}, Fecha de creacion: {}, Id:{}", 
						file.getName(),
						file.getOriginalFilename(),
						file.getCreatedTime(),
						file.getId());
				
				log.info("Extencion: {}, Spaces: {}, Parents: {}", file.getMimeType(), String.join(",", file.getSpaces()), file.getParents() );
				
				if( file.getProperties() != null ) {
					file.getProperties().forEach( (k,v) -> log.info("Propiedad: {}, Valor: {}", k, v) );					
				}
				
			}catch (Exception e) {
				log.error("Error al recuperar informacion del archivo.", e);
			}
			

		});

		return true;
	}

}
