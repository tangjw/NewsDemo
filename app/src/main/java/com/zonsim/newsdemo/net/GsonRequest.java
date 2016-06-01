package com.zonsim.newsdemo.net;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.zonsim.newsdemo.MyApp;
import com.zonsim.newsdemo.utils.FileCopyUtils;
import com.zonsim.newsdemo.utils.MD5Utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by tang-jw on 2016/5/26.
 */
public class GsonRequest<T> extends Request<T> {
	private static final String TAG = "GsonRequest";
	private final Gson gson = new Gson();
	private final Class<? extends T> clazz;
	private final Map<String, String> headers;
	private final Response.Listener<T> listener;
	private boolean flag;
	
	
	/**
	 * Make a GET request and return a parsed object from JSON.
	 *
	 * @param method  URL of the request to make
	 * @param url     URL of the request to make
	 * @param clazz   Relevant class object, for Gson's reflection
	 * @param headers Map of request headers
	 * @param flag    URL of the request to make
	 */
	public GsonRequest(int method, String url, Map<String, String> headers, Class<? extends T> clazz,
	                   Response.Listener<T> listener, Response.ErrorListener errorListener, boolean flag) {
		super(method, url, errorListener);
		this.clazz = clazz;
		this.headers = headers;
		this.listener = listener;
		this.flag = flag;
	}
	
	public Gson getGson() {
		return gson;
	}
	
	public Class<? extends T> getClazz() {
		return clazz;
	}
	
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return headers != null ? headers : super.getHeaders();
	}
	
	@Override
	protected void deliverResponse(T response) {
		listener.onResponse(response);
	}
	
	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			Log.d(TAG, "" + json);
			T result = null;
			try {
				result = gson.fromJson(json, clazz);
				// 如果解析成功,需要缓存就缓存
				if (flag) {
					Log.d(TAG, "result 缓存到本地");
					FileCopyUtils.copy(response.data, new File(MyApp.application.getCacheDir(), "" + MD5Utils.encode(getUrl())));
				}
			} catch (JsonSyntaxException | IOException e) {
				Log.e(TAG, "result解析失败");
				e.printStackTrace();
			}
			return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}
}
