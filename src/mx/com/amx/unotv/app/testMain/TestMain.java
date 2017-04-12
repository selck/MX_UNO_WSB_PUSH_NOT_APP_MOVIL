package mx.com.amx.unotv.app.testMain;

import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONObject;

public class TestMain {
	public static void main(String [] args){
		String respuesta="";
		String url_a_conectar = "http://api.ionic.io/push/notifications";
		try {
			
			//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//fecha=formatter.format(date);
			boolean success=true;
			
			  StringBuffer jsonRecibido=new StringBuffer();
		        jsonRecibido.append("{ \n");
		        jsonRecibido.append("	\"message\": \"test mamá\", \n");
		        jsonRecibido.append("	\"ios\": { \n");
		        jsonRecibido.append("		\"message\": \"mamá pingüino ¿? ¡! ñoño Barça \" \n");
		        jsonRecibido.append("	}, \n");
		        jsonRecibido.append("	\"android\": { \n");
		        jsonRecibido.append("		\"title\": \"Uno TV mamá\", \n");
		        jsonRecibido.append("		\"message\": \"Hola chic@s desde México para el mundo!\" \n");
		        jsonRecibido.append("	} \n");
		        jsonRecibido.append("} \n");
		        JSONObject jsonRecibidoObject=new JSONObject(jsonRecibido.toString());
		        
		        TimeZone tz = TimeZone.getTimeZone("America/Mexico_City");
				//DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		        
				df.setTimeZone(tz);
				
				Date date = new Date();
				String fechaS=df.format(date);
				fechaS=fechaS.substring(0, fechaS.length()-2)+":00";
				System.out.println(fechaS);
		        jsonRecibidoObject.put("fecha", fechaS);
		        
		        System.out.println(jsonRecibidoObject);
		        if (success) {
					FileWriter fw = new FileWriter("C:/pruebas/json/notificacion.json");
					fw.write(jsonRecibidoObject.toString());
					fw.flush();
					fw.close();
				}
		        /*
	            URL url = new URL(url_a_conectar);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            
	            conn.setRequestProperty("Accept-Charset", "UTF-8");
	            conn.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiI0YmU2NDY5My1hYzZmLTQ3NjktODRjZS1iOGM0ZTQ2YjhmZGEifQ.ooHtmDIA1RWE5rxtAjkKmDe56Q3WtuqNPFmIUl2JGTI");
	            conn.setRequestProperty("Content-Type", "application/json");
	            conn.setRequestMethod("POST");
	            conn.setDoOutput(true);
	            
	         
		        
		        JSONObject jsonRecibidoObject=new JSONObject(jsonRecibido.toString());
		        
		        JSONObject jsonLlamada = new JSONObject();
		        jsonLlamada.put("send_to_all", true);
		        jsonLlamada.put("profile", "unotvprod");
		        jsonLlamada.put("notification", jsonRecibidoObject);
		        
	            // Send GCM message content.
	            OutputStream outputStream = conn.getOutputStream();
	            outputStream.write(jsonLlamada.toString().getBytes());
	
	            // Read GCM response.
	            InputStream inputStream = conn.getInputStream();
	            respuesta = IOUtils.toString(inputStream);
	            System.out.println("Respuesta: "+respuesta);*/
			}catch (Exception e) {
				System.out.println("Error main: "+e.getLocalizedMessage());
				e.printStackTrace();
			}
	}
	
	
	
