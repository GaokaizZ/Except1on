@echo 1. 生成器自带了测试数据库,运行start_db.bat后再运行gen user_info即可生成文件
@echo 2. 生成器的主要配置文件为generator.xml,里面修改数据库连接属性
@echo 3. template目录为代码生成器的模板目录,可自由调整模板的目录结构

@set classpath=%classpath%;.;.\bin;.\lib\freemarker.jar;.\lib\mysql-connector-java-5.0.5-bin.jar;.\lib\ojdbc6-11.2.0.2.0.jar;.\lib\rapid-generator-springrain.jar

@set PATH=%JAVA_HOME%\bin;%PATH%;
@java -server -Xms128m -Xmx384m cn.org.rapid_framework.generator.ext.CommandLine -DtemplateRootDir=template
@if errorlevel 1 (
@echo ----------------------------------------------
@echo   ****错误***: 请设置好JAVA_HOME环境变量再运行或者检查你的classpath路径
@pause
)

:end