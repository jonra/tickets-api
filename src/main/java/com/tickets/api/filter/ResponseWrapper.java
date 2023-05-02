package com.tickets.api.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseWrapper extends HttpServletResponseWrapper {
	private final ByteArrayOutputStream byteArrayOutputStream;
	private PrintWriter writer;
	private ServletOutputStream outputStream;

	public ResponseWrapper(HttpServletResponse response) {
		super(response);
		byteArrayOutputStream = new ByteArrayOutputStream();
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (outputStream == null) {
			outputStream = new ServletOutputStream() {
				@Override
				public boolean isReady() {
					return true;
				}

				@Override
				public void setWriteListener(WriteListener writeListener) {
				}

				@Override
				public void write(int b) {
					byteArrayOutputStream.write(b);
				}
			};
		}
		return outputStream;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (writer == null) {
			writer = new PrintWriter(getOutputStream());
		}
		return writer;
	}

	public byte[] getContentAsByteArray() {
		if (writer != null) {
			writer.flush();
		}
		return byteArrayOutputStream.toByteArray();
	}

	public void copyBodyToResponse() throws IOException {
		HttpServletResponse originalResponse = (HttpServletResponse) getResponse();
		byte[] content = getContentAsByteArray();
		originalResponse.setContentLength(content.length);
		originalResponse.getOutputStream().write(content);
	}
}
