package com.idle.kb_i_dle_backend.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

public class FileDownload {
    public static void download(String fileUploadPath, String targetFilename,
                                HttpServletResponse response)
    {
        //서버에서 파일을 읽어서 1024바이트씩 잘라서 보낸다
        //데이터 전송시 소켓라이브러리 사용, 내부 버퍼 8k  정도


        FileInputStream fileInputStream=null;
        OutputStream out=null;
        try
        {
            String path = fileUploadPath+"/"+targetFilename;
            File file = new File(path); //서버에서 파일을 찾아 읽는다

            //클라이언트에게 응답을 한다. 보낼정보가 파일임을 알려야 한다
            response.setHeader("Content-Disposition", "attachment;filename="+
                    URLEncoder.encode(file.getName(), "UTF-8"));
            //파일의 종류가 동영상, 이미지, 텍스트 ... 그럼 바이너리로 읽어서 보내야 한다
            fileInputStream=new FileInputStream(path);
            //서버에 있는 파일을 읽기위한 객체

            out = response.getOutputStream(); //클라이언트로 출려가려고한다
            int read=0; //데이터를 실제 읽은 바이트수를 저장한다
            byte[] buffer = new byte[1024];
            read = fileInputStream.read(buffer); //buffer라는 변수로 데이터를 읽고 실제읽은 바이트후반환
            while(read!=-1)//더이상 읽을 데이터가 없으면 -1을 반환한다.
            {
                out.write(buffer, 0, read); //클라이언트로 전송한다
                read = fileInputStream.read(buffer);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if( fileInputStream!=null)
                    fileInputStream.close();
                if(out!=null)
                    out.close();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}





