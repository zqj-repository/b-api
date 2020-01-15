package stock.kdj;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static final String DYCY = "C:\\Users\\jackz\\git\\simple\\resource\\dycy.json";

    public static void main(String[] args) throws IOException, ParseException {
//        File file = new File(DYCY);
//        String json = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
//        System.out.println(getMacdWeight(json));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://pdfm.eastmoney.com/EM_UBG_PDTI_Fast/api/js?token=4f1862fc3b5e77c150a2b985b12db0fd&rtntype=6&id=6003111&type=k&authorityType=fa&cb=jsonp1579079430393");
        CloseableHttpResponse response1 = httpClient.execute(httpGet);
        String res = EntityUtils.toString(response1.getEntity()).replace("jsonp1579079430393(", "");
        res = res.substring(0, res.length() - 1);
        System.out.println(getMacdWeight(res));
    }

    public static float getMacdWeight(String json) throws IOException {
        Map<String, Object> map = new ObjectMapper().readValue(json, Map.class);
        if (map == null || map.get("data") == null) {
            return -1;
        }
        List list = (List) map.get("data");
        List<MACDLine> macdLines = new ArrayList<>();

        for (int i = 0; i < list.size(); i ++) {
            String[] data = ((String)list.get(i)).split(",");
            if (i == 0) {
                MACDLine macdLine = new MACDLine(0f, 0f, 0f, Float.valueOf(data[2]), Float.valueOf(data[2]));
                macdLines.add(macdLine);
                continue;
            }
            MACDLine preMacdLine = macdLines.get(i-1);
            float curSp = Float.valueOf(data[2]);
            float preEMA12 = preMacdLine.getEma12();
            float preEMA26 = preMacdLine.getEma26();
            float preDEA = preMacdLine.getDea();
            macdLines.add(macd(curSp, preEMA12, preEMA26, preDEA));
        }

        int last = macdLines.size() - 1;
        MACDLine lastMacd = macdLines.get(last);

        int downDays = 0; // 1 per day
        boolean gathering = false; // 10
        boolean diffNegative = false; // 5
        boolean goldX = false; // 5
        boolean trend = true; // 10 true 下行
        if (lastMacd.getDiff() - macdLines.get(last - 3).getDiff() > 0) {
            trend = false;
        }

        for (int i = last ; i > macdLines.size() - 30 && trend; i --) {
            float diff = macdLines.get(i).getDiff();
            float dea = macdLines.get(i).getDea();

            if (diff <= dea) {
                downDays ++;
            }
        }
        gathering = (downDays > 6 && macdLines.get(downDays/2).getdSubd() > macdLines.get(last).getdSubd()) && macdLines.get(last).getDiff() < macdLines.get(last).getDea();
        diffNegative = macdLines.get(last).getDiff() < 0;
        boolean willOverZero = macdLines.get(last).getDiff() > 0 && macdLines.get(last - 6).getDiff() < 0;
        goldX = lastMacd.getDiff() - lastMacd.getDea() <= 0.03f && lastMacd.getDiff() - lastMacd.getDea() >= -0.03f;

        return getWeight(downDays,gathering, diffNegative, goldX, trend, willOverZero);
    }

    public void calculateKDJ(String json) throws IOException {
        Map<String, Object> map = new ObjectMapper().readValue(json, Map.class);
        if (map == null || map.get("data") == null) {
            return;
        }
        List list = (List) map.get("data");
        List<KDJLine> kdjLines = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String[] data = ((String)list.get(i)).split(",");
            if (i == 0) {
                LowAndHi lowAndHi = lowAndHi(list, i);
                KDJLine kdjLine = kdj(50, 50, Float.valueOf(data[2]), lowAndHi.getLow(), lowAndHi.getHi());
                kdjLines.add(kdj(kdjLine.getRsv(), kdjLine.getRsv(), Float.valueOf(data[2]), lowAndHi.getLow(), lowAndHi.getHi()));
                continue;
            }
            float preK = kdjLines.get(i - 1).getK();
            float preD = kdjLines.get(i - 1).getD();
            LowAndHi lowAndHi = lowAndHi(list, i);
            kdjLines.add(kdj(preK, preD, Float.valueOf(data[2]), lowAndHi.getLow(), lowAndHi.getHi()));
        }
    }

    public static LowAndHi lowAndHi(List list, int curIndex) {
        float low = 1000000.00f;
        float hi = -0.1f;

        for (int i = curIndex; i >= 0 && curIndex - i < 9; i --) {
            String[] data = ((String)list.get(i)).split(",");

            if (Float.valueOf(data[4]) < low) {
                low = Float.valueOf(data[4]);
            }
            if (Float.valueOf(data[3]) > hi) {
                hi = Float.valueOf(data[3]);
            }
        }
        return new LowAndHi(low, hi);
    }

    public static MACDLine macd(float curSp, float preEMA12, float preEMA26, float preDEA) {
        float EMA12 = preEMA12 * 11 / 13 + curSp * 2 / 13;
        float EMA26 = preEMA26 * 25 / 27 + curSp * 2 / 27;
        float DIFF = EMA12 - EMA26;
        float DEA = preDEA * 8 / 10 + DIFF * 2 / 10;
        float MACD = 2 * (DIFF - DEA);
        return new MACDLine(DIFF, DEA, MACD, EMA12, EMA26);
    }

    public static KDJLine kdj (float preK, float preD, float curSp, float low9, float hi9){
        float rsv;

        if (hi9 <= low9) {
            rsv = 50f;
        } else {
            rsv = (curSp - low9) / (hi9 - low9) * 100f;
        }

        float k =  2f / 3f * preK + 1f / 3f * rsv;
        float d =  2f / 3f * preD + 1f / 3f * k;
        float j = 3f * k - 2f * d;

        return new KDJLine(k, d, j, rsv);
    }

    public static float getWeight(int downDays, boolean gathering, boolean diffNegative, boolean goldX, boolean trend, boolean willOverZero) {
        /**
         * float downDays = 0; // 0.5 per day
         *         boolean gathering = false; // 5
         *         boolean diffNegative = false; // 5
         *         boolean goldX = false; // 5
         *         boolean trend = true; // 5 true 下行
         */
        float weight = 0;
        weight += downDays * 0.5f;
        weight += gathering ? 5 : 0;
        weight += diffNegative ? 5 : 0;
        weight += trend ? 5 : 0;
        weight += gathering && willOverZero ? 5: 0;
        System.out.println(downDays + "" + gathering + diffNegative + trend + willOverZero);
        return weight;
    }
}
