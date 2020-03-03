package com.orvif.website3.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class ReCaptcha {

	private String urlG = "https://www.google.com/recaptcha/api/siteverify";
	private String secret = "6LdsVK4UAAAAAMx4d_fZQuIs_lKk8-FV2DEdEvj4";
	private String gRecaptchaResponse;

	public ReCaptcha(String token) {
		this.gRecaptchaResponse = token;
	}

	public boolean verify() {
		if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
			return false;
		}

		try {
			StringBuilder response = new StringBuilder();
			String postParams = "secret=" + secret + "&response="
					+ gRecaptchaResponse;
			URL url = new URL(urlG);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "utf-8");
			conn.setRequestProperty("Content-Length", String.valueOf(postParams.length()));
			conn.setReadTimeout(2 * 1000);
			conn.setDoOutput(true);
			OutputStream os = conn.getOutputStream();
			os.write(postParams.getBytes());
			os.flush();
			os.close();
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
			}
			JsonObject jsonObject = new Gson().fromJson(response.toString(), JsonObject.class);
			return jsonObject.get("success").getAsBoolean();

		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).severe("Could not verify captcha : " + e.getClass().getName() + " exception occured.\n StackTrace : \n");
			e.printStackTrace();
			return false;
		}


	}
}
