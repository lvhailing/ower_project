package com.jdhui.uitls;

import android.content.Context;

import com.jdhui.db.DBManager;
import com.jdhui.db.model.City;
import com.jdhui.db.model.Province;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class DownloadUtils {
    private static final int CONNECT_TIMEOUT = 10000;
    private static final int DATA_TIMEOUT = 40000;
    private final static int DATA_BUFFER = 8192;

    public interface DownloadListener {
        void downloading(int progress);

        void downloaded();
    }

    public static long download(String urlStr, File dest, boolean append, DownloadListener downloadListener) throws Exception {

        int downloadProgress = 0;
        long remoteSize = 0;
        int currentSize = 0;
        long totalSize = -1;

        if (!append && dest.exists() && dest.isFile()) {
            dest.delete();
        }

        if (append && dest.exists() && dest.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(dest);
                currentSize = fis.available();
            } catch (IOException e) {
                throw e;
            } finally {
                if (fis != null) {
                    fis.close();
                }
            }
        }

        HttpGet request = new HttpGet(urlStr);

        if (currentSize > 0) {
            request.addHeader("RANGE", "bytes=" + currentSize + "-");
        }

        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, CONNECT_TIMEOUT);
        HttpConnectionParams.setSoTimeout(params, DATA_TIMEOUT);
        HttpClient httpClient = new DefaultHttpClient(params);

        InputStream is = null;
        FileOutputStream os = null;
        try {
            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                is = response.getEntity().getContent();
                remoteSize = response.getEntity().getContentLength();
                org.apache.http.Header contentEncoding = response.getFirstHeader("Content-Encoding");
                if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
                    is = new GZIPInputStream(is);
                }
                os = new FileOutputStream(dest, append);
                byte buffer[] = new byte[DATA_BUFFER];
                int readSize = 0;
                while ((readSize = is.read(buffer)) > 0) {
                    os.write(buffer, 0, readSize);
                    os.flush();
                    totalSize += readSize;
                    if (downloadListener != null) {
                        downloadProgress = (int) (totalSize * 100 / remoteSize);
                        downloadListener.downloading(downloadProgress);
                    }
                }
                if (totalSize < 0) {
                    totalSize = 0;
                }
            }
        } finally {
            if (os != null) {
                os.close();
            }
            if (is != null) {
                is.close();
            }
        }

        if (totalSize < 0) {
            throw new Exception("Download file fail: " + urlStr);
        }

        if (downloadListener != null) {
            downloadListener.downloaded();
        }

        return totalSize;


		/*int downloadProgress = 0;
        long remoteSize = 0;
		int currentSize = 0;
		long totalSize = -1;

		if (!append && dest.exists() && dest.isFile()) {
			dest.delete();
		}

		if (append && dest.exists() && dest.exists()) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(dest);
				currentSize = fis.available();
			} catch (IOException e) {
				throw e;
			} finally {
				if (fis != null) {
					fis.close();
				}
			}
		}

		HttpGet request = new HttpGet(urlStr);

		if (currentSize > 0) {
			request.addHeader("RANGE", "bytes=" + currentSize + "-");
		}

		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, CONNECT_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, DATA_TIMEOUT);
		HttpClient httpClient = new DefaultHttpClient(params);
		

		InputStream is = null;
		FileOutputStream os = null;
		try {

			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, new TrustManager[] { new MyTrustManager() },
					new SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection
					.setDefaultHostnameVerifier(new MyHostnameVerifier());
			HttpsURLConnection conn = (HttpsURLConnection) new URL(urlStr)
					.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			HttpResponse response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				is = response.getEntity().getContent();
				remoteSize = response.getEntity().getContentLength();
				org.apache.http.Header contentEncoding = response
						.getFirstHeader("Content-Encoding");
				if (contentEncoding != null
						&& contentEncoding.getValue().equalsIgnoreCase("gzip")) {
					is = new GZIPInputStream(is);
				}
				os = new FileOutputStream(dest, append);
				byte buffer[] = new byte[DATA_BUFFER];
				int readSize = 0;
				while ((readSize = is.read(buffer)) > 0) {
					os.write(buffer, 0, readSize);
					os.flush();
					totalSize += readSize;
					if (downloadListener != null) {
						downloadProgress = (int) (totalSize * 100 / remoteSize);
						downloadListener.downloading(downloadProgress);
					}
				}
				if (totalSize < 0) {
					totalSize = 0;
				}
			}
		} finally {
			if (os != null) {
				os.close();
			}
			if (is != null) {
				is.close();
			}
		}

		if (totalSize < 0) {
			throw new Exception("Download file fail: " + urlStr);
		}

		if (downloadListener != null) {
			downloadListener.downloaded();
		}

		return totalSize;*/
    }
    /*
     * class MyHostnameVerifier implements HostnameVerifier {
	 * 
	 * @Override public boolean verify(String hostname, SSLSession session) {
	 * return true; }
	 * 
	 * }
	 * 
	 * class MyTrustManager implements X509TrustManager {
	 * 
	 * @Override public void checkClientTrusted(X509Certificate[] chain, String
	 * authType) throws CertificateException { }
	 * 
	 * @Override public void checkServerTrusted(X509Certificate[] chain, String
	 * authType)
	 * 
	 * throws CertificateException { }
	 * 
	 * @Override public X509Certificate[] getAcceptedIssuers() { return null; }
	 * 
	 * }
	 */

    public List<Province> getProvinceList(Context context) {
        List<Province> list = DBManager.getProvinceList(context);
        if (list == null || list.size() == 0) {
            //创建数据
            list = makeProvinceList(context);
        }

        return list;
    }

    private List<Province> makeProvinceList(Context context) {
        List<Province> list = new ArrayList<>();
        list.add(new Province("全部地区", "000"));
        list.add(new Province("内蒙古自治区", "001"));
        list.add(new Province("北京市", "002"));
        list.add(new Province("天津市", "003"));
        list.add(new Province("上海市", "004"));
        list.add(new Province("重庆市", "005"));
        list.add(new Province("河北省", "006"));
        list.add(new Province("山西省", "007"));
        list.add(new Province("辽宁省", "008"));
        list.add(new Province("吉林省", "009"));
        list.add(new Province("黑龙江省", "010"));
        list.add(new Province("江苏省", "011"));
        list.add(new Province("浙江省", "012"));
        list.add(new Province("安徽省", "013"));
        list.add(new Province("福建省", "014"));
        list.add(new Province("江西省", "015"));
        list.add(new Province("山东省", "016"));
        list.add(new Province("河南省", "017"));
        list.add(new Province("湖北省", "018"));
        list.add(new Province("湖南省", "019"));
        list.add(new Province("广东省", "020"));
        list.add(new Province("广西壮族自治区", "021"));
        list.add(new Province("海南省", "022"));
        list.add(new Province("四川省", "023"));
        list.add(new Province("贵州省", "024"));
        list.add(new Province("云南省", "025"));
        list.add(new Province("西藏自治区", "026"));
        list.add(new Province("陕西省", "027"));
        list.add(new Province("甘肃省", "028"));
        list.add(new Province("青海省", "029"));
        list.add(new Province("宁夏回族自治区", "030"));
        list.add(new Province("新疆维吾尔自治区", "031"));
        list.add(new Province("香港特别行政区", "032"));
        list.add(new Province("澳门特别行政区", "033"));
        list.add(new Province("台湾省", "034"));
        DBManager.saveProvinceList(context, list);
        return list;
    }

    public List<City> getCityList(Context context, String pid) {
        List<City> list = DBManager.getCityList(context, pid);
        if (list == null || list.size() == 0) {
            //创建数据
            makeCityList(context);
            list = DBManager.getCityList(context, pid);
        }
        return list;
    }

    private void makeCityList(Context context) {
        List<City> list = new ArrayList<>();
        //全部地区
        list.add(new City("全部", "000"));
        //内蒙
        list.add(new City("全部", "001"));
        list.add(new City("呼和浩特市", "001"));
        list.add(new City("包头市", "001"));
        list.add(new City("乌海市", "001"));
        list.add(new City("赤峰市", "001"));
        list.add(new City("通辽市", "001"));
        list.add(new City("鄂尔多斯市", "001"));
        list.add(new City("呼伦贝尔市", "001"));
        list.add(new City("巴彦淖尔市", "001"));
        list.add(new City("乌兰察布市", "001"));
        list.add(new City("兴安盟", "001"));
        list.add(new City("锡林郭勒盟", "001"));
        list.add(new City("阿拉善盟", "001"));
        //北京市
        list.add(new City("全部", "002"));
        list.add(new City("市辖区", "002"));
        list.add(new City("县", "002"));
        //天津市
        list.add(new City("全部", "003"));
        list.add(new City("市辖区", "003"));
        list.add(new City("县", "003"));
        //上海市
        list.add(new City("全部", "004"));
        list.add(new City("市辖区", "004"));
        list.add(new City("县", "004"));
        //重庆市
        list.add(new City("全部", "005"));
        list.add(new City("市辖区", "005"));
        list.add(new City("县", "005"));
        //河北省
        list.add(new City("全部", "006"));
        list.add(new City("石家庄市", "006"));
        list.add(new City("唐山市", "006"));
        list.add(new City("秦皇岛市", "006"));
        list.add(new City("邯郸市", "006"));
        list.add(new City("邢台市", "006"));
        list.add(new City("保定市", "006"));
        list.add(new City("张家口市", "006"));
        list.add(new City("承德市", "006"));
        list.add(new City("沧州市", "006"));
        list.add(new City("廊坊市", "006"));
        list.add(new City("衡水市", "006"));
        //山西省
        list.add(new City("全部", "007"));
        list.add(new City("太原市", "007"));
        list.add(new City("大同市", "007"));
        list.add(new City("阳泉市", "007"));
        list.add(new City("长治市", "007"));
        list.add(new City("晋城市", "007"));
        list.add(new City("朔州市", "007"));
        list.add(new City("晋中市", "007"));
        list.add(new City("运城市", "007"));
        list.add(new City("忻州市", "007"));
        list.add(new City("临汾市", "007"));
        list.add(new City("吕梁市", "007"));
        //辽宁省
        list.add(new City("全部", "008"));
        list.add(new City("沈阳市", "008"));
        list.add(new City("大连市", "008"));
        list.add(new City("鞍山市", "008"));
        list.add(new City("抚顺市", "008"));
        list.add(new City("本溪市", "008"));
        list.add(new City("丹东市", "008"));
        list.add(new City("锦州市", "008"));
        list.add(new City("营口市", "008"));
        list.add(new City("阜新市", "008"));
        list.add(new City("辽阳市", "008"));
        list.add(new City("盘锦市", "008"));
        list.add(new City("铁岭市", "008"));
        list.add(new City("朝阳市", "008"));
        list.add(new City("葫芦岛市", "008"));
        //吉林省
        list.add(new City("全部", "009"));
        list.add(new City("长春市", "009"));
        list.add(new City("吉林市", "009"));
        list.add(new City("四平市", "009"));
        list.add(new City("辽源市", "009"));
        list.add(new City("通化市", "009"));
        list.add(new City("白山市", "009"));
        list.add(new City("松原市", "009"));
        list.add(new City("白城市", "009"));
        list.add(new City("延边朝鲜族自治州", "009"));
        //黑龙江省
        list.add(new City("全部", "010"));
        list.add(new City("哈尔滨市", "010"));
        list.add(new City("齐齐哈尔市", "010"));
        list.add(new City("鸡西市", "010"));
        list.add(new City("鹤岗市", "010"));
        list.add(new City("双鸭山市", "010"));
        list.add(new City("大庆市", "010"));
        list.add(new City("伊春市", "010"));
        list.add(new City("佳木斯市", "010"));
        list.add(new City("七台河市", "010"));
        list.add(new City("牡丹江市", "010"));
        list.add(new City("黑河市", "010"));
        list.add(new City("绥化市", "010"));
        list.add(new City("大兴安岭地区", "010"));
        //江苏省
        list.add(new City("全部", "011"));
        list.add(new City("南京市", "011"));
        list.add(new City("无锡市", "011"));
        list.add(new City("徐州市", "011"));
        list.add(new City("常州市", "011"));
        list.add(new City("苏州市", "011"));
        list.add(new City("南通市", "011"));
        list.add(new City("连云港市", "011"));
        list.add(new City("淮安市", "011"));
        list.add(new City("盐城市", "011"));
        list.add(new City("扬州市", "011"));
        list.add(new City("镇江市", "011"));
        list.add(new City("泰州市", "011"));
        list.add(new City("宿迁市", "011"));
        //浙江省
        list.add(new City("全部", "012"));
        list.add(new City("杭州市", "012"));
        list.add(new City("宁波市", "012"));
        list.add(new City("温州市", "012"));
        list.add(new City("嘉兴市", "012"));
        list.add(new City("湖州市", "012"));
        list.add(new City("绍兴市", "012"));
        list.add(new City("金华市", "012"));
        list.add(new City("衢州市", "012"));
        list.add(new City("舟山市", "012"));
        list.add(new City("台州市", "012"));
        list.add(new City("丽水市", "012"));
        //安徽省
        list.add(new City("全部", "013"));
        list.add(new City("合肥市", "013"));
        list.add(new City("芜湖市", "013"));
        list.add(new City("蚌埠市", "013"));
        list.add(new City("淮南市", "013"));
        list.add(new City("马鞍山市", "013"));
        list.add(new City("淮北市", "013"));
        list.add(new City("铜陵市", "013"));
        list.add(new City("安庆市", "013"));
        list.add(new City("黄山市", "013"));
        list.add(new City("滁州市", "013"));
        list.add(new City("阜阳市", "013"));
        list.add(new City("宿州市", "013"));
        list.add(new City("巢湖市", "013"));
        list.add(new City("六安市", "013"));
        list.add(new City("亳州市", "013"));
        list.add(new City("池州市", "013"));
        list.add(new City("宣城市", "013"));
        //福建省
        list.add(new City("全部", "014"));
        list.add(new City("福州市", "014"));
        list.add(new City("厦门市", "014"));
        list.add(new City("莆田市", "014"));
        list.add(new City("三明市", "014"));
        list.add(new City("泉州市", "014"));
        list.add(new City("漳州市", "014"));
        list.add(new City("南平市", "014"));
        list.add(new City("龙岩市", "014"));
        list.add(new City("宁德市", "014"));
        //江西省
        list.add(new City("全部", "015"));
        list.add(new City("南昌市", "015"));
        list.add(new City("景德镇市", "015"));
        list.add(new City("萍乡市", "015"));
        list.add(new City("九江市", "015"));
        list.add(new City("新余市", "015"));
        list.add(new City("鹰潭市", "015"));
        list.add(new City("赣州市", "015"));
        list.add(new City("吉安市", "015"));
        list.add(new City("宜春市", "015"));
        list.add(new City("抚州市", "015"));
        list.add(new City("上饶市", "015"));
        //山东省
        list.add(new City("全部", "016"));
        list.add(new City("济南市", "016"));
        list.add(new City("青岛市", "016"));
        list.add(new City("淄博市", "016"));
        list.add(new City("枣庄市", "016"));
        list.add(new City("东营市", "016"));
        list.add(new City("烟台市", "016"));
        list.add(new City("潍坊市", "016"));
        list.add(new City("威海市", "016"));
        list.add(new City("济宁市", "016"));
        list.add(new City("泰安市", "016"));
        list.add(new City("日照市", "016"));
        list.add(new City("莱芜市", "016"));
        list.add(new City("临沂市", "016"));
        list.add(new City("德州市", "016"));
        list.add(new City("聊城市", "016"));
        list.add(new City("滨州市", "016"));
        list.add(new City("菏泽市", "016"));
        //河南省
        list.add(new City("全部", "017"));
        list.add(new City("郑州市", "017"));
        list.add(new City("开封市", "017"));
        list.add(new City("洛阳市", "017"));
        list.add(new City("平顶山市", "017"));
        list.add(new City("焦作市", "017"));
        list.add(new City("鹤壁市", "017"));
        list.add(new City("新乡市", "017"));
        list.add(new City("安阳市", "017"));
        list.add(new City("濮阳市", "017"));
        list.add(new City("许昌市", "017"));
        list.add(new City("漯河市", "017"));
        list.add(new City("三门峡市", "017"));
        list.add(new City("南阳市", "017"));
        list.add(new City("商丘市", "017"));
        list.add(new City("信阳市", "017"));
        list.add(new City("周口市", "017"));
        list.add(new City("驻马店市", "017"));
        //湖北省
        list.add(new City("全部", "018"));
        list.add(new City("武汉市", "018"));
        list.add(new City("黄石市", "018"));
        list.add(new City("襄樊市", "018"));
        list.add(new City("十堰市", "018"));
        list.add(new City("荆州市", "018"));
        list.add(new City("荆门市", "018"));
        list.add(new City("宜昌市", "018"));
        list.add(new City("鄂州市", "018"));
        list.add(new City("孝感市", "018"));
        list.add(new City("黄冈市", "018"));
        list.add(new City("咸宁市", "018"));
        list.add(new City("随州市", "018"));
        list.add(new City("恩施市", "018"));
        //湖南省
        list.add(new City("全部", "019"));
        list.add(new City("长沙市", "019"));
        list.add(new City("株洲市", "019"));
        list.add(new City("湘潭市", "019"));
        list.add(new City("衡阳市", "019"));
        list.add(new City("邵阳市", "019"));
        list.add(new City("岳阳市", "019"));
        list.add(new City("常德市", "019"));
        list.add(new City("张家界市", "019"));
        list.add(new City("益阳市", "019"));
        list.add(new City("郴州市", "019"));
        list.add(new City("永州市", "019"));
        list.add(new City("怀化市", "019"));
        list.add(new City("娄底市", "019"));
        list.add(new City("湘西市", "019"));
        //广东省
        list.add(new City("全部", "020"));
        list.add(new City("广州市", "020"));
        list.add(new City("深圳市", "020"));
        list.add(new City("珠海市", "020"));
        list.add(new City("汕头市", "020"));
        list.add(new City("韶关市", "020"));
        list.add(new City("佛山市", "020"));
        list.add(new City("江门市", "020"));
        list.add(new City("湛江市", "020"));
        list.add(new City("茂名市", "020"));
        list.add(new City("肇庆市", "020"));
        list.add(new City("惠州市", "020"));
        list.add(new City("梅州市", "020"));
        list.add(new City("汕尾市", "020"));
        list.add(new City("河源市", "020"));
        list.add(new City("阳江市", "020"));
        list.add(new City("清远市", "020"));
        list.add(new City("东莞市", "020"));
        list.add(new City("中山市", "020"));
        list.add(new City("潮州市", "020"));
        list.add(new City("揭阳市", "020"));
        list.add(new City("云浮市", "020"));
        //广西壮族自治区
        list.add(new City("全部", "021"));
        list.add(new City("南宁市", "021"));
        list.add(new City("柳州市", "021"));
        list.add(new City("桂林市", "021"));
        list.add(new City("梧州市", "021"));
        list.add(new City("北海市", "021"));
        list.add(new City("防城港市", "021"));
        list.add(new City("钦州市", "021"));
        list.add(new City("贵港市", "021"));
        list.add(new City("玉林市", "021"));
        list.add(new City("百色市", "021"));
        list.add(new City("贺州市", "021"));
        list.add(new City("河池市", "021"));
        list.add(new City("来宾市", "021"));
        list.add(new City("崇左市", "021"));
        //海南省
        list.add(new City("全部", "022"));
        list.add(new City("海口市", "022"));
        list.add(new City("三亚市", "022"));
        //四川省
        list.add(new City("全部", "023"));
        list.add(new City("成都市", "023"));
        list.add(new City("自贡市", "023"));
        list.add(new City("攀枝花市", "023"));
        list.add(new City("泸州市", "023"));
        list.add(new City("德阳市", "023"));
        list.add(new City("绵阳市", "023"));
        list.add(new City("广元市", "023"));
        list.add(new City("遂宁市", "023"));
        list.add(new City("内江市", "023"));
        list.add(new City("乐山市", "023"));
        list.add(new City("南充市", "023"));
        list.add(new City("宜宾市", "023"));
        list.add(new City("广安市", "023"));
        list.add(new City("达州市", "023"));
        list.add(new City("眉山市", "023"));
        list.add(new City("雅安市", "023"));
        list.add(new City("巴中市", "023"));
        list.add(new City("资阳市", "023"));
        list.add(new City("阿坝市", "023"));
        list.add(new City("甘孜市", "023"));
        list.add(new City("凉山市", "023"));
        //贵州省
        list.add(new City("全部", "024"));
        list.add(new City("贵阳市", "024"));
        list.add(new City("六盘水市", "024"));
        list.add(new City("遵义市", "024"));
        list.add(new City("安顺市", "024"));
        list.add(new City("铜仁地区", "024"));
        list.add(new City("毕节地区", "024"));
        list.add(new City("黔西南布依族", "024"));
        list.add(new City("黔东南苗族", "024"));
        list.add(new City("黔南布依族", "024"));
        //云南省
        list.add(new City("全部", "025"));
        list.add(new City("昆明市", "025"));
        list.add(new City("曲靖市", "025"));
        list.add(new City("玉溪市", "025"));
        list.add(new City("保山市", "025"));
        list.add(new City("昭通市", "025"));
        list.add(new City("丽江市", "025"));
        list.add(new City("普洱市", "025"));
        list.add(new City("临沧市", "025"));
        list.add(new City("文山壮族苗族自治州", "025"));
        list.add(new City("红河哈尼族彝族自治州", "025"));
        list.add(new City("西双版纳傣族自治州", "025"));
        list.add(new City("楚雄彝族自治州", "025"));
        list.add(new City("大理白族自治州", "025"));
        list.add(new City("德宏傣族景颇族自治州", "025"));
        list.add(new City("怒江傈僳族自治州", "025"));
        list.add(new City("迪庆藏族自治州", "025"));
        //西藏自治区
        list.add(new City("全部", "026"));
        list.add(new City("拉萨市", "026"));
        list.add(new City("昌都地区", "026"));
        list.add(new City("山南地区", "026"));
        list.add(new City("日喀则地区", "026"));
        list.add(new City("那曲地区", "026"));
        list.add(new City("阿里地区", "026"));
        list.add(new City("林芝地区", "026"));
        //陕西省
        list.add(new City("全部", "027"));
        list.add(new City("西安市", "027"));
        list.add(new City("铜川市", "027"));
        list.add(new City("宝鸡市", "027"));
        list.add(new City("咸阳市", "027"));
        list.add(new City("渭南市", "027"));
        list.add(new City("延安市", "027"));
        list.add(new City("汉中市", "027"));
        list.add(new City("榆林市", "027"));
        list.add(new City("安康市", "027"));
        list.add(new City("商洛市", "027"));
        //甘肃省
        list.add(new City("全部", "028"));
        list.add(new City("兰州市", "028"));
        list.add(new City("嘉峪关市", "028"));
        list.add(new City("金昌市", "028"));
        list.add(new City("白银市", "028"));
        list.add(new City("天水市", "028"));
        list.add(new City("武威市", "028"));
        list.add(new City("张掖市", "028"));
        list.add(new City("平凉市", "028"));
        list.add(new City("酒泉市", "028"));
        list.add(new City("庆阳市", "028"));
        list.add(new City("定西市", "028"));
        list.add(new City("陇南市", "028"));
        list.add(new City("临夏回族自治州", "028"));
        list.add(new City("甘南藏族自治州", "028"));
        //青海省
        list.add(new City("全部", "029"));
        list.add(new City("西宁市", "029"));
        list.add(new City("海东地区", "029"));
        list.add(new City("海北藏族自治州", "029"));
        list.add(new City("黄南藏族自治州", "029"));
        list.add(new City("海南藏族自治州", "029"));
        list.add(new City("果洛藏族自治州", "029"));
        list.add(new City("玉树藏族自治州", "029"));
        list.add(new City("海西蒙古族藏族自治州", "029"));
        //宁夏回族自治区
        list.add(new City("全部", "030"));
        list.add(new City("银川市", "030"));
        list.add(new City("石嘴山市", "030"));
        list.add(new City("吴忠市", "030"));
        list.add(new City("固原市", "030"));
        list.add(new City("中卫市", "030"));
        //新疆维吾尔自治区
        list.add(new City("全部", "031"));
        list.add(new City("乌鲁木齐市", "031"));
        list.add(new City("克拉玛依市", "031"));
        list.add(new City("吐鲁番地区", "031"));
        list.add(new City("哈密地区", "031"));
        list.add(new City("和田地区", "031"));
        list.add(new City("阿克苏地区", "031"));
        list.add(new City("喀什地区", "031"));
        list.add(new City("克孜勒苏柯尔克孜自治州", "031"));
        list.add(new City("巴音郭楞蒙古自治州", "031"));
        list.add(new City("昌吉回族自治州", "031"));
        list.add(new City("博尔塔拉蒙古自治州", "031"));
        list.add(new City("伊犁哈萨克自治州", "031"));
        list.add(new City("塔城地区", "031"));
        list.add(new City("阿勒泰地区", "031"));
        //香港特别行政区
        list.add(new City("全部", "032"));
        list.add(new City("市辖区", "032"));
        //澳门特别行政区
        list.add(new City("全部", "033"));
        list.add(new City("市辖区", "033"));
        //台湾省
        list.add(new City("全部", "034"));
        list.add(new City("台北市", "034"));

        DBManager.saveCityList(context, list);
    }
}
