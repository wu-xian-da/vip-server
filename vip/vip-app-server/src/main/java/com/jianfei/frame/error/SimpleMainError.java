
package com.jianfei.frame.error;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 功能说明：
 * </pre>
 *
 * @author 陈雄华
 * @version 1.0
 */
@XmlRootElement(name = "mainError")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class SimpleMainError implements MainError {

	@XmlElement
    private int code;
	@XmlElement
    private String message;
	@XmlElement
    private String solution;

    private List<SubError> subErrors = new ArrayList<SubError>();
    
    public SimpleMainError() {
	}

	public SimpleMainError(int code, String message, String solution) {
        this.code = code;
        this.message = message;
        this.solution = solution;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getSolution() {
        return solution;
    }

    public List<SubError> getSubErrors() {
        return this.subErrors;
    }

    public void setSubErrors(List<SubError> subErrors) {
        this.subErrors = subErrors;
    }

    public MainError addSubError(SubError subError) {
        this.subErrors.add(subError);
        return this;
    }

	@Override
	public String toString() {
		return "MainError [code=" + code + ", message=" + message + "]";
	}
}

