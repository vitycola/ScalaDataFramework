package org.framework


import org.apache.spark.sql.SparkSession
import org.framework.utils.Configuration

/**
 * @author ${user.name}
 */
object App {

  def main(args: Array[String]): Unit = {

    implicit val spark: SparkSession = SparkSession.builder()
      .master("local")//TODO:quitar
      .getOrCreate()

    val processConf = Configuration.loadConf("C:\\Users\\Solop\\OneDrive\\Documentos\\IntelliJProjects\\ScalaDataFramework\\src\\test\\resources\\exampleProcess.conf")

    val constructor = Class.forName(s"${processConf.processPackage}.${processConf.id}").getConstructors()(0)
    constructor.newInstance(processConf,spark).asInstanceOf[AbstractProcess].init()
  }

}

