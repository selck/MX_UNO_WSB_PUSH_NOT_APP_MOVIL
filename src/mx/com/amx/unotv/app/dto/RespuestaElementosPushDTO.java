package mx.com.amx.unotv.app.dto;

import mx.com.amx.unotv.app.dto.ElementoPushDTO;

import java.io.Serializable;
import java.util.List;

public class RespuestaElementosPushDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<ElementoPushDTO> listElementos;
	private String mensaje;
	private String codigo;
	private String causa_error;
	
	/**
	 * @return the listElementos
	 */
	public List<ElementoPushDTO> getListElementos() {
		return listElementos;
	}
	/**
	 * @param listElementos the listElementos to set
	 */
	public void setListElementos(List<ElementoPushDTO> listElementos) {
		this.listElementos = listElementos;
	}
	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}
	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return the causa_error
	 */
	public String getCausa_error() {
		return causa_error;
	}
	/**
	 * @param causa_error the causa_error to set
	 */
	public void setCausa_error(String causa_error) {
		this.causa_error = causa_error;
	}

	
	
	

}
