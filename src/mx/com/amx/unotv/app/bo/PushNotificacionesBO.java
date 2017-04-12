package mx.com.amx.unotv.app.bo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;

import mx.com.amx.unotv.app.bo.exception.PushNotificacionesBOException;
import mx.com.amx.unotv.app.dto.ElementoPushDTO;
import mx.com.amx.unotv.app.dto.HistoricalPushDTO;
import mx.com.amx.unotv.app.dto.ParametrosDTO;
import mx.com.amx.unotv.app.dto.PushDTO;
import mx.com.amx.unotv.app.dto.RespuestaElementosPushDTO;
import mx.com.amx.unotv.app.dto.WrapperPushDTO;
import mx.com.amx.unotv.app.dto.response.RespuestaBooleanDTO;
import mx.com.amx.unotv.app.util.CargaProperties;
import mx.com.amx.unotv.app.util.EnviaCorreo;

@Component
@Qualifier("pushNotificacionesBO")
public class PushNotificacionesBO {
	
	private static Logger log=Logger.getLogger(PushNotificacionesBO.class);
	private final Properties props = new Properties();
	private static int contadorErrorAndroid;
	private static int contadorSuccessAndroid;
	private static int contadorErrorIOS;
	private static int contadorSuccessIOS;
	String URL_WS_BASE = "";

	private RestTemplate restTemplate;
	HttpHeaders headers = new HttpHeaders();
	
	public PushNotificacionesBO() {
		super();
		restTemplate = new RestTemplate();
		ClientHttpRequestFactory factory = restTemplate.getRequestFactory();
		
		if ( factory instanceof SimpleClientHttpRequestFactory) {
			((SimpleClientHttpRequestFactory) factory).setConnectTimeout( 35 * 1000 );
			((SimpleClientHttpRequestFactory) factory).setReadTimeout( 35 * 1000 );
		
		} else if ( factory instanceof HttpComponentsClientHttpRequestFactory) {
			((HttpComponentsClientHttpRequestFactory) factory).setReadTimeout( 35 * 1000);
			((HttpComponentsClientHttpRequestFactory) factory).setConnectTimeout( 35 * 1000);
		}
		
		restTemplate.setRequestFactory( factory );
		headers.setContentType(MediaType.APPLICATION_JSON);
	      
		try {
			props.load( this.getClass().getResourceAsStream( "/general.properties" ) );						
		} catch(Exception e) {
			log.error("[ConsumeWS:init]Error al iniciar y cargar arhivo de propiedades." + e.getMessage());
		}		
		URL_WS_BASE = props.getProperty(props.getProperty( "ambiente" )+".urlws");
	}
	
	public String saveJSONVio(String jsonRecibido, ParametrosDTO parametrosDTO){
		String respuesta="SUCCESS";
		try {
			JSONObject jsonRecibidoObject=new JSONObject(jsonRecibido);
			FileWriter fw = new FileWriter(parametrosDTO.getPath_notificacion_json() + "transmissions.json");
			fw.write(jsonRecibidoObject.toString());
			fw.flush();
			fw.close();
		} catch (Exception e) {
			log.error("saveJSONVio: ",e);
			respuesta="ERROR";
		}
		 return respuesta;
	}
	
