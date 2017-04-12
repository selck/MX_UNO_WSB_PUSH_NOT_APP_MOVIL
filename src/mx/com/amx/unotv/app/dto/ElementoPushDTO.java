package mx.com.amx.unotv.app.dto;

import java.io.Serializable;

public class ElementoPushDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id_usuario;
	private String id_sender;
	private String sistema_operativo;
	
	/**
	 * @return the id_usuario
	 */
	public String getId_usuario() {
		return id_usuario;
	}
	/**
	 * @param id_usuario the id_usuario to set
	 */
	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}
	/**
	 * @return the id_sender
	 */
	public String getId_sender() {
		return id_sender;
	}
	/**
	 * @param id_sender the id_sender to set
	 */
	public void setId_sender(String id_sender) {
		this.id_sender = id_sender;
	}
	/**
	 * @return the sistema_operativo
	 */
	public String getSistema_operativo() {
		return sistema_operativo;
	}
	/**
	 * @param sistema_operativo the sistema_operativo to set
	 */
	public void setSistema_operativo(String sistema_operativo) {
		this.sistema_operativo = sistema_operativo;
	}
	
	
}