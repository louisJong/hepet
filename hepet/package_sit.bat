call svn up
set M2_HOME=D:\Program\apache-maven-3.0.4\
echo % M2_HOME%
call mvn clean:clean
call mvn package -Dmaven.test.skip=true -Psite
echo 生产环境打包结束
@pause
