# 购物车小项目
# 总体架构
  # MVC 设计模式:<br>
  &nbsp;&nbsp;&nbsp;&nbsp; --Model:POJO <br>
  &nbsp;&nbsp;&nbsp;&nbsp; --Controller:Servlet <br>
  &nbsp;&nbsp;&nbsp;&nbsp; --View:JSP + EL + JSTL <br>
# 技术选型
数据库：MySQL <br>
数据源：C3P0 <br>
JDBC 工具：DBUtils <br>
事务解决方案：Filter + ThreadLocal <br>
Ajax 解决方案：jQuery + JavaScript + JSON + google-gson <br>
层之间解耦方案：工厂设计模式 <br>
# 难点分析
通用的分页解决方案 <br>
带查询条件的分页 <br>
使用 Filter + ThreadLocal 解决事务 <br>


  
