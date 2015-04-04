package com;
import java.lang.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import oracle.sql.*;
import oracle.jdbc.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
//import org.apache.commons.fileupload.DiskFileUpload;
//import org.apache.commons.fileupload.FileItem;

public class UploadLayer extends BaseLayer{
	private final static String DOC_QUERY =	"select * from persons where person_id IN (select doctor_id from family_doctor)";
	private final static String PAT_QUERY = "select * from persons";
	private final static String NEW_RECORD = "INSERT into radiology_record VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final static int THUMB_SIZE = 75;
	private final static int REG_SIZE = 500;
	
	public UploadLayer() {}
	
	public void addRecord(Integer record_id,
		Integer doctor_id,
		Integer patient_id,
		Integer radiologist_id,
		String test_type,
		java.util.Date p_date,
		java.util.Date t_date,
		String diagnosis,
		String description)
	throws BaseLayerException
	{
		openConnection();
		
		try{
			java.sql.Date prescription_date = new java.sql.Date(p_date.getTime());
			java.sql.Date test_date = new java.sql.Date(t_date.getTime());
			PreparedStatement stmt = prepareStmt(NEW_RECORD);
			
			stmt.setInt(1, record_id);
			stmt.setInt(2, doctor_id);
			stmt.setInt(3, patient_id);
			stmt.setInt(4, radiologist_id);
			stmt.setString(5, test_type);
			stmt.setDate(6, prescription_date);
			stmt.setDate(7, test_date);
			stmt.setString(8, diagnosis);
			stmt.setString(9, description);
			
			stmt.executeUpdate();
		}catch(Exception ex){
			throw new BaseLayerException("Error with saving record: " + ex.getMessage(), ex);
		}
		
		closeConnection();
	}
	/*
	public void addImg(Integer image_id, Integer record_id, FileItem item)
	throws BaseLayerException
	{
		openConnection();
		
		try{
			InputStream instream = item.getInputStream();
			BufferedImage full = ImageIO.read(instream);
			BufferedImage reg = getSize(full, REG_SIZE);
			BufferedImage thumb = getSize(full, THUMB_SIZE);
			
			Statement stmt = getStmt();
			
			stmt.execute("INSERT INTO pacs_images VALUES("+record_id+","+image_id+",empty_blob(),empty_blob(),empty_blob())");
			ResultSet rset = stmt.executeQuery("SELECT * FROM pacs_images WHERE image_id = "+image_id+" FOR UPDATE");
			rset.next();
			
			BLOB thumbBlob = ((OracleResultSet)rset).getBLOB(3);
			OutputStream thumbOut = thumbBlob.getBinaryOutputStream();
			ImageIO.write(thumb, "jpg", thumbOut);
			thumbOut.close();
			
			BLOB regBlob = ((OracleResultSet)rset).getBLOB(4);
			OutputStream regOut = regBlob.getBinaryOutputStream();
			ImageIO.write(reg, "jpg", regOut);
			regOut.close();
			
			BLOB fullBlob = ((OracleResultSet)rset).getBLOB(5);
			OutputStream fullOut = fullBlob.getBinaryOutputStream();
			ImageIO.write(full, "jpg", fullOut);
			fullOut.close();
			
			instream.close();
			
			stmt.executeUpdate("commit");
		}catch(Exception ex){
			throw new BaseLayerException("Error with saving image: " + ex.getMessage(), ex);
		}
		
		closeConnection();
	}
	
	public boolean hasImage(Integer record_id)
	throws BaseLayerException
	{
		openConnection();
		
		ResultSet rset = null;
		Boolean valid = false;
		
		rset = GetQueryResult("SELECT * FROM pacs_images WHERE record_id = "+record_id);
		valid = (rset != null) ? true : false;
		
		closeConnection();
	}
	*/
	public ArrayList<Person> getDoctors()
	throws BaseLayerException
	{
		return getPersons(DOC_QUERY);
	}
	
	public ArrayList<Person> getPatients()
	throws BaseLayerException
	{
		return getPersons(PAT_QUERY);
	}
	
	private ArrayList<Person> getPersons(String query)
	throws BaseLayerException
	{
		ArrayList<Person> ret = new ArrayList<Person>();
		openConnection();
		ResultSet rs = GetQueryResult(query);
		
		try{
			while(rs.next())
			{
				ret.add(new Person(rs));
			}
		}catch(Exception ex){
			throw new BaseLayerException("Error with reading result set: " + ex.getMessage(), ex);
		}
		
		closeConnection();
		return ret;
	}
	/*
	private static BufferedImage shrink(BufferedImage image, int n)
	{
		int w = image.getWidth() / n;
		int h = image.getHeight() / n;

		BufferedImage shrunkImage =
			new BufferedImage(w, h, image.getType());

		for (int y=0; y < h; ++y)
			for (int x=0; x < w; ++x)
				shrunkImage.setRGB(x, y, image.getRGB(x*n, y*n));

		return shrunkImage;
    }
	
	private BufferedImage getSize(BufferedImage img, int size)
	{
		int max = Math.max(img.getWidth(), img.getHeight());
		
		if(size > max)
			return img;
			
		return shrink(img, max/size);
	}
	*/
}
