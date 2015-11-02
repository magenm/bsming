package com.bsming.common.stroage.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//import common.storage.service.impl.UpYun.PARAMS;

public class ImgStrorageUtilYPImpl implements ImgStorageUtil {

	// 运行前先设置好以下三个参数***********//此处需要获取配置文件参数信息
	private  String bucketName ;
	private  String userName ;
	private  String userPwd ;
	
	

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	/** 绑定的域名 */
	private   String URL;

	/** 根目录 */
	private static final String DIR_ROOT = "/";

	/** 上传到upyun的图片名 */
	private static final String PIC_NAME = "sample.jpeg";

	/** 本地待上传的测试文件 */
	private static final String fileUrl = System.getProperty("user.dir")
			+ "/sample.jpeg";

	private static UpYun upyun = null;


	public void UpYunInit() {

		upyun = new UpYun(bucketName, userName, userPwd);
		
		URL = "http://" + bucketName+ ".b0.upaiyun.com";
		// ****** 可选设置 begin ******

		// 切换 API 接口的域名接入点，默认为自动识别接入点
		// upyun.setApiDomain(UpYun.ED_AUTO);

		// 设置连接超时时间，默认为30秒
		// upyun.setTimeout(60);

		// 设置是否开启debug模式，默认不开启
		upyun.setDebug(true);
	}

	/**
	 * 上传文件
	 * 
	 * @throws IOException
	 */
	@Override
	public String imgStorage(String dir,String yunFileName, String fileUrl) throws IOException {
		// 初始化空间
		UpYunInit();
		// 验证dir及fileurl是否合法
		if (yunFileName==null||yunFileName=="") {
			yunFileName=UUID.randomUUID().toString();
		}
		String newfilename = yunFileName+ fileUrl.substring(fileUrl.lastIndexOf('.'));
		// 要传到upyun后的文件路径
		String filePath = DIR_ROOT + dir + "/" + newfilename;

		// 本地待上传的图片文件
		File file = new File(fileUrl);

		// 设置待上传文件的 Content-MD5 值
		// 如果又拍云服务端收到的文件MD5值与用户设置的不一致，将回报 406 NotAcceptable 错误
		upyun.setContentMD5(UpYun.md5(file));

		// 设置待上传文件的"访问密钥"
		// 注意：
		// 仅支持图片空！，设置密钥后，无法根据原文件URL直接访问，需带URL后面加上（缩略图间隔标志符+密钥）进行访问
		// 举例：
		// 如果缩略图间隔标志符为"!"，密钥为"bac"，上传文件路径为"/folder/test.jpg"，
		// 那么该图片的对外访问地址为：http://空间域名 /folder/test.jpg!bac
		//upyun.setFileSecret("bac");

		// 上传文件，并自动创建父级目录（最多10级）
		boolean result = upyun.writeFile(filePath, file, true);

		System.out.println(filePath + " 上传" + isSuccess(result));

		// 获取上传文件后的信息（仅图片空间有返回数据）
		System.out.println("\r\n****** " + file.getName() + " 的图片信息 *******");
		// 图片宽度
		System.out.println("图片宽度:" + upyun.getPicWidth());
		// 图片高度
		System.out.println("图片高度:" + upyun.getPicHeight());
		// 图片帧数
		System.out.println("图片帧数:" + upyun.getPicFrames());
		// 图片类型
		System.out.println("图片类型:" + upyun.getPicType());

		System.out.println("****************************************\r\n");

		System.out.println("若设置过访问密钥(bac)，且缩略图间隔标志符为'!'，则可以通过以下路径来访问图片：");
		System.out.println(URL + filePath + "!bac");
		System.out.println();
		if (result) {
			// 返回存储上文件位置
			return URL + filePath;
		} else {
			// 返回错误消息
			return "缩略图上传失败！";
		}

	}

