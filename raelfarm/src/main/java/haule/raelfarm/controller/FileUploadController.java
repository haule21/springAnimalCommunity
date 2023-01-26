package haule.raelfarm.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;

@Controller
public class FileUploadController {
	
	@RequestMapping(value="/uploadSummernoteImageFile", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody 
	public JsonObject uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {
		JsonObject jsonObject = new JsonObject();
		
		
		String fileRoot = "C:\\summernote_image\\";	//저장될 파일 경로
		String folder_date = getFolder();
		File uploadPath = new File(fileRoot, folder_date);
		
		if(uploadPath.exists() == false) {
			if(uploadPath.mkdirs()) System.out.print("Succecss Create Folder");
			else System.out.print("Fail Create Folder");
		}
		
		String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자
				
		String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명
		
		File targetFile = new File(uploadPath +"\\"+ savedFileName);	
		
		try {
			InputStream fileStream = multipartFile.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장
			
			jsonObject.addProperty("url",  "/summernoteImage/"+folder_date.replace("\\", "/")+"/" + savedFileName);
			jsonObject.addProperty("responseCode", "success");
				
		} catch (IOException e) {
			FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
			jsonObject.addProperty("responseCode", "error");
			e.printStackTrace();
		}
		
		return jsonObject;
	}
	
	@RequestMapping(value="/deleteSummernoteImageFile", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody 
	public JsonObject deleteSummernoteImageFile(@RequestParam("file") String file) {
		JsonObject jsonObject = new JsonObject();
		String[] files = file.split("/");
		File deleteFile = new File("C:\\summernote_image\\"+files[4]+"\\"+files[5]+"\\"+files[6], files[7]);
		String filePath = "/summernoteImage/"+ files[4]+"/"+files[5]+"/"+files[6]+"/"+files[7];
		
		if(deleteFile.delete()) {
			jsonObject.addProperty("filePath", filePath.trim());
			jsonObject.addProperty("responseCode", "success");
		}
		else {
			jsonObject.addProperty("responseCode", "error");
		}
		return jsonObject;
	}
	
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);
	}
}
