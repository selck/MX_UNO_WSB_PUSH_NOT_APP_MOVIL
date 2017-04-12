package mx.com.amx.unotv.app.controller;

import mx.com.amx.unotv.app.bo.PushNotificacionesBO;
import mx.com.amx.unotv.app.dto.ParametrosDTO;
import mx.com.amx.unotv.app.dto.WrapperPushDTO;
import mx.com.amx.unotv.app.dto.response.RespuestaBooleanDTO;
import mx.com.amx.unotv.app.util.CargaProperties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@RequestMapping("pushNotificacionesController")
public class PushNotificacionesController {
	
	protected static final Logger log=Logger.getLogger(PushNotificacionesController.class);
	private PushNotificacionesBO pushNotificacionesBO;
	
	@RequestMapping(value={"sendNotificationPush"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	public RespuestaBooleanDTO sendNotificationPush( @RequestBody WrapperPushDTO wrapperPushDTO){
		RespuestaBooleanDTO respuestaDTO = new RespuestaBooleanDTO();
		try{
			log.info("sendNotificationPush");
			respuestaDTO = pushNotificacionesBO.sendNotificationPush(wrapperPushDTO);
		} catch (Exception e){
			log.error(" Error pushNotificacionesController [sendNotificationPush]:", e);
			respuestaDTO=new RespuestaBooleanDTO();
			respuestaDTO.setCodigo("-1");
			respuestaDTO.setMensaje(e.getMessage());
			respuestaDTO.setCausa_error(e.getCause().toString());
			respuestaDTO.setResultado(false);
			return respuestaDTO;
		}
		return respuestaDTO;
	}
	
	@RequestMapping(value={"sendNotificationPushIonic"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	public String sendNotificationPushIonic( @RequestParam("json") String json, @RequestParam("usuario") String usuario){
		String respuesta="";
		try{
			log.info("sendNotificationPushIonic");
			CargaProperties cargaProperties=new CargaProperties();
			ParametrosDTO parametrosDTO=cargaProperties.obtenerPropiedades("ambiente.resources.properties");
			respuesta = pushNotificacionesBO.sendNotificationPushIonic(json,usuario, parametrosDTO);
		} catch (Exception e){
			log.error(" Error pushNotificacionesController [sendNotificationPushIonic]:", e);
		}
		return respuesta;
	}
	
	@RequestMapping(value={"saveJSONVio"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	public String saveJSONVio( @RequestBody String json){
		String respuesta="";
		try{
			log.info("saveJSONVio");
			CargaProperties cargaProperties=new CargaProperties();
			ParametrosDTO parametrosDTO=cargaProperties.obtenerPropiedades("ambiente.resources.properties");
			respuesta = pushNotificacionesBO.saveJSONVio(json, parametrosDTO);
		} catch (Exception e){
			log.error(" Error pushNotificacionesController [saveJSONVio]:", e);
		}
		return respuesta;
	}
	
	/**
	 * @return the pushNotificacionesBO
	 */
	public PushNotificacionesBO getPushNotificacionesBO() {
		return pushNotificacionesBO;
	}

	/**
	 * @param pushNotificacionesBO the pushNotificacionesBO to set
	 */
	@Autowired
	public void setPushNotificacionesBO(PushNotificacionesBO pushNotificacionesBO) {
		this.pushNotificacionesBO = pushNotificacionesBO;
	}
	
	
}
