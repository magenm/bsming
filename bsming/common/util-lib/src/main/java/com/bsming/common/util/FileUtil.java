package com.bsming.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileUtil {

	private static final Log log = LogFactory.getLog(FileUtil.class);
	
	/**
	 * 文件路径是否存在，不存在就创建，然会是否成功
	 * 
	 * @param path
	 * @return
	 */
	public static Boolean validatePath(String path){
		Boolean ret = false; 		
		File file = new File(path);
		
		if (!file.exists()) {
			ret=file.mkdirs();
		}
		
		return ret;
	}

	public static void writeFile(String path, boolean append, String content) {
		File myFilePath = new File(path);
		try {
			
			
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath, append);
			PrintWriter myFile = new PrintWriter(resultFile);
			myFile.println(content);
			resultFile.close();
		} catch (Exception e) {
			System.out.println("新建文件操作出错");
			e.printStackTrace();
		}
	}

	public static List<String> readFileAsList(String path) throws IOException {
		List<String> ls = new ArrayList<String>();
		InputStream in = new FileInputStream(path);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		String line = "";
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			if (line == null || "".equals(line)) {
				continue;
			}
			ls.add(line);
		}
		reader.close();
		return ls;
	}

	public static Set<String> readFileAsSet(String path) throws IOException {
		Set<String> set = new HashSet<String>();
		InputStream in = new FileInputStream(path);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		String line = "";
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			if (line == null || "".equals(line)) {
				continue;
			}
			if (!set.contains(line)) {
				set.add(line);
			}
		}
		reader.close();
		return set;
	}

	public static String readFileAsString(String path) throws IOException {
		StringBuffer sb = new StringBuffer();
		InputStream in = new FileInputStream(path);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		String line = "";
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			if (line == null || "".equals(line)) {
				continue;
			}
			sb.append(line);
		}
		reader.close();
		return sb.toString();
	}
	
	
	public static String readFileAsString(File file) throws IOException {
		StringBuffer sb = new StringBuffer();
		InputStream in = new FileInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		String line = "";
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			if (line == null || "".equals(line)) {
				continue;
			}
			sb.append(line);
		}
		reader.close();
		return sb.toString();
	}

	public static String[] readFileAsArray(String path) throws IOException {
		String[] result = new String[] {};
		InputStream in = new FileInputStream(path);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		String line = "";
		int i = 0;
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			if (line == null || "".equals(line)) {
				continue;
			}
			result[i] = line;
		}
		reader.close();
		return result;
	}

	public static List<String> readFileAsList(String path, String encode) throws IOException {
		List<String> ls = new ArrayList<String>();
		InputStream in = new FileInputStream(path);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, encode));
		String line = "";
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			if (line == null || "".equals(line)) {
				continue;
			}
			ls.add(line);
		}
		reader.close();
		return ls;
	}

	public static String readFileByCharAsString(String path, String encode) throws IOException {

		InputStream in = new FileInputStream(path);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, encode));
		StringBuffer contentbuffer = new StringBuffer();
		char[] temp = new char[1024];
		int size = 0;
		while ((size = reader.read(temp, 0, 1024)) != -1) {
			String tempstr = new String(temp, 0, size);
			contentbuffer.append(tempstr);
		}

		String content = contentbuffer.toString();
		reader.close();
		return content;
	}

	public static boolean InputStreamToFile(InputStream in, String pathname) {
		try {
			File f = new File(pathname);
			OutputStream out = FileUtils.openOutputStream(f);
			byte buf[] = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
			return true;
		} catch (IOException e) {
			log.error("", e);
		}
		return false;
	}

	public static boolean byteArrayToFile(byte[] in, String pathname) {
		if (null == in || in.length <= 0) return false;
		
		InputStream stream = new ByteArrayInputStream(in);
		return InputStreamToFile(stream, pathname);
	}
	
	public static void writeFile(String path,String name, boolean append, String content){
		
		File myFilePath = new File(path+"\\"+name);
		try {
			
			// Create multiple directories
			boolean success = (new File(path)).mkdirs();
			if (success) {
				System.out.println("Directories: " + path + " created");
			}
			
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			
			
			FileWriter resultFile = new FileWriter(myFilePath, append);
			PrintWriter myFile = new PrintWriter(resultFile);
			myFile.println(content);
			resultFile.close();
		} catch (Exception e) {
			System.out.println("新建文件操作出错");
			e.printStackTrace();
		}
		
		
	};
	public static void main(String args[]) {
		try {
			String strDirectoy = "test";
			String strManyDirectories = "dir1/dir2/dir3";

			// Create one directory
			boolean success = (new File(strDirectoy)).mkdir();
			if (success) {
				System.out.println("Directory: " + strDirectoy + " created");
			}
			// Create multiple directories
			success = (new File(strManyDirectories)).mkdirs();
			if (success) {
				System.out.println("Directories: " + strManyDirectories + " created");
			}

		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	
	/**
	 * 将Url保存到本地
	 * @param imageUrl
	 * @param destinationFile
	 * @throws IOException
	 */
	public static void saveImage(String imageUrl, String destinationFile) throws IOException {
		URL url = new URL(imageUrl);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destinationFile);

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}


}
