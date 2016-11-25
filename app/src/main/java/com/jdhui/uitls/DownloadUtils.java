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
        public void downloading(int progress);

        public void downloaded();
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
        list.add(new Province("内蒙古自治区", "100"));
        list.add(new Province("北京市", "200"));
        list.add(new Province("天津市", "300"));
        list.add(new Province("上海市", "400"));
        list.add(new Province("重庆市", "500"));
        DBManager.saveProvinceList(context, list);
        return list;
    }

    public List<City> getCityList(Context context, String pid) {
        List<City> list = DBManager.getCityList(context, pid);
        if (list == null || list.size() == 0) {
            //创建数据
            list = makeCityList(context);
        }
        return list;
    }

    private List<City> makeCityList(Context context) {
        List<City> list = new ArrayList<>();
        //全部地区
        list.add(new City("全部地区", "000"));
        //内蒙
        list.add(new City("全部", "100"));
        list.add(new City("呼和浩特市", "100"));
        list.add(new City("包头市", "100"));
        list.add(new City("乌海市", "100"));
        list.add(new City("赤峰市", "100"));
        list.add(new City("通辽市", "100"));
        list.add(new City("鄂尔多斯市", "100"));
        list.add(new City("呼伦贝尔市", "100"));
        list.add(new City("巴彦淖尔市", "100"));
        list.add(new City("乌兰察布市", "100"));
        list.add(new City("兴安盟", "100"));
        list.add(new City("锡林郭勒盟", "100"));
        list.add(new City("阿拉善盟", "100"));
        //北京市
        list.add(new City("全部", "200"));
        list.add(new City("市辖区", "200"));
        list.add(new City("县", "200"));
        //天津市
        list.add(new City("全部", "300"));
        list.add(new City("市辖区", "300"));
        list.add(new City("县", "300"));
        //上海市
        list.add(new City("全部", "400"));
        list.add(new City("市辖区", "400"));
        list.add(new City("县", "400"));
        //重庆市
        list.add(new City("全部", "500"));
        list.add(new City("市辖区", "500"));
        list.add(new City("县", "500"));
        DBManager.saveCityList(context, list);
        return list;
    }
}
