```
#生成命令
keytool -genkeypair -keysize 1024 -validity 3650 -alias "privateKey" -keystore "privateKeys.keystore" -storepass "ehl1234" -keypass "Ehl@1234_pri" -dname "CN=localhost, OU=localhost, O=localhost, L=SH, ST=SH, C=CN"
```

```
#导出命令
keytool -exportcert -alias "privateKey" -keystore "privateKeys.keystore" -storepass "ehl1234" -file "certfile.cer"
```

```
#导入命令
keytool -import -alias "publicCert" -file "certfile.cer" -keystore "publicCerts.keystore" -storepass "ehl1234"
```