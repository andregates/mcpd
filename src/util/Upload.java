package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.Part;

import models.Praga;

/**
 * Classe utilitária para upload de arquivos
 */
public class Upload {
	private static final Upload INSTANCE = new Upload();
	private int cont = 1;
	private List<String> images;
	List<String> fileNameTemp;
	String filePath = "C:\\teste";
	private Praga p;

	@PostConstruct
	public void init() {
		p = new Praga();

		for (int i = 1; i <= 5; i++)
			images.add(filePath + File.separator + p.getPragaId() + "_" + i);
	}

	public List<String> write(Part files, List<String> fileName) throws IOException {

		List<String> paths = new ArrayList<>();

		String name = extractFileName(files);

		File fileSaveDir = new File(filePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}
		
		String path = null;
		
		if(cont <=5) 
			path = filePath + File.separator + name + ++cont;
		
		files.write(path);

		paths.add(path);

		return paths;

	}

	/*
	 * public void writeTemp(Part part) throws IOException {
	 * 
	 * String fileName = extractFileName(part);
	 * 
	 * File fileSaveDir = new File(filePath); if (!fileSaveDir.exists()) {
	 * fileSaveDir.mkdir(); }
	 * 
	 * fileNameTemp = new ArrayList<String>();
	 * 
	 * if(fileNameTemp.size() < 5) fileNameTemp.add(fileName);
	 * 
	 * }
	 */

	public String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
	}

	public static Upload getInstance() {
		return INSTANCE;
	}

	public int getCont() {
		return cont;
	}

	public void setCont(int cont) {
		this.cont = cont;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
