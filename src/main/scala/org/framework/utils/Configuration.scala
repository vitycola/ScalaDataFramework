package org.framework.utils

import java.io.File

import com.typesafe.config.ConfigFactory
import pureconfig.loadConfigOrThrow

object Configuration {

  case class DataSourceConf(host: Option[String])

  case class ProcessConf(
                          id: String,
                          processPackage : String,
                          dataSources: Map[String, DataSourceConf],
                          inputTables: Map[String, TableConf],
                          outputTables: Map[String, OutputTableConf]
                        )

  case class TableConf(dataSource: String,
                       table: String,
                       schema: String,
                       query: Option[String],
                       select: Option[List[String]],
                       filter: Option[String],
                       topic: Option[String]
                      ) {
    val fullTableName = schema + "." + table
  }

  case class OutputTableConf(tableConf: TableConf,
                             mapColumns: Map[String, String],
                             overwrite: Boolean
                            ) {
    val fullTableName = tableConf.schema + "." + tableConf.table
  }

  def loadConf(confName: String): ProcessConf = {
    val file = new File(confName)
    val conf = ConfigFactory.parseFile(file)

    loadConfigOrThrow[ProcessConf](conf)
  }


}
