package mx.com.amx.unotv.app.dto.response;

import java.io.Serializable;

public class RespuestaBooleanDTO extends RespuestaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Boolean resultado;
		/**
	 * @return the resultado
	 */
	public Boolean getResultado() {
		return resultado;
	}
	/**
	 * @param resultado the resultado to set
	 */
	public void setResultado(Boolean resultado) {
		this.resultado = resultado;
	}	
	
}
