package com.bsming.common.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

public class QRCodeUtil {

	public static void main(String[] args) throws Exception{
        OutputStream os = new FileOutputStream(new File("F://t.png"));
		generateQRCode("千丁互联订单0123439053954 我是你何大爷",os);
		decodeQRCode(new File("F://t.png"));
	}

	public static void decodeQRCode(File file) {
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
		hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
		Result result = null;
		try {
			result = new MultiFormatReader().decode(bitmap, hints);
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(result.toString());
	}

	public static void generateQRCode(String contents, OutputStream os) {
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix matrix = null;
		try {
			matrix = new MultiFormatWriter().encode(contents,
					BarcodeFormat.QR_CODE, 300, 300, hints);
		} catch (WriterException e) {
			e.printStackTrace();
		}
		//File file = new File("F://t.png");
		try {
			MatrixToImageWriter.writeToStream(matrix, "png", os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
