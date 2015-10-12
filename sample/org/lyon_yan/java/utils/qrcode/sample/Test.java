package org.lyon_yan.java.utils.qrcode.sample;

import org.lyon_yan.java.utils.qrcode.ParseQRcode;

public class Test {
	/**
	 * sample test
	 * 
	 * @author Lyon_Yan <br/>
	 *         <b>time</b>: 2015年10月12日 上午10:09:12
	 * @param args
	 */
	public static void main(String[] args) {
		/**
		 * 测试采用的二维码，由草料二维码（http://cli.im/）提供支持
		 */
		System.out.println(new ParseQRcode().parse(
				"http://qrapi.cli.im/qr?data=this%2Bis%2Ba%2Btest%2Bqrcode&level=H&transparent=false&bgcolor=%23ffffff&forecolor=%23000000&blockpixel=12&marginblock=1&logourl=&size=280&kid=cliim&key=a39302b75dbd30b1ce4d7aef91cab45c"));
	}
}