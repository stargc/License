```
# 生成命令
# validity：私钥的有效期多少天
# alias：私钥别称
# keystore: 指定私钥库文件的名称(生成在当前目录)
# storepass：指定私钥库的密码(获取keystore信息所需的密码) 
# keypass：指定别名条目的密码(私钥的密码) 
keytool -genkeypair -keysize 1024 -validity 3650 -alias "privateKey" -keystore "privateKeys.keystore" -storepass "ehl1234" -keypass "Ehl@1234_pri" -dname "CN=localhost, OU=localhost, O=localhost, L=SH, ST=SH, C=CN"
```

```
#导出命令
# alias：私钥别称
# keystore：指定私钥库的名称(在当前目录查找)
# storepass: 指定私钥库的密码
# file：证书名称
keytool -exportcert -alias "privateKey" -keystore "privateKeys.keystore" -storepass "ehl1234" -file "certfile.cer"
```

```
#导入命令
# alias：公钥别称
# file：证书名称
# keystore：公钥文件名称
# storepass：指定私钥库的密码
keytool -import -alias "publicCert" -file "certfile.cer" -keystore "publicCerts.keystore" -storepass "ehl1234"
```