	public String sendNotificationPushIonic(String jsonRecibido,String usuario, ParametrosDTO parametrosDTO){
		String respuesta="";
		try {
				log.info("jsonRecibido: "+jsonRecibido);
				log.info("usuario: "+usuario);
	            /*URL url = new URL(parametrosDTO.getUrl_ionic());
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            
	            if(parametrosDTO.getAmbiente().equalsIgnoreCase("produccion")){
					conn.setRequestProperty("X-Target", parametrosDTO.getXtarget_ionic());
	            }
	            
	            conn.setRequestProperty("Accept-Charset", "UTF-8");
	            conn.setRequestProperty("Authorization", parametrosDTO.getAutorization_ionic());
	            conn.setRequestProperty("Content-Type", "application/json");
	            conn.setRequestMethod("POST");
	            conn.setDoOutput(true);
	            
	            log.info("jsonRecibido: "+jsonRecibido);
		        
	            // Send GCM message content.
	            OutputStream outputStream = conn.getOutputStream();
	            outputStream.write(jsonRecibido.toString().getBytes());
	
	            // Read GCM response.
	            InputStream inputStream = conn.getInputStream();
	            respuesta = IOUtils.toString(inputStream);
	            log.info("Respuesta: "+respuesta);
	            boolean res=false;
	            try {
	            	res=saveJSON(jsonRecibido, parametrosDTO);
		            log.info("Json "+parametrosDTO.getPath_notificacion_json()+parametrosDTO.getNombre_notificacion_json()+" guardado: "+res);
				} catch (Exception e) {
					log.error(e.getLocalizedMessage());
				}
	            */
				boolean res=false;
	            try {
	            	//String auxi=jsonRecibido.replace("\"{","{").replace("}\"", "}");
	            	
	            	//log.info("Auxi: "+auxi);
	            	JSONObject jsonObject = new JSONObject(jsonRecibido);
	    			
	    			JSONObject notificacion=(JSONObject) jsonObject.get("notification");
	    			String msj=(String) notificacion.get("message");
	    			log.info("Mensaje: "+msj);
	    			
	    			JSONObject payload=(JSONObject) notificacion.get("payload");
	    			String url_nota=(String) payload.get("noticia");
	    			url_nota=url_nota.substring(8,url_nota.length()-2);
	    			
	    			log.info("url_nota: "+url_nota);
	    			
	            	HistoricalPushDTO historicalPush=new HistoricalPushDTO();
	            	historicalPush.setUsuario(usuario);
	            	historicalPush.setMensaje_push(msj);
	            	historicalPush.setUrl_nota(url_nota);
	            	res=saveHistoricalPush(historicalPush);
	            	log.info("saveHistoricalPush: "+res);
				} catch (Exception e) {
					log.error(e.getLocalizedMessage());
				}
			}catch (Exception e) {
				log.error("Error en sendNotificationPushIonic[BO]: ",e );
			}
		return respuesta;
	}

	public RespuestaBooleanDTO sendNotificationPush(WrapperPushDTO wrapperPushDTO ) throws PushNotificacionesBOException{
		RespuestaBooleanDTO respuestaBooleanDTO=new RespuestaBooleanDTO();
		try {
			String compania=wrapperPushDTO.getListPush().get(0).getCompania().toLowerCase();
			String sistema_operativo=wrapperPushDTO.getListPush().get(0).getSistema_operativo();
			String tipo_news=wrapperPushDTO.getListPush().get(0).getTipo_news();
			
			List<ElementoPushDTO> listElementosAndroid=null;
			List<ElementoPushDTO> listElementosIOS=null;
			CargaProperties cargaProperties=new CargaProperties();
			ParametrosDTO parametrosDTO=cargaProperties.obtenerPropiedades("ambiente.resources.properties");
			
			
			
			if(sistema_operativo.equalsIgnoreCase("todos")){
				listElementosAndroid=getElementosPushToSend(compania, "android",tipo_news );
				log.info("listElementosAndroid.size: "+listElementosAndroid.size());
				
				listElementosIOS=getElementosPushToSend(compania, "ios",tipo_news);
				log.info("listElementosIOS.size: "+listElementosIOS.size());
				
				for (PushDTO pushDTO : wrapperPushDTO.getListPush()) {
					
					if (listElementosAndroid!= null && listElementosAndroid.size()>0) {
						for (ElementoPushDTO elementoAndroid : listElementosAndroid) {
							try {
								sendNotificacionAndroid(pushDTO, parametrosDTO, elementoAndroid.getId_sender());
							} catch (Exception e) {
								log.error("Error sendNotificacionAndroid[For]: "+e.getClass()+"|"+e.getLocalizedMessage());
								continue;
							}
						}
					}
					
					if (listElementosIOS!= null && listElementosIOS.size()>0) {
						for (ElementoPushDTO elementoIOS : listElementosIOS) {
							sendNotificacionIOS(pushDTO, parametrosDTO, elementoIOS.getId_sender());
						}
					}
				}
			}else if(sistema_operativo.equalsIgnoreCase("android")){
				listElementosAndroid=getElementosPushToSend(compania, "android", tipo_news);
				log.info("listElementosAndroid.size: "+listElementosAndroid.size());
				log.info("Iniciando la llamada");
				log.info(new Date().toString());
				for (PushDTO pushDTO : wrapperPushDTO.getListPush()) {
					
					if (listElementosAndroid!= null && listElementosAndroid.size()>0) {
						for (ElementoPushDTO elementoAndroid : listElementosAndroid) {
							try {
								sendNotificacionAndroid(pushDTO, parametrosDTO, elementoAndroid.getId_sender());
							} catch (Exception e) {
								log.error("Error sendNotificacionAndroid[For]: "+e.getClass()+"|"+e.getLocalizedMessage());
								continue;
							}
						}
					}
					
				}
				log.info("Terminando la llamada");
				log.info(new Date().toString());
				log.info("Enviando Email...");
				EnviaCorreo enviaCorreo=new EnviaCorreo();
				log.info(enviaCorreo.cuerpoCorreo("Test del correo", contadorSuccessAndroid, contadorErrorAndroid, contadorSuccessIOS, contadorErrorIOS));
				//enviaCorreo.sendMail(wrapperPushDTO, contadorSuccessAndroid, contadorErrorAndroid, contadorSuccessIOS, contadorErrorIOS);
			}else if(sistema_operativo.equalsIgnoreCase("ios")){
				listElementosIOS=getElementosPushToSend(compania, "ios", tipo_news);
				log.info("listElementosIOS.size: "+listElementosIOS.size());
				for (PushDTO pushDTO : wrapperPushDTO.getListPush()) {
					if (listElementosIOS!= null && listElementosIOS.size()>0) {
						for (ElementoPushDTO elementoIOS : listElementosIOS) {
							sendNotificacionIOS(pushDTO, parametrosDTO, elementoIOS.getId_sender());
						}
					}
				}
			}
			respuestaBooleanDTO.setCausa_error("");
			respuestaBooleanDTO.setCodigo("0");
			respuestaBooleanDTO.setMensaje("OK");
			respuestaBooleanDTO.setResultado(true);
		} catch (Exception e) {
			log.error("Exception en sendNotificationPush: " + e.getMessage() );
			throw new PushNotificacionesBOException(e.getMessage(), e);
		}
		return respuestaBooleanDTO;
	}
	
