package org.micro.rpc.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * IO操作工具类
 * @author jassassin
 * @date 2018年1月18日
 */
public final class IOUtil {

	/**
	 * 关闭流 
	 */
	public static final void close(Closeable stream){
		if (stream != null){
			try {
				stream.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}
	
	/**
	 * 关闭流 
	 */
	public static final void close(AutoCloseable stream){
		if (stream != null){
			try {
				stream.close();
			} catch (Exception e) {
				// ignore
			}
		}
	}
}
