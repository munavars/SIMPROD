package com.tiredex.yoko.web.control;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

/**
 * This file is part of JHttpServer.
 * 
 * This package is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * JHttpServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JHttpServer; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 * // Title :        ServletOutputStreamImpl.java
 * // Version :      0.97
 * // Copyright :    Copyright (c) 2001-2002
 * // Author :       Florent CUETO <jhttpserver@cqs.dyndns.org>
 * // Description :  Basic implementation of ServletOutputStream
 * 
 * 
 * @author AlainG and
 *         http://java2s.com/Open-Source/Java/HTTP/socksviahttp/jhttpserver
 *         /ServletOutputStreamImpl.java.htm
 * 
 */
public class ServletOutputStreamImpl extends ServletOutputStream {
	private ByteArrayOutputStream os;
	
	

	public ServletOutputStreamImpl(ByteArrayOutputStream os) {
		super();
		this.os = os;
	}
	


	public void write(int ch) throws IOException {
		os.write(ch);
	}
	


	public void write(byte[] b) throws IOException {
		write(b, 0, b.length);
	}
	


	public void write(byte[] b, int off, int len) throws IOException {
		os.write(b, off, len);
	}
	


	public void flush() throws IOException {
		os.flush();
	}
	


	public void close() throws IOException {
		os.close();
	}



//	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}



//	@Override
	public void setWriteListener(WriteListener arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
