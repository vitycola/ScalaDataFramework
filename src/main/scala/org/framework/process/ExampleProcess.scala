package org.framework.process

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.framework.AbstractProcess
import org.framework.utils.Configuration.ProcessConf

class ExampleProcess(processConf: ProcessConf)(implicit sparkSession: SparkSession) extends AbstractProcess(processConf: ProcessConf){

  override def process(inputTables: Map[String, DataFrame]): Map[String, DataFrame] = {
    print("Do something")
    val outputMap : Map[String, DataFrame] = Map.empty
    outputMap
  }

}
