package capstone_project.av_service.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import capstone_project.av_service.controller.error.ClamAVSizeLimitException;


public class ClamAVClient {

	private String hostName;
	private int port;
	private int timeout;
	private static final int CHUNK_SIZE = 2048;
	private static final int DEFAULT_TIMEOUT = 5000;
	private static final int PONG_REPLY_LEN = 4;


	public ClamAVClient(String hostName, int port, int timeout) {
		if (timeout < 0) {
			throw new IllegalArgumentException("Negative timeout value does not make sense.");
		}
		this.hostName = hostName;
		this.port = port;
		this.timeout = timeout;
	}

	public ClamAVClient(String hostName, int port) {
		this(hostName, port, DEFAULT_TIMEOUT);
	}


	public byte[] scan(InputStream is) throws IOException {
		//Open Socket connection into ClamAv by hostname and port
		try (Socket s = new Socket(hostName, port); OutputStream outs = new BufferedOutputStream(s.getOutputStream())) {
			s.setSoTimeout(timeout);


			outs.write(asBytes("zINSTREAM\0"));
			outs.flush();
			byte[] chunk = new byte[CHUNK_SIZE];

			// Read data from input stream
			try (InputStream clamIs = s.getInputStream()) {
				int read = is.read(chunk);
				while (read >= 0) {

					byte[] chunkSize = ByteBuffer.allocate(4).putInt(read).array();
					System.out.println("=======chunkSize lenght====== size: " + read);
//					System.out.println(chunk.length);

					outs.write(chunkSize);
					outs.write(chunk, 0, read);
					if (clamIs.available() > 0) {
						byte[] reply = assertSizeLimit(readAll(clamIs));
						throw new IOException(
								"Scan aborted. Reply from server: " + new String(reply, StandardCharsets.US_ASCII));
					}
					read = is.read(chunk);
				}

				outs.write(new byte[] { 0, 0, 0, 0 });
				outs.flush();
				// read reply
				return assertSizeLimit(readAll(clamIs));
			}
		}
	}

	public byte[] scan(byte[] in) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(in);
		return scan(bis);
	}

	public static String isCleanReply(byte[] reply) {
		String r = new String(reply, StandardCharsets.US_ASCII);
		System.out.println(r);
		return r;
	}

	private byte[] assertSizeLimit(byte[] reply) {
		String r = new String(reply, StandardCharsets.US_ASCII);
		// convert byte array into output as string
		System.out.println(r);
		if (r.startsWith("INSTREAM size limit exceeded."))
			throw new ClamAVSizeLimitException("Clamd size limit exceeded. Full reply from server: " + r);
		return reply;
	}

	private static byte[] asBytes(String s) {
		return s.getBytes(StandardCharsets.US_ASCII);
	}

	// reads all available bytes from the stream
	private static byte[] readAll(InputStream is) throws IOException {
		ByteArrayOutputStream tmp = new ByteArrayOutputStream();

		byte[] buf = new byte[2000];
		int read = 0;
		do {
			read = is.read(buf);
			System.out.println("=======Buf lenght======: size: " + read);
//			System.out.println(buf.length);
			tmp.write(buf, 0, read);
		} while ((read > 0) && (is.available() > 0));
		//convert from stream into byteArray
		return tmp.toByteArray();
	}
}
