# 前台、后台系统调用接口

**此项目启动必备工具：jdk、mysql、nacos、redis、maven、nginx**

①下载工程项目

方式一：git clone https://github.com/xxxccc1986/Online_Education.git

方式二：右上角绿色标志Code --> Download Zip  -->  解压到对应的地方

②打开此工程项目安装maven依赖

- 工程项目中使用到了阿里巴巴一些尚未开源的依赖，需要手动下载依赖install到本地的maven仓库中

③启动nacos、redis、nginx

- 注意：

  1.各个工程中的appplication.yml的nacos、redis地址必须和这里的一致

  2.nginx需要设置好反向代理

④按需求启动子模块

- 全部启动建议按照下方的顺序启动

gateway -- acl -- oss -- vod --usercenter -- statistic -- edu -- comment -- order -- cms -- msm
