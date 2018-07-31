mybatis的xml解析例子，以后可以使用XPathParser类进行解析


下面列出了最有用的路径表达式：
表达式	描述
nodename	选取此节点的所有子节点。
/	从根节点选取。
//	从匹配选择的当前节点选择文档中的节点，而不考虑它们的位置。
.	选取当前节点。
..	选取当前节点的父节点。
@	选取属性。
XPath 通配符可用来选取未知的 XML 元素。
通配符	描述
*	匹配任何元素节点。
@*	匹配任何属性节点。
node()	匹配任何类型的节点。


参考例子
EntityResolver XMLMapperEntityResolver
org/apache/ibatis/builder/xml/mybatis-3-config.dtd
org/apache/ibatis/builder/xml/mybatis-3-mapper.dtd

https://blog.csdn.net/q547550831/article/details/50541424

待解析的xml的头文件要加上如下内容：
内部DTD文档
<!DOCTYPE 根元素 [定义内容]>
外部DTD文档
<!DOCTYPE 根元素 SYSTEM "DTD文件路径">
内外部DTD文档结合
<!DOCTYPE 根元素 SYSTEM "DTD文件路径" [
定义内容
]>
当引用的文件在本地时，采用如下方式：
<!DOCTYPE 文档根结点 SYSTEM "DTD文件的URL">
例如： <!DOCTYPE 书架 SYSTEM “book.dtd”>
当引用的文件是一个公共的文件时，采用如下方式：
<!DOCTYPE 文档根结点 PUBLIC "DTD名称" "DTD文件的URL">
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-config.dtd">

dtd文件语法
DTD元素
基本语法
    <!ELEMENT NAME CONTENT>
NAME：元素名称。
CONTENT：元素类型，有四种，都必须大写。
    1、EMPTY－该元素不能包含子元素和文本，但可以有属性（空元素）
    2、ANY－该元素可以包含任何在DTD中定义的元素内容
    3、#PCDATA－可以包含任何字符数据，但是不能在其中包含任何子元素
    4、其它类型(组合)，可以是子元素，子元素与修饰符组合，基本元素与子元素与修饰符组合。
案例：
<!ELEMENT 班级 (学生+,作者)>
<!ELEMENT 学生 (名字,年龄,介绍)>
<!ELEMENT 作者 (#PCDATA)>
<!ELEMENT 名字 (#PCDATA)>
<!ELEMENT 年龄 (#PCDATA)>
<!ELEMENT 介绍 (#PCDATA)>