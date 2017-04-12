package mx.com.amx.unotv.app.bo;

import java.util.Scanner;

import org.codehaus.jettison.json.JSONObject;


public class JsonParseTest {

	
	
	public static void main(String[] args) {
		
		StringBuffer json=new StringBuffer();
		json.append("{");
		json.append(" \"send_to_all\": true,");
		json.append(" \"profile\": \"unotvprod\",");
		json.append(" 	\"notification\": {");
		json.append(" 		\"message\": \"EU lanzó decenas de misiles Tomahawk a Siria\",");
		json.append(" 		\"ios\": {");
		json.append(" 			\"message\": \"EU lanzó decenas de misiles Tomahawk a Siria\",");
		json.append(" 			\"sound\": \"default\"");
		json.append(" 		},");
		json.append(" 		\"android\": {");
		json.append(" 			\"title\": \"Uno TV\",");
		json.append(" 			\"message\": \"EU lanzó decenas de misiles Tomahawk a Siria\",");
		json.append(" 			\"sound\": \"default\"");
		json.append(" 		},");
		json.append(" 		\"payload\": {");
		//json.append(" 			\"noticia\": \"algo\" ");
		json.append(" 			\"noticia\": \"{\"url\":\"http://www.unotv.com/noticias/portal/internacional/detalle/eu-lanzo-docenas-de-misiles-tomahawk-siria-096889/\"}\" ");
		json.append(" }");
		json.append(" }");
		json.append(" }");
		
		String auxi=json.toString().replace("\"{","{").replace("}\"", "}");
		
		//System.out.println("Auxi: "+auxi);
		try {
			
			/*JSONObject jsonObject = new JSONObject(auxi);
			
			JSONObject notificacion=(JSONObject) jsonObject.get("notification");
			String msj=(String) notificacion.get("message");
			System.out.println("Mensaje: "+msj);
			
			JSONObject payload=(JSONObject) notificacion.get("payload");
			System.out.println(payload);
			
			JSONObject noticia=(JSONObject) payload.get("noticia");
			System.out.println("url: "+noticia.get("url"));*/
			
			String x="http://www.unotv.com/noticias/portal/internacional/detalle/eu-lanzo-docenas-de-misiles-tomahawk-siria-096889/";
			System.out.println(x.length());
			
				

		}catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}