	/**
	 * 图片做缩略图
	 * <p>
	 * 注意：若使用了缩略图功能，则会丢弃原图
	 * 
	 * @throws IOException
	 */
	@Override
	public String imgGmkerl(String dir,String yunFileName, String picFile,
			Map<String, String> params) throws IOException {
		// 初始化空间
		UpYunInit();
		if (yunFileName==null||yunFileName=="") {
			yunFileName=UUID.randomUUID().toString();
		}
		String newfilename = yunFileName+ fileUrl.substring(fileUrl.lastIndexOf('.'));
		// 要传到upyun后的文件路径
		String filePath = DIR_ROOT + dir + "/" + newfilename;


		// 本地待上传的图片文件
		File file = new File(picFile);

		// 设置缩略图的参数
		// Map<String, String> params = new HashMap<String, String>();

		// 设置缩略图类型，必须搭配缩略图参数值（KEY_VALUE）使用，否则无效
		params.put(PARAMS.KEY_X_GMKERL_TYPE.getValue(),
				PARAMS.VALUE_FIX_BOTH.getValue());

		// 设置缩略图参数值，必须搭配缩略图类型（KEY_TYPE）使用，否则无效
		params.put(PARAMS.KEY_X_GMKERL_VALUE.getValue(), "150x150");

		// 设置缩略图的质量，默认 95
		params.put(PARAMS.KEY_X_GMKERL_QUALITY.getValue(), "95");

		// 设置缩略图的锐化，默认锐化（true）
		params.put(PARAMS.KEY_X_GMKERL_UNSHARP.getValue(), "true");

		// 若在 upyun 后台配置过缩略图版本号，则可以设置缩略图的版本名称
		// 注意：只有存在缩略图版本名称，才会按照配置参数制作缩略图，否则无效
		params.put(PARAMS.KEY_X_GMKERL_THUMBNAIL.getValue(), "small");

		// 上传文件，并自动创建父级目录（最多10级）
		boolean result = upyun.writeFile(filePath, file, true, params);

		System.out.println(filePath + " 制作缩略图" + isSuccess(result));
		System.out.println("可以通过该路径来访问图片：" + URL + filePath);
		System.out.println();
		if (result) {
			// 返回存储上文件位置
			return URL + filePath;
		} else {
			// 返回错误消息
			return "缩略图上传失败！";
		}
	}

	/**
	 * 图片旋转
	 * 
	 * @throws IOException
	 */
	@Override
	public String imgRotate(String dir,String yunFileName, String picFile, String param)
			throws IOException {
		// 初始化空间
		UpYunInit();
		if (yunFileName==null||yunFileName=="") {
			yunFileName=UUID.randomUUID().toString();
		}
		String newfilename = yunFileName+ fileUrl.substring(fileUrl.lastIndexOf('.'));
		// 要传到upyun后的文件路径
		String filePath = DIR_ROOT + dir + "/" + newfilename;


		// 本地待上传的图片文件
		File file = new File(picFile);

		// 图片旋转功能具体可参考：http://wiki.upyun.com/index.php?title=图片旋转
		Map<String, String> params = new HashMap<String, String>();

		// 设置图片旋转：只接受"auto"，"90"，"180"，"270"四种参数
		params.put(PARAMS.KEY_X_GMKERL_ROTATE.getValue(), param);

		// 上传文件，并自动创建父级目录（最多10级）
		boolean result = upyun.writeFile(filePath, file, true, params);

		System.out.println(filePath + " 图片旋转" + isSuccess(result));
		System.out.println("可以通过该路径来访问图片：" + URL + filePath);
		System.out.println();
		if (result) {
			// 返回存储上文件位置
			return URL + filePath;
		} else {
			// 返回错误消息
			return "缩略图上传失败！";
		}
	}

