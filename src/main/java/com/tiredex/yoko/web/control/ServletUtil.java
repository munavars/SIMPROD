package com.tiredex.yoko.web.control;

import java.io.OutputStream;

import javax.servlet.ServletResponse;

/**
 * This package was copied from
 * http://javabeanz.wordpress.com/2009/04/21/code-bytestreamresponsewrapper/
 * 
 * @author AlainG and
 *         http://javabeanz.wordpress.com/2009/04/21/code-bytestreamresponsewrapper/
 * 
 */
public class ServletUtil {
	private static final String CLASS_NAME = ByteStreamResponseWrapper.class.getSimpleName();
	
	private static final String CONFIG_MGMT_VERSION = "$Revision: 11 $";
	
	

	/**
	 * @param response
	 * @param bytes
	 */
	public static void write(ServletResponse response, byte[] bytes) {
		int contentLength = -1;
		OutputStream outputStream = null;
		
		if ((null != response) && (null != bytes) && (bytes.length > 0)) {
			contentLength = bytes.length;
			
			try {
				response.setContentLength(contentLength);
				outputStream = response.getOutputStream();
				outputStream.write(bytes);
			}
			catch (Exception exception) {
				outputStream = null;
			}
			finally {
				try {
					if (null != outputStream)
						outputStream.close();
				}
				catch (Exception e) {
				}
			} // finally
		} // if null != response ...
	}
	
}
