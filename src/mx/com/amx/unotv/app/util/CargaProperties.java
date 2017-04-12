package mx.com.amx.unotv.app.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;



import mx.com.amx.unotv.app.dto.ParametrosDTO;

import org.apache.log4j.Logger;

public class CargaProperties {
	
	private Logger logger=Logger.getLogger(CargaProperties.class);
	
	public ParametrosDTO obtenerPropiedades(String properties) {
		ParametrosDTO parametros = new ParametrosDTO();
		try {
			Properties propsTmp = new Properties();
			propsTmp.load(this.getClass().getResourceAsStream( "/general.properties" ));
			String ambiente = propsTmp.getProperty("ambiente");
			String rutaProperties = propsTmp.getProperty(properties.replace("ambiente", ambiente));			
			Properties props = new Properties();
			props.load(new FileInputStream(new File(rutaProperties)));				
			
			parametros.setKey_android(props.getProperty("key_android"));
			parametros.setUrl_gcm(props.getProperty("url_gcm"));
			parametros.setPath_certificate_ios(props.getProperty("path_certificate_ios"));
			parametros.setPassword_certificate_ios(props.getProperty("password_certificate_ios"));
			parametros.setAmbiente(ambiente);
			parametros.setXtarget_url_gcm(props.getProperty("xtarget_url_gcm"));
			parametros.setEstructura_push_json(props.getProperty("estructura_push_json"));
			parametros.setDominio(props.getProperty("dominio"));
			
			//correo
			parametros.setSmtpAutenPassword(props.getProperty("smtpAutenPassword"));
			parametros.setSmtpAutenUserName(props.getProperty("smtpAutenUserName"));
			parametros.setSmtpsender(props.getProperty("smtpsender"));
			parametros.setSmtpserver(props.getProperty("smtpserver"));
			parametros.setAsuntoCorreo(props.getProperty("asuntoCorreo"));
			parametros.setDestinatarios(props.getProperty("destinatarios"));
			
			//ionic
			parametros.setUrl_ionic(props.getProperty("url_ionic"));
			parametros.setXtarget_ionic(props.getProperty("xtarget_ionic"));
			parametros.setAutorization_ionic(props.getProperty("autorization_ionic"));
			
			parametros.setPath_notificacion_json(props.getProperty("path_notificacion_json"));
			parametros.setNombre_notificacion_json(props.getProperty("nombre_notificacion_json"));
		} catch (Exception ex) {
			parametros = new ParametrosDTO();
			logger.error("No se encontro el Archivo de propiedades: ", ex);
		}
		return parametros;
    }

	
}
