# MyBatis 如果打开idea不展示md格式，则重新打开下idea即可,它可能累了

<a name="775f76b7"></a>
### 1、什么是 MyBatis？
MyBatis 是一款优秀的持久层框架，它支持自定义 SQL、存储过程以及高级映射。MyBatis 免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作。MyBatis 可以通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。<br />

<a name="0846df21"></a>
### ORM简介
在系统开发过程中，开发人员需要使用面向对象的思维实现业务逻辑，但设计数据库表或是操作数据库记录时，则需要通过关系型的思维方式考虑问题。应用程序与关系型数据库之间进行交互时，数据在对象和关系结构中的表、列、字段等之间进行转换。<br />使用JDBC开发步骤：

1. 加载JDBC驱动
1. 建立并获取数据库连接
1. 创建 JDBC Statements 对象
1. 设置SQL语句的传入参数
1. 执行SQL语句并获得查询结果
1. 对查询结果进行转换处理并将处理结果返回
1. 释放相关资源（关闭Connection，关闭Statement，关闭ResultSet）



```java
public static List<Map<String,Object>> queryForList(){
  Connection connection = null;  
  ResultSet rs = null;  
  PreparedStatement stmt = null;  
  List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();  
  try {  
  	//加载JDBC驱动  
    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();  
    String url = "";  
    String user = "";   
    String password = "";   
              
    //获取数据库连接  
    connection = DriverManager.getConnection(url,user,password);   
    String sql = "select * from userinfo where user_id = ? ";  
    //创建Statement对象（每一个Statement为一次数据库执行请求）  
		stmt = connection.prepareStatement(sql);  
    //设置传入参数  
    stmt.setString(1, "zhangsan");  
    //执行SQL语句  
    rs = stmt.executeQuery();  
    //处理查询结果（将查询结果转换成List<Map>格式）  
    ResultSetMetaData rsmd = rs.getMetaData();  
    int num = rsmd.getColumnCount();  
              
		while(rs.next()){  
			Map map = new HashMap();  
      for(int i = 0;i < num;i++){  
      	String columnName = rsmd.getColumnName(i+1);  
      	map.put(columnName,rs.getString(columnName));  
			}  
      resultList.add(map);  
    }  
	} catch (Exception e) {  
		e.printStackTrace();  
	} finally {  
    // 释放资源
		try {  
			//关闭结果集  
      if (rs != null) {  
				rs.close();  
        rs = null;  
			}  
			//关闭执行  
			if (stmt != null) {  
				stmt.close();  
				stmt = null;  
			}
      //关闭连接
			if (connection != null) {  
				connection.close();  
				connection = null;  
			}  
		} catch (SQLException e) {  
			e.printStackTrace();  
		}  
	}  
	return resultList;  
}
```

