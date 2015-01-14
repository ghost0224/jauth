package com.hp.security.jauth.soap.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class SoapUtil {

	public static Element getRoot(InputStream in) throws IOException {
		Document document = null;
		Element root = null;
		SAXReader reader = new SAXReader();
		if(null != in) {
			try {
				document = reader.read(in);
				root = document.getRootElement();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		return root;
	}

	public static Element getHeader(InputStream in) throws IOException {
		Element root = getRoot(in);
		if (null != root) {
			List<Element> children = root.elements();
			for (Element c : children) {
				if (c.getName().equalsIgnoreCase("Header")) {
					return c;
				}
			}
		}
		return null;
	}

	public static Element getBody(InputStream in) throws IOException {
		Element root = getRoot(in);
		if (null != root) {
			List<Element> children = root.elements();
			for (Element c : children) {
				if (c.getName().equalsIgnoreCase("Body")) {
					return c;
				}
			}
		}
		return null;
	}

	public static String getOperationName(Element operation) {
		return operation != null ? operation.getName() : null;
	}

	public static String getOperationName(InputStream in) throws IOException {
		Element operation = getOperation(in);
		return operation != null ? operation.getName() : null;
	}

	public static Element getOperation(InputStream in) throws IOException {
		Element body = getBody(in);
		if (null != body && body.elements().size() > 0) {
			return (Element) body.elements().get(0);
		} else {
			return null;
		}
	}

	public Object deepClone(Object o) throws IOException, ClassNotFoundException {
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(this);
		ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
		ObjectInputStream oi = new ObjectInputStream(bi);
		return (oi.readObject());
	}

}
