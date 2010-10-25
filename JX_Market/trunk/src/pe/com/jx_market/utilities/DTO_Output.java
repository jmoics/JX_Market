package pe.com.jx_market.utilities;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DTO_Output extends DTO_Input implements Serializable {
	private int errorCode;
	private String errorMsg;

	public DTO_Output() {
		this.errorMsg = "Motivo de error no especificado";
	}

	public DTO_Output(Serializable obj) {
		super(obj);
	}

	public DTO_Output(Map m) {
		super(m);
	}

	public DTO_Output(List l) {
		super(l);
	}

	public int getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public void setError(int errorCode, String msg) {
		this.errorCode = errorCode;
		this.errorMsg = msg;
	}
}