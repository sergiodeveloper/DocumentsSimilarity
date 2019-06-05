package documentssimilarity.exception;

public class MetadataDiferentSizeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MetadataDiferentSizeException() {
		super();
	}

	public MetadataDiferentSizeException(final String message) {
		super(message);
	}

}
