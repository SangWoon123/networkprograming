package Tour;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;


public class TourAPI {
	public String TourInfor(Double[] mapData) {
    	//Client로부터 받아온 Ip를 변수에 저
    	String mapX = String.valueOf(mapData[1]);
    	String mapY = String.valueOf(mapData[0]);
    	
    	StringBuilder ti = new StringBuilder("");
    	
    	// Encoding된 개인 인증키 입력 
    	String serviceKey = "IEdxaJW5V8q5sWDHqQ%2FAmjSc2p%2Bg9DjxkRI%2B%2BuTGUNDA5vC8qVHmy7ZiIJhHrENtDT2QphqDvwltqCGxUd12sg%3D%3D";
    	
    	// parsing한 데이터를 저장할 변수
    	String result = "";
    	
    	try {
    		// 1. URL을 만들기 위한 StringBuilder.
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551011/KorService1/locationBasedList1"); /*URL*/
            
            // 2. OpenAPI의 요청 규격에 맞는 parameter 생성.
            urlBuilder.append("?serviceKey=" + serviceKey); /*Service Key*/
            urlBuilder.append("&numOfRows=" + "10");
            urlBuilder.append("&pageNo=" + "1");
            urlBuilder.append("&MobileOS=" + "ETC");
            urlBuilder.append("&MobileApp=" + "AppTest"); 
            urlBuilder.append("&_type=" + "json");
            urlBuilder.append("&mapX=" + mapX);
            urlBuilder.append("&mapY=" + mapY);
            urlBuilder.append("&radius=" + "20000");
            
            // 3. URL 객체 생성.
            URL url = new URL(urlBuilder.toString());
            
            
            // 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성.
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 5. 통신을 위한 메소드 SET.
            conn.setRequestMethod("GET");
            // 6. 통신을 위한 Content-type SET. 
            conn.setRequestProperty("Content-type", "application/json");
            // Data Parsing
            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            result = bf.readLine();
            
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
            JSONObject response = (JSONObject)jsonObject.get("response");
            JSONObject body = (JSONObject)response.get("body");
            JSONObject items = (JSONObject)body.get("items");
            JSONArray item = (JSONArray)items.get("item");
            
            if(item.size() > 0) {
            	for(int i = 0; i < item.size(); i++) {
            		StringBuilder temp = new StringBuilder("");
            		
            		JSONObject tourInfor = (JSONObject)item.get(i);
            		
            		String addr1 = tourInfor.get("addr1").toString();
            		Double dist = Math.floor(Double.parseDouble(tourInfor.get("dist").toString())) / 1000;
            		String tel = tourInfor.get("tel").toString();
            		String title = tourInfor.get("title").toString();
            		
            		ti.append("주소1 : " + addr1 + "\n");
            		ti.append("거리 : " + dist + "\n");
            		ti.append("전화번호 : " + tel + "\n");
            		ti.append("관광지명 : " + title + "\n\n,");
            	}
            }          
            conn.disconnect();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	String tourInformation = ti.toString();
    	
    	return tourInformation;
    }
}