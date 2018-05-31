package kh.semi.trip.model.service;

import java.sql.Connection;
import java.util.ArrayList;


import kh.semi.trip.model.dao.TripRegistDao;
import kh.semi.trip.model.vo.Attachment;
import kh.semi.trip.model.vo.TripRegist;

import static kh.semi.common.JDBCTemplate.*;

public class TripRegistService {

	public int insertThumbnail(TripRegist tregist, ArrayList<Attachment> fileList) {
		Connection con = getConnection();
		int result = 0;
		
		int result1 = new TripRegistDao().insertThumbnailContent(con, tregist);
		
		if(result1 > 0){
			int trno = new TripRegistDao().selectCurrval(con);
			System.out.println(trno);
			
			for(int i = 0; i < fileList.size(); i++){
				fileList.get(i).setBno(trno);
			}
		}
		
		int result2 = new TripRegistDao().insertAttachment(con, fileList);
		
		if(result1 > 0 && result2 > 0){
			commit(con);
			result = 1;
		}else{
			rollback(con);
		}
		
		close(con);
		
		return result;
	}

}
