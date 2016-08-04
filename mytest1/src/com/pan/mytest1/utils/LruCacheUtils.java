package com.pan.mytest1.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.LruCache;

@SuppressLint("NewApi") 
public class LruCacheUtils {

	private LruCache<String, Bitmap> mMemoryCache;
	private int MAXMEMONRY = (int) (Runtime.getRuntime().maxMemory() / 1024);
	private static LruCacheUtils mCacheUtils;

	public static LruCacheUtils getInstance() {
		if (mCacheUtils == null) {
			mCacheUtils = new LruCacheUtils();
		}
		return mCacheUtils;
	}

	private LruCacheUtils() {
		if (mMemoryCache == null) {
			mMemoryCache = new LruCache<String, Bitmap>(MAXMEMONRY / 8) {

				@Override
				protected void entryRemoved(boolean evicted, String key,
						Bitmap oldValue, Bitmap newValue) {
					// super.entryRemoved(evicted, key, oldValue, newValue);
				}

				@Override
				protected int sizeOf(String key, Bitmap value) {
					return value.getRowBytes() * value.getHeight() / 1024;
				}
			};
		}
	}
	/**
	 * 清除缓存
	 */
	public void clearCache()
	{
		if (mMemoryCache != null) {
			if (mMemoryCache.size()>0) {
				mMemoryCache.evictAll();
			}
			mMemoryCache = null;
		}
	}
	/**
	 * 添加图片到缓存
	 * @param key 位图表示符
	 * @param bitmap 位图对象
	 */
	public synchronized void addBitmapToMemoryCache(String key,Bitmap bitmap)
	{
		if (mMemoryCache.get(key) == null) {
			if (key != null && bitmap != null) {
				mMemoryCache.put(key, bitmap);
			}
			else Logger.W("the res is alread exits");
		}
	}
	/**
	 * 从缓存中取得图片
	 * @param key
	 * @return
	 */
	public synchronized Bitmap getBitmapFromMemCache(String key)
	{
		Bitmap bm = mMemoryCache.get(key);
		if (key != null) {
			return bm;
		}
		return null;
	}
	/**
	 * 移除缓存
	 * @param key
	 */
	public synchronized void removeImageCache(String key)
	{
		if (key != null) {
			if (mMemoryCache != null) {
				Bitmap bm = mMemoryCache.remove(key);
				if (bm != null) {
					bm.recycle();
				}
			}
		}
	}
	/**
	 * 清空缓存
	 */
	public void clearAllImageCache()
	{
		if (mMemoryCache != null && mMemoryCache.size()>0) {
			mMemoryCache.evictAll();
		}
	}
	
}
