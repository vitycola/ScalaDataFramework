package org.framework.datasources

import org.apache.spark.sql.SparkSession
import org.framework.exception.CommonException.{DataSourceNotImplementedError, HostNotDefinedError}
import org.framework.utils.Configuration.DataSourceConf

object DataSourceFactory {
  def apply(dataSource: String, conf: DataSourceConf)(implicit sparkSession: SparkSession): DataSource = {
    dataSource.toLowerCase() match {
      case "hdfs" => HDFSDataSource.instance(conf.host.getOrElse(throw  HostNotDefinedError()),sparkSession)
      case _ => throw DataSourceNotImplementedError()
    }
  }
}