	/**
	 * 图片裁剪
	 * 
	 * @throws IOException
	 */
	@Override
	public String imgCrop(String dir,String yunFileName, String picFile, String cropParams)
			throws IOException {
		// 初始化空间
		UpYunInit();

		if (yunFileName==null||yunFileName=="") {
			yunFileName=UUID.randomUUID().toString();
		}
		String newfilename = yunFileName+ fileUrl.substring(fileUrl.lastIndexOf('.'));
		// 要传到upyun后的文件路径
		String filePath = DIR_ROOT + dir + "/" + newfilename;

		// 本地待上传的图片文件
		File file = new File(picFile);

		// 图片裁剪功能具体可参考：http://wiki.upyun.com/index.php?title=图片裁剪
		Map<String, String> params = new HashMap<String, String>();

		// 设置图片裁剪，参数格式：x,y,width,height "50,50,300,300"
		params.put(PARAMS.KEY_X_GMKERL_CROP.getValue(), cropParams);

		// 上传文件，并自动创建父级目录（最多10级）
		boolean result = upyun.writeFile(filePath, file, true, params);

		System.out.println(filePath + " 图片裁剪" + isSuccess(result));
		System.out.println("可以通过该路径来访问图片：" + URL + filePath);
		System.out.println();
		if (result) {
			// 返回存储上文件位置
			return URL + filePath;
		} else {
			// 返回错误消息
			return "缩略图上传失败！";
		}
	}

	private static String isSuccess(boolean result) {
		return result ? " 成功" : " 失败";
	}

	private static String isFile(String fileUrl) {

		File picFile = new File(fileUrl);
		if (!picFile.isFile()) {
			System.out.println("本地待上传的测试文件不存在!");
		} else {
		}
		return fileUrl;

	}

	public enum PARAMS {

		/**
		 * 缩略图类型
		 * <p>
		 * 使用场景：上传图片时若无需保存原图，而只需某种特定大小的缩略图，比如说用户头像。
		 * <p>
		 * 说明：该参数必须搭配 KEY_X_GMKERL_VALUE 使用，否则无效。另外，使用该参数后将不保存原图，切忌。
		 * <p>
		 * 可选参数：<br>
		 * 1)VALUE_FIX_MAX("fix_max")："限定最长边，短边自适应"<br>
		 * 2)VALUE_FIX_MIN("fix_min")："限定最短边，长边自适应"<br>
		 * 3)VALUE_FIX_WIDTH_OR_HEIGHT("fix_width_or_height")："限定宽度和高度"<br>
		 * 4)VALUE_FIX_WIDTH("fix_width")："限定宽度，高度自适应"<br>
		 * 5)VALUE_FIX_HEIGHT("fix_height")："限定高度，宽度自适应"<br>
		 * 6)VALUE_FIX_BOTH("fix_both")："固定宽度和高度"<br>
		 * 7)VALUE_FIX_SCALE("fix_scale")："等比例缩放"<br>
		 * 8)VALUE_SQUARE("square")："方块图，固定高固定宽"<br>
		 * 
		 * @see 参数举例：http://wiki.upyun.com/index.php?title=缩略图方式差别举例
		 */
		KEY_X_GMKERL_TYPE("x-gmkerl-type"),

		/**
		 * 缩略图参数值
		 * <p>
		 * 说明：该参数必须搭配 KEY_X_GMKERL_TYPE 使用，否则无效。具体的值需要根据 KEY_X_GMKERL_TYPE 而定。
		 */
		KEY_X_GMKERL_VALUE("x-gmkerl-value"),

		/**
		 * 缩略图质量：图片压缩质量，默认 95
		 * <p>
		 * 使用场景：用户上传高保真图片，但自身业务又无需太高质量的图片时，可以设置该参数减少文件保存的大小，从而减少空间的使用量。
		 * <p>
		 * 说明：使用该参数后将不保存原图，切忌。
		 */
		KEY_X_GMKERL_QUALITY("x-gmkerl-quality"),

		/**
		 * 图片锐化：默认锐化（true）
		 * <p>
		 * 使用场景：图片处理后质量太差，可以使用该参数模糊边缘，提高图片的清晰度或者焦距程度，使图片特定区域的色彩更加鲜明。
		 * <p>
		 * 说明：锐化不是万能的，很容易使图片不真实；另外，也无法通过锐化达到原图的效果。
		 */
		KEY_X_GMKERL_UNSHARP("x-gmkerl-unsharp"),

		/**
		 * 缩略图版本
		 * <p>
		 * 使用场景：快速处理原图，生成自定义的缩略图。
		 * <p>
		 * 说明：使用该参数前需要创建好缩略图版本号；另外，使用该参数后将不保存原图，切忌。
		 * 
		 * @see http://wiki.upyun.com/index.php?title=如何创建自定义缩略图
		 */
		KEY_X_GMKERL_THUMBNAIL("x-gmkerl-thumbnail"),

