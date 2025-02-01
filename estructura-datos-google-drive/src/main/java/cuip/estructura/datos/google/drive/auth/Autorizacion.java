/**
 * 
 */
package cuip.estructura.datos.google.drive.auth;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

/**
 * @author tcruz
 *
 */
public class Autorizacion {

	private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(Autorizacion.class);

	/**
	 * Application name.
	 */
	private static final String APPLICATION_NAME = "prueba-carga-archivos";

	/**
	 * Global instance of the JSON factory.
	 */
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

	/**
	 * Directory to store authorization tokens for this application.
	 */
	private static final String TOKENS_DIRECTORY_PATH = "tokens";

	/**
	 * Global instance of the scopes required by this quickstart. If modifying these
	 * scopes, delete your previously saved tokens/ folder.
	 */
	private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_FILE);

	private static final String CREDENTIALS_FILE_PATH = "credenciales.json";

	public Drive login() throws RuntimeException {
		
		try {
			
			// Build a new authorized API client service.
			final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			

			// Load pre-authorized user credentials from the environment.
		    // TODO(developer) - See https://developers.google.com/identity for
		    // guides on implementing OAuth2 for your application.
			Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
					.setApplicationName(APPLICATION_NAME).build();
			
//		    GoogleCredentials credentials = GoogleCredentials.getApplicationDefault()
//		        .createScoped(Arrays.asList(DriveScopes.DRIVE_FILE));
//		    HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(
//		        credentials);
//		    
//		 // Build a new authorized API client service.
//		    Drive drive = new Drive.Builder(new NetHttpTransport(),
//		        GsonFactory.getDefaultInstance(),
//		        requestInitializer)
//		        .setApplicationName(APPLICATION_NAME)
//		        .build();
		    
			log.info("login correcto");
			
			return drive;
			
		}catch (Exception e) {
			
			log.info("error al crear el login");
			throw new RuntimeException(e);
		}

		

	}

	/**
	 * Creates an authorized Credential object.
	 *
	 * @param HTTP_TRANSPORT The network HTTP Transport.
	 * @return An authorized Credential object.
	 * @throws IOException If the credentials.json file cannot be found.
	 */
	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {

		log.info("creando login");
		
		// Load client secrets.
		Path pathCredenciales = Paths.get("src", "main", "resources", CREDENTIALS_FILE_PATH);
		InputStream in = Files.newInputStream(pathCredenciales);

		if (in == null) {
			throw new FileNotFoundException("Credenciales no encontradas: " + CREDENTIALS_FILE_PATH);
		}

		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES)
						.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
						.setAccessType("offline").build();

		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

		Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

		// returns an authorized Credential object.
		return credential;
	}

}
