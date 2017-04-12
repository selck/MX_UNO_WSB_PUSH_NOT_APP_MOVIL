package mx.com.amx.unotv.app.dto.response;

import java.io.Serializable;

public class RespuestaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String mensaje;
	private String codigo;
	private String causa_error;
	
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
	
	@Override
	public String toString() {
		String NEW_LINE = System.getProperty("line.separator");
		StringBuffer result=new StringBuffer();
		result.append(" [Begin of Class] " + NEW_LINE);
		result.append(this.getClass().getName() + " Object {" + NEW_LINE);
		result.append(" mensaje: _" + this.getMensaje() + "_" + NEW_LINE);
		result.append(" codigo: _" + this.getCodigo() + "_" + NEW_LINE);
		result.append(" [End of Class] " + NEW_LINE);
		result.append("}");
		NEW_LINE = null;
		return result.toString();
	}
}
