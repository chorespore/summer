package com.chao.summer;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;

public class AutoCode {
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        // 1、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("Chao");
        gc.setOpen(false);
        gc.setFileOverride(false); // 是否覆盖
        gc.setServiceName("%sService"); // 去Service的I前缀
        gc.setIdType(IdType.ASSIGN_ID);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);
        // 2、设置数据源
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://rock.chao.com:3306/summer?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("chao");
        dsc.setPassword("mysql2020!");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);
        // 3、包的配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("summer");
        pc.setParent("com.chao");
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setServiceImpl("service.impl");
        pc.setService("service");
        pc.setController("controller");
        mpg.setPackageInfo(pc);
        // 4、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("user"); // 设置要映射的表名
//        strategy.setInclude("blog_tags", "course", "links", "sys_settings", "user_record", "user_say"); // 设置要映射的表名
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true); // 自动lombok
        strategy.setLogicDeleteFieldName("deleted");
        // 自动填充配置
        TableFill gmtCreate = new TableFill("gmt_create", FieldFill.INSERT);
        TableFill gmtModified = new TableFill("gmt_modified", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(gmtCreate);
        tableFills.add(gmtModified);
        strategy.setTableFillList(tableFills);
        // 乐观锁
        strategy.setVersionFieldName("version");
        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);

        //指定自定义模板路径, 位置：/resources/templates/entity2.java.ftl(或者是.vm)
        //注意不要带上.ftl(或者是.vm), 会根据使用的模板引擎自动识别
        /*TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setController("templates/controller.java")
                .setService("templates/service.java")
                .setServiceImpl("templates/serviceImpl.java");
        mpg.setTemplate(templateConfig);
*/
        mpg.execute(); //执行
    }
}