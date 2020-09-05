package org.framework.datasources

import org.apache.spark.sql.DataFrame
import org.framework.utils.Configuration._

trait DataSource {

  def read(fullTableName: String): DataFrame

  def write(tableConf: OutputTableConf, data: DataFrame, overwrite: Boolean)

  def deleteData(tableName: String, key: Seq[String])

}