		/**
		 * 图片旋转
		 * <p>
		 * 使用场景：待上传的图片若是倾斜的，使用该参数可以直接进行强制的或自动的扶正。
		 * <p>
		 * 说明：只接受"auto"，"90"，"180"，"270"四种参数，其中"auto"参数根据图片 EXIF
		 * 中的信息进行自动扶正，若图片没有 EXIF 信息，则该参数无效。另外，使用该参数后将不保存原图，切忌。
		 * 
		 * @see http://wiki.upyun.com/index.php?title=图片旋转
		 */
		KEY_X_GMKERL_ROTATE("x-gmkerl-rotate"),

		/**
		 * 图片裁剪
		 * <p>
		 * 使用场景：只需要保存待上传图片的某一个部分，比如用户上传头像图片进行裁剪。
		 * <p>
		 * 说明：参数格式为x,y,width,height，且需要满足 x >= 0 && y >=0 && width > 0 && height
		 * > 0
		 * 
		 * @see http://wiki.upyun.com/index.php?title=图片裁剪
		 */
		KEY_X_GMKERL_CROP("x-gmkerl-crop"),

		/**
		 * 是否保留exif信息
		 * <p>
		 * 使用场景：对于原图包含EXIF信息，在上传图片时又进行了“破坏性处理”（比如裁剪、缩略、自定义版本等），
		 * upyun默认会删除原图的EXIF信息。 此时搭配该参数可以保留原图的EXIF信息。比如旅游应用从缩略图中获取具体的地理信息。
		 * <p>
		 * 说明：仅搭配"破坏性处理"的参数使用时有效，其他处理均无效；另外key对应的值仅设置为"true"时有效；
		 */
		KEY_X_GMKERL_EXIF_SWITCH("x-gmkerl-exif-switch"),

		/**
		 * 创建目录
		 * <p>
		 * 说明：SDK内部使用
		 */
		KEY_MAKE_DIR("folder"),

		/**
		 * 缩略图类型之 "限定最长边，短边自适应"，参数为像素值，如: 150
		 */
		VALUE_FIX_MAX("fix_max"),
		/**
		 * 缩略图类型之 "限定最短边，长边自适应"，参数为像素值，如: 150
		 */
		VALUE_FIX_MIN("fix_min"),
		/**
		 * 缩略图类型之 "限定宽度和高度"，参数为像素值，如: 150x130
		 */
		VALUE_FIX_WIDTH_OR_HEIGHT("fix_width_or_height"),
		/**
		 * 缩略图类型之 "限定宽度，高度自适应"，参数为像素值，如: 150
		 */
		VALUE_FIX_WIDTH("fix_width"),
		/**
		 * 缩略图类型之 "限定高度，宽度自适应"，参数为像素值，如: 150
		 */
		VALUE_FIX_HEIGHT("fix_height"),
		/**
		 * 缩略图类型之 "方块图，固定高固定宽"，参数为像素值，如: 150
		 */
		VALUE_SQUARE("square"),
		/**
		 * 缩略图类型之 "固定宽度和高度"，参数为像素值，如: 150x130
		 */
		VALUE_FIX_BOTH("fix_both"),
		/**
		 * 缩略图类型之 "等比例缩放"，参数为比例值（1-99），如: 50
		 */
		VALUE_FIX_SCALE("fix_scale"),

		/**
		 * 图片旋转之 "自动扶正"
		 */
		VALUE_ROTATE_AUTO("auto"),
		/**
		 * 图片旋转之 "旋转90度"
		 */
		VALUE_ROTATE_90("90"),
		/**
		 * 图片旋转之 "旋转180度"
		 */
		VALUE_ROTATE_180("180"),
		/**
		 * 图片旋转之 "旋转270度"
		 */
		VALUE_ROTATE_270("270");

		private final String value;

		private PARAMS(String val) {
			value = val;
		}

		public String getValue() {
			return value;
		}
	}
}