	private boolean saveHistoricalPush(HistoricalPushDTO historicalPushDTO){
		boolean respuesta=false;
		String URL_WS_BASE="";
		Properties props=new Properties();
		try {
			props.load(PushNotificacionesBO.class.getResourceAsStream("/general.properties"));
			String ambiente=props.getProperty("ambiente");
			URL_WS_BASE=props.getProperty(ambiente+".url.ws.utils");
			String URL_WS=URL_WS_BASE+"pushController/saveHistoricalPush";
			log.info("URL_WS: "+URL_WS);
			HttpEntity<HistoricalPushDTO> entity = new HttpEntity<HistoricalPushDTO>( historicalPushDTO );
			respuesta=restTemplate.postForObject(URL_WS, entity, Boolean.class);
		} catch (Exception e) {
			log.error("Error saveHistoricalPush: ",e);
		}
		return respuesta;
	}
	
	private List<ElementoPushDTO> getElementosPushToSend(String id_compania, String sistema_operativo, String tipo_news) throws PushNotificacionesBOException{
		
		String metodo="appPushNotificacionesController/obtieneElementosPush";
		String URL_WS=URL_WS_BASE+metodo;
		RespuestaElementosPushDTO respuestaElementosPushDTO=new RespuestaElementosPushDTO();
		try {
			
			log.info("URL_WS: "+URL_WS);
			log.info("id_compania: "+id_compania);
			log.info("sistema_operativo: "+sistema_operativo);
			log.info("tipo_news: "+tipo_news);
			
			MultiValueMap<String, Object> parts;
			parts = new LinkedMultiValueMap<String, Object>();
			parts.add("id_compania", id_compania);
			parts.add("sistema_operativo", sistema_operativo);
			parts.add("tipo_news", tipo_news);
			
			//ElementoPushDTO[] arrayNotasRecibidas = restTemplate.postForObject(URL_WS,parts, ElementoPushDTO[].class);
			respuestaElementosPushDTO=restTemplate.postForObject(URL_WS, parts, RespuestaElementosPushDTO.class);
			if(respuestaElementosPushDTO.getListElementos()!=null)
				return respuestaElementosPushDTO.getListElementos();
			else
				throw new PushNotificacionesBOException("Lista de elementos push vacia");
			
		} catch (Exception e) {
			log.error("Exception en getElementosPushToSend: " + e.getMessage() );
			throw new PushNotificacionesBOException(e.getMessage(), e);
		}
	}
	
