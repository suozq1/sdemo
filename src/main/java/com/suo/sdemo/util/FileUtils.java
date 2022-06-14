package com.suo.sdemo.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import com.suo.sdemo.common.ErrorCode;
import com.suo.sdemo.common.exception.AppException;

/**
 * 操作文件工具类
 * @author 锁战强
 *
 */
public class FileUtils {
	
//	private static final Integer BUFFER_SIZE = 100 * 1024;//100KB
	/**
	 * 保存目标文件到指定位置
	 * @param srcFile
	 * @param destFile
	 * @throws IOException
	 */
	public static void write(MultipartFile srcFile, File destFile) throws IOException {
		if(srcFile == null || destFile == null) {
			throw new NullPointerException("srcFile or destFile cannot be null");
		}
		write(srcFile.getInputStream(),new FileOutputStream(destFile));
	}
	
	public static void write(MultipartFile srcFile,String destFile) throws IOException {
		File file = new File(destFile);
		write(srcFile, file);
	}
	
	public static void write(File file,OutputStream out) throws IOException {
		try(InputStream in = new FileInputStream(file)){
			write(in, out);
		}catch (Exception e) {
			throw new AppException(ErrorCode.FILE_READ_FAIL);
		}
	}
	/**
	 * 从in读入，写出到out
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static void write(InputStream in,OutputStream out) throws IOException {
//		try(InputStream bis = new BufferedInputStream(in);
//		    BufferedOutputStream bos = new BufferedOutputStream(out)){
//			byte[] buffer = new byte[1024];
//			while (bis.read(buffer)!=-1) {
//				bos.write(buffer);
//			}
//			bos.flush();
//	    } 
		try {
			IOUtils.copy(in, out);
		}finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
	}
	
	/**
	 * 获取扩展名
	 * @return
	 */
	public static String getExt(String fileName) {
		String ext = "";
		if(fileName == null || fileName.length()==0) {
			return ext;
		}
		String[] arr = fileName.split("\\.");
		if(arr.length > 1) {
			return arr[arr.length-1];
		}
		return ext;
	}
	
	public static byte[] getFileByte(File file) {
		byte[] buffer = null;
		if(!file.exists()){
			return buffer;
		}
        try(FileInputStream fis = new FileInputStream(file);
        	ByteArrayOutputStream bos = new ByteArrayOutputStream()){
        	byte[] b = new byte[1024*8];
            int len=-1;
            while ((len = fis.read(b)) != -1){
                bos.write(b, 0, len);
            }
            buffer = bos.toByteArray();
        }catch (IOException e){
            e.printStackTrace();
        }
        return buffer;
	}
	
	public static void main(String[] args) {
		String fileName = "suo.txt.mp4";
		System.out.println(getExt(fileName));
		
	}
}