<br />上述步骤1~步骤 5以及步骤 7 在每次查询操作中都会出现，在保存、更新、删除等其他 数据库操作中也有类似的重复性代码。在实践中，为了提高代码的可维护性，可以将上述重复性代码封装到一个类似 DBUtils 的工具类中 。 步骤 6 中完成了关系模型到对象模型的转换，要使用比较通用的方式封装这种复杂的转换是比较困难的 。为了接口该问题ORM (Object Relational Mapping，对象-关系映射)框架应运而生 。如图所示， ORM 框架的主要功能就是根据映射配置文件，完成数据在对象模型与 关系模型之 间的映射，同时也屏蔽了上述重复的代码，只暴露简单的 API 供开发人员使用 。<br />
<br />![ORM.png](https://cdn.nlark.com/yuque/0/2020/png/399029/1589166842302-31ee035e-a666-49fa-8325-d89c592dfc41.png#align=left&display=inline&height=270&margin=%5Bobject%20Object%5D&name=ORM.png&originHeight=270&originWidth=1184&size=26796&status=done&style=none&width=1184)<br />实际生产环境中对系统的性能是有一定要求的，数据库作为系统中比较珍贵的资源， 极易成为整个系统的性能瓶颈，所以我们不能像上述 JDBC 操作那样简单粗暴地直接访问数据 库、直接关闭数据库连接。应用程序 一般需要通过集成缓存、数据源、数据库连接池等组件进 行优化，如果没有 ORM 框架的存在，就要求开发人员熟悉相关组件的 API 井手动编写集成相 关的代码，这就提高了开发难度并延长了开发周期。<br />很多ORM 框架都提供了集成第三方缓存、第三方数据源等组件的接口，而且这些接口都 是业界统一的，开发和运维人员可以通过简单的配置完成第三方组件的集成。 当系统需要更换 第三方组件时，只要选择支持该接口的组件并更新配置即可，这不仅提高了开发效率，而且提 高了系统的可维护性。
<a name="K2Bzs"></a>
### 持久化框架的分析与对比
<a name="NRr1f"></a>
#### Hibernate
> Hibernate：Hibernate是当前最流行的ORM框架之一，对JDBC提供了较为完整的封装。Hibernate的O/R Mapping实现了POJO 和数据库表之间的映射，以及SQL的自动生成和执行。

**优点：**

- Hibernate的DAO层开发比Mybatis简单，Mybatis需要维护SQL和结果映射<br />
- Hibernate对对象的维护和缓存要比Mybatis好，对增删改查的对象的维护要方便<br />
- Hibernate数据库移植性好。<br />
- Hibernate有更好的二级缓存机制，可以使用第三方缓存。Mybatis本身提供的缓存机制不佳。<br />

**缺点：**

- 学习难度较大，在设计O/R映射，在性能和对象模型之间如何权衡取得平衡，以及怎样用好Hibernate。<br />
- Hibernate不适合数据库模式不规范，约束不完整，需要大量复杂查询的系统。<br />
<a name="KF7h9"></a>
#### MyBatis
> Mybatis同样也是非常流行的ORM框架，主要着力点在于 POJO 与 SQL 之间的映射关系。然后通过映射配置文件，将SQL所需的参数，以及返回的结果字段映射到指定 POJO 。相对Hibernate“O/R”而言，Mybatis 是一种“Sql Mapping”的ORM实现。

**优点：**

- Mybatis直接在映射配置文件中编写待执行的原生 SQL 语句，提高执行效率<br />
- Mybatis更容易掌握。<br />
- 提供了数据库映射功能提供了对底层数据访问的封装<br />
- 提供了连接管理，缓存支持，事物管理<br />
- 提供了强大的动态 SQL功能，可以根据执行时传入的实际参数值拼凑出完整的、可执行的 SQL语句<br />
- SQL集中管理<br />

**缺点：**

- MyBatis是一种半ORM，工具支持较少<br />
- 需要手动写SQL<br />
- 配置文件多<br />
- 数据库移植性较差<br />



<a name="FElIN"></a>
### 2、MyBatis 整体架构
MyBatis 的整体架构分为三层， 分别是基础支持层、 核心处理层和接口层。<br />![MyBatis整体架构.png](https://cdn.nlark.com/yuque/0/2020/png/399029/1589166889193-ffbe15db-5096-447d-880f-63a9da6090b5.png#align=left&display=inline&height=1034&margin=%5Bobject%20Object%5D&name=MyBatis%E6%95%B4%E4%BD%93%E6%9E%B6%E6%9E%84.png&originHeight=1034&originWidth=1380&size=120994&status=done&style=none&width=1380)

- 接口层：提供给外部使用的接口API，开发人员通过这些API来操纵数据库。接口层接收到调用请求就会调用数据处理层来完成具体到数据处理。
- 核心处理层：负责具体的SQL查找、SQL解析、SQL执行和结果映射处理等。其主要目的是根据调用请求完成一次数据库操作。
- 基础支持层：包括最基础的功能支撑，包括连接管理，事务管理，配置加载和缓存处理，日志记录等。



<a name="15cb0983"></a>
### 3、MyBatis运行原理与分析
<a name="b383ea4d"></a>
#### 3.1 MyBatis运行流程
MyBatis的运行分为三大部分：

1. 读取配置文件缓存到Configuration对象中，创建SqlSessionFactory对象。
<br />Configuration 是 MyBatis 全局性的配置对象,在 MyBatis 初始化的过程中，所有配置信息会被解析成相应的对 象井记录到 Configuration对象中。
1. 通过SqlSessionFactory创建SqlSession。
1. SqlSession执行具体SQL查询。

![Mybatis运行流程.png](https://cdn.nlark.com/yuque/0/2020/png/399029/1589166925576-17f85a9e-d3a7-495e-ae3a-a15487d3ccf1.png#align=left&display=inline&height=3414&margin=%5Bobject%20Object%5D&name=Mybatis%E8%BF%90%E8%A1%8C%E6%B5%81%E7%A8%8B.png&originHeight=3414&originWidth=2583&size=898403&status=done&style=none&width=2583)
<a name="78OUZ"></a>
#### 3.2 MyBatis运行原理分享
<a name="WoGZg"></a>
##### 3.2.1 MyBatis配置文件
MyBatis中的配置文件主要有两个，分别是 [mybatis-config.xml](https://mybatis.org/mybatis-3/zh/configuration.html) 配置文件和[映射配置文件](https://mybatis.org/mybatis-3/zh/sqlmap-xml.html) 。现在主流的配置方式除了使用 XML 配置文件，[还会配合注解进行配置](https://blog.csdn.net/weixin_43791238/article/details/93527217)。在 MyBatis 初始化的过程中，除了会读取 mybatis-config.xml 配置文件以及映射配置文件，还会加载配直文件指定的类，处理类中的注解，创建一些配置对象，最终完成框架中各个模块的初始化。初始化入口是 SqlSessionFactoryBuilder.build()方法。

- XML配置文件：[https://mybatis.org/mybatis-3/zh/configuration.html](https://mybatis.org/mybatis-3/zh/configuration.html)<br />
- XML 映射文件：[https://mybatis.org/mybatis-3/zh/sqlmap-xml.html](https://mybatis.org/mybatis-3/zh/sqlmap-xml.html)<br />
<a name="N6RiS"></a>
##### 3.2.2 创建SqlSessionFacotry的过程
![DefaultSessionFactory.png](https://cdn.nlark.com/yuque/0/2020/png/399029/1589166961175-ad1f1497-7d80-4fc4-983b-3c8c1e2eb2aa.png#align=left&display=inline&height=471&margin=%5Bobject%20Object%5D&name=DefaultSessionFactory.png&originHeight=471&originWidth=780&size=14513&status=done&style=none&width=780)
<a name="3nYys"></a>
##### 3.2.3 创建SqlSession的过程
![SqlSession.png](https://cdn.nlark.com/yuque/0/2020/png/399029/1589166979267-2fed1aab-0359-4725-ac90-2dfad2c89e28.png#align=left&display=inline&height=475&margin=%5Bobject%20Object%5D&name=SqlSession.png&originHeight=475&originWidth=754&size=12438&status=done&style=none&width=754)
<a name="fRG38"></a>
##### 3.2.4 创建Mapper的过程
![Mapper.png](https://cdn.nlark.com/yuque/0/2020/png/399029/1589166998971-61bac094-278b-4831-80c7-6e6491c7a05f.png#align=left&display=inline&height=351&margin=%5Bobject%20Object%5D&name=Mapper.png&originHeight=351&originWidth=766&size=10349&status=done&style=none&width=766)<br />Binding模块<br />![MapperRegistry.png](https://cdn.nlark.com/yuque/0/2020/png/399029/1589167018650-ea66003b-dad6-4adf-8760-8927bfe187ae.png#align=left&display=inline&height=742&margin=%5Bobject%20Object%5D&name=MapperRegistry.png&originHeight=742&originWidth=718&size=51871&status=done&style=none&width=718)<br />MapperRegistry是 Mapper 接口及其对应的代理对象工厂MapperProxyFactory的注册中心。在 MyBatis 初始化的过程中，所有配置信息会被解析成相应的对 象井记录到 Configuration对象中。 这里关注 Configuration.mapperRegistry的字段，它记录当前使用的 MapperRegistry 对象。在 MyBatis 初始化过程中会读取映射配置文件以及 Mapper 接口中的注解信息 ，并调用MapperRegistry.addMapper()方法填充 MapperRegistry.knownMappers集合 ， 该集合的 key 是 Mapper 接口对应的 Class 对象， value 为 MapperProxyFactory 工厂对象，可以为 Mapper 接口创 建代理对象。<br />sqlSession.getMapper(XxxMapper,class)方法得到的实际 上是 MyBatis通过 JDK动态代理为 XxxMapper接口生成的代理对象 。
<a name="P2WDG"></a>
##### 3.2.5 代理类执行SQL的过程
MyBatis底层还是采用原生jdbc来对数据库进行操作的，只是通过 SqlSessionFactory，SqlSession Executor,StatementHandler，ParameterHandler,ResultSetHandler和TypeHandler等几个处理器封装了这些过程。<br />SQL 语句的执行涉及多个组件 ，其中比较重要的是 Executor、 StatementHandler、 ParameterHandler 和 ResultSetHandler。 Executor 主要负责维护一级缓存和二级缓存， 并提供事务管理的相关操作 ，它会将数据库相关操作委托给 StatementHandler完成。<br />StatementHandler 首先通过 ParameterHandler 完成 SQL 语句的实参绑定，然后通过 java.sql.Statement 对 象执行 SQL 语句并得到 结果集，最后通过 ResultSetHandler 完成结 果集的映射，得到结果对象并返回。<br />![sql执行流程.png](https://cdn.nlark.com/yuque/0/2020/png/399029/1589167037292-33bb0b9d-2ee3-4a02-bc09-3fe22608d409.png#align=left&display=inline&height=483&margin=%5Bobject%20Object%5D&name=sql%E6%89%A7%E8%A1%8C%E6%B5%81%E7%A8%8B.png&originHeight=483&originWidth=684&size=176223&status=done&style=none&width=684)
<a name="iyDFj"></a>
### 4 Mybatis源码结构
![Mybatis源码结构.png](https://cdn.nlark.com/yuque/0/2020/png/399029/1589167055217-6f3f223b-0cc6-4907-ae78-6ba75a97433f.png#align=left&display=inline&height=1723&margin=%5Bobject%20Object%5D&name=Mybatis%E6%BA%90%E7%A0%81%E7%BB%93%E6%9E%84.png&originHeight=1723&originWidth=1537&size=539388&status=done&style=none&width=1537)