	private boolean sendNotificacionIOS(PushDTO pushDTO, ParametrosDTO parametrosDTO, String sender_id){
		boolean respuesta=false;
		try {
			 String estructuraJson=parametrosDTO.getEstructura_push_json();
			 /*StringBuffer sb = new StringBuffer();
	   		 sb.append("{ ");
	   		 sb.append("\"id_seccion\": \"\",");
	   		 sb.append("\"id_noticia\":\""+pushDTO.getId_noticia()+"\", ");    		 
	   		 sb.append("\"titulo\":\""+pushDTO.getTitulo()+"\", ");
	   		 sb.append("\"descripcion\":\""+pushDTO.getDescripcion()+"\", ");
	   		 sb.append("\"url_imagen\":\"URL de la imagen correspondiente a la noticia\"");    		 
	   		 sb.append("}");*/
			 estructuraJson=estructuraJson.replace("%ID_TAG%", pushDTO.getId_tag());
			 estructuraJson=estructuraJson.replace("%ID_CONTENIDO%", pushDTO.getId_contenido());
			 estructuraJson=estructuraJson.replace("%TITULO_CONTENIDO%", pushDTO.getTitulo());
			 estructuraJson=estructuraJson.replace("%DESCRIPCION_CONTENIDO%", pushDTO.getDescripcion());
			 estructuraJson=estructuraJson.replace("%FECHA_PUBLICACION%", pushDTO.getFecha_publicacion());
			 estructuraJson=estructuraJson.replace("%TIPO_CONTENIDO%", pushDTO.getId_tipo_nota());
			 estructuraJson=estructuraJson.replace("%URL_IMAGEN_PRINCIPAL%", pushDTO.getImagen_principal());
			 estructuraJson=estructuraJson.replace("%URL_IMAGEN_MINIATURA%", pushDTO.getImagen_miniatura());
			 estructuraJson=estructuraJson.replace("%ID_CATEGORIA%", pushDTO.getId_categoria());
	   		 
			 StringBuffer payload = new StringBuffer();
			 payload.append("{");
			 payload.append("	\"aps\": {");
			 payload.append("		\"alert\": {");
			 payload.append("			\"action-loc-key\": \"PLAY\",");
			 payload.append("			\"body\": \""+pushDTO.getTitulo()+"\",");
			 payload.append("			\"noticia\": {");
			 payload.append("				\"id_tag\": \"\",");
			 payload.append("				\"id_seccion\": \""+pushDTO.getId_categoria()+"\",");
			 payload.append("				\"id_noticia\": \""+pushDTO.getId_contenido()+"\",");
			 payload.append("				\"titulo\": \""+pushDTO.getTitulo()+"\",");
			 payload.append("				\"descripcion\": \""+pushDTO.getDescripcion()+"\"");
			 payload.append("			}");
			 payload.append("		},");
			 payload.append("			\"sound\": \"default\"");
			 payload.append("	}");
			 payload.append("}");
	   		 log.info("Estructura JSON: "+payload);
	   		 
	   		ApnsService service = null;
	   		
	   		if (parametrosDTO.getAmbiente().equalsIgnoreCase("produccion")) {
	   			service =
		   			     APNS.newService()
		   			     .withCert(parametrosDTO.getPath_certificate_ios(), parametrosDTO.getPassword_certificate_ios())
		   			     .withProductionDestination()
		   			     .build();
				
			}else{
				service =
		   			     APNS.newService()
		   			     .withCert(parametrosDTO.getPath_certificate_ios(), parametrosDTO.getPassword_certificate_ios())
		   			     .withProductionDestination()
		   			     .build();
			}

	   		service.push(sender_id, payload.toString());	
	   		log.info("Sending push notification IOS OK");
		        respuesta=true;
		} catch (Exception e) {
			log.error("Exception en sendNotificacionIOS: ", e );
			return false;
		}
		return respuesta;
	}

