package org.lyon_yan.java.utils.qrcode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.io.FileUtil;

/**
 * 二維碼解碼
 * 
 * @author Lyon_Yan <br/>
 *         <b>time</b>: 2015年10月12日 上午9:58:44
 */
public class ParseQRcode {
	/**
	 * 解析網絡資源的二維碼
	 * 
	 * @author Lyon_Yan <br/>
	 *         <b>time</b>: 2015年10月12日 上午10:10:58
	 * @param url
	 *            二維碼的URL地址
	 * @return 二維碼的內容
	 */
	public String parse(String url) {
		return parse(new ByteArrayInputStream(load(url)));
	}

	/**
	 * 加載網絡資源的二維碼
	 * 
	 * @author Lyon_Yan <br/>
	 *         <b>time</b>: 2015年10月12日 上午10:10:58
	 * @param url
	 *            二維碼的URL地址
	 * @return 二維碼的二進制字節數組
	 */
	public byte[] load(String url) {
		HttpRequest httpRequest = HttpRequest.get(url);
		HttpResponse response = httpRequest.send();
		return response.bodyBytes();
	}

	/**
	 * 加載本地資源的二維碼
	 * 
	 * @author Lyon_Yan <br/>
	 *         <b>time</b>: 2015年10月12日 上午10:14:05
	 * @param file
	 *            本地二維碼的文件對象
	 * @return 二維碼的二進制字節數組
	 * @throws IOException
	 */
	public byte[] load(File file) throws IOException {
		return FileUtil.readBytes(file);
	}

	/**
	 * 加載網絡資源的二維碼至本地
	 * 
	 * @author Lyon_Yan <br/>
	 *         <b>time</b>: 2015年10月12日 上午10:18:24
	 * @param url
	 *            二維碼的URL地址
	 * @param out
	 *            輸出的文件地址
	 * @return 返回圖片是否輸出成功
	 */
	public boolean save(String url, String out) {
		byte[] a = load(url);
		File file = new File(out);
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(a, 0, a.length);
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 解析I/O流中的二維碼
	 * 
	 * @author Lyon_Yan <br/>
	 *         <b>time</b>: 2015年10月12日 上午10:20:03
	 * @param inputStream
	 *            載有二維碼的I/O流
	 * @return 二維碼的內容
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String parse(InputStream inputStream) {
		MultiFormatReader formatReader = new MultiFormatReader();
		String result_t = null;
		BufferedImage image;
		try {
			image = ImageIO.read(inputStream);
			// 将图像数据转换为1 bit data
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			// BinaryBitmap是ZXing用来表示1 bit data位图的类，Reader对象将对它进行解析
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			// 创建一个map集合
			Map hints = new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			// 对图像进行解码
			Result result = formatReader.decode(binaryBitmap, hints);
			result_t = result.getText();
			// System.out.println("result = " + result.toString());
			// System.out.println("resultFormat = " +
			// result.getBarcodeFormat());
			// System.out.println("resultText = " + result.getText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "This code decoding is failed！";
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "This code decoding is failed！";
		}
		System.gc();
		return result_t;
	}

}
