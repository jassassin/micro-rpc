package org.micro.rpc.util;

/**
 * 字符串工具类
 * @author jassassin
 * @date 2018年2月2日
 */
public final class StringUtil {

	/**
	 * 判断字符串是否为空. ""或null. 
	 * <pre>
	 * StringUtil.isEmpty(null)      = true
	 * StringUtil.isEmpty("")        = true
	 * StringUtil.isEmpty(" ")       = false
	 * </pre>
	 */
	public static final boolean isEmpty(final CharSequence str){
		return str == null || str.length() == 0;
	}
	
	/**
	 * 判断字符串是否非空. 非null并且非""
	 * <pre>
	 * StringUtil.isNotEmpty(null)      = false
	 * StringUtil.isNotEmpty("")        = false
	 * StringUtil.isNotEmpty(" ")       = true
	 * </pre>
	 */
	public static final boolean isNotEmpty(final CharSequence str){
		return !isEmpty(str);
	}
}
