package com.ssafy.haru.model.dto;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileConverter {

	public static UploadFile storeFile(MultipartFile file, String folder) throws IOException{
		if(file.isEmpty()) return null;
		String originName = file.getOriginalFilename();
		String storeName = UUID.randomUUID()+"."+extractExt(originName);
		File saveFile = new File("/home/ubuntu/imgs"+folder, storeName);
//		file.transferTo();
		FileCopyUtils.copy(file.getBytes(), saveFile);
		return new UploadFile(originName, storeName);
	}
	
	private static String extractExt(String origin) {
        int pos = origin.lastIndexOf(".");
        return origin.substring(pos + 1);
    }
}
