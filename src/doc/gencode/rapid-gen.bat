@echo 1. �������Դ��˲������ݿ�,����start_db.bat��������gen user_info���������ļ�
@echo 2. ����������Ҫ�����ļ�Ϊgenerator.xml,�����޸����ݿ���������
@echo 3. templateĿ¼Ϊ������������ģ��Ŀ¼,�����ɵ���ģ���Ŀ¼�ṹ

@set classpath=%classpath%;.;.\bin;.\lib\freemarker.jar;.\lib\mysql-connector-java-5.0.5-bin.jar;.\lib\ojdbc6-11.2.0.2.0.jar;.\lib\rapid-generator-springrain.jar

@set PATH=%JAVA_HOME%\bin;%PATH%;
@java -server -Xms128m -Xmx384m cn.org.rapid_framework.generator.ext.CommandLine -DtemplateRootDir=template
@if errorlevel 1 (
@echo ----------------------------------------------
@echo   ****����***: �����ú�JAVA_HOME�������������л��߼�����classpath·��
@pause
)

:end