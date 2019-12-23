package capstone_project.av_service.controller.error;

import java.util.List;

import capstone_project.av_service.constant.ExceptionMessageConstants;


public class BussinessException extends RuntimeException {
    private ErrorMessage errorMessage;

    public BussinessException(String errorMessages) {
    	super(errorMessages);
        this.errorMessage = new ErrorMessage(errorMessages);
    }

    public BussinessException(ErrorMessage errorMessages) {
        this.errorMessage = errorMessages;
    }

    /**
     * Constructor Parameterized errors list
     * @param errorList
     */
    public BussinessException(List<ErrorDetail> errorList) {
        this.errorMessage = new ErrorMessage(ExceptionMessageConstants.BUSINESS_EXCEPTIONS, errorList);
    }

    /**
     * @return the errorMessage
     */
    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

}
