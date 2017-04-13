package com.tiredex.yoko.web.control;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * This package was taken from
 * http://javabeanz.wordpress.com/2009/04/21/code-bytestreamresponsewrapper/
 * 
 * @author AlainG and
 *         http://javabeanz.wordpress.com/2009/04/21/code-bytestreamresponsewrapper
 *         /
 * 
 */
public class ByteStreamResponseWrapper extends HttpServletResponseWrapper {
	private static final String CLASS_NAME = ByteStreamResponseWrapper.class.getSimpleName();
	
	private static final String CONFIG_MGMT_VERSION = "$Revision: 11 $";
	
	private ByteArrayOutputStream byteStream;
	
	

	public ByteStreamResponseWrapper(HttpServletResponse response) {
		super(response);
	}
	


	@Override
	public ServletOutputStream getOutputStream() {
		ServletOutputStreamImpl outputStream = null;
		
		this.byteStream = (null == this.byteStream) ? new ByteArrayOutputStream() : this.byteStream;
		outputStream = new ServletOutputStreamImpl(this.byteStream);
		
		return (outputStream);
	}
	


	@Override
	public PrintWriter getWriter() {
		PrintWriter printWriter = null;
		
		this.byteStream = (null == this.byteStream) ? new ByteArrayOutputStream() : this.byteStream;
		printWriter = new PrintWriter(this.byteStream);
		
		return (printWriter);
	}
	


	@Override
	public String toString() {
		return ((null == this.byteStream) ? null : new String(this.byteStream.toByteArray()));
	}
	


	public byte[] toBytes() {
		return ((null == this.byteStream) ? null : this.byteStream.toByteArray());
	}
}
