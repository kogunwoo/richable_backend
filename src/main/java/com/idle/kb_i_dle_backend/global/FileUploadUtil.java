package com.idle.kb_i_dle_backend.global;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class FileUploadUtil {



	public static String getFileName(String contextPath , String orifilename)
	{
		String filePath = contextPath ;
		int pos = orifilename.lastIndexOf(".");
		String ext = orifilename.substring(pos+1);
		String oriFile = orifilename.substring(0, pos);

		String filename = orifilename;

		File newFile = new File(filePath+"/" +filename);
		int i=1;
		while(newFile.exists())
		{

			filename = oriFile + "("+i+")." + ext;
			i++;
			newFile = new File(filePath+"/" +filename);

		}

		return filename;
	}

	public static String upload(String uploadDir, MultipartFile multipartFile) throws IOException {
		// 요기 수정: String이 아니라 Path 타입으로 설정
		Path uploadPath = Paths.get("C:/rich/richable_backend-dev/fileupload");

		// getFileName 메서드는 String 타입을 받으므로, toString()으로 변환
		String fileName = getFileName(uploadPath.toString(), multipartFile.getOriginalFilename());

		// 업로드 경로 확인용 절대 경로 출력
		System.out.println("Current upload path: " + uploadPath.toAbsolutePath());

		// 경로가 존재하지 않으면 디렉토리 생성
		if (!Files.exists(uploadPath)) {
			try {
				Files.createDirectories(uploadPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 파일 복사 및 저장
		try (InputStream inputStream = multipartFile.getInputStream()) {
			// Path 객체에서 resolve() 메서드 사용 가능
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Could not save image file: " + fileName, ioe);
		}

		return fileName;
	}


	public static void upload(String contextPath, List<MultipartFile> fileList,
							  List<String> fileNameList)
	{

		String filePath =  contextPath;

		//java.io.File
		File file = new File(filePath);
		if( !file.exists())
		{

			file.mkdir();
		}

		//System.out.println("filesize : " + fileList.size());

		if( fileList!=null && fileList.size()>0)
		{
			for(MultipartFile multipartFile : fileList)
			{
				if( multipartFile.getOriginalFilename().length()==0)//
					break;


				String orifilename = multipartFile.getOriginalFilename();
				String filename = getFileName(contextPath, orifilename);


				System.out.println("filename : " + filename);
				fileNameList.add(filename);


				String newFileName   = filePath + "/" + filename;
				try
				{
					System.out.println(newFileName);
					multipartFile.transferTo( new File(newFileName));

				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}

	}
}


