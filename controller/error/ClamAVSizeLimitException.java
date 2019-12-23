package capstone_project.av_service.controller.error;


public class ClamAVSizeLimitException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ClamAVSizeLimitException(String msg) {
		super(msg);
	}
}
