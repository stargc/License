package com.ehl.heartbeat.support;

import com.ehl.heartbeat.tool.ServerInfoUtil;
import de.schlichtherle.license.*;
import de.schlichtherle.xml.GenericCertificate;
import org.springframework.util.Assert;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;


/**
 * 自定义LicenseManager，用于增加额外的服务器硬件信息校验
 */
public class CustomHeartBeatManager extends LicenseManager {

    //XML编码
    private static final String XML_CHARSET = "UTF-8";
    //默认BUFSIZE
    private static final int DEFAULT_BUFSIZE = 8 * 1024;

    public CustomHeartBeatManager(LicenseParam param) {
        super(param);
    }

    /**
     * 复写create方法
     */
    @Override
    protected synchronized byte[] create(
            LicenseContent content,
            LicenseNotary notary)
            throws Exception {
        initialize(content);
        this.validateCreate(content);
        final GenericCertificate certificate = notary.sign(content);
        return getPrivacyGuard().cert2key(certificate);
    }

    /**
     * 复写install方法，其中validate方法调用本类中的validate方法，校验IP地址、Mac地址等其他信息
     */
    @Override
    protected synchronized LicenseContent install(
            final byte[] key,
            final LicenseNotary notary)
            throws Exception {
        final GenericCertificate certificate = getPrivacyGuard().key2cert(key);

        notary.verify(certificate);
        final LicenseContent content = (LicenseContent)this.load(certificate.getEncoded());
        this.validate(content);
        setLicenseKey(key);
        setCertificate(certificate);
        return content;
    }
    public final synchronized LicenseContent install(
            final byte[] key)
            throws Exception {
        return install(key,getLicenseNotary());
    }

    /**
     * 复写verify方法，调用本类中的validate方法，校验IP地址、Mac地址等其他信息
     */
    @Override
    protected synchronized LicenseContent verify(final LicenseNotary notary)
            throws Exception {
        GenericCertificate certificate;

        final byte[] key = getLicenseKey();
        if (null == key){
            throw new NoLicenseInstalledException(getLicenseParam().getSubject());
        }

        certificate = getPrivacyGuard().key2cert(key);
        notary.verify(certificate);
        final LicenseContent content = (LicenseContent)this.load(certificate.getEncoded());
        this.validate(content);
        setCertificate(certificate);


        return content;
    }

    /**
     * 校验生成证书的参数信息
     */
    private synchronized void validateCreate(final LicenseContent content)
            throws LicenseContentException {

        final Date now = new Date();
        final Date notBefore = content.getNotBefore();
        final Date notAfter = content.getNotAfter();
        if (null != notAfter && now.after(notAfter)){
            throw new LicenseContentException("证书失效时间不能早于当前时间");
        }
        if (null != notBefore && null != notAfter && notAfter.before(notBefore)){
            throw new LicenseContentException("证书生效时间不能晚于证书失效时间");
        }
        final String consumerType = content.getConsumerType();
        if (null == consumerType){
            throw new LicenseContentException("用户类型不能为空");
        }
    }


    /**
     * 复写validate方法，增加IP地址、Mac地址等其他信息校验
     */
    @Override
    protected synchronized void validate(final LicenseContent content)
            throws LicenseContentException {
        //1. 首先调用父类的validate方法
        super.validate(content);

        //2. 然后校验自定义的License参数
        //License中可被允许的参数信息
        String code = (String) content.getExtra();
        //当前服务器真实的参数信息
        String  realCode = ServerInfoUtil.getServerInfo();

        Assert.notNull(realCode,"不能获取服务器硬件信息");
        Assert.notNull(code,"不能获取服务器硬件信息");
        Assert.isTrue(realCode.equals(code),"当前服务器没有授权");
    }

    public synchronized int getAfterDays() throws Exception{
        LicenseContent content = verify();
        //获取距离过期时间
//        Calendar c  = Calendar.getInstance();
//        c.set(Calendar.MONTH,6);
//        content.setNotAfter(c.getTime());
        int days = (int) ((content.getNotAfter().getTime() - new Date().getTime()) / (1000*3600*24)) + 1;
        return days;
    }


    /**
     * 重写XMLDecoder解析XML
     */
    private Object load(String encoded){
        BufferedInputStream inputStream = null;
        XMLDecoder decoder = null;
        try {
            inputStream = new BufferedInputStream(new ByteArrayInputStream(encoded.getBytes(XML_CHARSET)));
            decoder = new XMLDecoder(new BufferedInputStream(inputStream, DEFAULT_BUFSIZE),null,null);
            return decoder.readObject();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            try {
                if(decoder != null){
                    decoder.close();
                }
                if(inputStream != null){
                    inputStream.close();
                }
            } catch (Exception e) { }
        }

        return null;
    }

}
