package org.micro.rpc.util;

import java.lang.reflect.Array;

/**
 * 数组操作工具类
 * @author jassassin
 * @date 2017年12月29日
 */
public final class ArrayUtil {
	
	/**
	 * 未在数组查找到指定元素时返回-1
	 */
	public static final int INDEX_NOT_FOUND = -1;
	
	/**
	 * 判断数组是否为null或空数组
	 */
	public static final <T> boolean isEmpty(final T[] array){
		return array == null || array.length == 0;
	}
	
	/**
	 * 判断数组是否非空 
	 */
	public static final <T> boolean isNotEmpty(final T[] array){
		return !isEmpty(array);
	}
	
	/**
	 * 判断int[]是否为null或空数组
	 */
	public static final boolean isEmpty(final int[] array){
		return array == null || array.length == 0;
	}
	
	/**
	 * 判断byte[]是否为null或空数组
	 */
	public static final boolean isEmpty(final byte[] array){
		return array == null || array.length == 0;
	}
	
	/**
	 * 判断char[]是否为null或空数组
	 */
	public static final boolean isEmpty(final char[] array){
		return array == null || array.length == 0;
	}
	
	/**
	 * 判断short[]是否为null或空数组
	 */
	public static final boolean isEmpty(final short[] array){
		return array == null || array.length == 0;
	}
	
	/**
	 * 判断long[]是否为null或空数组
	 */
	public static final boolean isEmpty(final long[] array){
		return array == null || array.length == 0;
	}
	
	/**
	 * 判断double[]是否为null或空数组
	 */
	public static final boolean isEmpty(final double[] array){
		return array == null || array.length == 0;
	}
	
	/**
	 * 判断float[]是否为null或空数组
	 */
	public static final boolean isEmpty(final float[] array){
		return array == null || array.length == 0;
	}
	
	/**
	 * 判断int[]是否为非空数组 
	 */
	public static final boolean isNotEmpty(final int[] array){
		return !isEmpty(array);
	}
	
	/**
	 * 判断float[]是否为非空数组 
	 */
	public static final boolean isNotEmpty(final float[] array){
		return !isEmpty(array);
	}
	
	/**
	 * 判断byte[]是否为非空数组 
	 */
	public static final boolean isNotEmpty(final byte[] array){
		return !isEmpty(array);
	}
	
	/**
	 * 判断short[]是否为非空数组 
	 */
	public static final boolean isNotEmpty(final short[] array){
		return !isEmpty(array);
	}
	
	/**
	 * 判断char[]是否为非空数组 
	 */
	public static final boolean isNotEmpty(final char[] array){
		return !isEmpty(array);
	}
	
	/**
	 * 判断long[]是否为非空数组 
	 */
	public static final boolean isNotEmpty(final long[] array){
		return !isEmpty(array);
	}
	
	/**
	 * 判断double[]是否为非空数组 
	 */
	public static final boolean isNotEmpty(final double[] array){
		return !isEmpty(array);
	}
	
	/**
	 * 判断obj是否是数组类型
	 */
	public static final boolean isArray(final Object obj){
		return obj != null && obj.getClass().isArray();
	}
	
	/**
	 * 创建新数组 
	 */
	@SuppressWarnings("unchecked")
	public static final <T> T[] newArray(Class<?> elementType, int size){
		return (T[]) Array.newInstance(elementType, size);
	}
	
	/**
	 * 获取数组元素的类型
	 */
	public static final Class<?> getElementType(Class<?> arrayCls){
		return arrayCls == null ? null : arrayCls.getComponentType();
	}
	
	/**
	 * 获取数组元素的类型
	 */
	public static final Class<?> getElementType(Object array){
		return array == null ? null : array.getClass().getComponentType();
	}
	
	/**
	 * 调整数组大小,生成一个新的数组
	 */
	public static final <T> T[] resize(T[] array, int newSize, Class<?> elementType){
		T[] newArray = array;
		// 数组长度发生变化
		if (newSize != length(array)){
			newArray = newArray(elementType, newSize);
			// 原数组为非空数组,拷贝已有元素到新数组
			if (isNotEmpty(array)){
				System.arraycopy(array, 0, newArray, 0, Math.min(array.length, newSize));
			}
		}
		return newArray;
	}
	
	/**
	 * 调整数组大小,生成一个新的数组
	 */
	public static final <T> T[] resize(T[] array, int newSize){
		return resize(array, newSize, getElementType(array));
	}
	
	/**
	 * 获取数组长度.如果array为null,返回0 
	 */
	public static final <T> int length(T[] array){
		return isEmpty(array) ? 0 : array.length;
	}
	
	/**
	 * 追加元素
	 */
	@SafeVarargs
	public static final <T> T[] append(T[] array, T... elements){
		if (isEmpty(elements)){
			return array;
		}
		T[] t = resize(array, array.length + elements.length);
		System.arraycopy(elements, 0, t, array.length, elements.length);
		return t;
	}
	
	/**
	 * 以separator为分隔符,将数组转为字符串
	 */
	public static final String join(int[] array, CharSequence separator){
		String result = null;
		if (isNotEmpty(array)){
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < array.length; i++){
				if (i != 0){
					builder.append(separator);
				}
				builder.append(array[i]);
			}
			result = builder.toString();
		}
		return result;
	}
}
