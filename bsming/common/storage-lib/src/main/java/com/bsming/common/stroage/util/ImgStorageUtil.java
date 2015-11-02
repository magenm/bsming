package com.bsming.common.stroage.util;

import java.io.IOException;
import java.util.Map;

public interface ImgStorageUtil {
	/**
	 * 
	 * @Title: ImgStorage
	 * @Description: 上传文件
	 * @param @param dir:文件存储目录路径，不包括文件名。以"/"分割,如"a/b"，若不存在,云端会自动创建
	 * @param @param yunfilename:上传云端后的文件名称。
	 * @param @param pic_file:待上传文件路径。
	 * @param @return
	 * @param @throws IOException
	 * @return String:返回存储后文件云端地址。
	 * @throws
	 * @Date 2014年3月27日 下午1:56:04
	 */
	String imgStorage(String dir,String yunFileName, String picFile) throws IOException;

	/**
	 * 
	 * @Title: ImgGmkerl
	 * @Description: 上传图片做缩略图
	 * @param @param dir:文件存储目录路径，不包括文件名。以"/"分割,如"a/b"，若不存在,云端会自动创建
	 * @param @param yunfilename:上传云端后的文件名称。
	 * @param @param pic_file:待上传文件路径。
	 * @param @param params:图片处理参数。
	 * @param @return
	 * @param @throws IOException
	 * @return String:返回存储后图片文件云端地址。
	 * @throws
	 * @Date 2014年3月27日 下午1:56:04
	 */
	String imgGmkerl(String dir,String yunFileName, String picFile, Map<String, String> params)
			throws IOException;

	/**
	 * 
	 * @Title: ImgRotate
	 * @Description: 只接受"auto"，"90"，"180"，"270"四种参数，其中"auto"参数根据图片 EXIF
	 * @param @param dir:文件存储目录路径，不包括文件名。以"/"分割,如"a/b"，若不存在,云端会自动创建
	 * @param @param yunfilename:上传云端后的文件名称。
	 * @param @param pic_file:待上传文件路径。
	 * @param @param params:图片处理参数。
	 * @param @return
	 * @param @throws IOException
	 * @return String:返回存储后图片文件云端地址。
	 * @throws
	 * @Date 2014年3月27日 下午1:56:04
	 */
	String imgRotate(String dir,String yunFileName, String picFile, String params)
			throws IOException;

	/**
	 * 
	 * @Title: ImgCrop
	 * @Description: 图片裁剪
	 * @param @param dir:文件存储目录路径，不包括文件名。以"/"分割,如"a/b"，若不存在,云端会自动创建
	 * @param @param yunfilename:上传云端后的文件名称。
	 * @param @param pic_file:待上传文件路径。
	 * @param @param params:裁剪大小，格式为"50,50,300,300".
	 * @param @return
	 * @param @throws IOException
	 * @return String:返回存储后图片文件云端地址。
	 * @throws
	 * @Date 2014年3月27日 下午2:02:35
	 */
	String imgCrop(String dir,String yunFileName, String picFile, String params)
			throws IOException;

}