	/*public static void mainIOS(String [] args){
	final String pathCertificado1="C:/Users/Jesus/Desktop/Trabajo/Properties/NUBE_WAS_APLICATIVOS/certificado_ios/ProductPush.p12";
	
	try {
		BasicConfigurator.configure();
   		StringBuffer sb_ios = new StringBuffer();
   		sb_ios.append("{");
   		sb_ios.append("	\"aps\": {");
   		sb_ios.append("		\"alert\": {");
   		sb_ios.append("			\"action-loc-key\": \"PLAY\",");
   		sb_ios.append("			\"body\": \"Ola k ase\",");
   		sb_ios.append("			\"noticia\": {");
   		sb_ios.append("				\"id_tag\": \"\",");
   		sb_ios.append("				\"id_seccion\": \"entretenimiento\",");
   		sb_ios.append("				\"id_noticia\": \"033a5a23-1344-492d-8f9e-181abfe8817a\",");
   		sb_ios.append("				\"titulo\": \"Katy Perry sube al escenario a una fan �drogada� \",");
   		sb_ios.append("				\"descripcion\": \"La fan�tica s�lo la abrazaba y besaba.\"");
   		sb_ios.append("			}");
   		sb_ios.append("		},");
   		sb_ios.append("			\"sound\": \"default\"");
   		sb_ios.append("	}");
   		sb_ios.append("}");
   		
   		
   		StringBuffer isa_contenido = new StringBuffer();
   		//isa_contenido.append("			\"action-loc-key\": \"PLAY\", ");
   		//isa_contenido.append("			\"body\": \"Ya existe el puro mas caro y exclusivo del mundo lo hicieron en Cuba\", ");
   		isa_contenido.append("			\"noticia\": { ");
   		isa_contenido.append("				\"id_tag\": \"\", ");
   		isa_contenido.append("				\"id_seccion\": \"negocios\", ");
   		isa_contenido.append("				\"id_noticia\": \"fa37780a-a979-4d8d-bdb2-8fb585c0143d\", ");
   		isa_contenido.append("				\"titulo\": \"Ya existe el puro mas caro y exclusivo del mundo lo hicieron en Cuba\", ");
   		isa_contenido.append("				\"descripcion\": \"Es parte de la mas distinguida de la marca Cohiba.\" ");
   		isa_contenido.append("			} ");
   	
   		
   		System.out.println(isa_contenido);

   		
		String deviceTokenProductivos="c596d31a3997bdc19365efbdd9baf173dcef49dd966d6ea4216424e6ded42cfd|" //Eliss
				+ "5361fa1ddbaca1973fdece897ec5b927c8f8c7f4f74e57b657cb3e36b464ed8e|"//Isai
				+ "48cd475e33842a2a5976a04cbfc4cf4acec2b6cb95371a1776be424696d2253c|"//Tona
				+ "be73c39b007606cfbc0c433dfec02bdc298f91c6f045ce515d5326b546881fce|"// Fer
				+ "66977ed6e3aeb23320c0f67c31e89d185c53ca5e0e87f118f4fbac97a37082db";//Ruth
				//+ "ed76d6de05f574bfc8e4323e83e3a58fa61012e91f9927187beb4ae57852d73d"; //Desconocido	
			
		
		String devideTokenFer="66977ed6e3aeb23320c0f67c31e89d185c53ca5e0e87f118f4fbac97a37082db";
  		ApnsService service =
			     APNS.newService()
			     .withCert(pathCertificado1, "")
			     .withProductionDestination()
			     .build();
	
			String payload =
			    APNS.newPayload()
			    .alertBody("Hola Elisussss")
			    .badge(45)
			    .sound("default")
			    .build();
   			
			
			System.out.println("Sending push notification IOS...");
			service.push(devideTokenFer, payload);
			
			System.out.println("Ok...");
			for (String token : deviceTokenProductivos.split("\\|")) {
				System.out.println("Sending push notification...");
   			service.push(token, payload);
   			System.out.println("Ok...");
   			System.out.println("----------------------------------------");
		}
			
	} catch (Exception e) {
		System.out.println("error main");
		e.printStackTrace();
	}
}*/
	public static void mainChido(String [] args){
		JSONObject jGcmData = new JSONObject();
		
		try {
			System.out.println("Inserta un titulo: ");
			//Scanner reader = new Scanner(System.in);
			//String sTitulo = reader.next();
			String sTitulo="El Tri, a romper otra \n \n \t  \" "+
	"maldicion \"";
			//String x="";
			//x=sTitulo.replace("\n", "").replace("\b", "").replace("\t", "").trim();
			//System.out.println("Titulo del scan: "+x);
			
			//System.out.println("quote(x): "+quote(sTitulo));
			StringBuffer sb = new StringBuffer();
	   		sb.append("{ ");
	   		sb.append("\"id_tag\": \"\",");
	   		sb.append("\"id_contenido\": \"50f04e40-2044-4103-9a67-ab5e03abd74f\",");
	   		//sb.append("\"id_contenido\": \"\",");
	   		//sb.append("\"titulo\": "+quote(sTitulo)+",");
	   		sb.append("\"descripcion\": \"Costo de luz ha disminuido 10% desde 2014 gracias a reforma: Pe�a Desc\",");
	   		sb.append("\"fecha_publicacion\": \"\",");
	   		sb.append("\"id_tipo_nota\": \"video\",");
	   		sb.append("\"imagen_principal\":\"/portal/unotv/imagenes/53246-Principal.jpg\",");
	   		sb.append("\"imagen_miniatura\":\"/portal/unotv/imagenes/53246-Miniatura.jpg\",");
	   		sb.append("\"id_categoria\":\"negocios\"");
	   		sb.append("}");
	   			   		
	        JSONObject jData = new JSONObject(sb.toString());
            
	        String idToken="f0hQT3kuu3g:APA91bGXXzwo15dstAhQ0ER7mnmQj9Uc8yili2A11biZWZIhrWEUNUdfh7nii_tIoA0QTH_1T6sjcEHuJeQBC3y2zeh3gLcbUfZFqd3vk76MDbTWztQZxzE9L9Nyx_816lKnd7cHSjaN";//  chuchotwitter
            
            jGcmData.put("data", jData);
            jGcmData.put("to",idToken);
            
            System.out.println("jGcmData: "+jGcmData);
            
            URL url = new URL("https://gcm-http.googleapis.com/gcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            conn.setRequestProperty("Authorization", "key=AIzaSyBJvj-woMLufjA91ZdarIJx4OxSXjwdyL8");
            
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            
            // Send GCM message content.
            OutputStream outputStream = conn.getOutputStream();
            //outputStream.write(jGcmData.toString().getBytes());
            outputStream.write(jGcmData.toString().getBytes(Charset.forName("UTF-8")));
            // Read GCM response.
            InputStream inputStream = conn.getInputStream();
            String respuestaAndroid = IOUtils.toString(inputStream);
        		
        	try {
    			JSONObject respuestaJSON=new JSONObject(respuestaAndroid);
    			System.out.println(respuestaJSON.toString());
    			System.out.println("Ya veda");
        	} catch (Exception e) {
        			System.out.println("Error al parsear la respuesta: "+e.getLocalizedMessage());
        	}
    			
		} catch (Exception e) {
			System.out.println("Error enviando Push Android");
			e.printStackTrace();
		}
	}
}