	private boolean sendNotificacionAndroid(PushDTO pushDTO, ParametrosDTO parametrosDTO, String sender_id){
		boolean respuesta=false;
		try {
				JSONObject jGcmData = new JSONObject();
				
				String estructuraJson=parametrosDTO.getEstructura_push_json();
				
				estructuraJson=estructuraJson.replace("%ID_TAG%", pushDTO.getId_tag());
				 estructuraJson=estructuraJson.replace("%ID_CONTENIDO%", pushDTO.getId_contenido());
				 estructuraJson=estructuraJson.replace("%TITULO_CONTENIDO%", quote(pushDTO.getTitulo()));
				 estructuraJson=estructuraJson.replace("%DESCRIPCION_CONTENIDO%", quote(pushDTO.getDescripcion()));
				 estructuraJson=estructuraJson.replace("%FECHA_PUBLICACION%", pushDTO.getFecha_publicacion());
				 estructuraJson=estructuraJson.replace("%TIPO_CONTENIDO%", pushDTO.getId_tipo_nota());
				 estructuraJson=estructuraJson.replace("%URL_IMAGEN_PRINCIPAL%", pushDTO.getImagen_principal());
				 estructuraJson=estructuraJson.replace("%URL_IMAGEN_MINIATURA%", pushDTO.getImagen_miniatura());
				 estructuraJson=estructuraJson.replace("%ID_CATEGORIA%", pushDTO.getId_categoria());
		   		 
		   		//log.info("Estructura JSON: "+estructuraJson);
	            JSONObject jData = new JSONObject(estructuraJson);
	            
	            jGcmData.put("to",sender_id);
	            
	            jGcmData.put("data", jData);
	    		
	            URL url = new URL(parametrosDTO.getUrl_gcm());
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            
	            if(parametrosDTO.getAmbiente().equalsIgnoreCase("produccion")){
					conn.setRequestProperty("X-Target", parametrosDTO.getXtarget_url_gcm());					
	            }
	            
	            conn.setRequestProperty("Authorization", "key="+parametrosDTO.getKey_android());
	            conn.setRequestProperty("Content-Type", "application/json");
	            conn.setRequestMethod("POST");
	            conn.setDoOutput(true);
	
	            // Send GCM message content.
	            OutputStream outputStream = conn.getOutputStream();
	            outputStream.write(jGcmData.toString().getBytes());
	
	            // Read GCM response.
	            InputStream inputStream = conn.getInputStream();
	            String respuestaAndroid = IOUtils.toString(inputStream);
	        		
	        	try {
        			JSONObject respuestaJSON=new JSONObject(respuestaAndroid);
        			
        			if(respuestaJSON.get("failure").toString().equals("1"))
        				contadorErrorAndroid++;
        			if(respuestaJSON.get("success").toString().equals("1"))
        				contadorSuccessAndroid++;
	        	} catch (Exception e) {
	        			log.error("Error al parsear la respuesta: "+e.getLocalizedMessage());
	        			contadorErrorAndroid++;
	        	}
	        	
	            //log.info("Respuesta sendNotificacionAndroid: "+resp);
	            respuesta=true;
			} catch (MalformedURLException e) {
				log.error("Exception en sendNotificacionAndroid[MalformedURLException]: ",e );
				contadorErrorAndroid++;
				return false;
			} catch (IOException e) {
				log.error("Exception en sendNotificacionAndroid[IOException]: ",e );
				contadorErrorAndroid++;
				return false;
			} catch (Exception e) {
				log.error("Exception en sendNotificacionAndroid[Exception]: ",e );
				contadorErrorAndroid++;
				return false;
			}
		return respuesta;
	}
	
	private boolean saveJSON(String jsonRecibido, ParametrosDTO parametrosDTO) throws Exception{
		boolean success=false;
		try {
		        JSONObject jsonRecibidoObject=new JSONObject(jsonRecibido);
		        TimeZone tz = TimeZone.getTimeZone("America/Mexico_City");
				//DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
				df.setTimeZone(tz);
				Date date = new Date();
				String fechaS=df.format(date);
				fechaS=fechaS.substring(0, fechaS.length()-2)+":00";
		        //TimeZone tz = TimeZone.getTimeZone("CST");
				//DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
				//df.setTimeZone(tz);
				//Date date = new Date();
		        jsonRecibidoObject.put("fecha", fechaS);
		        success=true;
		        if (success) {
					FileWriter fw = new FileWriter(parametrosDTO.getPath_notificacion_json() + parametrosDTO.getNombre_notificacion_json());
					fw.write(jsonRecibidoObject.toString());
					fw.flush();
					fw.close();
				}
			      
			}catch (Exception e) {
				log.error("Error en saveJSON [BO]: ",e );
			}
		return success;
		
	}
	
	 private static String quote(String string) {
		 
         if (string == null || string.length() == 0) {
             return "\"\"";
         }
         string=string.replace("\n", "").replace("\b", "").replace("\t", "").trim();
         char         c = 0;
         int          i;
         int          len = string.length();
         StringBuilder sb = new StringBuilder(len + 4);
         String       t;

         sb.append('"');
         for (i = 0; i < len; i += 1) {
             c = string.charAt(i);
             switch (c) {
             case '\\':
             case '"':
                 sb.append('\\');
                 sb.append(c);
                 break;
             case '/':
 //                if (b == '<') {
                     sb.append('\\');
 //                }
                 sb.append(c);
                 break;
             case '\b':
                 sb.append("\\b");
                 break;
             case '\t':
                 sb.append("\\t");
                 break;
             case '\n':
                 sb.append("\\n");
                 break;
             case '\f':
                 sb.append("\\f");
                 break;
             case '\r':
                sb.append("\\r");
                break;
             default:
                 if (c < ' ') {
                     t = "000" + Integer.toHexString(c);
                     sb.append("\\u" + t.substring(t.length() - 4));
                 } else {
                     sb.append(c);
                 }
             }
         }
         sb.append('"');
         return sb.toString();
     }
	
	
}
