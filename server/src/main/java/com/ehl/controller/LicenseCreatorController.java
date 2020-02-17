package com.ehl.controller;

import com.ehl.core.LicenseCreator;
import com.ehl.core.LicenseCreatorParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/license")
public class LicenseCreatorController {
    @Autowired
    private LicenseCreator licenseCreator;
    /**
     * 生成证书
     * @param param 生成证书需要的参数，如：{"subject":"ccx-models","privateAlias":"privateKey","keyPass":"5T7Zz5Y0dJFcqTxvzkH5LDGJJSGMzQ","storePass":"3538cef8e7","licensePath":"C:/Users/zifangsky/Desktop/license.lic","privateKeysStorePath":"C:/Users/zifangsky/Desktop/privateKeys.keystore","issuedTime":"2018-04-26 14:48:12","expiryTime":"2018-12-31 00:00:00","consumerType":"User","consumerAmount":1,"description":"这是证书描述信息","licenseCheckModel":{"ipAddress":["192.168.245.1","10.0.5.22"],"macAddress":["00-50-56-C0-00-01","50-7B-9D-F9-18-41"],"cpuSerial":"BFEBFBFF000406E3","mainBoardSerial":"L1HF65E00X9"}}
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping(value = "/generateLicense",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Map<String,Object> generateLicense(@RequestBody(required = true) LicenseCreatorParam param) {
        Map<String,Object> resultMap = new HashMap<>(2);

        boolean result = licenseCreator.generateLicense(param);

        if(result){
            resultMap.put("result","ok");
            resultMap.put("msg",param);
        }else{
            resultMap.put("result","error");
            resultMap.put("msg","证书文件生成失败！");
        }

        return resultMap;
    }
    @RequestMapping("/form/generateLicense")
    @ResponseBody
    public void generateLicense(@RequestParam String md5, @RequestParam String start, @RequestParam String end, HttpServletResponse response) throws Exception {
        java.io.BufferedInputStream bis = null;
        java.io.BufferedOutputStream bos = null;
        response.setCharacterEncoding("utf-8");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(MessageFormat.format("md5:{0};start:{1};end:{2}",md5,start,end));
        LicenseCreatorParam param = new LicenseCreatorParam();
        param.setCode(md5);
        param.setIssuedTime(format.parse(start));
        param.setExpiryTime(format.parse(end));
        String path = licenseCreator.generateLicenseForPath(param);

        if(!path.equals("")){
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition","attachment;filename=license.lic");
            bis = new BufferedInputStream(new FileInputStream(path));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bis.close();
            bos.close();
            new File(path).delete();
        }
    }

}
