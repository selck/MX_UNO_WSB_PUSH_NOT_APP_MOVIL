package mx.com.amx.unotv.app.dto;

import java.io.Serializable;
import java.util.List;

public class WrapperPushDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private List<PushDTO> listPush;
	
	/**
	 * @return el listPush
	 */
	public List<PushDTO> getListPush() {
		return listPush;
	}
	/**
	 * @param listPush el listPush a establecer
	 */
	public void setListPush(List<PushDTO> listPush) {
		this.listPush = listPush;
	}
	
	
}
