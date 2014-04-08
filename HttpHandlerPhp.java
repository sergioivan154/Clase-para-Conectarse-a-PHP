package com.upiicsa.turisteando.mx.web.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class HttpHandlerPhp extends AsyncTask<String, Void, String> {
 

	private Context context;
	private String resultado;
	private HttpPost httpPost;
	private HttpResponse response;
	private HttpClient httpClient;
	private HttpRequest httpRequest;
	public HttpHandlerPhp(Context context) {
		this.context=context;
	}
	@Override
	protected String doInBackground(String... params) {
		try
		{
			 httpClient=new DefaultHttpClient();
			 httpPost =new HttpPost(params[0]);
			 
			 List<NameValuePair> lNameValuePairs=new ArrayList<NameValuePair>();
			 lNameValuePairs.add(new BasicNameValuePair("dato", "20"));
			 httpPost.setEntity(new UrlEncodedFormEntity(lNameValuePairs));
			 
			 response= httpClient.execute(httpPost);
			 
			HttpEntity entity= response.getEntity();
			
			String responseText= EntityUtils.toString(entity);
			Header []header=response.getAllHeaders();
			for(Header h: header)
			{
				Log.i("Header", h.getName()+" "+ h.getValue());
			}
			HttpParams httpParams=response.getParams();
			boolean n=httpParams.isParameterFalse("dato");
			String f=(String)httpParams.getParameter("dato");
			setResultado(responseText);
			
			
			
			return responseText;
		}
		catch(Exception e)
		{
			return e.getMessage();
		}
	}
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);

		
		if(getResultado()!=null)
		{
			
			Builder builder=new Builder(context);
			
			try {
				builder.setMessage(getResultado());
				
				AlertDialog alertDialog=builder.create();
				alertDialog.show();
				
			}
			catch (Exception e) {
				// TODO: handle exception
				Log.i("error", e.getMessage());
			}
			
		}
		else 	Log.i("error", "sin resultado");

	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
}
