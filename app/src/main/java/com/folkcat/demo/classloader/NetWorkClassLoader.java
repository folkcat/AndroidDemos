package com.folkcat.demo.classloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 加载网络class的ClassLoader
 * 适用于有内部类的情况
 */
public class NetWorkClassLoader extends ClassLoader {

	private String rootUrl;
	private String className;
	private String classPath;

	/*
	 * 参数1：根url，形如：http://blogstatic.folkcat.com
	 * 参数2：类路径，形如：/class_Foo.class
	 */
	public NetWorkClassLoader(String rootUrl,String path,String name) {
		this.rootUrl = rootUrl;
		this.className=name;
		this.classPath=path;
	}
	@Override
	protected Class<?> findClass(String p) throws ClassNotFoundException {
		Class clazz = null;// this.findLoadedClass(name); // 父类已加载
		// if (clazz == null) { //检查该类是否已被加载过
		byte[] classData = getClassData(classPath); // 根据类的二进制名称,获得该class文件的字节码数组
		if (classData == null) {
			throw new ClassNotFoundException();
		}
		clazz = defineClass(className, classData, 0, classData.length); // 将class的字节码数组转换成Class类的实例
		return clazz;
	}

	private byte[] getClassData(String path) {
		InputStream is = null;
		try {
			String classPath = rootUrl+path;
			URL url = new URL(classPath);
			byte[] buff = new byte[1024 * 4];
			int len = -1;
			is = url.openStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((len = is.read(buff)) != -1) {
				baos.write(buff, 0, len);
			}
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
