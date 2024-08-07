@echo off
echo.
echo JarでWebを実行
echo.

cd %~dp0
cd ../ebs-admin/target

set JAVA_OPTS=-Xms256m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m

java -jar %JAVA_OPTS% ebs-admin.jar

cd bin
pause