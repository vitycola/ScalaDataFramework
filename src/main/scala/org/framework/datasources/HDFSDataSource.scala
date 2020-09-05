package org.framework.datasources

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.framework.utils.Configuration.OutputTableConf

class HDFSDataSource(val host: String, val sparkSession: SparkSession) extends DataSource {

  override def read(fullTableName: String): DataFrame = {
    sparkSession.read.parquet(s"hdfs://${host}/${fullTableName}")
  }

  override def write(tableConf: OutputTableConf, data: DataFrame, overwrite: Boolean): Unit = {
    if (overwrite) {
      data.write.mode("overwrite").saveAsTable(tableConf.fullTableName)
    } else {
      data.write.saveAsTable(tableConf.fullTableName)
    }
  }

  override def deleteData(tableName: String, key: Seq[String]): Unit = {
    //TODO: Implement
  }
}

object HDFSDataSource {
  private var _instance: HDFSDataSource = null

  def instance(host: String, sparkSession: SparkSession): HDFSDataSource = {
    if (_instance == null)
      _instance = new HDFSDataSource(host, sparkSession)
    _instance
  }
}