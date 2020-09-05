package org.framework

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.framework.datasources.{DataSource, DataSourceFactory}
import org.framework.utils.Configuration.ProcessConf

abstract class AbstractProcess(processConf: ProcessConf)(implicit val sparkSession: SparkSession) {

  val inputTables = processConf.inputTables
  val outputTables = processConf.outputTables
  val dataSources = getDataSources(processConf)

  //Entrypoint for every process
  def init() = {

    write(process(read()))
  }


  def read(): Map[String, DataFrame] = {
    val tables: Map[String, DataFrame] = Map.empty

    val tablesMap = inputTables.foldLeft(tables) { (acc, i) =>
      acc + (i._1 -> dataSources(i._2.dataSource)
        .read(i._2.fullTableName)
        )
    }
    tablesMap
  }

  def process(inputTables: Map[String, DataFrame]): Map[String, DataFrame] {}

  def write(outputMap: Map[String, DataFrame]): Unit = {
    outputTables.foreach(table =>
      dataSources(table._2.tableConf.dataSource)
        .write(outputTables(table._1), outputMap(table._1), table._2.overwrite))
  }

  /** Method to instance all the DataSources that will be used
   *
   * @param conf: Process configuration
   * @return all the datasources: Map [dataSourceName, DataSource]
   */
  private[this] def getDataSources(conf: ProcessConf): Map[String, DataSource] = {
    val dataSources: Map[String, DataSource] = Map.empty
    conf.dataSources.foldLeft(dataSources) { (acc, i) => acc + (i._1 -> DataSourceFactory(i._1, i._2)) }
  }


}
