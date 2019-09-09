import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Solution {

	private static void parseURL(URL url) {

		try {

			String str = "";

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			System.out.println("Response code is: " + responsecode);

			if (responsecode != 200)
				throw new RuntimeException("HttpResponseCode: " + responsecode);
			else {
				Scanner scanner = new Scanner(url.openStream());
				while (scanner.hasNext()) {
					str += scanner.nextLine();
				}
				scanner.close();
			}

			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(str);
			JSONArray jsonArray = (JSONArray) jsonObject.get("list");

			AtomicInteger cityCounter = new AtomicInteger();

			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonobj_1 = (JSONObject) jsonArray.get(i);
				String strCity = (String) jsonobj_1.get("name");
				if (strCity.startsWith("T")) {
					cityCounter.incrementAndGet();
					System.out.println(jsonobj_1);
				}
			}

			System.out.println("Number of cities begins with the letter T: " + cityCounter);
			conn.disconnect();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public static void main(String[] args) {

		try {
			URL url = new URL(
					"https://samples.openweathermap.org/data/2.5/box/city?bbox=12,32,15,37,10&appid=b6907d289e10d714a6e88b30761fae22");

			parseURL(url);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
